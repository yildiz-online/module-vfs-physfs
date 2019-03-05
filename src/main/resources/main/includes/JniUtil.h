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

#ifndef YZ_PHYSFS_JNIUTIL_H
#define YZ_PHYSFS_JNIUTIL_H

#include <jni.h>
#include <string>

/**
*@author Grégory Van den Borre
*/
class JniStringWrapper {

    public:
        JniStringWrapper(JNIEnv* env, jstring& jstr) {
            this->env = env;
            this->jstr = jstr;
            this->str = env->GetStringUTFChars(jstr, 0);
        }

        ~JniStringWrapper() {
            this->env->ReleaseStringUTFChars(this->jstr, this->str);
        }

        std::string getValue() const {
            return this->str;
        }

    private:
        jstring jstr;
        const char* str;
        JNIEnv* env;
};

    inline void throwException(JNIEnv* env, const char* message) {
        jclass exception = env->FindClass("be/yildiz-games/common/exception/NativeException");
        env->ThrowNew(exception, message);
    }

#endif
