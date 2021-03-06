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

package jni;

import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.vfs.physfs.internal.PhysFsArchiveInfoImplementation;
import be.yildizgames.module.vfs.physfs.internal.PhysFsContainerImplementation;
import be.yildizgames.module.vfs.physfs.internal.PhysFsWrapperImplementation;

/**
 * Interface to the JNI code.
 * @author Grégory van den Borre
 */
public class PhysFsWrapperNative implements PhysFsWrapperImplementation {

    private final PhysFsContainerImplementation containerImplementation = new PhysFsContainerNative();

    private final PhysFsArchiveInfoImplementation archiveInfoImplementation = new PhysFsArchiveInfoNative();

    @Override
    public native long initialize();

    @Override
    public native long registerContainer(long pointer, String path);

    @Override
    public native long[] getSupportedArchiveType(long pointer);

    @Override
    public native String[] enumerateFiles(long pointer, String dir);

    @Override
    public void loadLibraries(NativeResourceLoader loader) {
        loader.loadBaseLibrary();
        loader.loadLibrary("libyildizphysfs");
    }

    @Override
    public PhysFsContainerImplementation getContainerImplementation() {
        return this.containerImplementation;
    }

    @Override
    public PhysFsArchiveInfoImplementation getArchiveInfoImplementation() {
        return this.archiveInfoImplementation;
    }
}
