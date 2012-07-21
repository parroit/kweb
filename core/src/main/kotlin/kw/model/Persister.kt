package kw.server

import java.io.Serializable
import java.util.List
import kw.model.PersistentHelper
import org.hibernate.Session
import org.hibernate.criterion.Restrictions
import kw.server.Persister.PageList
import kotlin.nullable.count

open public class Persister<TModel>(private val clazz: Class<TModel>, private val persisterHelper: PersistentHelper){

    private fun usingNewSession<TResults>(procedure: (session: Session)->TResults): TResults {

        //without this null initialization a runtime exception is thrown in the try
        var session: Session? = null
        try{
            session = persisterHelper.OpenSession()

            return procedure(session.sure())
        } finally {
            session?.close()
        }
    }
    public fun byId(id: Any): TModel? {
        return usingNewSession { (session: Session) ->
            session.get(clazz, id as Serializable) as TModel?
        }
    }

    public fun byName(name: String): TModel? {
        return usingNewSession { (session: Session) ->
            session.createCriteria(clazz)?.add(Restrictions.eq("name", name))?.uniqueResult() as TModel?
        }
    }

    public class PageList<TModel>(
        public val list : List<TModel>,
        public val currentPage:Int,
        public val totalPage:Int
    )
    public fun save(instance:TModel) {
        usingNewSession { (session: Session) ->
            val tr=session.beginTransaction().sure();
            session.saveOrUpdate(instance)
            session.flush()
            tr.commit()
        }

    }
    public fun all(val currentPage:Int,val pageLength:Int): PageList<TModel> {
        return usingNewSession { (session: Session) ->
            val results = session.createCriteria(clazz)
            val actualPageResult=session.createCriteria(clazz)
                ?.setFirstResult((currentPage - 1) * pageLength)
                ?.setMaxResults(pageLength).sure()

            val count = results?.list()?.size.sure() / pageLength
            println(currentPage.toString()+"/"+count)
            PageList(actualPageResult.list() as List<TModel>,currentPage, count)
        }
    }
}