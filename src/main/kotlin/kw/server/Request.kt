package kw.server

import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import java.util.LinkedList
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.HttpResponseStatus.*
import org.jboss.netty.handler.codec.http.HttpRequest
import org.jboss.netty.channel.Channel
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.channel.ChannelFutureListener
import org.jboss.netty.handler.codec.http.HttpVersion
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import org.jboss.netty.handler.codec.http.HttpHeaders.*
import org.jboss.netty.handler.codec.http.HttpMessage
import org.jboss.netty.channel.ChannelFuture
import java.util.List

import java.util.ArrayList
import java.util.concurrent.Future
import org.jboss.netty.handler.codec.http.HttpResponse
import java.util.Map
import java.util.HashMap
import java.util.regex.Matcher

public class RequestResponse(e: MessageEvent,handler:HttpHandler) {
    public val request :HttpRequest  = e.getMessage() as HttpRequest
    public val response:HttpResponse = DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN)
    public val channel:Channel  = e.getChannel().sure()

    public val path  :String   = request.getUri().sure().sanitizeUri()
    public val staticBase:String=handler.staticBase
    public val urlArguments:List<String> = ArrayList()
    public var postArguments:Map<String?, List<String?>?> = HashMap<String?, List<String?>?>()
    public var getArguments:Map<String?, List<String?>?> = HashMap<String?, List<String?>?>()

    public fun initUrlArguments(val matcher:Matcher){
        urlArguments.clear()
        for (i in 1 .. matcher.groupCount()){
            urlArguments.add(matcher.group(i).toString())
        }
    }

    public fun setError(status: HttpResponseStatus?) {
        response.setStatus(status)
        response["Content-Type"] = "text/plain; charset=UTF-8"
        response.content = "Failure: " + status.toString() + "\r\n"
    }

    public fun redirect(path: String) {
        response.setStatus(HttpResponseStatus.MOVED_PERMANENTLY)
        response["Location"] = path
        response["Content-Length"] = 0
        channel.write(response)
    }

    public fun ok() : RequestResponse {
        response.setStatus(HttpResponseStatus.OK)
        return this
    }



}


fun String.sanitizeUri() : String {
    val path = decodeURI("UTF-8") ?: decodeURI("ISO-8859-1") ?: throw Error()

    val localizedPath = path.replace('/', File.separatorChar)

    return if (localizedPath.contains(File.separator + ".") ||
        localizedPath.contains("." + File.separator) ||
        localizedPath.startsWith(".") || localizedPath.endsWith("."))
            throw Error()
        else
            path
}

fun String.decodeURI(encoding : String) : String? {
    try {
        return URLDecoder.decode(this, encoding)
    }
    catch (e : UnsupportedEncodingException) {
        return null
    }
}

trait Processor {
    fun write(request: RequestResponse):ChannelFuture
    fun tryToProcess(request: RequestResponse) : Boolean
}

