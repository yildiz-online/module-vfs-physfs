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

import be.yildizgames.common.jni.Native;
import be.yildizgames.common.jni.NativePointer;
import be.yildizgames.common.libloader.GlobalNativeResourceLoader;
import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.vfs.VfsEngine;
import be.yildizgames.module.vfs.VfsArchiveInfo;
import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.physfs.exception.VfsException;
import be.yildizgames.module.vfs.physfs.internal.PhysFsImplementationFactory;
import be.yildizgames.module.vfs.physfs.internal.PhysFsWrapperImplementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * PhysFs implementation for the VFS.
 * @author Grégory Van den Borre
 */
public class PhysFsVfsEngine implements VfsEngine, Native {

    /**
     * Pointer address of the native object.
     */
    private final NativePointer pointer;

    /**
     * Implementation to reach native code.
     */
    private final PhysFsWrapperImplementation implementation = PhysFsImplementationFactory.getImplementation();

    /**
     * Create a new instance, uncompress and load the native libs if necessary, and initialize the PhysFS library.
     * @param loader Will unzip and load the native libraries.
     */
    private PhysFsVfsEngine(final NativeResourceLoader loader) {
        super();
        System.Logger logger = System.getLogger(PhysFsVfsEngine.class.getName());
        logger.log(System.Logger.Level.INFO,"Initializing PhysFs virtual file system component...");
        this.implementation.loadLibraries(loader);
        this.pointer = NativePointer.create(this.implementation.initialize());
        logger.log(System.Logger.Level.INFO,"Initializing PhysFs virtual file system component complete.");
    }

    /**
     * Create a new instance, the native resources will be loaded from the classpath jars.
     * @return The created instance.
     */
    static PhysFsVfsEngine create() {
        return create(GlobalNativeResourceLoader.getInstance().getLoader());
    }

    /**
     * Create a new instance.
     * @param loader Loader to load the native resources.
     * @return The created instance.
     */
    static PhysFsVfsEngine create(final NativeResourceLoader loader) {
        return new PhysFsVfsEngine(loader);
    }

    @Override
    public final VfsContainer registerContainer(final Path path) {
        if(Files.notExists(Objects.requireNonNull(path))) {
            throw VfsException.containerNotExists(path);
        }
        return new PhysFsContainer(this.implementation.getContainerImplementation(), path, NativePointer.create(this.implementation.registerContainer(this.pointer.getPointerAddress(), path.toString())));
    }

    @Override
    public final List<VfsArchiveInfo> getSupportedArchiveInfo() {
        return Arrays.stream(this.implementation.getSupportedArchiveType(this.pointer.getPointerAddress()))
                .mapToObj(NativePointer::create)
                .map(p -> new PhysFsArchiveInfo(this.implementation.getArchiveInfoImplementation(), p))
                .collect(Collectors.toList());
    }

    public final List<String> enumerateFiles(String dir) {
        return Arrays.asList(this.implementation.enumerateFiles(this.pointer.getPointerAddress(), Objects.requireNonNull(dir)));
    }

    @Override
    public final NativePointer getPointer() {
        return this.pointer;
    }

    @Override
    public void delete() {
        //FIXME implements
    }
}
