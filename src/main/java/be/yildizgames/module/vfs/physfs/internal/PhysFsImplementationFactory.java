/*
 *
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
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */
package be.yildizgames.module.vfs.physfs.internal;

import be.yildizgames.module.vfs.physfs.internal.dummy.PhysFsWrapperDummy;
import jni.PhysFsWrapperNative;

/**
 * Different implementation to reach native code are possible, this allow to create unit tests without invoking the native code.
 * As the native code is not yet compiled, it would not be possible to run unit tests without this.
 *
 * @author Grégory Van den Borre
 */
public class PhysFsImplementationFactory {

    /**
     * Once set to true, the test implementation (not using real native code) will be used.
     * This is package protected and must only be invoked by unit tests.
     * Complete tests (using real native code) can be done in their respective sub projects(win64, linux64,...)
     */
    static boolean test = false;

    public static PhysFsWrapperImplementation getImplementation() {
        if(test) {
            return new PhysFsWrapperDummy();
        }
        return new PhysFsWrapperNative();
    }

}
