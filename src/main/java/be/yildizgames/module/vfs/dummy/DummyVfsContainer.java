package be.yildizgames.module.vfs.dummy;

import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.VfsFile;

import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
public class DummyVfsContainer implements VfsContainer {

    @Override
    public VfsFile openFile(String name) {
        return new DummyVfsFile();
    }

    @Override
    public VfsFile addFile(Path path) {
        return new DummyVfsFile();
    }
}
