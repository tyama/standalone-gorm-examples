@Grab("org.grails:grails-datastore-gorm-mongodb:4.0.0.RELEASE")
@Grab("org.springframework:spring-expression:4.1.6.RELEASE")
@Grab('org.slf4j:slf4j-simple:1.7.12')
import grails.persistence.*
import grails.mongodb.geo.*
import grails.mongodb.bootstrap.*
import org.bson.types.ObjectId
import com.mongodb.BasicDBObject
 
def initializer = new MongoDbDataStoreSpringInitializer(City)
initializer.configure()
 
City.withTransaction {
    City.collection.remove(new BasicDBObject())
    City.saveAll( [ new City(name:"London", location: Point.valueOf( [-0.125487, 51.508515] ) ),
                    new City(name:"Paris", location: Point.valueOf( [2.352222, 48.856614] ) ),
                    new City(name:"New York", location: Point.valueOf( [-74.005973, 40.714353] ) ),
                    new City(name:"San Francisco", location: Point.valueOf( [-122.419416, 37.774929] ) ) ] )
 
}
 
def city = City.where { name == "London" }.find()
List<City> closest = City.findAllByLocationNear(city.location)
 
println "Closest city to London is: ${closest[1].name}"
 
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
