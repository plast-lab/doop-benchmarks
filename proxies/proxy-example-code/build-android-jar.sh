OUTPUT_JAR=android/Main.jar
if [ "${BUILD_TOOLS_DIR}" == "" ]
then
    BUILD_TOOLS_DIR=~/Android/Sdk/build-tools/24.0.0
fi

echo Compiling Main.java as Java 7...
javac -source 1.7 -target 1.7 Main.java

echo Converting .class to .dex...
${BUILD_TOOLS_DIR}/dx --dex --output=${OUTPUT_JAR} A.class AHandler.class AProxyForG.class G.class Main.class

# echo Pushing to device...
# adb push ${OUTPUT_JAR} /data/user/proxy-test/Main.jar
