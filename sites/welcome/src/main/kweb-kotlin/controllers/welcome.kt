package sites.welcome.controllers
import sites.welcome.model.*
import kw.server.RequestResponse
import sites.welcome.model.Quote
import java.util.List
import kw.server.Persister.PageList
import java.net.URI

public fun RequestResponse.index(uri:String): String {
    return "test "+uri
}
public fun RequestResponse.editQuote(id:String): Quote {
    val quote = QuotePersister.byId(id)
    if (quote!=null)
        return quote
    throw RuntimeException("Quote not found:"+id)
}
public fun RequestResponse.saveQuote(id:String): PageList<Quote> {
    val quote = QuotePersister.byId(id) ?:  Quote()
    quote.code=postArguments["code"].sure()[0].sure()
    quote.header=postArguments["header"].sure()[0].sure()
    var customer:Int
    try {
        customer=Integer.parseInt(postArguments["customer"].sure()[0])
    } catch(e:Exception){
        customer=1
    }



    quote.customer=CompanyPersister.byId(customer)

    QuotePersister.save(quote)
    return quotes(0)
}

public fun RequestResponse.quotes(val currentPage:Int): PageList<Quote> {
    return QuotePersister.all(currentPage,10)
}
public fun RequestResponse.newQuote(): Quote {
    return Quote()
}