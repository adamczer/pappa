package jniexample.juav;

/**
 * Created by gassa on 2/14/16.
 */
public class NativeHelloworld {
    /**
     * this function just do print out, no native depeendies are required.
     */
    public static native void nativePrint1(String str);

    /**
     * this function has native dependencies from native directories.
     */
    public static native void nativePrint2(String str);
}
