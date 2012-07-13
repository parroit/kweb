package kw.views

import java.io.StringWriter
import java.io.PrintWriter

fun formatException(exception:Throwable):String{
    val stringWriter =StringWriter()
    exception.printStackTrace(PrintWriter(stringWriter))
    return stringWriter.toString().sure().replace("\n","<br/>")
}

fun error(exception:Throwable)="""
 <html>
    <head>
        <title>Error</title>

    </head>
    <body>
        <h2>An exception has been thrown</h2>
        <h3>${exception.getMessage()}</h3>
        <p>
        ${formatException(exception)}
        </p>
    </body>
 </html>
"""