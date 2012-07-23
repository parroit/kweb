package kw.server

import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import java.util.LinkedList
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.HttpResponseStatus.*
import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.jboss.netty.channel.ChannelFutureListener
import org.jboss.netty.channel.ChannelFuture
import org.jboss.netty.handler.codec.http.HttpHeaders
import org.jboss.netty.handler.codec.http.HttpMessage
import org.jboss.netty.handler.codec.spdy.SpdyHeaders.HttpNames
import java.nio.channels.Channels
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.nio.channels.Channel
import org.jboss.netty.channel.ExceptionEvent
import kw.views.Renderer
import org.jboss.netty.handler.codec.http.DefaultHttpMessage
import org.jboss.netty.handler.codec.http.DefaultHttpRequest
import java.io.StringWriter
import java.io.OutputStream
import java.io.StringReader
import java.io.OutputStreamWriter
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.nio.charset.Charset
import javax.xml.transform.URIResolver
import java.net.URI
import java.net.URLDecoder
import kw.server.RequestResponseBuilder


class Listener(val request:RequestResponse) :ChannelFutureListener {
    public override fun operationComplete(p0:ChannelFuture? ){
        //request.channel.disconnect()
        request.channel.close()

    }
}

class HttpHandler(
        val processors:LinkedList<Processor>,
        public val staticBase:String,
        public var onError:((Throwable)->String)
    ) : SimpleChannelUpstreamHandler() {

    public override fun exceptionCaught(ctx: ChannelHandlerContext?, e: ExceptionEvent?) {
        //super<SimpleChannelUpstreamHandler>.exceptionCaught(ctx, e)
        e?.getCause()?.printStackTrace()


    }
    fun messageReceived(ctx : ChannelHandlerContext, e : MessageEvent) {

        val request = RequestResponseBuilder().build(e,this)
        for(processor in processors) {
            if(processor.tryToProcess(request)) {
                /*val keepAlive=HttpHeaders.isKeepAlive(e.getMessage() as HttpMessage)
                if (keepAlive) {
                    // Add 'Content-Length' header only for a keep-alive connection.
                    request.response.setHeader(HttpHeaders.Names.CONTENT_LENGTH, request.response.getContent()?.readableBytes());
                    // Add keep alive header as per:
                    // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
                    request.response.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                }*/
                val message = e.getMessage()as DefaultHttpRequest
                //val content=message.getContent()

               //val buff=java.io.ByteArrayOutputStream()
                //content?.readBytes(buff,content?.readableBytes().sure())
                //val toString = buff.toString(Charset.defaultCharset()?.name())
                message.setChunked(false)
                //URLDecoder.decode(toString)

                val future=processor.write(request)
                //if (!keepAlive)
                future.addListener(ChannelFutureListener.CLOSE);



                return
            }
        }

        request.setError(NOT_FOUND)
    }





}
