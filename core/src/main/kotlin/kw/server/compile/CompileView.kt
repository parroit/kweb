package kw.server.compile;

import java.io.File
import org.jetbrains.jet.cli.jvm.K2JVMCompiler
import org.jetbrains.jet.cli.jvm.K2JVMCompilerArguments

/**
* Author: parroit
* Created: 01/07/12 10.17
*/
public class CompileView(val siteViewsRoot: File) {
    public fun compileModule(moduleFile: File) {
        val arguments = K2JVMCompilerArguments()
        arguments.setModule(moduleFile.getPath())
        arguments.setOutputDir("html")

        val compiler = K2JVMCompiler()

        compiler.exec(System.err, arguments)
    }
    public fun viewSource(fileName: String): File = File(siteViewsRoot, fileName + ".html.kt")

    public fun viewOutdated(viewName: String): Boolean {
        val file = File(outFolder, "namespace$${viewName}$1.class")

        return !file.exists() || file.lastModified() < viewSource(viewName).lastModified()
    }


    public val tmpFolder: File = {
        val fld = File("tmp")
        if (!fld.exists())
            fld.mkdirs()
        fld
    }()

    public val outFolder: File = {
        val fld = File("html")
        if (!fld.exists())
            fld.mkdirs()
        fld
    }()


    public fun  module(viewName :String):File = File(tmpFolder, viewName+".html.kts")
    public fun  kFile(viewName :String):File = File(tmpFolder, viewName+".kt")

    public fun recompile(viewName :String) {


        val kFile = kFile(viewName)
        val module = module(viewName)
        module.writeText(compileScript(viewName, kFile))


        var lineCount = 0
        val buff = StringBuilder()

        val viewSource = viewSource(viewName)

        viewSource.forEachLine ({ line ->
            if (!(lineCount < 2 || line contains "</html>")) {
                buff.append(line)?.append("\r\n")
            }
            lineCount++;
        })

        kFile.writeText(buff.toString() + "")

        compileModule(module)



    }
}


