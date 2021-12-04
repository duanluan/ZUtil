package top.zhogjianhao;

/**
 * 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

  /**
   * 转换为下划线命名
   *
   * @param source 需要转换的内容
   * @return 转换后的内容
   */
  public static String toUnderscore(String source) {
    StringBuilder sb = new StringBuilder(source);
    int temp = 0;
    if (!source.contains(" ")) {
      for (int i = 0; i < source.length(); i++) {
        // 如果为大写字母且不为开头
        if (Character.isUpperCase(source.charAt(i)) && !source.startsWith(String.valueOf(source.charAt(i)))) {
          // 在大写字母前插入下划线
          sb.insert(i + temp, "_");
          temp += 1;
        }
      }
    }
    return sb.toString().toLowerCase();
  }
}