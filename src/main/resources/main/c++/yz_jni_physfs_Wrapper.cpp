/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

#include "../includes/yz_jni_physfs_Wrapper.h"
#include "../includes/yz_physfs_Wrapper.hpp"
#include "../includes/JniUtil.h"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jlong JNICALL Java_jni_PhysFsWrapperNative_initialize(JNIEnv* env, jobject o) {
    try {
        return reinterpret_cast<jlong>(new yz::physfs::Wrapper());
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT jlong JNICALL Java_jni_PhysFsWrapperNative_registerContainer(JNIEnv* env, jobject o, jlong pointer, jstring jpath) {
    yz::physfs::Wrapper* wrapper = reinterpret_cast<yz::physfs::Wrapper*>(pointer);
    JniStringWrapper path = JniStringWrapper(env, jpath);
    try {
        yz::physfs::Container* container = wrapper->registerContainer(path.getValue());
        return reinterpret_cast<jlong>(container);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return -1L;
}

JNIEXPORT jlongArray JNICALL Java_jni_PhysFsWrapperNative_getSupportedArchiveType(JNIEnv* env, jobject o, jlong pointer) {
    try {
        yz::physfs::Wrapper* wrapper = reinterpret_cast<yz::physfs::Wrapper*>(pointer);
        std::vector<yz::physfs::ArchiveTypeInfo*> list = wrapper->getSupportedArchiveType();
        const int size = list.size();
        jlong* buf = new jlong[size];
        for (int i = 0; i < size; i++) {
            buf[i] = reinterpret_cast<jlong>(list.at(i));
        }
        jlongArray result = env->NewLongArray(size);
        env->SetLongArrayRegion(result, 0, size, buf);
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewLongArray(0);
}

JNIEXPORT jobjectArray JNICALL Java_jni_PhysFsWrapperNative_enumerateFiles(JNIEnv* env, jobject o, jlong pointer, jstring jdir)  {
    try {
        yz::physfs::Wrapper* wrapper = reinterpret_cast<yz::physfs::Wrapper*>(pointer);
        JniStringWrapper dir = JniStringWrapper(env, jdir);
        std::vector<std::string> list = wrapper->enumerateFiles(dir.getValue().c_str());
        const int size = list.size();
        jobjectArray result = env->NewObjectArray(size, env->FindClass("java/lang/String"), env->NewStringUTF(""));
        for (int i = 0; i < size; i++) {
            env->SetObjectArrayElement(result, i, env->NewStringUTF(list[i].c_str()));
        }
        return result;
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
    return env->NewObjectArray(1, env->FindClass("java/lang/String"), env->NewStringUTF(""));
}
