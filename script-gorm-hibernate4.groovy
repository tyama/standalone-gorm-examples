@Grab("org.grails:grails-datastore-gorm-hibernate4:3.1.4.RELEASE")
@Grab("com.h2database:h2:1.3.173")
@Grab("org.grails:grails-spring:2.5.0")
@Grab('org.slf4j:slf4j-simple:1.7.12')
import grails.orm.bootstrap.*
import grails.persistence.*
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.h2.Driver

def init = new HibernateDatastoreSpringInitializer(Person)
def dataSource = new DriverManagerDataSource("jdbc:h2:data;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE", 'sa', '')
init.configureForDataSource(dataSource)

@Entity
class Person {
    String name
}
Person.withNewSession {
    Person.list()*.delete(flush:true)

    new Person(name:'tyama').save()
    new Person(name:'tama').save()

    println Person.count()
    println Person.list()*.name
}
