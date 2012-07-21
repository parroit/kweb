
package kw.model

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.Session
import kotlin.nullable.toSortedSet
import org.hibernate.mapping.PersistentClass
import java.util.Map
import java.util.HashMap

public class ModelConfiguration:Configuration() {

}
public open class PersistentHelper(packageName:String,configurationResource:String) {

    val maps=HashMap<String,PersistentClass>()

    private val sessionFactory: SessionFactory={

        val configuration = Configuration()
        configuration.buildMapping()
        configuration.configure(configurationResource)

        configuration.addPackage(packageName)

        val factory = configuration.buildSessionFactory().sure()
        val mapsIterator = configuration.getClassMappings()
        while (mapsIterator?.hasNext().sure()){
            val next = mapsIterator?.next().sure()
            maps.put(next?.getClassName().sure(),next)
        }
        factory
    }();



    public fun model<TModel>(clazz:Class<TModel>):PersistentClass
    {
        val name = clazz.getName().sure()
        return maps[name].sure();
    }


    public fun OpenSession():Session
    {
        return sessionFactory.openSession().sure();
    }


}