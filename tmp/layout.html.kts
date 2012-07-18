
        import kotlin.modules.*

                fun project() {
        }
        var module = module("layout-module") {
            sources += "/home/descottl/dev/kweb/tmp/layout.kt"
            // Output directory
            classpath += "out/production/KWeb"
            classpath += "out/production/welcome"
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

            classpath += "lib/kotlin-runtime.jar"
            classpath += "lib/netty-3.5.0.Final.jar"
            classpath += "lib/hibernate-core-4.1.4.Final.jar"
            classpath += "lib/guava-11.0.2.jar"
            classpath += "lib/dom4j-1.6.1.jar"
            classpath += "lib/js.jar"


        }


        