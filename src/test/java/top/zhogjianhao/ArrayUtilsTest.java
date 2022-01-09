package top.zhogjianhao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.charset.StandardCharsets;

import java.nio.CharBuffer;
import java.util.Objects;

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
      println("new String(chars).getBytes：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars)).array();
      }
      println("Charset.encode(CharBuffer.wrap(chars)).array()：" + (System.currentTimeMillis() - currentTimeMillis));

      currentTimeMillis = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        CharBuffer cb = CharBuffer.allocate(chars.length).put(chars);
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
    for (byte b : Objects.requireNonNull(ArrayUtils.toBytes(chars, StandardCharsets.ISO_8859_1))) {
      println(b);
    }
    println("——————————————————");
    for (byte b : Objects.requireNonNull(ArrayUtils.toBytes(chars, StandardCharsets.GB2312))) {
      println(b);
    }
    println("——————————————————");
    // UTF-8 字符集的字符数组转字节数组
    for (byte b : ArrayUtils.toBytes(chars)) {
      println(b);
    }
  }
}
