package kw.server

import org.jboss.netty.handler.codec.http.HttpMethod
import java.io.File
import java.io.RandomAccessFile
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.channel.DefaultFileRegion
import org.jboss.netty.handler.codec.http.HttpVersion
import org.jboss.netty.handler.codec.http.HttpResponseStatus.*
import org.jboss.netty.channel.ChannelFuture

class StaticFileProcessor(val prefix: String, val root: String) : Processor {
    override fun tryToProcess(request: RequestResponse) : Boolean {
        if(request.path.startsWith(prefix)) {
            return request.processStaticFile()
        }
        return false;
    }
    override fun write(request: RequestResponse):ChannelFuture {
        val file = File(root + File.separator + request.path.substring(prefix.length))

        var raf = RandomAccessFile(file, "r")

        val fileLength = raf.length()

        request.response["Content-Length"] = fileLength

        request.channel.write(request.response)
        return request.channel.write(DefaultFileRegion(raf.getChannel(), 0, fileLength)).sure()

    }
    fun RequestResponse.processStaticFile() : Boolean {
        val file = File(root + File.separator + path.substring(prefix.length))
        if(!file.exists())
            return false;

        if (request.getMethod() != HttpMethod.GET) {
            setError(METHOD_NOT_ALLOWED);
            return false;
        }
        if (file.isHidden() || !file.isFile()) {
            setError(FORBIDDEN)
            return false;
        }
        return true
    }
}

