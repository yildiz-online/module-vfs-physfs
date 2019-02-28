package jni;

public class PhysFsFileNative {

    private PhysFsFileNative() {
        super();
    }

    public static native int getSize(long pointer);

    public static native void write(long pointer, byte[] data);
}
