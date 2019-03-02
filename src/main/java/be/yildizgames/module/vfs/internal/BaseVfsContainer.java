package be.yildizgames.module.vfs.internal;

import be.yildizgames.module.vfs.VfsContainer;
import be.yildizgames.module.vfs.VfsFile;

import java.nio.file.Path;

public abstract class BaseVfsContainer implements VfsContainer {

    private final HogFile hogFile = new HogFile();
    private final Path path;

    public BaseVfsContainer(Path path) {
        this.path = path;
    }

    @Override
    public final VfsFile addFile(Path file) {
        hogFile.addFile(this.path, file);
        this.reinit();
        return this.openFile(file.getFileName().toString());
    }

    protected abstract void reinit();
}
