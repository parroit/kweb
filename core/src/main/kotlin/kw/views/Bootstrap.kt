package kw.views

import java.util.List
import kw.model.FieldTypeManager
import kw.model.PersistentHelper
import kw.server.Persister.PageList
import kw.views.For
import kw.views.*
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Property
import java.util.HashMap
import com.google.common.collect.Maps
import java.util.Map
import org.hibernate.mapping.MetaAttribute
import kw.server.RoutesResolver1
import kw.views.ButtonFlavours
import kw.views.ButtonFlavours.Flavours

fun table<TItem>(rows: PageList<TItem>,pageRoutes:RoutesResolver1<Int>, vararg columns: Property) = """
   <table class="table table-bordered table-striped table-condensed">
    <thead>
    <tr>
        ${columns.For{ (cell: Property?)->
            "<th>${cell.header()}</th>"
        }}
    </tr>
    </thead>
    <tbody>
    ${rows.list.For{ (it: TItem)->"""
        <tr>
            ${columns.For{ (cell: Property?)->
                "<td>${cell.escape(it)}</td>"
            }}
        </tr>
     """}}
     </tbody>
   </table>

   <div class="pagination">
        <ul>

            <li><a href="${pageRoutes(1)}">First</a></li>

            ${if (rows.currentPage > 1)
                """<li><a href="${pageRoutes(rows.currentPage - 1)}">Prev</a></li>"""
            else
                """<li class="active">
                                <a href="${pageRoutes(1)}">Prev</a>
                               </li>"""
            }

            ${( rows.currentPage - 5..rows.currentPage + 5 ).For{(i: Int)->
                if (i >= 1 && i <= rows.totalPage)
                    """
                        <li ${if (i == rows.currentPage) "class=\"active\"" else ""} >
                            <a href="${pageRoutes(i)}">${i}</a>
                        </li>

                    """
                else
                    "<li class='active'> <a href='${rows.currentPage}'>&nbsp</a></li> "
            }}

            ${if (rows.currentPage < rows.totalPage)
                """<li><a href="${pageRoutes(rows.currentPage + 1)}">Next</a></li>"""
            else
                """<li class="active"><a href="${pageRoutes(rows.totalPage)}">Next</a></li>"""
            }
            <li><a href="${pageRoutes(rows.totalPage)}">Last</a></li>
        </ul>
    </div>
"""

fun Flavours?.descrFlavour(): String {
    return if (this == null)
        ""
    else
    {
        var btn: Flavours = this
        "btn-" + btn.descr
    }
}
fun submit(flavour: Flavours? = null, content: ()->String) = """
    <button class="btn btn-large ${flavour.descrFlavour()}" type="submit"> ${content()}</button>
"""

fun button(value: String, flavour: Flavours? = null) = """
    <input class="btn ${flavour.descrFlavour()}" type="button" value="${value}"/>
"""

fun button(href: String, flavour: Flavours? = null, content: ()->String) = """
    <a class="btn ${flavour.descrFlavour()}" href="${href}"> ${content()}</a>
"""

public fun btForm(submitTo:String,clazz:PersistentClass,id:String="",legend:String="",content:PersistentClass.()->String):String =
    form(submitTo,clazz,id,"well form-horizontal"){"""
        <legend>${legend}</legend>
        <fieldset>
            ${clazz.content()}
        </fieldset>
    """}
public fun btInput (name:String,var id:String?=null,value:String="",inputType:String="",legend:String="",helpBlock:(()->String)?=null):String = """
    <div class="control-group">
        <label class="control-label" for="${id}">${legend}</label>
        <div class="controls">
            ${input(name,id,value,inputType)}
            <p class="help-block">${if(helpBlock!=null) helpBlock.invoke() else ""}</p>
        </div>
    </div>
"""
class ButtonProperty<TModel>(val name:String,val text: String, val editRoute: TModel.()->String): Property(), Escaper{
    public override fun escapeProperty(instance: Any): String {
        return button((instance as TModel).editRoute(), ButtonFlavours.Primary){text}
    }

    {
        var map :Map<String,MetaAttribute>? = getMetaAttributes() as Map<String,MetaAttribute>?
        if (map==null){
            map=HashMap<String,MetaAttribute>()
            setMetaAttributes(map)
        }

        val attr=MetaAttribute("header")
        attr.addValue("&nbsp;")
        map?.put(attr.getName().sure(),attr)
        setName(name)
    }

}