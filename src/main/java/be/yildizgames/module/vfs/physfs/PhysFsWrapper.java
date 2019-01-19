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

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.libloader.GlobalNativeResourceLoader;
import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.vfs.Vfs;
import be.yildizgames.module.vfs.VfsArchiveInfo;
import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.exception.VfsException;
import jni.PhysFsWrapperNative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PhysFs implementation for the VFS.
 * @author Grégory Van den Borre
 */
public class PhysFsWrapper implements Vfs {

    /**
     * Pointer address of the native object.
     */
    private final NativePointer pointer;

    /**
     * Create a new instance, uncompress and load the native libs if necessary, and initialize the PhysFS library.
     * @param loader Will unzip and  load the native libraries.
     */
    private PhysFsWrapper(final NativeResourceLoader loader) {
        super();
        ImplementationException.throwForNull(loader);
        Logger logger = LoggerFactory.getLogger(PhysFsWrapper.class);
        logger.info("Initializing PhysFs vfs component...");
        loader.loadBaseLibrary();
        loader.loadLibrary("libyildizphysfs");
        this.pointer = NativePointer.create(PhysFsWrapperNative.initialize());
        logger.info("PhysFs vfs component initialized.");
    }

    /**
     * Create a new instance, the native resources will be loaded from the classpath jars.
     * @return The created instance.
     */
    public static Vfs create() {
        return create(GlobalNativeResourceLoader.getInstance().getLoader());
    }

    /**
     * Create a new instance.
     * @param loader Loader to load the native resources.
     * @return The created instance.
     */
    public static Vfs create(final NativeResourceLoader loader) {
        return new PhysFsWrapper(loader);
    }

    @Override
    public final VfsContainer registerContainer(final Path path) {
        ImplementationException.throwForNull(path);
        if(Files.notExists(path)) {
            throw VfsException.containerNotExists(path);
        }
        return new PhysFsContainer(NativePointer.create(PhysFsWrapperNative.registerContainer(this.pointer.getPointerAddress(), path.toString())));
    }

    @Override
    public final List<VfsArchiveInfo> getSupportedArchiveInfo() {
        return Arrays.stream(PhysFsWrapperNative.getSupportedArchiveType(this.pointer.getPointerAddress()))
                .mapToObj(NativePointer::create)
                .map(PhysFsArchiveInfo::new)
                .collect(Collectors.toList());
    }

}
