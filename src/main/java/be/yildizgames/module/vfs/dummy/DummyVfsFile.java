package be.yildizgames.module.vfs.dummy;

import be.yildizgames.module.vfs.VfsFile;

/**
 * @author Grégory Van den Borre
 */
public class DummyVfsFile implements VfsFile {

    @Override
    public long getSize() {
        return 0;
    }
}
