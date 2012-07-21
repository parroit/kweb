package kw.server

import java.util.regex.Pattern


 public trait RoutesResolver{
    public fun resolve(prefix: String,vararg args:Any): String {
        
        val pattern=Pattern.compile(".*(\\(.*\\)).*").sure()
        val matcher=pattern.matcher(prefix).sure()
        val url=StringBuilder(prefix)
        matcher.matches()
        var group=1
        for (arg in args){
            val start=matcher.start(group)
            val end=matcher.end(group)
            url.replace(start,end,arg.toString())
            group++
        }
        return url.toString().sure()
    }
 }
class RoutesResolver1<T1>(private val prefix: String): (T1)->String,RoutesResolver {
    public override fun invoke(p1: T1): String {
          return resolve(prefix,p1)
    }
}
class RoutesResolver2<T1,T2>(private val prefix: String): (T1,T2)->String , RoutesResolver{
    public override fun invoke(p1: T1,p2:T2): String {
        return resolve(prefix,p1,p2)
    }
}
class RoutesResolver3<T1,T2,T3>(private val prefix: String): (T1,T2,T3)->String,RoutesResolver {
    public override fun invoke(p1: T1,p2:T2,p3:T3): String {
        return resolve(prefix,p1,p2,p3)
    }
}

class RoutesResolver0(private val prefix: String): ()->String,RoutesResolver {
    public fun resolve(): String {
        return resolve(prefix)
    }
    public override fun invoke(): String {
        return resolve()
    }
}
