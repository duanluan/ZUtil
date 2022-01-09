package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.charset.StandardCharsets;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 字符数组工具
 */
@Slf4j
public final class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

  /**
   * 字符数组转字节数组
   *
   * @param chars 字符数组
   * @return 字节数组
   */
  public static byte[] toBytes(char[] chars, @NonNull Charset charsets) {
    CharBuffer cb = CharBuffer.allocate(chars.length).put(chars);
    try {
      return ((ByteBuffer) Charset.class.getMethod("encode", CharBuffer.class).invoke(charsets, cb)).array();
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 字符数组转字节数组
   *
   * @param chars 字符数组
   * @return 字节数组
   */
  public static byte[] toBytes(char[] chars) {
    CharBuffer cb = CharBuffer.allocate(chars.length).put(chars);
    ByteBuffer bb = StandardCharsets.UTF_8.encode(cb);
    return bb.array();
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
