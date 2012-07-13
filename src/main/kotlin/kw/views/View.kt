package kw.views

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.channel.ChannelFutureListener
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.ChannelPipeline
import org.jboss.netty.channel.ChannelPipelineFactory
import org.jboss.netty.channel.Channels
import org.jboss.netty.channel.ExceptionEvent
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.handler.codec.http.CookieDecoder
import org.jboss.netty.handler.codec.http.CookieEncoder
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.handler.codec.http.HttpContentCompressor
import org.jboss.netty.handler.codec.http.HttpHeaders
import org.jboss.netty.handler.codec.http.HttpHeaders.*
import org.jboss.netty.handler.codec.http.HttpHeaders.Names.*
import org.jboss.netty.handler.codec.http.HttpRequest
import org.jboss.netty.handler.codec.http.HttpRequestDecoder
import org.jboss.netty.handler.codec.http.HttpResponse
import org.jboss.netty.handler.codec.http.HttpResponseStatus.*
import org.jboss.netty.handler.codec.http.HttpResponse.*
import org.jboss.netty.handler.codec.http.HttpVersion.*
import org.jboss.netty.handler.codec.http.HttpResponseEncoder
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import org.jboss.netty.util.CharsetUtil
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper.ThreadController

import java.util.regex.Pattern
import kotlin.nullable.count
import kw.server.*
import org.jboss.netty.handler.codec.http.HttpHeaders
import java.util.Map
import java.util.Collection
import com.asual.lesscss.LessEngine
import java.io.File
import java.io.RandomAccessFile
import org.jboss.netty.channel.DefaultFileRegion
import kw.model.PersistentHelper
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Property
import kw.model.FieldTypeManager

fun RequestResponse.importJavascript(fileName:String)=
"<script src='${staticBase}/javascripts/${fileName}.js' type='text/javascript'></script>"
fun RequestResponse.importCss(fileName:String)=
"<link rel='stylesheet' href='${staticBase}/stylesheets/${fileName}.css'>"
fun RequestResponse.importLess(fileName:String)=
"<link rel='stylesheet' href='/stylesheets/${fileName}.css'>"

public class view(private val viewContent:RequestResponse.()->String):Renderer {
    public override fun render(requestResponse: RequestResponse){
        val content = requestResponse.viewContent()
        requestResponse.response.content= content
        requestResponse.response.setHeader(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}
public object routes

fun <TItem> Iterable<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}
fun <TItem> Collection<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}
fun <TItem> Array<TItem>.For(body:(TItem)->String):String{
    val b=StringBuilder()
    for (h in this) {
        b.append(body(h));
    }
    return b.toString().sure()
}
public trait Renderer{
    public fun render(requestResponse: RequestResponse)
}
public fun isRenderer(any:Any):Boolean=any is Renderer
public fun asRenderer(any:Any):Renderer=any as Renderer


public class less(private val filePath:String):Renderer {
    public override fun render(requestResponse: RequestResponse){
        val engine = LessEngine();

        val fileTarget = File(File("./public"), filePath).getAbsoluteFile()
        val fileSource = File(File("./less"), filePath.replace(".css", ".less")).getAbsoluteFile()

        if (!(fileTarget?.exists().sure()))
            engine.compile(fileSource,fileTarget)

        var raf = RandomAccessFile(fileTarget, "r")

        val fileLength = raf.length()

        requestResponse.response["Content-Length"] = fileLength

        requestResponse.channel.write(requestResponse.response)
        requestResponse.channel.write(DefaultFileRegion(raf.getChannel(), 0, fileLength))

    }
}
fun Property?.header(): String {
    if (this == null) return ""
    val metaAttribute = this.getMetaAttribute("header")
    if (metaAttribute == null)
    {
        val name = this.getName()
        if (name == null)
            return "&nbsp;"
        val result = StringBuilder()
        result.append(Character.toUpperCase(name[0]))
        for (c in name.substring(1)){
            if (c.isUpperCase())
                result.append(" ")
            result.append(c)
        }
        return result.toString().sure()
    }

    return metaAttribute.getValue().toString()
}
public fun PersistentHelper?.get<TModel>(clazz: Class<TModel>): PersistentClass {
    return model(clazz)
}
public fun PersistentHelper?.get<TModel>(clazz: Class<TModel>, propertyName: String): Property {
    return model(clazz).getProperty(propertyName).sure()
}
public fun PersistentClass.get(propertyName: String): Property {
    return this?.getProperty(propertyName).sure()
}

fun Property?.escape<TModel>(instance: TModel): String {
    if (this == null) return ""
    val clazz = javaClass<Escaper>()
    if (clazz.isAssignableFrom(this.javaClass))
        return clazz.cast(this)?.escapeProperty(instance).sure()

    FieldTypeManager.Registry.initialize()
    val valueClazz = instance.javaClass
    val getter = this?.getPropertyAccessor(valueClazz)?.getGetter(valueClazz, this?.getName())

    val typeMan = FieldTypeManager.Registry.findManager<Any>(getter?.getReturnType().sure() as Class<Any>).sure()
    return typeMan.toEngineValue(getter?.get(instance)).sure()
}
trait Escaper{
    public fun escapeProperty(instance: Any): String
}