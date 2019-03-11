package be.yildizgames.module.vfs.internal;

import be.yildizgames.module.vfs.Vfs;
import be.yildizgames.module.vfs.VfsArchiveFormat;
import be.yildizgames.module.vfs.VfsContainer;

import java.io.IOException;
import java.nio.file.Path;

public abstract class BaseVfs implements Vfs {

    private final HogFile hogFile = new HogFile();

    @Override
    public final VfsContainer createContainer(Path path, VfsArchiveFormat format) throws IOException {
        this.hogFile.createContainer(path);
        return this.registerContainer(path);
    }
}
