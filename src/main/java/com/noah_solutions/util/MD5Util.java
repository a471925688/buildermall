//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.noah_solutions.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    protected static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest messageDigest = null;

    public MD5Util() {
    }

    private static String getSystemPath() {
        String path = MD5Util.class.getResource("").getPath();
        String parentPath = path.split("/WEB-INF")[0].concat("/soft/apps/");
        parentPath = parentPath.substring(1);
        parentPath = parentPath.replace("%20", " ");
        return parentPath;
    }

    public static String getStringMD5(String str) throws Exception {
        messageDigest.update(str.getBytes("UTF-8"));
        return bufferToHex(messageDigest.digest());
    }

    public static String getFileMD5String(String fileName) throws Exception {
        File f = new File(getSystemPath().concat(fileName));
        return getFileMD5String(f);
    }

    public static String getFileMD5String(File file) throws Exception {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(MapMode.READ_ONLY, 0L, file.length());
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for(int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util.");
            var1.printStackTrace();
        }

    }
}
