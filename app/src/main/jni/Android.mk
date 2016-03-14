LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := callback
LOCAL_SRC_FILES := callback.c //sourceCode:多个的话 用空格隔开
LOCAL_LDLIBS    += -llog //需要链接的 android自带的 一些库 -l指定.so路径 空格隔开多个库

include $(BUILD_SHARED_LIBRARY)