Test Android application, created in Android Studio using the "empty
activity" template for new projects.

First, you must have a local.properties file that shows the location
of the Android SDK:

sdk.dir=/home/user/Android/Sdk

Then, post the application to the server with the following command:

  ./gradlew clean :app:assemble :app:sourcesJar :app:jcpluginZip :app:postBundle
