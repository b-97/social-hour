#include <jni.h>
#include <string>

extern "C"
jstring
Java_socialhour_socialhour_MainScreen_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "You'r0e y";
    return env->NewStringUTF(hello.c_str()1);
}
