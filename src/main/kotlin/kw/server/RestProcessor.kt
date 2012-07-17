package kw.server

import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.nio.channels.Channel
import org.jboss.netty.channel.ChannelFuture
import kw.views.Renderer
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import java.util.Map
import java.util.List

class RestBuilder() {
    var onGet  : (RequestResponse.()->Any?)? = null
    var onPost : (RequestResponse.()->Any?)? = null

    fun GET(handler: RequestResponse.()->Unit) {
        onGet = handler
    }

    fun POST(handler: RequestResponse.()->Unit) {
        onPost = handler
    }



    fun get(renderer: RequestResponse.()->    Renderer) {
        val handler:(RequestResponse.()->Unit)= {
            renderer().render(this)
        }
        onGet = handler
    }

    fun post(renderer: RequestResponse.()->    Renderer) {
        val handler:(RequestResponse.()->Unit)= {
            renderer().render(this)
        }
        onPost = handler
    }
}

class RestProcessor(val prefix: String, val builder: RestBuilder) : Processor {
    //splitted and moved to RequestResponseBuilder#build and RequestResponse#initUrlArguments
    /*private fun initUrlArguments(request: RequestResponse,val matcher:Matcher){

    }*/

    fun getMatches(request: RequestResponse) = request.request.getMethod() == HttpMethod.GET && builder.onGet != null
    fun postMatches(request: RequestResponse) = request.request.getMethod() == HttpMethod.POST && builder.onPost != null

    override fun write(request: RequestResponse) :ChannelFuture{


        if(getMatches(request)) {
            request.ok()
            request.(builder.onGet.sure())()
        }
        else if(postMatches(request)) {
            request.ok()
            request.(builder.onPost.sure())()
        }
        else {
            request.setError(HttpResponseStatus.METHOD_NOT_ALLOWED)
        }
        request.response["Content-Length"] = request.response.getContent()?.writableBytes()
        val write = request.channel.write(request.response).sure()

        return write
    }
    override fun tryToProcess(request: RequestResponse): Boolean {


        val pattern=Pattern.compile(prefix).sure()


        //strip params from request path in order to make get arguments work
        //on every url
        val path = if (request.path.contains("?"))
            request.path.subSequence(0,request.path.indexOf('?'))
        else
            request.path
        println(path)
        val matcher = pattern.matcher(path).sure()

        if(matcher.matches() && (postMatches(request)|| getMatches(request))) {


            request.initUrlArguments(matcher)
            return true;
        }
        return false
    }
}