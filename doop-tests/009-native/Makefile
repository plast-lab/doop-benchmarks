JAR=build/libs/009-native.jar
JNI_LIBS=src/main/resources/lib
CC?=gcc
JNI_INCLUDE_PATH?=$(JAVA_HOME)/include
EXTRA_INCLUDE_PATH?=$(JAVA_HOME)/include/linux
ARCH?=unknown
OPENJDK?=-DOPENJDK

all: jni-x86-64 jniAndroid jar

jni-x86-64:
	mkdir -p $(JNI_LIBS)/x86-64
	$(CC) -fPIC -I"$(JNI_INCLUDE_PATH)" -I"$(EXTRA_INCLUDE_PATH)" $(OPENJDK) -shared -o $(JNI_LIBS)/x86-64/libhello.so src/main/c/HelloJNI.c

jni-x86-32:
	mkdir -p $(JNI_LIBS)/x86
	$(CC) -m32 -fPIC -I"$(JNI_INCLUDE_PATH)" -I"$(EXTRA_INCLUDE_PATH)" $(OPENJDK) -shared -o $(JNI_LIBS)/x86/libhello.so src/main/c/HelloJNI.c

jniAndroid:
	./make-android.sh

# This target is used by the Android script.
jni:
	mkdir -p $(JNI_LIBS)/$(ARCH)
	$(CC) -fPIC -I"$(JNI_INCLUDE_PATH)" -I"$(EXTRA_INCLUDE_PATH)" -shared -o $(JNI_LIBS)/$(ARCH)/libhello.so src/main/c/HelloJNI.c


native-image: jni-x86-64
	../gradlew :009-native:nativeImage

jar:
	../gradlew :009-native:jar

run: all
	java -jar $(JAR)

capture_hprof: all
	java -agentlib:hprof=heap=dump,format=b,depth=8 -jar $(JAR)

capture_jfr_jdk8: all
	java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:FlightRecorderOptions=stackdepth=1024 -XX:FlightRecorderOptions=defaultrecording=true,settings=profile,disk=true,maxsize=500M,stackdepth=1024,dumponexit=true -jar $(JAR)

capture_jfr_jdk11: all
	java -XX:+FlightRecorder -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:FlightRecorderOptions=stackdepth=1024 -XX:StartFlightRecording=disk=true,settings=profile,maxsize=500M,dumponexit=true -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -jar $(JAR)

clean:
	rm -rf $(JNI_LIBS) heap-dump*.hprof hotspot-pid-*.jfr
	../gradlew :009-native:clean
