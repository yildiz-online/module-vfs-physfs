/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */
package be.yildizgames.module.vfs.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Hog file specs:
 * char sig[3] = {'D', 'H', 'F'}; // "DHF"=Descent HOG File
 *
 * struct {
 *  char file_name[13]; // Filename, padded to 13 bytes with 0s
 *  int file_size; // filesize in bytes
 *  char data[file_size]; // The file data
 * } FILE_STRUCT; // Repeated until the end of the file.
 *
 * @author Grégory Van den Borre
 */
class HogFile {

    private final Logger logger = LoggerFactory.getLogger(HogFile.class);

    final void createContainer(Path file) throws IOException {
        Files.write(file, "DHF".getBytes());
    }

    final void addFile(Path file, Path toAdd) {
        try {
            int nameLength = toAdd.getFileName().toString().length();
            if (nameLength > 13) {
                String name = toAdd.getFileName().toString().substring(nameLength - 14, nameLength - 1);
                Files.write(file, name.getBytes(), StandardOpenOption.APPEND);
            } else {
                String name = toAdd.getFileName().toString();
                byte[] nameBytes = name.getBytes();
                byte[] bytes = "\0\0\0\0\0\0\0\0\0\0\0\0\0".getBytes();
                if (nameLength >= 0) {
                    System.arraycopy(nameBytes, 0, bytes, 0, nameLength);
                }
                Files.write(file, bytes, StandardOpenOption.APPEND);
            }

            int fileSize = Integer.reverseBytes((int) toAdd.toFile().length());
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.putInt(fileSize);
            byte[] sizeByte = bb.array();
            Files.write(file, sizeByte , StandardOpenOption.APPEND);
            Files.write(file, Files.readAllBytes(toAdd), StandardOpenOption.APPEND);
        } catch (IOException e) {
            this.logger.error("Error adding file {} to archive {}.", toAdd.toString(), file.toString(), e);
        }
    }

}
