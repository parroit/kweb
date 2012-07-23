package sites.welcome

import java.awt.Desktop
import java.net.URI
import kw.server.*
import kw.views.less
import kw.views.Renderer
import sites.welcome.controllers.editQuote as welcome_editQuote
import sites.welcome.controllers.saveQuote as welcome_saveQuote
import sites.welcome.controllers.index as welcome_index
import sites.welcome.controllers.newQuote as welcome_newQuote
import sites.welcome.controllers.quotes as welcome_quotes
import sites.welcome.views.*
import java.io.File
import kw.server.KApplication.Renderer404


public fun runSample(){
    Desktop.getDesktop()?.browse(URI.create("http://localhost:8080/quotes"))

    App.startDebug()
}

public fun main(args: Array<String>) {
    runSample()
}

object App:KApplication() {
    public  KApplication (){
        not_found_layout={url->layout(url)}
    }
    public var staticFiles:RoutesResolver1<Int> = RoutesResolver1<Int>(staticFiles("/public", File("./public").getAbsolutePath().sure()))

    public var root :RoutesResolver0=                   redirect0("/")                      {"/index/0"}
    public var root_index:RoutesResolver0 =             redirect0("/(\\d*)")                {"/index/${urlArguments[0]}"}

    public var less_files :RoutesResolver0 =            whenGet0("/stylesheets/(.*)")       {less(urlArguments[0])}
    public var index_digit:RoutesResolver1<Int> =       whenGet1("/index/(\\d*)")           {defaultWelcome(welcome_index(urlArguments[0]))}
    public var quotes_list:RoutesResolver1<Int> =       whenGet1("/quotes/(\\d*)")          {quotes(welcome_quotes(urlArguments[0].toInt()))}
    public var quotes_list_page0:RoutesResolver0 =      redirect0("/quotes")                {quotes_list(1)}
    public var edit_quote:RoutesResolver1<String> =     whenGet1("/edit-quote/(.*)")        {editQuote(welcome_editQuote(urlArguments[0]))}
    public var save_quote:RoutesResolver1<String> =     whenPost1("/edit-quote/(.*)")       {quotes(welcome_saveQuote(urlArguments[0]))}
    public var new_quote:RoutesResolver0 =              whenGet0("/new-quote")              {editQuote(welcome_newQuote())}

    //view to test request parameters
    public var show_request:RoutesResolver0 =       whenGet0("/show-request")           {showRequest(getArguments,postArguments)}
    public var show_request_post:RoutesResolver0 =       whenPost0("/show-request")           {showRequest(getArguments,postArguments)}
}
