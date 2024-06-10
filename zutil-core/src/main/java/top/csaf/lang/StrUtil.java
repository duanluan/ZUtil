package top.csaf.lang;

import lombok.NonNull;

/**
 * 字符串工具类
 */
public class StrUtil extends org.apache.commons.lang3.StringUtils {

  /**
   * 判断是否为空
   *
   * @param obj 对象
   * @return 是否为空
   */
  public static boolean isEmpty(final Object obj) {
    return obj == null || isEmpty(obj.toString());
  }

  /**
   * 判断是否不为空
   *
   * @param obj 对象
   * @return 是否不为空
   */
  public static boolean isNotEmpty(final Object obj) {
    return !isEmpty(obj);
  }

  /**
   * 判断是否任意一个为空
   *
   * @param objs 对象数组
   * @return 是否任意一个为空
   */
  public static boolean isAnyEmpty(final Object... objs) {
    if (objs == null) {
      return true;
    }
    for (Object obj : objs) {
      if (isEmpty(obj)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断是否为空或空字符串
   *
   * @param obj 对象
   * @return 是否为空或空字符串
   */
  public static boolean isBlank(final Object obj) {
    return obj == null || isBlank(obj.toString());
  }

  /**
   * 判断是否不为空且不为空字符串
   *
   * @param obj 对象
   * @return 是否不为空且不为空字符串
   */
  public static boolean isNotBlank(final Object obj) {
    return !isBlank(obj);
  }

  /**
   * 判断是否任意一个为空或空字符串
   *
   * @param objs 对象数组
   * @return 是否任意一个为空或空字符串
   */
  public static boolean isAnyBlank(final Object... objs) {
    if (objs == null) {
      return true;
    }
    for (Object obj : objs) {
      if (isBlank(obj)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 查找 seq 中 searchSeq 的第一个索引位置，找不到返回 -1
   *
   * @param seq       被查找的字符串
   * @param searchSeq 需要查找的字符串
   * @return 需要查找的字符串在被查找的字符串中的第一个索引位置
   */
  public static int indexOf(@NonNull final CharSequence seq, @NonNull final CharSequence searchSeq) {
    if (searchSeq.length() == 2) {
      return ArrayUtil.indexOf(seq.toString().toCharArray(), searchSeq.toString());
    }
    return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq);
  }

  /**
   * 删除开头，忽略大小写
   *
   * @param str    被删除的字符串
   * @param remove 需要删除的开头字符串
   * @return 删除后的字符串
   */
  public static String removeStartIgnoreCase(@NonNull final String str, @NonNull final String remove) {
    if (StrUtil.isBlank(str)) {
      throw new IllegalArgumentException("Str: should not be blank");
    }
    if (StrUtil.isBlank(remove)) {
      throw new IllegalArgumentException("Remove: should not be blank");
    }
    if (str.toLowerCase().startsWith(remove.toLowerCase())) {
      return substring(str, remove.length());
    }
    return str;
  }

  /**
   * 删除结尾，忽略大小写
   *
   * @param str    被删除的字符串
   * @param remove 需要删除的结尾字符串
   * @return 删除后的字符串
   */
  public static String removeEndIgnoreCase(@NonNull final String str, @NonNull final String remove) {
    if (StrUtil.isBlank(str)) {
      throw new IllegalArgumentException("Str: should not be blank");
    }
    if (StrUtil.isBlank(remove)) {
      throw new IllegalArgumentException("Remove: should not be blank");
    }
    if (str.toLowerCase().endsWith(remove.toLowerCase())) {
      return substring(str, 0, str.length() - remove.length());
    }
    return str;
  }

  private static final String FORMAT_REPLACE_STR = "{}";

  /**
   * 格式化：将多个字符串依次替换字符串的 {}
   *
   * @param str  被格式化的字符串
   * @param vals 需要替换的多个字符串
   * @return 格式化后的字符串
   */
  public static String format(@NonNull final String str, @NonNull final String... vals) {
    if (StrUtil.isBlank(str)) {
      throw new IllegalArgumentException("Str: should not be blank");
    }
    if (StrUtil.isAllBlank(vals)) {
      throw new IllegalArgumentException("Vals: should not be all blank");
    }
    StringBuilder result = new StringBuilder();
    int index;
    int i = 0;
    int fromIndex = 0;
    while ((index = str.indexOf(FORMAT_REPLACE_STR, fromIndex)) != -1) {
      String val = vals[i];
      result.append(substring(str, fromIndex, index)).append(val);
      fromIndex = index + 2;
      i++;
    }
    return result + str.substring(fromIndex);
  }

  /**
   * 转换为下划线命名
   *
   * @param source 需要转换的内容
   * @return 转换后的内容
   */
  public static String toUnderscore(@NonNull final String source) {
    if (isBlank(source)) {
      return source;
    }
    StringBuilder sb = new StringBuilder(source);
    int temp = 0;
    if (!source.contains(" ")) {
      for (int i = 0; i < source.length(); i++) {
        // 如果为大写字母且不为开头
        if (Character.isUpperCase(source.charAt(i)) && !source.startsWith(String.valueOf(source.charAt(i)))) {
          // 在大写字母前插入下划线
          sb.insert(i + temp, "_");
          temp++;
        }
      }
    }
    return sb.toString().toLowerCase();
  }

  /**
   * 首字母大写
   *
   * @param source 字符串
   * @return 首字母大写
   */
  public static String toInitialUpperCase(@NonNull final String source) {
    if (isBlank(source)) {
      return source;
    }
    char[] chars = source.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return String.valueOf(chars);
  }

  /**
   * 字符串重复 n 遍
   *
   * @param source 字符串
   * @param n      重复次数
   * @return 重复 n 遍的字符串
   */
  public static String nCopies(@NonNull final String source, final long n) {
    if (isBlank(source) || n < 1) {
      return source;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      sb.append(source);
    }
    return sb.toString();
  }
}
