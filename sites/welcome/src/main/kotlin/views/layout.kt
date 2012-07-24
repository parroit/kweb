package sites.welcome.views

import sites.welcome.*
import io.kool.template.html.*
import java.io.StringReader
import kotlin.dom.*
import org.w3c.dom.*
import kw.views.*
import org.cyberneko.html.parsers.DOMFragmentParser
import org.junit.Test
import org.xml.sax.InputSource

fun Node.unescaped(text: String) {


    val parser = DOMFragmentParser ();
    val fragment = ownerDocument().createDocumentFragment()!!;
    parser.parse(InputSource(StringReader(text)), fragment)


    if (this.nodeType == Node.ELEMENT_NODE) {
        var node = fragment.firstChild
        while (node != null)
        {
            val nextNode = node?.nextSibling
            appendChild(node)
            node = nextNode
        }
    }


}


fun layout(content: () -> String) = view {
    "<!DOCTYPE html>"+
	html {
        head{
            title("Kweb Kool template sample:Quotes list")
            unescaped(
                    importJavascript("jquery-1.7.1.min") +
                    importJavascript("bootstrap-alert") +
                    importJavascript("bootstrap-modal") +
                    importLess("bootstrap")
            )

        }
        body{
            div(klass="container"){
                div (id="header", klass="row"){
                    div (klass="span12")  {
                        div (klass="navbar navbar-fixed-top"){
                            div (klass="navbar-inner"){
                                div (klass="container"){

                                    img(klass="brand", src="/public/images/ammi.png")


                                    span(klass="name brand"){
                                        this+"Kweb sample:quotes/span"
                                    }

                                     ul (klass="nav"){
                                         li (klass="active"){a (href="/",text="Home")}
                                        /* li {a( href=App.quotes_list_page0(), text= "Quotes")}*/
                                         li {a( href="#",text="Bills")}
                                         li {a( href="#",text="Company")}
                                     }


                                }
                            }
                        }
                    }
                }
                div (id = "content", klass = "row"){
                    div (klass = "span12"){
                        unescaped(content())
                    }
                }

                div (id="footer"){

                    div (klass="navbar navbar-fixed-bottom"){
                        div (klass="navbar-inner"){
                            div (klass="container"){
                                this + "A wonderful world"
                            }
                        }
                    }

                }
            }
        }
    }.toXmlString()!!
}


