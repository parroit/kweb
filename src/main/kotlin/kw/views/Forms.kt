package kw.views

import kw.server.RoutesResolver
import org.hibernate.mapping.PersistentClass

public fun form(submitTo:String,id:String="",cmsClass:String="",content:()->String):String = """
    <form
        action="${submitTo}"
        method='post'
        ${if (!id.isEmpty()) "id='${id}'" else ""}
        ${if (!cmsClass.isEmpty()) "class='${cmsClass}'" else ""}>

        ${content()}
    </form>
"""
public fun form(submitTo:String,clazz:PersistentClass,id:String="",cmsClass:String="",content:PersistentClass.()->String):String =
    form(submitTo,id,cmsClass,{clazz.content()})


public fun input (name:String,var id:String?=null,value:String="",inputType:String="",cmsClass:String=""):String {
    if (id==null) id=name;
        return """
    <input
        name="${name}"
        id='${id}'
        ${if (!value.isEmpty()) "value='${value}'" else ""}
        type="${inputType}"
        ${if (!cmsClass.isEmpty()) "class='${cmsClass}'" else ""}/>

"""
}

