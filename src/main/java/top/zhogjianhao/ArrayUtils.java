package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.charset.StandardCharsets;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

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
    if (startIndex < 0 || length < 1 || startIndex >= arrayLen || arrayLen - length < 0) {
      throw new IndexOutOfBoundsException("StartIndex: " + startIndex + ", Length:" + length);
    }

    // 根据 array 创建结果数组
    final Object result = Array.newInstance(array.getClass().getComponentType(), arrayLen);
    // 将 0 ~ 开始下标 的内容先 copy 到结果数组
    System.arraycopy(array, 0, result, 0, startIndex);
    // 再将 开始下标 + 位数 ~ 结尾 的内容 copy 到结果数组
    System.arraycopy(array, startIndex + length, result, startIndex, arrayLen - length - startIndex);
    return result;
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  private static Object remove(@NonNull final Object array, final int index, @NonNull final Object lastElementValue) {
    Object result = moveForward(array, index, 1);
    // 给数组最后一个元素赋值
    if (array instanceof int[]) {
      ((int[]) result)[((int[]) result).length - 1] = (int) lastElementValue;
    } else if (array instanceof long[]) {
      ((long[]) result)[((long[]) result).length - 1] = (long) lastElementValue;
    } else if (array instanceof double[]) {
      ((double[]) result)[((double[]) result).length - 1] = (double) lastElementValue;
    } else if (array instanceof float[]) {
      ((float[]) result)[((float[]) result).length - 1] = (float) lastElementValue;
    } else if (array instanceof byte[]) {
      ((byte[]) result)[((byte[]) result).length - 1] = (byte) lastElementValue;
    } else if (array instanceof char[]) {
      ((char[]) result)[((char[]) result).length - 1] = (char) lastElementValue;
    } else if (array instanceof boolean[]) {
      ((boolean[]) result)[((boolean[]) result).length - 1] = (boolean) lastElementValue;
    } else if (array instanceof short[]) {
      ((short[]) result)[((short[]) result).length - 1] = (short) lastElementValue;
    } else {
      ((Object[]) result)[((Object[]) result).length - 1] = lastElementValue;
    }
    return result;
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @param <T>              数组类型
   * @return 数组
   */
  public static <T> T[] remove(@NonNull final T[] array, final int index, @NonNull final Object lastElementValue) {
    return (T[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static boolean[] remove(@NonNull final boolean[] array, final int index, @NonNull final Object lastElementValue) {
    return (boolean[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static byte[] remove(@NonNull final byte[] array, final int index, @NonNull final Object lastElementValue) {
    return (byte[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static char[] remove(@NonNull final char[] array, final int index, @NonNull final Object lastElementValue) {
    return (char[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static double[] remove(@NonNull final double[] array, final int index, @NonNull final Object lastElementValue) {
    return (double[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static float[] remove(@NonNull final float[] array, final int index, @NonNull final Object lastElementValue) {
    return (float[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static short[] remove(@NonNull final short[] array, final int index, @NonNull final Object lastElementValue) {
    return (short[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static int[] remove(@NonNull final int[] array, final int index, @NonNull final Object lastElementValue) {
    return (int[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 删除指定下标的元素
   *
   * @param array            数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static long[] remove(@NonNull final long[] array, final int index, @NonNull final Object lastElementValue) {
    return (long[]) remove((Object) array, index, lastElementValue);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  private static Object fill(@NonNull final Object array, final int fromIndex, final int toIndex, final Object val) {
    int arrayLen = getLength(array);
    Object result = array;
    // 此处不减 1，因为 Arrays.fill 的 toIndex 要 +1 才能填充完最后一个元素
    if (toIndex > arrayLen) {
      // 根据结束下标创建结果数组
      result = Array.newInstance(array.getClass().getComponentType(), toIndex);
      // 将内容 copy 到结果数组
      System.arraycopy(array, 0, result, 0, arrayLen);
    }
    if (result instanceof int[]) {
      Arrays.fill((int[]) result, fromIndex, toIndex, (int) val);
    } else if (result instanceof long[]) {
      Arrays.fill((long[]) result, fromIndex, toIndex, (long) val);
    } else if (result instanceof double[]) {
      Arrays.fill((double[]) result, fromIndex, toIndex, (double) val);
    } else if (result instanceof float[]) {
      Arrays.fill((float[]) result, fromIndex, toIndex, (float) val);
    } else if (result instanceof byte[]) {
      Arrays.fill((byte[]) result, fromIndex, toIndex, (byte) val);
    } else if (result instanceof char[]) {
      Arrays.fill((char[]) result, fromIndex, toIndex, (char) val);
    } else if (result instanceof boolean[]) {
      Arrays.fill((boolean[]) result, fromIndex, toIndex, (boolean) val);
    } else if (result instanceof short[]) {
      Arrays.fill((short[]) result, fromIndex, toIndex, (short) val);
    } else {
      Arrays.fill((Object[]) result, fromIndex, toIndex, val);
    }
    return result;
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @param <T>       数组类型
   * @return 数组
   */
  public static <T> T[] fill(@NonNull final T[] array, final int fromIndex, final int toIndex, final Object val) {
    return (T[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static boolean[] fill(@NonNull final boolean[] array, final int fromIndex, final int toIndex, final Object val) {
    return (boolean[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static byte[] fill(@NonNull final byte[] array, final int fromIndex, final int toIndex, final Object val) {
    return (byte[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static char[] fill(@NonNull final char[] array, final int fromIndex, final int toIndex, final Object val) {
    return (char[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static double[] fill(@NonNull final double[] array, final int fromIndex, final int toIndex, final Object val) {
    return (double[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static float[] fill(@NonNull final float[] array, final int fromIndex, final int toIndex, final Object val) {
    return (float[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static short[] fill(@NonNull final short[] array, final int fromIndex, final int toIndex, final Object val) {
    return (short[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static int[] fill(@NonNull final int[] array, final int fromIndex, final int toIndex, final Object val) {
    return (int[]) fill((Object) array, fromIndex, toIndex, val);
  }

  /**
   * 填充，结束下标超出数组长度时，超出部分也填充
   *
   * @param array     数组
   * @param fromIndex 开始下标
   * @param toIndex   结束下标
   * @param val       填充值
   * @return 数组
   */
  public static long[] fill(@NonNull final long[] array, final int fromIndex, final int toIndex, final Object val) {
    return (long[]) fill((Object) array, fromIndex, toIndex, val);
  }
}
