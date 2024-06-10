package top.csaf.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * 数字工具类
 */
@Slf4j
public class NumberUtil extends org.apache.commons.lang3.math.NumberUtils {

  /**
   * 小于 0，数字为 null 时返回 false
   *
   * @param number 数字
   * @return 是否小于 0
   */
  public static boolean leThanZero(Number number) {
    if (number == null) {
      return false;
    }
    return number.intValue() < 0;
  }

  /**
   * 小于等于 0，数字为 null 时返回 false
   *
   * @param number 数字
   * @return 是否小于等于 0
   */
  public static boolean leThanEqToZero(Number number) {
    if (number == null) {
      return false;
    }
    return number.doubleValue() <= 0;
  }

  /**
   * 大于 0，数字为 null 时返回 false
   *
   * @param number 数字
   * @return 是否大于 0
   */
  public static boolean geThanZero(Number number) {
    if (number == null) {
      return false;
    }
    return number.doubleValue() > 0;
  }

  /**
   * 大于等于 0，数字为 null 时返回 false
   *
   * @param number 数字
   * @return 是否大于等于 0
   */
  public static boolean geThanEqToZero(Number number) {
    if (number == null) {
      return false;
    }
    return number.doubleValue() >= 0;
  }

  /**
   * 是否为整数，字符串为 null 时返回 false
   *
   * @param str 字符串
   * @return 是否为整数
   */
  public static boolean isInteger(String str) {
    if (StrUtil.isBlank(str)) {
      return false;
    }
    return str.matches("^[-+]?[\\d]*$");
  }

  /**
   * 字符串转 Long，解析失败时为 0L
   *
   * @param s 字符串
   * @return Long 类型变量
   */
  public static Long parseLong(String s) {
    try {
      return Long.parseLong(s);
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
      return 0L;
    }
  }
}
