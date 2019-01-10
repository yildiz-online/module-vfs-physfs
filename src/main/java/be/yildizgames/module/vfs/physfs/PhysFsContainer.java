/*
 *
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

package be.yildizgames.module.vfs.physfs;

import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.VfsFile;
import be.yildizgames.module.vfs.VfsFileEditable;
import jni.PhysFsContainerNative;

class PhysFsContainer implements VfsContainer {

    private final NativePointer pointer;

    PhysFsContainer(NativePointer pointer) {
        super();
        this.pointer = pointer;
    }

    public VfsFile openFile(String name) {
        long address = PhysFsContainerNative.openFile(this.pointer.getPointerAddress(), name);
        return new PhysFsFile(NativePointer.create(address));
    }

    public VfsFileEditable openFileToWrite(String name) {
        long address = PhysFsContainerNative.openFileToWrite(this.pointer.getPointerAddress(), name);
        return new PhysFsFileEditable(NativePointer.create(address));
    }
}
