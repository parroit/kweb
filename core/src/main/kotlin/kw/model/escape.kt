package kw.views

import org.hibernate.mapping.Property
import org.hibernate.mapping.PersistentClass
import kw.model.FieldTypeManager
import kw.model.PersistentHelper


fun Property?.escape<TModel>(instance: TModel): String {
    if (this == null) return ""
    val clazz = javaClass<Escaper>()
    if (clazz.isAssignableFrom(this.javaClass))
        return clazz.cast(this)?.escapeProperty(instance).sure()

    FieldTypeManager.Registry.initialize()
    val valueClazz = instance.javaClass
    val getter = this?.getPropertyAccessor(valueClazz)?.getGetter(valueClazz, this?.getName())

    val typeMan = FieldTypeManager.Registry.findManager<Any>(getter?.getReturnType().sure() as Class<Any>).sure()
    return typeMan.toEngineValue(getter?.get(instance)).sure()
}
trait Escaper{
    public fun escapeProperty(instance: Any): String
}

fun Property?.header(): String {
    if (this == null) return ""
    val metaAttribute = this.getMetaAttribute("header")
    if (metaAttribute == null)
    {
        val name = this.getName()
        if (name == null)
            return "&nbsp;"
        val result = StringBuilder()
        result.append(Character.toUpperCase(name[0]))
        for (c in name.substring(1)){
            if (c.isUpperCase())
                result.append(" ")
            result.append(c)
        }
        return result.toString().sure()
    }

    return metaAttribute.getValue().toString()
}
public fun PersistentHelper?.get<TModel>(clazz: Class<TModel>): PersistentClass {
    return model(clazz)
}
public fun PersistentHelper?.get<TModel>(clazz: Class<TModel>, propertyName: String): Property {
    return model(clazz).getProperty(propertyName).sure()
}
public fun PersistentClass.get(propertyName: String): Property {
    return this.getProperty(propertyName).sure()
}