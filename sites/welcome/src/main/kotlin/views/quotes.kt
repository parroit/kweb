package sites.welcome.views

import java.util.List
import kw.model.FieldTypeManager
import kw.model.PersistentHelper
import kw.server.Persister.PageList
import kw.views.*
import kw.views.ButtonFlavours.*


import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Property
import sites.welcome.*
import sites.welcome.model.*
import java.util.HashMap
import com.google.common.collect.Maps
import java.util.Map
import org.hibernate.mapping.MetaAttribute



fun editQuote(qt: Quote) = layout {"""



        ${btForm(
            submitTo=App.edit_quote(qt.code),
            clazz=WelcomeModel[javaClass<Quote>()],
            legend="Edit quote"
            ) {

            metaField(this["code"],qt) +
            metaField(this["header"],qt)+
            metaField(this["customer"],qt)+
            submit(Flavours("success")){"Save"}
        }}


"""}

fun metaField(property:Property,instance:Any)=
    btInput(name=property.getName().sure(),inputType="text",legend=property.header(),value=property.escape(instance))

fun quotes(qts: PageList<Quote>) = layout { """
   <p>
   ${button(App.new_quote.resolve(), Flavours("primary")){"create"}}
   </p>

   ${table<Quote>(qts,App.quotes_list,
        WelcomeModel[javaClass<Quote>(), "code"],
        WelcomeModel[javaClass<Quote>(), "header"],
        WelcomeModel[javaClass<Quote>(), "customer"],
        ButtonProperty<Quote>("code","Edit", {App.edit_quote(this.code)})
    )}




"""}