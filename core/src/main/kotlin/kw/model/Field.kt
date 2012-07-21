package kw.model
import kw.model.FieldTypeManager


public class Field<FieldType>(
    val fieldName:String
    ,
    val clazz:Class<FieldType>
    ){
    val typeManager:FieldTypeManager<FieldType> =
        FieldTypeManager.Registry.findManager<FieldType>(clazz).sure()
}
