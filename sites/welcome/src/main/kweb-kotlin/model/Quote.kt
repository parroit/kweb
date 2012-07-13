package sites.welcome.model

import java.util.Date
import org.hibernate.Session
import java.io.Serializable
import java.util.List
import org.hibernate.Criteria
import org.hibernate.criterion.Restrictions
import kw.server.Persister

import kw.model.described
import kw.model.ReferenceFieldValue

public object QuotePersister:Persister<Quote>(javaClass<Quote>(),WelcomeModel){

}

public class Quote() {


    public var header:String =""

    public var code:String =""

    public var createdOn: Date = Date();
    public var lastEditedOn:Date = Date();

    public var year:Int =Date().getYear()+1900;

    public var customer :Company? =null
    public fun Description():String
    {

        return code +": "+header;

    }
}
public object CompanyPersister:Persister<Company>(javaClass<Company>(),WelcomeModel)

public class Company():ReferenceFieldValue<Company>  {
    public override fun fromEngine(value: String?): Company? {
        val company = Company()
        company.code=value?.toInt().sure()
        return company
    }
    public override fun toEngine(): String? {
        return name

    }

    public override fun toString(): String? {
        return name
    }
    public var name :String=""
    public var code :Int=0
    public var customer :Boolean?=false

}
