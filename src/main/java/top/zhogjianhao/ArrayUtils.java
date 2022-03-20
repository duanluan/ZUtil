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
  public static byte[] toBytes(@NonNull final char[] chars, @NonNull final Charset charsets) {
    return new String(chars).getBytes(charsets);
  }

  /**
   * 字符数组转换为指定字符集的字节数组
   *
   * @param chars       字符数组
   * @param charsetName 字符集
   * @return 指定字符集的字节数组
   * @throws UnsupportedEncodingException 字符编码不支持
   */
  public static byte[] toBytes(@NonNull final char[] chars, @NonNull final String charsetName) throws UnsupportedEncodingException {
    return new String(chars).getBytes(charsetName);
  }

  /**
   * 字符数组转换为 UTF-8 字符集的字节数组
   *
   * @param chars 字符数组
   * @return UTF-8 字符集的字节数组
   */
  public static byte[] toBytes(@NonNull final char[] chars) {
    return new String(chars).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * 字节数组转换为指定字符集的字符数组
   *
   * @param bytes    字节数组
   * @param charsets 字符集
   * @return 指定字符集的字符数组
   */
  public static char[] toChars(@NonNull final byte[] bytes, @NonNull final Charset charsets) {
    return new String(bytes, charsets).toCharArray();
  }

  /**
   * 字节数组转换为指定字符集的字符数组
   *
   * @param bytes       字节数组
   * @param charsetName 字符集
   * @return 指定字符集的字符数组
   * @throws UnsupportedEncodingException 字符编码不支持
   */
  public static char[] toChars(@NonNull final byte[] bytes, @NonNull final String charsetName) throws UnsupportedEncodingException {
    return new String(bytes, charsetName).toCharArray();
  }

  /**
   * 字节数组转换为 UTF-8 字符集的字符数组
   *
   * @param bytes 字节数组
   * @return UTF-8 字符集的字符数组
   */
  public static char[] toChars(@NonNull final byte[] bytes) {
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

  /**
   * 不确定类型是否包含
   *
   * @param array 数组
   * @param val   包含内容
   * @return 是否包含
   */
  private static boolean containsObject(@NonNull final Object array, final Object val) {
    if (array instanceof int[]) {
      return contains((int[]) array, ((int[]) val)[0]);
    } else if (array instanceof long[]) {
      return contains((long[]) array, ((long[]) val)[0]);
    } else if (array instanceof double[]) {
      return contains((double[]) array, ((double[]) val)[0]);
    } else if (array instanceof float[]) {
      return contains((float[]) array, ((float[]) val)[0]);
    } else if (array instanceof byte[]) {
      return contains((byte[]) array, ((byte[]) val)[0]);
    } else if (array instanceof char[]) {
      return contains((char[]) array, ((char[]) val)[0]);
    } else if (array instanceof boolean[]) {
      return contains((boolean[]) array, ((boolean[]) val)[0]);
    } else if (array instanceof short[]) {
      return contains((short[]) array, ((short[]) val)[0]);
    } else {
      return contains((Object[]) array, ((Object[]) val)[0]);
    }
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  private static Object removeAllElements(@NonNull final Object array, @NonNull final Object... vals) {
    Object result;
    if (array instanceof int[]) {
      result = ((int[]) array).clone();
    } else if (array instanceof long[]) {
      result = ((long[]) array).clone();
    } else if (array instanceof double[]) {
      result = ((double[]) array).clone();
    } else if (array instanceof float[]) {
      result = ((float[]) array).clone();
    } else if (array instanceof byte[]) {
      result = ((byte[]) array).clone();
    } else if (array instanceof char[]) {
      result = ((char[]) array).clone();
    } else if (array instanceof boolean[]) {
      result = ((boolean[]) array).clone();
    } else if (array instanceof short[]) {
      result = ((short[]) array).clone();
    } else {
      result = ((Object[]) array).clone();
    }
    for (Object val : vals) {
      while (containsObject(result, val)) {
        if (result instanceof int[]) {
          result = removeElement((int[]) result, ((int[]) val)[0]);
        } else if (array instanceof long[]) {
          result = removeElement((long[]) result, ((long[]) val)[0]);
        } else if (array instanceof double[]) {
          result = removeElement((double[]) result, ((double[]) val)[0]);
        } else if (array instanceof float[]) {
          result = removeElement((float[]) result, ((float[]) val)[0]);
        } else if (array instanceof byte[]) {
          result = removeElement((byte[]) result, ((byte[]) val)[0]);
        } else if (array instanceof char[]) {
          result = removeElement((char[]) result, ((char[]) val)[0]);
        } else if (array instanceof boolean[]) {
          result = removeElement((boolean[]) result, ((boolean[]) val)[0]);
        } else if (array instanceof short[]) {
          result = removeElement((short[]) result, ((short[]) val)[0]);
        } else {
          result = removeElement((Object[]) result, ((Object[]) val)[0]);
        }
      }
    }
    return result;
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @param <T>   数组类型
   * @return 数组
   */
  public static <T> T[] removeAllElements(@NonNull final T[] array, @NonNull final T... vals) {
    return (T[]) removeAllElements((Object) array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static boolean[] removeAllElements(@NonNull final boolean[] array, @NonNull final boolean... vals) {
    return (boolean[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static byte[] removeAllElements(@NonNull final byte[] array, @NonNull final byte... vals) {
    return (byte[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static char[] removeAllElements(@NonNull final char[] array, @NonNull final char... vals) {
    return (char[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static double[] removeAllElements(@NonNull final double[] array, @NonNull final double... vals) {
    return (double[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static float[] removeAllElements(@NonNull float[] array, @NonNull final float... vals) {
    return (float[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static short[] removeAllElements(@NonNull final short[] array, @NonNull final short... vals) {
    return (short[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static int[] removeAllElements(@NonNull final int[] array, @NonNull final int... vals) {
    return (int[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 删除所有匹配项
   *
   * @param array 数组
   * @param vals  需要删除的内容
   * @return 数组
   */
  public static long[] removeAllElements(@NonNull final long[] array, @NonNull final long... vals) {
    return (long[]) removeAllElements(array, (Object) vals);
  }

  /**
   * 从指定索引开始查找数组中指定的字符串
   *
   * @param chars       字符数组
   * @param valueToFind 要查找的字符串
   * @param startIndex  开始查找的索引
   * @return 要查找的字符串在数组中的开始位置
   */
  public static int indexOf(@NonNull final char[] chars, final @NonNull String valueToFind, int startIndex) {
    if (ArrayUtils.isEmpty(chars)) {
      throw new IllegalArgumentException("Chars: should not be empty");
    }
    if (StringUtils.isBlank(valueToFind)) {
      throw new IllegalArgumentException("ValueToFind: should not be blank");
    }
    if (startIndex < 0) {
      throw new IllegalArgumentException("StartIndex: should be greater than or equal to 0");
    }
    char[] vals = valueToFind.toCharArray();
    for (int i = 0; i < vals.length; i++) {
      int index = indexOf(chars, vals[i], startIndex);
      if (index == -1) {
        return -1;
      }
      if (i == vals.length - 1) {
        return index - valueToFind.length() + 1;
      }
      startIndex += 1;
    }
    return -1;
  }

  /**
   * 查找数组中指定的字符串
   *
   * @param chars       字符数组
   * @param valueToFind 要查找的字符串
   * @return 要查找的字符串在数组中的开始位置
   */
  public static int indexOf(@NonNull final char[] chars, final @NonNull String valueToFind) {
    return indexOf(chars, valueToFind, 0);
  }

  /**
   * 所有元素的长度总和
   *
   * @param strs 字符串数组
   * @return 所有元素的长度总和
   */
  public static int allLength(@NonNull final String... strs) {
    if (StringUtils.isAllBlank(strs)) {
      throw new IllegalArgumentException("Strs: should not be all blank");
    }
    int result = 0;
    for (String str : strs) {
      result += str.length();
    }
    return result;
  }
}
