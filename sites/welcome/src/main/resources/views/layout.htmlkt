<!DOCTYPE html>
<html lang="en">
package templates
import kw.views.*
import sites.welcome.*
fun layout(content:()->String) = htmlView { """
<head>
    <title>Kweb sample:Quotes list</title>
    ${importJavascript("jquery-1.7.1.min")}
    ${importJavascript("bootstrap-alert")}
    ${importJavascript("bootstrap-modal")}
    ${importLess("bootstrap")}

</head>
<body>
    <div class="container">
        <div id="header" class="row">
            <div class="span12">
                <div class="navbar navbar-fixed-top">
                    <div class="navbar-inner">
                        <div class="container">

                            <img class="brand" src='/public/images/ammi.png'/>
                            <span class="name brand">Kweb sample:quotes</span>

                            <ul class="nav">
                                <li class="active">
                                    <a href="#">Home</a>
                                </li>
                                <li><a href="#">Bills</a></li>
                                <li><a href="#">Company</a></li>

                               <li><a href='${"#"/*uncommenting this I get an unresolved error, Im investingating why! App.quotes_list_page0()*/}'>Quotes</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="content" class="row">
            <div class="span12">
                ${content()}
            </div>

        </div>

        <div id="footer">

            <div class="navbar navbar-fixed-bottom">
                <div class="navbar-inner">
                    <div class="container">
                        A wonderful world
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
"""}
</html>