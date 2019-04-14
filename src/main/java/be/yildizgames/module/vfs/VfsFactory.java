package be.yildizgames.module.vfs;

import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.vfs.physfs.PhysFsWrapper;

/**
 * @author Grégory Van den Borre
 */
public class VfsFactory {

    public static Vfs getVfs(NativeResourceLoader loader) {
        return PhysFsWrapper.create(loader);
    }

    public static Vfs getVfs() {
        return PhysFsWrapper.create();
    }
}
