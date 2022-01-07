package top.zhogjianhao;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 字符数组工具
 *
 * @author wjy
 */
public final class CharArrayUtils {

    /**
     * 字符数组 => 字节数组
     *
     * @param chars 字符数组
     * @return
     */
    public static byte[] toBytes(char[] chars) {
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = StandardCharsets.UTF_8.encode(cb);
        byte[] bytes = bb.array();
        // 去除数组尾部的0元素
        int endIndex = bytes.length - 1;
        while (bytes[endIndex] == (byte) 0) {
            endIndex--;
        }
        return endIndex == bytes.length - 1 ? bytes : Arrays.copyOfRange(bytes, 0, endIndex + 1);
    }

    /**
     * 字节数组 => 字符数组
     *
     * @param bytes
     * @return
     */
    public static char[] toChars(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = StandardCharsets.UTF_8.decode(bb);
        return cb.array();
    }

    /**
     * 擦除字符数组
     *
     * @param chars 字符数组
     */
    public static void wipe(char[] chars) {
        Arrays.fill(chars, ' ');
    }

    /**
     * 擦除字节数组
     *
     * @param bytes 字节数组
     */
    public static void wipe(byte[] bytes) {
        Arrays.fill(bytes, (byte) 0);
    }
}

