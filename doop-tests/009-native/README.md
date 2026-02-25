== OpenJDK ==

To build, set JAVA_HOME environment variable to point to the local
Java installation path (for the JNI headers).

Build and run with  : make run
Capture .hprof with : make capture_hprof
Capture .jfr with   : make capture_jfr_jdk8 (for Java 8)
                      make_capture_jfr_jdk11 (for Java 11)

== GraalVM native images ==

Build native image  : OPENJDK= make native-image
