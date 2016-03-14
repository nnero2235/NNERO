//
// Created by nnero on 16/1/27.
//
#include <jni.h>
#include <assert.h>
#include <unistd.h>
#include "log.h"
#include <pthread.h>

#define JNI_CLASS_CALLBACK "com/nnero/ncustomview/play/Player"
#define M_NUM(x) ((int)(sizeof(x)/sizeof((x)[0])))

//该宏 可以找到clazz
#define FIND_JAVA_CLASS(env__, var__, classsign__) \
    do { \
        var__ = (*env__)->FindClass(env__, classsign__); \
    } while(0);
static JavaVM *g_jvm;
static jclass clazz; //全局引用 加载该.so库的java class

static int is_shutdown = 1;
static int x = 0;
static jobject fn = NULL;

static void *start_loop(void *args){
    JNIEnv *env = (JNIEnv *)args;
    is_shutdown = 1;
    while(is_shutdown){
        sleep(1);
        x++;
        LOG_D("x:%d",x);
        if(fn){
            if((*g_jvm)->AttachCurrentThread(g_jvm, &env, NULL) != JNI_OK) {
                LOG_E("%s: AttachCurrentThread() failed", __FUNCTION__);
                return NULL;
            }
            LOG_D("fn 不为空");
            jclass jclazz_listener = (*env)->GetObjectClass(env,fn);
            jmethodID method_id =(*env)->GetMethodID(env,jclazz_listener,"onWhile","(I)V");
            (*env)->CallVoidMethod(env,fn,method_id,x);
            if((*g_jvm)->DetachCurrentThread(g_jvm) != JNI_OK) {
                LOG_E("%s: DetachCurrentThread() failed", __FUNCTION__);
            }
        } else{
            LOG_D("fn为空");
        }
    }
}

static void callback_start(JNIEnv *env,jobject thiz){
    LOG_D("start");
    pthread_t t;

    pthread_create(&t,NULL,start_loop,env);
}

static void callback_regist_on_while_listener(JNIEnv *env,jobject thiz,jobject jlistener){
    LOG_D("regist");
    fn = (*env)->NewGlobalRef(env,jlistener);
}

static void callback_unregist_on_while_listener(JNIEnv *env,jobject thiz){
    LOG_D("unregist");
    (*env)->DeleteGlobalRef(env,fn);
    fn = NULL;
}

static void callback_shutdown(){
    LOG_D("shutdown");
    is_shutdown = 0;
}


static JNINativeMethod methods[] = {
        {"_start","()V",(void *)callback_start},
        {"_registOnWhileListener","(Lcom/nnero/ncustomview/play/Player$OnWhileListener;)V",(void *)callback_regist_on_while_listener},
        {"_unregistOnWhileListener","()V",(void *)callback_unregist_on_while_listener},
        {"_shutdown","()V",(void *)callback_shutdown}
};

//该方法在jni.h文件中声明，实现由用户自己
//在java层 System.loadlibrary("xxx");的时候自动调用该方法  注册 所有方法映射
//用了这种模式 就不需要 头文件声明模式了 也就不需要javah了
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved){
    g_jvm = vm;
    JNIEnv* env = NULL;

    if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }
    assert(env != NULL);

    // FindClass returns LocalReference
    FIND_JAVA_CLASS(env, clazz, JNI_CLASS_CALLBACK);
    (*env)->RegisterNatives(env, clazz, methods, M_NUM(methods));

    return JNI_VERSION_1_4;
}
//该方法可用于释放资源
JNIEXPORT void JNI_OnUnload(JavaVM *jvm, void *reserved) {
    //TODO: release sources
}