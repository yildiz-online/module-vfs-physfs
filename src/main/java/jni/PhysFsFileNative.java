package jni;

public class PhysFsFileNative {

    private PhysFsFileNative() {
        super();
    }

    public static native long getSize(long pointer);
}
