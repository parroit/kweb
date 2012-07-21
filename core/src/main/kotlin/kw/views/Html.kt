package kw.views

import java.util.ArrayList
import java.util.HashMap


public trait Factory<T> {
    public fun create() : T?
    public fun doo() : T?=null
}

public abstract class Element(){
    public abstract fun string():String
}

public class TextElement(val text : String) : Element(){
    public override fun string()=text
}

public abstract class Tag(val name : String) : Element() {
    val children = ArrayList<Element>()
    val attributes = HashMap<String, String?>()

    public override fun string():String {
        val result=StringBuilder()
        result.append("<${name}")
        for (attr in attributes){
            result.append(" ${attr.key}='${attr.value}'")
        }
        result.append(">\r\n")
        for (c in children)
            result.append(c.string())

        result.append("</${name}>\r\n")
        return result.toString().sure()
    }

    var id : String?
        get() = attributes["id"]
        set(value) { attributes["id"] = value }
    var clazz : String?
        get() = attributes["class"]
        set(value) { attributes["class"] = value }
    var style : String?
        get() = attributes["style"]
        set(value) { attributes["style"] = value }

    protected fun initTag<T : Element>(tag:T,init : T.() -> Unit) : T {
       tag.init()
       children.add(tag)
       return tag
    }
}

public abstract class TagWithText(name : String) : Tag(name) {
    fun String.plus() {
        children.add(TextElement(this))
    }
}

public class HTML() : TagWithText("html") {
    class object : Factory<HTML> {
        override fun create() = HTML()
    }

    fun head(init : Head.() -> Unit) = initTag(Head(),init)

    fun body(init : Body.() -> Unit) = initTag(Body(),init)
}
public class HeadFactory: Factory<Head> {
    public override fun create() = Head()
}

public class Head() : TagWithText("head") {


    fun title(init : Title.() -> Unit) = initTag(Title(),init)
}

public class Title() : TagWithText("title")

public abstract class BodyTag(name : String) : TagWithText(name) {
    fun div(init : Div.() -> Unit) = initTag(Div(),init)
    fun b(init : B.() -> Unit) = initTag(B(),init)
    fun p(init : P.() -> Unit) = initTag(P(),init)
    fun h1(init : H1.() -> Unit) = initTag(H1(),init)
    fun h2(init : H2.() -> Unit) = initTag(H2(),init)
    fun h3(init : H3.() -> Unit) = initTag(H3(),init)
    fun h4(init : H4.() -> Unit) = initTag(H4(),init)
    fun h5(init : H5.() -> Unit) = initTag(H5(),init)
    fun h6(init : H6.() -> Unit) = initTag(H6(),init)
    fun li(init : LI.() -> Unit) = initTag(LI(),init)
    fun i(init : I.() -> Unit) = initTag(I(),init)
    fun img(init : IMG.() -> Unit) = initTag(IMG(),init)
    fun span(init : Span.() -> Unit) = initTag(Span(),init)
    fun ul(init : UL.() -> Unit) = initTag(UL(),init)
    fun a(href : String, init : A.() -> Unit) ={
        val aTag = initTag(A(),init)
        aTag.href = href
        aTag
    }
}

public class Body() : BodyTag("body") {
    class object : Factory<Body> {
        override fun create() = Body()
    }

}

public abstract class ContentContainerTag(tagName:String) : BodyTag(tagName) {


}


public class Span() : BodyTag("span")
public class Div() : BodyTag("div")
public class B() : BodyTag("b")
public class P() : BodyTag("p")
public class H1() : BodyTag("h1")
public class UL() : BodyTag("ul")
public class LI() : BodyTag("li")
public class H2() : BodyTag("h2")
public class H3() : BodyTag("h3")
public class H4() : BodyTag("h4")
public class H5() : BodyTag("h5")
public class H6() : BodyTag("h6")
public class I() : BodyTag("i")
public class IMG() : BodyTag("a") {
    public var src : String
        get() = attributes["src"] ?: ""
        set(value) { attributes["src"] = value }
}

public class A() : BodyTag("a") {
    public var href : String
        get() = attributes["href"] ?: ""
        set(value) { attributes["href"] = value }
}

public fun html(init : HTML.() -> Unit) : HTML {
    val html = HTML()
    html.init()
    return html
}


