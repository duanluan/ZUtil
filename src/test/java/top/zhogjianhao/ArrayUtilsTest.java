package top.zhogjianhao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.charset.StandardCharsets;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

@DisplayName("数组工具类测试")
public class ArrayUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("不同写法的字符数组转字节数组性能测试")
  @Test
  void toBytes1() {
    char[] chars = new char[]{'中', '国', 'a'};
    if (new String(chars).getBytes(StandardCharsets.UTF_8)[0] == StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars)).array()[0]) {
      long currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        new String(chars).getBytes(StandardCharsets.UTF_8);
      }
      println("new String(chars).getBytes()：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars)).array();
      }
      println("Charset.encode(CharBuffer.wrap(chars)).array()：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        CharBuffer cb = CharBuffer.allocate(chars.length).put(chars);
        cb.flip();
        StandardCharsets.UTF_8.encode(cb).array();
      }
      println("Charset.encode(CharBuffer.allocate(chars.length).put(chars)).array()：" + (System.currentTimeMillis() - currentTimeMillis));
    }
  }

  @DisplayName("toBytes：转字节数组")
  @Test
  void toBytes() {
    char[] chars = new char[]{'中', '国', 'a'};
    // 不同字符集的字符数组转字节数组
    for (byte b : ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1)) {
      println(b);
    }
    println("——————————————————");
    for (byte b : ArrayUtils.toBytes(chars, StandardCharsets.GB2312)) {
      println(b);
    }
    println("——————————————————");
    // UTF-8 字符集的字符数组转字节数组
    for (byte b : ArrayUtils.toBytes(chars)) {
      println(b);
    }
  }

  @DisplayName("不同写法的字节数组转字符数组性能测试")
  @Test
  void toChars1() {
    byte[] bytes = {-28, -72, -83, -27, -101, -67, 97};
    if (new String(bytes, StandardCharsets.UTF_8).toCharArray()[0] == StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)).array()[0]) {
      long currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        new String(bytes, StandardCharsets.UTF_8).toCharArray();
      }
      println("new String(bytes).toCharArray()：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)).array();
      }
      println("Charset.encode(ByteBuffer.wrap(bytes)).array()：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length).put(bytes);
        bb.flip();
        StandardCharsets.UTF_8.decode(bb).array();
      }
      println("Charset.encode(ByteBuffer.allocate(bytes.length).put(bytes)).array()：" + (System.currentTimeMillis() - currentTimeMillis));
    }
  }

  @DisplayName("toChars：转字符数组")
  @Test
  void toChars() {
    char[] chars = new char[]{'中', '国', 'a'};
    // 不同字符集的字符数组转字节数组
    for (char c : ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1)) {
      println(c);
    }
    println("——————————————————");
    for (char c : ArrayUtils.toChars(ArrayUtils.toBytes(chars, StandardCharsets.GB2312), StandardCharsets.GB2312)) {
      println(c);
    }
    println("——————————————————");
    // UTF-8 字符集的字符数组转字节数组
    for (char c : ArrayUtils.toChars(ArrayUtils.toBytes(chars))) {
      println(c);
    }
  }

  @DisplayName("fori 手动往前移元素和 System.arraycopy 的性能")
  @Test
  void removeAndArraycopy() {
    Integer[] testInts = new Integer[1000000];
    for (int i = 0; i < testInts.length; i++) {
      testInts[i] = i + 1;
    }

    Integer[] ints = testInts;
    long currentTimeMillis = System.currentTimeMillis();
    for (int i = 3; i < ints.length - 1; i++) {
      ints[i] = ints[i + 1];
    }
    println("fori：" + (System.currentTimeMillis() - currentTimeMillis));
    println(ints[3]);

    ints = testInts;
    for (int i = 0; i < ints.length; i++) {
      ints[i] = i + 1;
    }
    currentTimeMillis = System.currentTimeMillis();
    if (ints.length - 1 - 3 >= 0) {
      System.arraycopy(ints, 3 + 1, ints, 3, ints.length - 1 - 3);
    }
    println("System.arraycopy：" + (System.currentTimeMillis() - currentTimeMillis));
    println(ints[3]);
  }

  @DisplayName("remove：删除元素")
  @Test
  void remove() {
    int[] ints = {1, 2, 3, 4, 5};
    ints = ArrayUtils.remove(ints, 3, 9);
    println("删除下标为 3 的元素后下标为 3 的元素：" + ints[3]);
    println("删除下标为 3 的元素后，最后一个元素（给默认值）：" + ints[ints.length - 1] + "，数组长度：" + ints.length);
    ints = new int[]{1, 2, 3, 4, 5};
    ints = ArrayUtils.remove(ints, 3);
    println("删除下标为 3 的元素后，最后一个元素（无默认值）：" + ints[ints.length - 1] + "，数组长度：" + ints.length);
    println("——————————————————");
    Integer[] ints1 = {1, 2, 3, 4, 5};
    ints1 = ArrayUtils.remove(ints1, 3, 9);
    println("删除下标为 3 的元素后下标为 3 的元素：" + ints1[3]);
    println("删除下标为 3 的元素后，最后一个元素（给默认值）：" + ints1[ints1.length - 1] + "，数组长度：" + ints1.length);
    ints1 = new Integer[]{1, 2, 3, 4, 5};
    ints1 = ArrayUtils.remove(ints1, 3);
    println("删除下标为 3 的元素后，最后一个元素（无默认值）：" + ints1[ints1.length - 1] + "，数组长度：" + ints1.length);
  }
}
