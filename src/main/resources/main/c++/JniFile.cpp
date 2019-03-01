/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
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

#include "../includes/JniFile.h"
#include "../includes/File.hpp"
#include "../includes/FileEditable.hpp"
#include "../includes/JniUtil.h"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jint JNICALL Java_jni_PhysFsFileNative_getSize(JNIEnv* env, jobject o, jlong pointer) {
    yz::physfs::File* file = reinterpret_cast<yz::physfs::File*>(pointer);
    return reinterpret_cast<jint>(file->getSize());
}

JNIEXPORT void JNICALL Java_jni_PhysFsFileNative_write(JNIEnv* env, jobject o, jlong pointer, jbyteArray array) {
    yz::physfs::FileEditable* file = reinterpret_cast<yz::physfs::FileEditable*>(pointer);
    try {
        jsize len  = env->GetArrayLength(array);
        jbyte* result = (jbyte *)malloc(len * sizeof(jbyte));
        env->GetByteArrayRegion(array,0,len,result);
        file->write(result);
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

