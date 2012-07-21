package kw.server
import kotlin.io.*
import java.net.InetSocketAddress
import java.util.LinkedList
import java.util.concurrent.Executors
import kw.views.Renderer
import kw.views.asRenderer
import kw.views.error
import kw.views.error404
import kw.views.isRenderer
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.handler.codec.http.HttpChunkAggregator
import org.jboss.netty.handler.codec.http.HttpHeaders
import org.jboss.netty.handler.codec.http.HttpRequestDecoder
import org.jboss.netty.handler.codec.http.HttpResponseEncoder
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.jboss.netty.handler.stream.ChunkedWriteHandler
import jet.modules.Module
import kotlin.modules.module
import org.jetbrains.jet.cli.jvm.K2JVMCompiler
import org.jetbrains.jet.cli.jvm.K2JVMCompilerArguments
import java.io.File
import org.junit.Test
import java.nio.charset.Charset
import kw.server.compile.CompileView

/**
* Author: parroit
* Created: 28/06/12 10.55
*/

public open class KApplication() {
    public var port: Int = 8080
    val processors = LinkedList<Processor>();
    public var not_found_layout: ((()->String)->Any)? = null

    public fun whenPost0(url:String,result:RequestResponse.()-> Renderer):RoutesResolver0{
        rest(url) {
            post {result()}
        }
        return RoutesResolver0(url)
    }
    public fun whenPost1<ParameterType>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver1<ParameterType>{
        rest(url) {
            post {result()}
        }
        return RoutesResolver1<ParameterType>(url)
    }

    public fun whenPost2<ParameterType1,ParameterType2>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver2<ParameterType1,ParameterType2>{
        rest(url) {
            post {result()}
        }
        return RoutesResolver2<ParameterType1,ParameterType2>(url)
    }
    public fun whenPost3<ParameterType1,ParameterType2,ParameterType3>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>{
        rest(url) {
            post {result()}
        }
        return RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>(url)
    }
    
   
    public fun whenGet0(url:String,result:RequestResponse.()-> Renderer):RoutesResolver0{
        rest(url) {
            get {result()}
        }
        return RoutesResolver0(url)
    }
    public fun whenGet1<ParameterType>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver1<ParameterType>{
        rest(url) {
            get {result()}
        }
        return RoutesResolver1<ParameterType>(url)
    }

    public fun whenGet2<ParameterType1,ParameterType2>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver2<ParameterType1,ParameterType2>{
        rest(url) {
            get {result()}
        }
        return RoutesResolver2<ParameterType1,ParameterType2>(url)
    }
    public fun whenGet3<ParameterType1,ParameterType2,ParameterType3>(url:String,result:RequestResponse.()-> Renderer):RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>{
        rest(url) {
            get {result()}
        }
        return RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>(url)
    }
    public fun redirect0(url:String,result:RequestResponse.()-> String):RoutesResolver0{
        rest(url) {
            GET {redirect(result())}
        }
        return RoutesResolver0(url)
    }


    public fun redirect1<ParameterType>(url:String,result:RequestResponse.()-> String):RoutesResolver1<ParameterType>{
        rest(url) {
            GET {redirect(result())}
        }
        return RoutesResolver1<ParameterType>(url)
    }

    public fun redirect2<ParameterType1,ParameterType2>(url:String,result:RequestResponse.()-> String):RoutesResolver2<ParameterType1,ParameterType2>{
        rest(url) {
            GET {redirect(result())}
        }
        return RoutesResolver2<ParameterType1,ParameterType2>(url)
    }
    public fun redirect3<ParameterType1,ParameterType2,ParameterType3>(url:String,result:RequestResponse.()-> String):RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>{
        rest(url) {
            GET {redirect(result())}
        }
        return RoutesResolver3<ParameterType1,ParameterType2,ParameterType3>(url)
    }
    


    var onError = {(t: Throwable)->error(t)}

    public var staticBase: String = "/public"

    public fun staticFiles(prefix: String = "/", directory: String): String {
        processors.add(StaticFileProcessor(prefix, directory))
        return prefix
    }


    public fun rest(prefix: String, config: RestBuilder.()->Any?): String {
        val builder = RestBuilder()
        (builder.bind(config))()
        processors.add(RestProcessor(prefix, builder))
        return prefix
    }

    class Renderer404(var url: String): kw.views.Renderer{
        public override fun render(requestResponse: RequestResponse) {
            if (not_found_layout != null)
            {
                val layout = not_found_layout.sure()
                val result = layout(error404(url))
                if (isRenderer(result ))
                {
                    asRenderer(result).render(requestResponse)

                } else
                {
                    requestResponse.response.content = result.toString()
                    requestResponse.response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
                }




            } else
            {
                requestResponse.response.content = error404(url)
                requestResponse.response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
            }

            requestResponse.response.setStatus(HttpResponseStatus.NOT_FOUND)


        }

    }

    public fun startDebug() {

       //TODO: make view compilation work smoothly
       /* val compiler=CompileView(File("sites/welcome/src/main/kweb-kotlin/views"))
        if (compiler.viewOutdated("layout"))
           //I did not manage how to compile views here.
           //for now, use CompileViewTest#compileCreateOutFile
           //in order to compile layout.html.kt

            compiler.recompile("layout")
        else
        {
            val dest = File("out/production/welcome/templates/namespace\$layout\$1.class")
            if (dest.exists())
                dest.delete()
            File("html/templates/namespace\$layout\$1.class").copyTo(dest)

            val dest2 = File("out/production/welcome/templates/namespace.class")
            if (dest2.exists())
                dest2.delete()
            File("html/templates/namespace.class").copyTo(dest2)


        }*/

        rest("(.*)") {
            get{Renderer404(urlArguments[0])}
            post{Renderer404(urlArguments[0])}
        }
        val bootstrap = ServerBootstrap(
                NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                )
        )
        bootstrap.configPipeline {
            addLast("decoder", HttpRequestDecoder())
            addLast("aggregator", HttpChunkAggregator(65536))

            addLast("encoder", HttpResponseEncoder())
            addLast("chunkedWriter", ChunkedWriteHandler())

            addLast("handler", HttpHandler(processors, staticBase, onError.sure()))

        }

        bootstrap.bind(InetSocketAddress(port))
    }
}
