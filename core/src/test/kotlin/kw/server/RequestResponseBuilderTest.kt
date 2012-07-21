package kw.server

import kw.server.RequestResponse
import kw.server.HttpHandler
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import java.util.regex.Matcher
import org.junit.Test
import junit.framework.Assert
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.List
import java.util.Map


class RequestResponseBuilderTest{
  fun checkResult(url:String ,values : Map<String, List<String>>){
      val expected = LinkedHashMap(values)
      val actual = RequestResponseBuilder().decodeAndSanitize(url)
      Assert.assertEquals(expected, actual)
  }

  Test fun decodeNonHtml(){

      checkResult("/?code=002&header=myheader&customer=1",hashMap(
          #("code",arrayList("002")),
          #("header",arrayList("myheader")),
          #("customer",arrayList("1"))
      ))
  }

    Test fun decodeHtml(){

        checkResult("/?code=<script>&header=myheader&customer=1",hashMap(
                #("code",arrayList("&lt;script&gt;")),
                #("header",arrayList("myheader")),
                #("customer",arrayList("1"))
        ))
    }


}