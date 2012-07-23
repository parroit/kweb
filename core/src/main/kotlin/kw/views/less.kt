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

/**
    Allow
*/
public class less(private val filePath:String):Renderer {
    public override fun render(requestResponse: RequestResponse){
        val engine = LessEngine();

        val fileTarget = File(File("./public"), filePath).getAbsoluteFile()
        val fileSource = File(File("./less"), filePath.replace(".css", ".less")).getAbsoluteFile()

        if (!(fileTarget?.exists()!!))
            engine.compile(fileSource,fileTarget)

        var raf = RandomAccessFile(fileTarget, "r")
        val fileLength = raf.length()
        requestResponse.response["Content-Length"] = fileLength
        requestResponse.channel.write(requestResponse.response)
        requestResponse.channel.write(DefaultFileRegion(raf.getChannel(), 0, fileLength))

    }
}
