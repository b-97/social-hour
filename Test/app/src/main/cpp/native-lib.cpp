#include <jni.h>
#include <string>

extern "C"
jstring
Java_socialhour_test_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "We'll figure t";
    return env->NewStringUTF(hello.c_str());
}
