import kotlin.modules.*

fun project() {
}
var module = module("welcome") {
    addSourceFiles("controllers")
    addSourceFiles("views")
    addSourceFiles("model")
    sources+="App.kt"
    // Boot classpath
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/alt-rt.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/alt-string.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/charsets.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/deploy.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/javaws.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/jce.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/jsse.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/management-agent.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/plugin.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/resources.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/rt.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/ext/dnsns.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/ext/localedata.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/ext/sunjce_provider.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/ext/sunmscapi.jar"
    classpath += "C:/Program Files/Java/jdk1.6.0_29/jre/lib/ext/sunpkcs11.jar"
    // Compilation classpath
    // Output directory, commented out
    //         classpath += "C:/projects/KWeb/out/test/welcome"
    // Output directory, commented out
    //         classpath += "C:/projects/KWeb/out/production/welcome"
    classpath += "C:/projects/KWeb/lib/kotlin-runtime.jar"
    classpath += "C:/projects/KWeb/out/test/KWeb"
    classpath += "C:/projects/KWeb/out/production/KWeb"
    classpath += "C:/projects/KWeb/lib/netty-3.5.0.Final/jar/netty-3.5.0.Final.jar"
    classpath += "C:/projects/KWeb/lib/hibernate-core-4.1.4.Final.jar"
    classpath += "C:/projects/KWeb/lib/guava-11.0.2.jar"
    classpath += "C:/projects/KWeb/lib/dom4j-1.6.1.jar"
    classpath += "C:/projects/KWeb/lib/commons-logging-1.1.1-bin/commons-logging-1.1.1/commons-logging-1.1.1.jar"
    classpath += "C:/projects/KWeb/lib/commons-logging-1.1.1-bin/commons-logging-1.1.1/commons-logging-adapters-1.1.1.jar"
    classpath += "C:/projects/KWeb/lib/rhino1_7R3/js.jar"
    classpath += "C:/projects/KWeb/lib/junit.jar"
    classpath += "C:/projects/KWeb/lib/annotations.jar"
    classpath += "C:/projects/KWeb/sites/welcome/lib/jtds-1.2.5.jar"
}

