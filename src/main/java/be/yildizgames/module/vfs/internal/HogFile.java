package be.yildizgames.module.vfs.internal;

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
            e.printStackTrace();
        }
    }

}
