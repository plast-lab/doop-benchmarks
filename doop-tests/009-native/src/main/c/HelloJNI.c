// Adapted from
// https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
#include <jni.h>
#include <stdio.h>

static char* array[2][2] = { {"ConstAAAA", "ConstBBBB"}, {"ConstCCCC", "ConstDDDD"} };

// Test method to inspect parts of the jobject data structure. This
// only examines the first two elements pointed to by <obj>, plus the
// first two elements pointed to in each case. We assume these pairs
// usually exist
// (https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.7).
void inspect_jobject(jobject obj) {
#ifdef OPENJDK
    printf("obj = %p\n", obj);
    int i, j;
    for (i = 0; i < 2; i++) {
        printf("obj[%d] = %p\n", i, (void*)((long*)obj)[i]);
        for (j = 0; j < 2; j++)
            printf("obj[%d][%d] = %p\n", i, j, (void*)((long**)obj)[i][j]);
    }
#endif // OPENJDK
}

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT void JNICALL Java_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
   inspect_jobject(thisObj);
   printf("Hello World!\n");
   return;
}

JNIEXPORT jobject JNICALL Java_HelloJNI_newJNIObj(JNIEnv *env, jobject thisObj) {
    inspect_jobject(thisObj);
    jclass cls = (*env)->FindClass(env, "HelloJNI");
    jmethodID constructor = (*env)->GetMethodID(env, cls, "<init>", "()V");
    return (*env)->NewObject(env, cls, constructor);
}

JNIEXPORT void JNICALL Java_HelloJNI_callBack(JNIEnv *env, jobject obj) {
    inspect_jobject(obj);
    jclass cls = (*env)->FindClass(env, "HelloJNI");
    jmethodID helloMethod = (*env)->GetMethodID(env, cls, "helloMethod", "(Ljava/lang/Object;Ljava/lang/Object;)I");
    jint i = (*env)->CallIntMethod(env, obj, helloMethod, obj, obj);
    printf("callBack(): i = %d\n", i);
}

// This method is not registered following the JNI naming convention
// but goes through RegisterNatives() in JNI_OnLoad().
JNIEXPORT void JNICALL jniMethod(JNIEnv *env, jobject obj1, jobject obj2) {
    jclass cls = (*env)->FindClass(env, "HelloJNI");
    jmethodID helloMethod2 = (*env)->GetMethodID(env, cls, "helloMethod2", "(Ljava/lang/Object;)I");
    jint i = (*env)->CallIntMethod(env, obj1, helloMethod2, obj2);
    printf("helloMethod2() via jniMethod(): i = %d\n", i);
}

static JNINativeMethod method0 = { "customRegisteredMethod", "(Ljava/lang/Object;)V", (void*)jniMethod };
static JNINativeMethod* nMethods = { &method0 };
static int numMethods = 1;

#define CHECK_INDEX(FUNC) if (((void**)*env)[i] == ((void*)(*env)->FUNC)) { printf("==> "#FUNC" = %d\n", i); }

void discoverJNILinkage(JNIEnv *env) {
    for (int i = 0; i < 800; i++) {
        // if (((void**)*env)[i] == ((void*)(*env)->RegisterNatives)) { printf("==> RegisterNatives = %d\n", i); }
        CHECK_INDEX(GetBooleanArrayRegion)
        CHECK_INDEX(RegisterNatives)
    }
}

// Test native method registration.
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    int i, j;

    jint jniVer = JNI_VERSION_1_4;

    printf("JNI_OnLoad()\n");
    for (i = 0; i < 2; i++)
        for (j = 0; j < 2; j++)
            printf("array[%d][%d] = %s\n", i, j, array[i][j]);

    printf("Registering %d methods:\n", numMethods);
    for (i = 0; i < numMethods; i++) {
        JNINativeMethod jnm = nMethods[i];
        printf("Registering: {'%s', '%s', '%p'}\n", jnm.name, jnm.signature, jnm.fnPtr);
    }
    printf("jniMethod = %p\n", (void*)jniMethod);

    JNIEnv* env = NULL;
    if ((*vm)->GetEnv(vm, (void**) &env, jniVer) != JNI_OK) {
        printf("GetEnv failed!\n");
        return JNI_ERR;
    }

    discoverJNILinkage(env);

    jclass cls = (*env)->FindClass(env, "HelloJNI");
    if ((*env)->RegisterNatives(env, cls, nMethods, numMethods) < 0) {
        printf("RegisterNatives failed! error=%d\n", JNI_ERR);
        return JNI_ERR;
    }

    printf("JNI_OnLoad() Done.\n");
    return jniVer;
}
