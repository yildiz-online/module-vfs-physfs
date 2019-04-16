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

package be.yildizgames.module.vfs.physfs.exception;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Technical exception for Virtual file systems.
 * @author Grégory Van den Borre
 */
public class VfsException extends IllegalStateException {

    /**
     * Private constructor, New instance are from static methods.
     * @param message Error message.
     */
    private VfsException(String message) {
        super(message);
    }

    /**
     * Private constructor, New instance are from static methods.
     * @param root Root exception.
     */
    private VfsException(Exception root) {
        super(root);
    }

    /**
     * Exception thrown when a VFS container does not exists.
     * @param container Container name.
     * @return The created exception.
     */
    public static VfsException containerNotExists(Path container) {
        return new VfsException("The container " + container + " does not exists");
    }

    /**
     * Exception thrown when an IO is thrown.
     * @param e Root io exception.
     * @return The created exception.
     */
    public static VfsException io(IOException e) {
        return new VfsException(e);
    }
}
