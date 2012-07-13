<!DOCTYPE html>
<html lang="en">
package templates
import kw.views.*
fun layout(content:()->String) = view { """
<head>
    <title>Quotes list</title>
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
                            <span class="name brand">Amministrazione</span>

                            <ul class="nav">
                                <li class="active">
                                    <a href="#">Home</a>
                                </li>
                                <li><a href="#">Fatture</a></li>
                                <li><a href="#">Preventivi</a></li>
                                <li><a href="@routes.Aziende.list()">Aziende</a></li>
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
                        @if(session.containsKey("loggedUser")) {

                        <a class="pull-right" href="@routes.Application.logout()">&nbsp;Logout</a>
                        <span class="pull-right">Sei autenticato come @session.get("loggedUser") |&nbsp;</span>
                        } else {

                        <a class="pull-right" href="@routes.Application.login()">Login</a>
                        <span class="pull-right">Non sei autenticato. |&nbsp;</span>
                        }
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
"""}
</html>