package kw.server

import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import java.util.List
import java.util.Map
import kotlin.template.HtmlFormatter



class RequestResponseBuilder(){
    fun initPostArguments(request: RequestResponse) {
        val content = request?.request?.getContent().sure();

        if (content.readable().sure()) {
            val param = content.toString("UTF-8");
            request.postArguments =decodeAndSanitize("/?" + param)
        }
    }

    fun initGetArguments(request: RequestResponse) {
        val uri = request?.request?.getUri().sure();

        request.getArguments = decodeAndSanitize(uri)
    }

    //taken from HtmlFormatter. HtmlFormatter#format give a NoSuchMethodError
    public fun format(buffer : Appendable, text : String) : Unit {
        for (c in text) {
            if (c == '<') buffer.append("&lt;")
            else if (c == '>') buffer.append("&gt;")
            else if (c == '&') buffer.append("&amp;")
            else if (c == '"') buffer.append("&quot;")
            else buffer.append(c)
        }
    }
    private fun sanitize(value:String):String{
        val builder=StringBuilder()
        format(builder, value)
        return builder.toString().sure()
    }
    public fun decodeAndSanitize(params:String):Map<String?, List<String?>?>
    {
        val queryStringDecoder = QueryStringDecoder(params);
        val params = queryStringDecoder.getParameters();
        for (pair in params) {

            val values= pair.value.sure()

            for (idx in 0 .. values.size()-1) {

                values[idx]= sanitize(values[idx].sure())
            }


        }
        return params.sure()
    }


    public fun build(message: MessageEvent, handler: HttpHandler): RequestResponse {
        val reqResp = kw.server.RequestResponse(message, handler)
        initPostArguments(reqResp)
        initGetArguments(reqResp)
        return reqResp
    }
}

