@Grab('org.grails:gorm-mongodb-spring-boot:1.1.0.RELEASE')
import grails.persistence.*
import grails.mongodb.geo.*
import org.bson.types.ObjectId
import com.mongodb.BasicDBObject
import com.mongodb.Mongo
import org.springframework.http.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import static org.springframework.web.bind.annotation.RequestMethod.*

@RestController
class CityController {

    @RequestMapping(value="/", method = GET)
    List index() {
        City.list().collect { [name: it.name] }
    }

    @RequestMapping(value="/near/{cityName}", method = GET)
    ResponseEntity near(@PathVariable String cityName) {
        def city = City.where { name == cityName }.find()
        if(city) {
            List<City> closest = City.findAllByLocationNear(city.location)
            return new ResponseEntity([name: closest[1].name], HttpStatus.OK)
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Autowired
    Mongo mongo

    @PostConstruct
    void populateCities() {
        City.withTransaction{
            City.collection.remove(new BasicDBObject())
            City.saveAll( [ new City(name:"London", location: Point.valueOf( [-0.125487, 51.508515] ) ),
                            new City(name:"Paris", location: Point.valueOf( [2.352222, 48.856614] ) ),
                            new City(name:"New York", location: Point.valueOf( [-74.005973, 40.714353] ) ),
                            new City(name:"San Francisco", location: Point.valueOf( [-122.419416, 37.774929] ) ) ] )
        }
    }
}

@Entity
class City {
    ObjectId id
    String name
    Point location

    static constraints = {
        name blank:false
        location nullable:false
    }

    static mapping = {
        location geoIndex:'2dsphere'
    }
}

/* boot 1.2:
Error creating bean with name 'mappingMongoConverter' defined in class path resource 
[o/s/b/a/m/MongoDataAutoConfiguration.class]: 
Unsatisfied dependency expressed through constructor argument with index 1 of type 
[o.s.d.m.c.m.MongoMappingContext]
*/
@Bean
MongoMappingContext springDataMongoMappingContext() {
    return new MongoMappingContext()
}