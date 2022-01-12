package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.charset.StandardCharsets;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;

/**
 * 数组工具类
 */
@Slf4j
public final class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

  /**
   * 字符数组转换为指定字符集的字节数组
   *
   * @param chars    字符数组
   * @param charsets 字符集
   * @return 指定字符集的字节数组
   */
  public static byte[] toBytes(@NonNull char[] chars, @NonNull Charset charsets) {
    return new String(chars).getBytes(charsets);
  }

  /**
   * 字符数组转换为指定字符集的字节数组
   *
   * @param chars       字符数组
   * @param charsetName 字符集
   * @return 指定字符集的字节数组
   */
  public static byte[] toBytes(@NonNull char[] chars, @NonNull String charsetName) throws UnsupportedEncodingException {
    return new String(chars).getBytes(charsetName);
  }

  /**
   * 字符数组转换为 UTF-8 字符集的字节数组
   *
   * @param chars 字符数组
   * @return UTF-8 字符集的字节数组
   */
  public static byte[] toBytes(@NonNull char[] chars) {
    return new String(chars).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * 字节数组转换为指定字符集的字符数组
   *
   * @param bytes    字节数组
   * @param charsets 字符集
   * @return 指定字符集的字符数组
   */
  public static char[] toChars(@NonNull byte[] bytes, @NonNull Charset charsets) {
    return new String(bytes, charsets).toCharArray();
  }

  /**
   * 字节数组转换为指定字符集的字符数组
   *
   * @param bytes       字节数组
   * @param charsetName 字符集
   * @return 指定字符集的字符数组
   */
  public static char[] toChars(@NonNull byte[] bytes, @NonNull String charsetName) throws UnsupportedEncodingException {
    return new String(bytes, charsetName).toCharArray();
  }

  /**
   * 字节数组转换为 UTF-8 字符集的字符数组
   *
   * @param bytes 字节数组
   * @return UTF-8 字符集的字符数组
   */
  public static char[] toChars(@NonNull byte[] bytes) {
    return new String(bytes).toCharArray();
  }

  /**
   * 从指定下标开始，将后面的元素往前复制指定位数
   *
   * @param array      数组
   * @param startIndex 开始下标
   * @param length     位数
   * @return 数组
   */
  public static Object moveForward(@NonNull final Object array, final int startIndex, final int length) {
    // 不是数组
    if (!(array instanceof Object[] || array instanceof int[] || array instanceof long[] || array instanceof double[] || array instanceof float[] || array instanceof boolean[] || array instanceof short[] || array instanceof byte[] || array instanceof char[])) {
      throw new IllegalArgumentException("Array: should be an array");
    }
    // 开始下标或位数不正确
    int arrayLen = getLength(array);
    if (startIndex < 0 || startIndex >= arrayLen || arrayLen - length < 0) {
      throw new IndexOutOfBoundsException("StartIndex: " + startIndex + ", Length:" + length);
    }

    final Object result = Array.newInstance(array.getClass().getComponentType(), arrayLen);
    System.arraycopy(array, 0, result, 0, startIndex);
    System.arraycopy(array, startIndex + length, result, startIndex, arrayLen - length - startIndex);
    return result;
  }

  /**
   * 删除指定下标的元素
   *
   * @param array              数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  private static Object remove(@NonNull final Object array, final int index, @NonNull final Object lastElementValue) {
    Object result = moveForward(array, index, 1);
    if (array instanceof int[]) {
      ((int[]) result)[((int[]) result).length - 1] = (int) lastElementValue;
    } else if (array instanceof long[]) {
      ((long[]) result)[((long[]) result).length - 1] = (long) lastElementValue;
    } else if (array instanceof double[]) {
      ((double[]) result)[((double[]) result).length - 1] = (double) lastElementValue;
    } else if (array instanceof float[]) {
      ((float[]) result)[((float[]) result).length - 1] = (float) lastElementValue;
    } else if (array instanceof boolean[]) {
      ((boolean[]) result)[((boolean[]) result).length - 1] = (boolean) lastElementValue;
    } else if (array instanceof short[]) {
      ((short[]) result)[((short[]) result).length - 1] = (short) lastElementValue;
    } else if (array instanceof byte[]) {
      ((byte[]) result)[((byte[]) result).length - 1] = (byte) lastElementValue;
    } else if (array instanceof char[]) {
      ((char[]) result)[((char[]) result).length - 1] = (char) lastElementValue;
    }else {
      ((Object[]) result)[((Object[]) result).length - 1] = lastElementValue;
    }
    return result;
  }

  public static <T> T[] remove(@NonNull final T[] array, final int index, @NonNull final Object lastElementValue) {
    return (T[]) remove((Object) array, index, lastElementValue);
  }

  public static boolean[] remove(@NonNull final boolean[] array, final int index, @NonNull final Object lastElementValue) {
    return (boolean[]) remove((Object) array, index, lastElementValue);
  }

  public static byte[] remove(@NonNull final byte[] array, final int index, @NonNull final Object lastElementValue) {
    return (byte[]) remove((Object) array, index, lastElementValue);
  }

  public static char[] remove(@NonNull final char[] array, final int index, @NonNull final Object lastElementValue) {
    return (char[]) remove((Object) array, index, lastElementValue);
  }

  public static double[] remove(@NonNull final double[] array, final int index, @NonNull final Object lastElementValue) {
    return (double[]) remove((Object) array, index, lastElementValue);
  }

  public static float[] remove(@NonNull final float[] array, final int index, @NonNull final Object lastElementValue) {
    return (float[]) remove((Object) array, index, lastElementValue);
  }

  public static short[] remove(@NonNull final short[] array, final int index, @NonNull final Object lastElementValue) {
    return (short[]) remove((Object) array, index, lastElementValue);
  }

  public static int[] remove(@NonNull final int[] array, final int index, @NonNull final Object lastElementValue) {
    return (int[]) remove((Object) array, index, lastElementValue);
  }

  public static long[] remove(@NonNull final long[] array, final int index, @NonNull final Object lastElementValue) {
    return (long[]) remove((Object) array, index, lastElementValue);
  }
}
