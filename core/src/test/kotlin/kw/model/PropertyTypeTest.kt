package kw.model

import sun.reflect.generics.reflectiveObjects.NotImplementedException
import org.junit.Test
import org.junit.Assert

class TestModel{
    public val age:Int=15
    public val weight:Int?=null
    public var name:String?=null
    public var surname:String="something"

}

//it appear reification is not ready to work
class propertyType1<TProperty>(property:TProperty){
    fun get()=javaClass<TProperty>()
}

//also with extension methods
fun <TProperty> TProperty?.propertyType2()=javaClass<TProperty>()

fun <TModel> propertyType(model:TModel,propertyName:String):Class<out Any?> {
    val clazz = model.javaClass
    return clazz.getMethod("get"+propertyName.capitalize())?.getReturnType().sure()
}


class PropertyTypeTest{
    val model=TestModel()

    Test
    fun returnTypeOnNonNullVar(){
        Assert.assertEquals(javaClass<String>(),propertyType(model,"surname"))
    }
    Test
    fun returnTypeOnNullVar(){
        Assert.assertEquals(javaClass<String>(),propertyType(model,"name"))
    }

    Test
    fun returnTypeOnNonNullVal(){
        //This does not work: we get a NoClassDefFoundError: for class "I"
        //val javaClass1 = javaClass<Int>()

        val javaClass1 = 1.javaClass    //this does return java.lang.Integer.TYPE. I'm expecting jet.Int
        Assert.assertEquals(javaClass1,propertyType(model,"age"))
    }
    Test
    fun returnTypeOnNullVal(){
        val javaClass1 = javaClass<Integer>()   //it appear that nullable vals get java.lang.Integer.class, whereas
                                                //non nullable ones get java.lang.Integer.TYPE. This is correct, because
                                                //primitives types could not hold null values.
        Assert.assertEquals(javaClass1,propertyType(model,"weight"))
    }



}
