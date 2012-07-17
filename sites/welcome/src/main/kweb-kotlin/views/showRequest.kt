package sites.welcome.views

import templates.layout
import java.util.List
import java.util.Map

fun showRequest(getArgs:Map<String?, List<String?>?>,postArgs:Map<String?, List<String?>?>)=layout {
    val builder=StringBuilder()
    builder.append("<h2>GET:</h2>")
    builder.append("<ul>")
    for (args in getArgs.entrySet())
        builder.append("<li>${args.key} --&gt; ${args.value}</li>")

    builder.append("</ul>")


    builder.append("<h2>POST:</h2>")
    builder.append("<ul>")
    for (args in postArgs.entrySet())
        builder.append("<li>${args.key} --&gt; ${args.value}</li>")

    builder.append("</ul>")




    builder.toString() + """
        <form action="#" method="post">
        <input type='text' name='testcode'/>
        <input type='file' name='testfile'/>
        <button type='submit'>send</button>
        </form>
    """
}