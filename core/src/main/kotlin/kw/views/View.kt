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



public class htmlView(private val viewContent:RequestResponse.()->String):Renderer {
    public override fun render(requestResponse: RequestResponse){
        val content = requestResponse.viewContent()
        requestResponse.response.content= """<!DOCTYPE html>
        <html lang="en">
        ${content}
        </html>"""
        requestResponse.response.setHeader(CONTENT_TYPE, "text/html; charset=UTF-8");
    }
}


public object routes

/* moved to For.kt
fun <TItem> Iterable<TItem>.For(body:(TItem)->String):String{

} */
public trait Renderer{
    public fun render(requestResponse: RequestResponse)
}
public fun isRenderer(any:Any):Boolean=any is Renderer
public fun asRenderer(any:Any):Renderer=any as Renderer


//moved to escape.kt
/*
fun Property?.escape<TModel>(instance: TModel): String {
  }
trait Escaper{

}*/
