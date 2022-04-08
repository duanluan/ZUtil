package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;

/**
 * 数字工具类
 */
@Slf4j
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

  /**
   * 小于 0（Integer、Long、Double、Float、Short）
   *
   * @param object 值
   * @return 是否小于 0
   */
  public static boolean leThanZero(Object object) {
    if (object instanceof Integer) {
      return (Integer) object < 0;
    } else if (object instanceof Long) {
      return (Long) object < 0;
    } else if (object instanceof Double) {
      return (Double) object < 0;
    } else if (object instanceof Float) {
      return (Float) object < 0;
    } else if (object instanceof Short) {
      return (Short) object < 0;
    }
    return false;
  }

  /**
   * 小于等于 0（Integer、Long、Double、Float、Short）
   *
   * @param object 值
   * @return 是否小于等于 0
   */
  public static boolean leThanEqToZero(Object object) {
    if (object instanceof Integer) {
      return (Integer) object <= 0;
    } else if (object instanceof Long) {
      return (Long) object <= 0;
    } else if (object instanceof Double) {
      return (Double) object <= 0;
    } else if (object instanceof Float) {
      return (Float) object <= 0;
    } else if (object instanceof Short) {
      return (Short) object <= 0;
    }
    return false;
  }

  /**
   * 大于 0（Integer、Long、Double、Float、Short）
   *
   * @param object 值
   * @return 是否大于 0
   */
  public static boolean geThanZero(Object object) {
    if (object instanceof Integer) {
      return (Integer) object > 0;
    } else if (object instanceof Long) {
      return (Long) object > 0;
    } else if (object instanceof Double) {
      return (Double) object > 0;
    } else if (object instanceof Float) {
      return (Float) object > 0;
    } else if (object instanceof Short) {
      return (Short) object > 0;
    }
    return false;
  }

  /**
   * 大于等于 0（Integer、Long、Double、Float、Short）
   *
   * @param object 值
   * @return 是否大于等于 0
   */
  public static boolean geThanEqToZero(Object object) {
    if (object instanceof Integer) {
      return (Integer) object >= 0;
    } else if (object instanceof Long) {
      return (Long) object >= 0;
    } else if (object instanceof Double) {
      return (Double) object >= 0;
    } else if (object instanceof Float) {
      return (Float) object >= 0;
    } else if (object instanceof Short) {
      return (Short) object >= 0;
    }
    return false;
  }

  /**
   * 是否为整数
   *
   * @param str 字符串
   * @return 是否为整数
   */
  public static boolean isInteger(String str) {
    return str.matches("^[-+]?[\\d]*$");
  }

  /**
   * 解析为 Long
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
