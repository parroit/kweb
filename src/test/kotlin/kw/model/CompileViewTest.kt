package kw.server.compile;

import org.junit.Test

import java.io.File
import org.junit.Assert
import java.util.Date
import org.junit.Ignore


/**
 * Author: parroit
 * Created: 01/07/12 10.23
 */
public class CompileViewTest {
    val path = "sites/welcome/src/main/resources/views";
    val compiler=CompileView(File(path))
    val viewName="layout"

    Test public fun viewSourceExists(){
        Assert.assertTrue(compiler.viewSource(viewName).exists())
    }
    Test public fun outFolderAutoCreated(){
        Assert.assertTrue(compiler.outFolder.exists())
        for (f in compiler.outFolder.listFiles()){
            f?.delete()
        }

        Assert.assertTrue(compiler.outFolder.delete())
        val otherCompiler=CompileView(File(path))
        Assert.assertTrue(otherCompiler.outFolder.exists())
    }

    Test public fun tmpFolderAutoCreated(){
        Assert.assertTrue(compiler.tmpFolder.exists())
        for (f in compiler.tmpFolder.listFiles()){
            f?.delete()
        }

        Assert.assertTrue(compiler.tmpFolder.delete())
        val otherCompiler=CompileView(File(path))
        Assert.assertTrue(otherCompiler.tmpFolder.exists())
    }

    Test public fun viewOutdatedIfOutFileNotExists(){
        val file = File(compiler.outFolder,"namespace$${viewName}$1.class")
        if (file.exists())
            Assert.assertTrue(file.delete())

        Assert.assertTrue(compiler.viewOutdated(viewName))



    }
    Test public fun viewOutdatedIfOutFileExistsButOlderThanSource(){
        val file = File(compiler.outFolder,"namespace$${viewName}$1.class")
        if (file.exists())
            Assert.assertTrue(file.delete())

        file.writeText("just a test")
        val today = Date()
        today.setYear(111)
        file.setLastModified(today.getTime())


        Assert.assertTrue(compiler.viewOutdated(viewName))


    }

    Test public fun viewNotOutdatedIfOutFileExistsAndRecentThanSource(){
        val file = File(compiler.outFolder,"namespace$${viewName}$1.class")
        if (file.exists())
            Assert.assertTrue(file.delete())

        file.writeText("just a test")
        val today = Date()
        today.setYear(211)
        file.setLastModified(today.getTime())

        Assert.assertFalse(compiler.viewOutdated(viewName))
    }


    //TODO unignore to run compilation
    Ignore Test public fun compileCreateOutFile(){
        val file = File(compiler.outFolder,"templates/namespace$${viewName}$1.class")
        if (file.exists())
            Assert.assertTrue(file.delete())

        Assert.assertFalse(file.exists())

        compiler.recompile(viewName)

        Assert.assertTrue(file.exists())
    }




}
