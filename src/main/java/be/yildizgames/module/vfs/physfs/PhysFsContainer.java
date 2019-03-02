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

package be.yildizgames.module.vfs.physfs;

import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.VfsFile;
import be.yildizgames.module.vfs.internal.BaseVfsContainer;
import jni.PhysFsContainerNative;

import java.nio.file.Path;

/**
 * PhysFS implementation for a container.
 * @author Grégory Van den Borre
 */
class PhysFsContainer extends BaseVfsContainer implements VfsContainer {

    /**
     * Pointer address of the native object.
     */
    private final NativePointer pointer;

    /**
     * Create a new instance.
     * @param pointer Pointer to the native object.
     */
    PhysFsContainer(final NativePointer pointer, Path path) {
        super(path);
        this.pointer = pointer;
    }

    @Override
    public final VfsFile openFile(final String name) {
        long address = PhysFsContainerNative.openFile(this.pointer.getPointerAddress(), name);
        return new PhysFsFile(NativePointer.create(address));
    }

    @Override
    protected void reinit() {
        PhysFsContainerNative.reinit(this.pointer.getPointerAddress());
    }
}
