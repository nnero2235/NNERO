//
// Created by nnero on 16/1/27.
//

#ifndef CALL_BACK_LOG_H
#define CALL_BACK_LOG_H

#include <android/log.h>

#define LOG_TAG ("NCustom")
#define LOG_V(...) ((void)__android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__))
#define LOG_D(...) ((void)__android_log_print(ANDROID_LOG_DEBUG  , LOG_TAG, __VA_ARGS__))
#define LOG_I(...) ((void)__android_log_print(ANDROID_LOG_INFO   , LOG_TAG, __VA_ARGS__))
#define LOG_W(...) ((void)__android_log_print(ANDROID_LOG_WARN   , LOG_TAG, __VA_ARGS__))
#define LOG_E(...) ((void)__android_log_print(ANDROID_LOG_ERROR  , LOG_TAG, __VA_ARGS__))

#endif
