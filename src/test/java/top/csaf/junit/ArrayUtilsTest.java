package top.csaf.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.ArrayUtils;
import top.csaf.charset.StandardCharsets;
import top.csaf.util.ReflectionTestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("数组工具类测试")
class ArrayUtilsTest {

  @DisplayName("toBytesAndToString：转字节数组和转字符串")
  @Test
  void toBytesAndToString() throws UnsupportedEncodingException {
    String str = "中国 No.1";
    String str1 = "?? No.1";
    char[] chars = str.toCharArray();

    assertThrows(NullPointerException.class, () -> ArrayUtils.toBytes(null, StandardCharsets.GB2312));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toBytes(chars, (Charset) null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toString(null, StandardCharsets.GB2312));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toString(new byte[]{}, (Charset) null));
    // 指定字符集的字符数组转字节数组
    assertEquals(str1, ArrayUtils.toString(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1));
    assertEquals(str, ArrayUtils.toString(ArrayUtils.toBytes(chars, StandardCharsets.GB2312), StandardCharsets.GB2312));

    assertThrows(NullPointerException.class, () -> ArrayUtils.toBytes(null, StandardCharsets.GB2312.name()));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toBytes(chars, (String) null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toString(null, StandardCharsets.GB2312.name()));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toString(new byte[]{}, (String) null));
    assertEquals(str1, ArrayUtils.toString(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1.name()), StandardCharsets.ISO_8859_1.name()));
    assertEquals(str, ArrayUtils.toString(ArrayUtils.toBytes(chars, StandardCharsets.GB2312.name()), StandardCharsets.GB2312.name()));

    assertThrows(NullPointerException.class, () -> ArrayUtils.toBytes(null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toString(null));
    // 默认 UTF-8 字符集的字符数组转字节数组
    assertEquals(str, ArrayUtils.toString(ArrayUtils.toBytes(chars)));
  }

  @DisplayName("toChars：转字符数组")
  @Test
  void toChars() throws UnsupportedEncodingException {
    char[] chars = "中国 No.1".toCharArray();
    char[] chars1 = "?? No.1".toCharArray();

    // 异常
    assertThrows(NullPointerException.class, () -> ArrayUtils.toChars(null, StandardCharsets.GB2312));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toChars(ArrayUtils.toBytes(chars), (Charset) null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toChars(null, StandardCharsets.GB2312.name()));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toChars(ArrayUtils.toBytes(chars), (String) null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.toChars(null));

    assertArrayEquals(chars1, ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1));
    assertArrayEquals(chars, ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.GB2312), StandardCharsets.GB2312));
    assertArrayEquals(chars1, ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1.name()), StandardCharsets.ISO_8859_1.name()));
    assertArrayEquals(chars, ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.GB2312.name()), StandardCharsets.GB2312.name()));
    assertArrayEquals(chars, ArrayUtils.toChars(ArrayUtils.toBytes(chars)));
    assertArrayEquals(chars, ArrayUtils.toChars(ArrayUtils.toBytes(chars)));
  }

  @DisplayName("removeAndMoveForward：删除元素和移动复制元素")
  @Test
  void removeAndMoveForward() {
    // assertThrows(NullPointerException.class, () -> ArrayUtils.moveForward(null, 1, 1));
    // 不是数组
    // assertThrows(IllegalArgumentException.class, () -> ArrayUtils.moveForward(1, 1, 1));
    // 下标错误
    assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.moveForward(new int[]{1}, -1, 1));
    // assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.moveForward(new int[]{}, 1, -1));
    // assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.moveForward(new int[]{}, 1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtils.moveForward(new int[]{1}, 0, 2));

    // null
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new Class[]{Object.class, int.class, Object.class}, null, 0, 1));
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new Class[]{Object.class, int.class, Object.class}, new Integer[]{1}, 0, null));
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new Class[]{Object.class, int.class, Object.class}, null, 0, null));

    // lastElementValue 类型错误
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new int[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new int[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new long[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new double[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new float[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new byte[]{1}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new char[]{1}, 0, ""));
    // assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new boolean[]{true}, 0, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new short[]{1}, 0, ""));

    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((Integer[]) null, 0, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove(new Integer[]{}, 0, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((int[]) null, 0, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((long[]) null, 0, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((double[]) null, 0, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((float[]) null, 0, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((byte[]) null, 0, (byte) 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((char[]) null, 0, '1'));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove(null, 0, true));
    assertThrows(NullPointerException.class, () -> ArrayUtils.remove((short[]) null, 0, (short) 1));
    // 下标错误
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "remove", new int[]{1}, 0, null));

    int[] ints = {1, 2, 3, 4, 5};
    // 删除下标为 3 的元素后，最后一个元素默认为 9，即保持元素个数不变
    ints = ArrayUtils.remove(ints, 3, 9);
    // 下标为 3 的元素
    assertEquals(5, ints[3]);
    // 最后一个元素
    assertEquals(9, ints[ints.length - 1]);

    long[] longs = {1, 2, 3, 4, 5};
    longs = ArrayUtils.remove(longs, 3, 9L);
    assertEquals(5, longs[3]);
    assertEquals(9, longs[longs.length - 1]);

    double[] doubles = {1, 2, 3, 4, 5};
    doubles = ArrayUtils.remove(doubles, 3, 9.0);
    assertEquals(5, doubles[3]);
    assertEquals(9, doubles[doubles.length - 1]);

    float[] floats = {1, 2, 3, 4, 5};
    floats = ArrayUtils.remove(floats, 3, 9f);
    assertEquals(5, floats[3]);
    assertEquals(9, floats[floats.length - 1]);

    byte[] bytes = {1, 2, 3, 4, 5};
    bytes = ArrayUtils.remove(bytes, 3, (byte) 9);
    assertEquals(5, bytes[3]);
    assertEquals(9, bytes[bytes.length - 1]);

    char[] chars = {'1', '2', '3', '4', '5'};
    chars = ArrayUtils.remove(chars, 3, '9');
    assertEquals('5', chars[3]);
    assertEquals('9', chars[chars.length - 1]);

    boolean[] booleans = {true, false, true, false, true};
    booleans = ArrayUtils.remove(booleans, 3, false);
    assertTrue(booleans[3]);
    assertFalse(booleans[booleans.length - 1]);

    short[] shorts = {1, 2, 3, 4, 5};
    shorts = ArrayUtils.remove(shorts, 3, (short) 9);
    assertEquals(5, shorts[3]);
    assertEquals(9, shorts[shorts.length - 1]);

    Integer[] ints1 = {1, 2, 3, 4, 5};
    ints1 = ArrayUtils.remove(ints1, 3, 9);
    assertEquals(5, ints1[3]);
    assertEquals(9, ints1[ints1.length - 1]);
  }

  @DisplayName("fill：填充元素")
  @Test
  void fill() {
    // 不是数组
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", 1, 1, 2, 1));

    // null
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new Class[]{Object.class, int.class, int.class, Object.class}, null, 0, 1, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((int[]) null, 0, 1, 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((long[]) null, 0, 1, 1L));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill(null, 0, 1, 1.0));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((float[]) null, 0, 1, 1f));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((byte[]) null, 0, 1, (byte) 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((char[]) null, 0, 1, '1'));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill(null, 0, 1, true));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((short[]) null, 0, 1, (short) 1));
    assertThrows(NullPointerException.class, () -> ArrayUtils.fill((Integer[]) null, 0, 1, 9));

    // lastElementValue 类型错误
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new int[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new long[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new double[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new float[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new byte[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new char[]{1}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new boolean[]{true}, 0, 1, ""));
    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "fill", new short[]{1}, 0, 1, ""));

    int[] ints = {1, 2, 3, 4, 5};
    // 从下标为 3 开始填充 1，填充到下标为 5（含前不含后）的位置
    ints = ArrayUtils.fill(ints, 3, ints.length, 1);
    // 最后一个元素
    assertEquals(1, ints[ints.length - 1]);
    // 数组长度
    assertEquals(5, ints.length);

    long[] longs = {1, 2, 3, 4, 5};
    // 从下标为 3 开始填充 1，填充到下标为 6（就算含前不含后也超出了数组长度，会先扩充再填充）的位置
    longs = ArrayUtils.fill(longs, 3, longs.length + 1, 1L);
    assertEquals(1, longs[longs.length - 1]);
    assertEquals(6, longs.length);

    double[] doubles = {1, 2, 3, 4, 5};
    doubles = ArrayUtils.fill(doubles, 3, doubles.length, 1.0);
    assertEquals(1, doubles[doubles.length - 1]);
    assertEquals(5, doubles.length);

    float[] floats = {1, 2, 3, 4, 5};
    floats = ArrayUtils.fill(floats, 3, floats.length, 1f);
    assertEquals(1, floats[floats.length - 1]);
    assertEquals(5, floats.length);

    byte[] bytes = {1, 2, 3, 4, 5};
    bytes = ArrayUtils.fill(bytes, 3, bytes.length, (byte) 1);
    assertEquals(1, bytes[bytes.length - 1]);
    assertEquals(5, bytes.length);

    char[] chars = {'1', '2', '3', '4', '5'};
    chars = ArrayUtils.fill(chars, 3, chars.length, '1');
    assertEquals('1', chars[chars.length - 1]);
    assertEquals(5, chars.length);

    boolean[] booleans = {true, false, true, false, true};
    booleans = ArrayUtils.fill(booleans, 3, booleans.length, false);
    assertFalse(booleans[booleans.length - 1]);
    assertEquals(5, booleans.length);

    short[] shorts = {1, 2, 3, 4, 5};
    shorts = ArrayUtils.fill(shorts, 3, shorts.length, (short) 1);
    assertEquals(1, shorts[shorts.length - 1]);
    assertEquals(5, shorts.length);

    Integer[] ints1 = {1, 2, 3, 4, 5};
    ints1 = ArrayUtils.fill(ints1, 3, ints1.length, 9);
    assertEquals(9, ints1[ints1.length - 1]);
    assertEquals(5, ints1.length);
  }

  @DisplayName("removeAllElements：删除所有匹配元素")
  @Test
  void removeAllElements() {
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((int[]) null, 2));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new int[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((int[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((long[]) null, 2L));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new long[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((long[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((double[]) null, 2.0));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new double[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((double[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((float[]) null, 2f));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new float[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((float[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((byte[]) null, (byte) 2));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new byte[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((byte[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((char[]) null, '2'));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new char[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((char[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((boolean[]) null, true));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new boolean[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((boolean[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((short[]) null, (short) 2));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new short[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((short[]) null, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((Integer[]) null, 2));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements(new Integer[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.removeAllElements((Integer[]) null, null));
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "removeAllElements", new Class[]{Object.class, Object[].class}, null, new Object[]{2}));
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "removeAllElements", new Class[]{Object.class, Object[].class}, new int[]{}, null));
    assertThrows(NullPointerException.class, () -> ReflectionTestUtils.invokeMethod(ArrayUtils.class, "removeAllElements", new Class[]{Object.class, Object[].class}, null, null));

    assertArrayEquals(new int[]{1}, ArrayUtils.removeAllElements(new int[]{1, 2}, 2));
    assertArrayEquals(new long[]{1}, ArrayUtils.removeAllElements(new long[]{1, 2}, 2L));
    assertArrayEquals(new double[]{1}, ArrayUtils.removeAllElements(new double[]{1, 2}, 2.0));
    assertArrayEquals(new float[]{1}, ArrayUtils.removeAllElements(new float[]{1, 2}, 2f));
    assertArrayEquals(new byte[]{1}, ArrayUtils.removeAllElements(new byte[]{1, 2}, (byte) 2));
    assertArrayEquals(new char[]{'1'}, ArrayUtils.removeAllElements(new char[]{'1', '2'}, '2'));
    assertArrayEquals(new boolean[]{true}, ArrayUtils.removeAllElements(new boolean[]{true, false}, false));
    assertArrayEquals(new short[]{1}, ArrayUtils.removeAllElements(new short[]{1, 2}, (short) 2));
    assertArrayEquals(new Integer[]{1}, ArrayUtils.removeAllElements(new Integer[]{1, 2}, 2));
  }

  @DisplayName("indexOf：查找数组中指定的字符串")
  @Test
  void indexOf() {
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf((char[]) null, "", 0));
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf(new char[]{}, null, 0));
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf((char[]) null, null, 0));
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf((char[]) null, ""));
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf(new char[]{}, null));
    assertThrows(NullPointerException.class, () -> ArrayUtils.indexOf((char[]) null, null));

    // assertThrows(IllegalArgumentException.class, () -> ArrayUtils.indexOf(new char[]{}, ""));
    assertThrows(IllegalArgumentException.class, () -> ArrayUtils.indexOf(new char[]{'0'}, ""));
    assertThrows(IllegalArgumentException.class, () -> ArrayUtils.indexOf(new char[]{'0'}, "0", -1));

    char[] chars = "{1}{}".toCharArray();
    assertEquals(-1, ArrayUtils.indexOf(chars, "0}"));
    assertEquals(-1, ArrayUtils.indexOf(chars, "0}"));
    assertEquals(-1, ArrayUtils.indexOf(chars, "}0", 1));
    assertEquals(-1, ArrayUtils.indexOf(chars, "}0", 3));
    assertEquals(3, ArrayUtils.indexOf(chars, "{}"));
    assertEquals(3, ArrayUtils.indexOf(chars, "{}", 1));
    assertEquals(2, ArrayUtils.indexOf(chars, "}{}"));
  }

  @DisplayName("allLength：所有元素的长度总和")
  @Test
  void allLength() {
    assertThrows(NullPointerException.class, () -> ArrayUtils.allLength(null));
    // assertThrows(IllegalArgumentException.class, () -> ArrayUtils.allLength("", ""));

    assertEquals(2, ArrayUtils.allLength("1", "2"));
  }
}
