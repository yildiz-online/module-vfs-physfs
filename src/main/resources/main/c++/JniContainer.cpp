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

#include "../includes/JniContainer.h"
#include "../includes/Container.hpp"
#include "../includes/JniUtil.h"

/**
* @author Grégory Van den Borre
*/

JNIEXPORT jlong JNICALL Java_jni_PhysFsContainerNative_openFile(JNIEnv* env, jobject o, jlong pointer, jstring jpath) {
    yz::physfs::Container* container = reinterpret_cast<yz::physfs::Container*>(pointer);
    JniStringWrapper path = JniStringWrapper(env, jpath);
    return reinterpret_cast<jlong>(container->openFile(path.getValue()));
}

JNIEXPORT jlong JNICALL Java_jni_PhysFsContainerNative_openFileToWrite(JNIEnv* env, jobject o, jlong pointer, jstring jpath) {
    yz::physfs::Container* container = reinterpret_cast<yz::physfs::Container*>(pointer);
    JniStringWrapper path = JniStringWrapper(env, jpath);
    return reinterpret_cast<jlong>(container->openFileToWrite(path.getValue()));
}

JNIEXPORT void JNICALL Java_jni_PhysFsContainerNative_setDirectoryWritable(JNIEnv* env, jobject o, jlong pointer) {
    try {
        yz::physfs::Container* container = reinterpret_cast<yz::physfs::Container*>(pointer);
        container->setDirectoryWritable();
    } catch (std::exception& e) {
        throwException(env, e.what());
    }
}

