// Module script for production
import kotlin.modules.*
fun project() {
    module("welcome") {
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/App.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/welcome.kts"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/views/defaultWelcome.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/views/quotes.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/model/WelcomeModel.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/views/layout.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/model/Quote.kt"
        sources += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin/controllers/welcome.kt"
        // Boot classpath
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/management-agent.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/jsse.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/rhino.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/resources.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/charsets.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/jce.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/rt.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/localedata.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/sunjce_provider.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/sunpkcs11.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/gnome-java-bridge.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/pulse-java.jar"
        classpath += "/usr/lib/jvm/java-1.6.0/jre/lib/ext/dnsns.jar"
        // Compilation classpath
        // Output directory, commented out
        //         classpath += "/home/descottl/dev/kweb/sites/welcome/target/test-classes"
        // Output directory, commented out
        //         classpath += "/home/descottl/dev/kweb/sites/welcome/target/classes"
        classpath += "/home/descottl/dev/kweb/target/classes"
        classpath += "/home/descottl/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/0.1.2580/kotlin-stdlib-0.1.2580.jar"
        classpath += "/home/descottl/.m2/repository/org/jetbrains/kotlin/kotlin-runtime/0.1.2580/kotlin-runtime-0.1.2580.jar"
        classpath += "/home/descottl/.m2/repository/org/jetbrains/kotlin/kotlin-compiler/0.1.2580/kotlin-compiler-0.1.2580.jar"
        classpath += "/home/descottl/.m2/repository/junit/junit/4.4/junit-4.4.jar"
        classpath += "/home/descottl/.m2/repository/org/hibernate/hibernate-core/4.1.4.Final/hibernate-core-4.1.4.Final.jar"
        classpath += "/home/descottl/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar"
        classpath += "/home/descottl/.m2/repository/org/jboss/logging/jboss-logging/3.1.0.GA/jboss-logging-3.1.0.GA.jar"
        classpath += "/home/descottl/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.1_spec/1.0.0.Final/jboss-transaction-api_1.1_spec-1.0.0.Final.jar"
        classpath += "/home/descottl/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar"
        classpath += "/home/descottl/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.1.Final/hibernate-jpa-2.0-api-1.0.1.Final.jar"
        classpath += "/home/descottl/.m2/repository/org/javassist/javassist/3.15.0-GA/javassist-3.15.0-GA.jar"
        classpath += "/home/descottl/.m2/repository/org/hibernate/common/hibernate-commons-annotations/4.0.1.Final/hibernate-commons-annotations-4.0.1.Final.jar"
        classpath += "/home/descottl/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar"
        classpath += "/home/descottl/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar"
        classpath += "/home/descottl/.m2/repository/io/netty/netty/3.5.1.Final/netty-3.5.1.Final.jar"
        classpath += "/home/descottl/.m2/repository/com/google/guava/guava/12.0.1/guava-12.0.1.jar"
        classpath += "/home/descottl/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar"
        classpath += "/home/descottl/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar"
        classpath += "/home/descottl/.m2/repository/rhino/js/1.7R2/js-1.7R2.jar"
        // Java classpath (for Java sources)
        classpath += "/home/descottl/.IntelliJIdea11/system/compiler/kwebproject.45fe2b76/.generated/Flex_Resource_Compiler/welcome.1a47aec0/test"
        classpath += "/home/descottl/.IntelliJIdea11/system/compiler/kwebproject.45fe2b76/.generated/ActionScript_Compiler/welcome.1a47aec0/test"
        classpath += "/home/descottl/.IntelliJIdea11/system/compiler/kwebproject.45fe2b76/.generated/Flex_Resource_Compiler/welcome.1a47aec0/production"
        classpath += "/home/descottl/.IntelliJIdea11/system/compiler/kwebproject.45fe2b76/.generated/ActionScript_Compiler/welcome.1a47aec0/production"
        classpath += "/home/descottl/dev/kweb/sites/welcome/src/main/resources"
        classpath += "/home/descottl/dev/kweb/sites/welcome/src/main/kotlin"
        // Main output
    }
}
