package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;

/**
 * 数字工具类
 *
 * @author duanluan
 */
@Slf4j
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

  private NumberUtils() {
  }

  /**
   * 小于 0（Integer、Long、Double、Float、Short）
   *
   * @param obj 值
   * @return 是否小于 0
   */
  public static boolean lessThanZero(Object obj) {
    if (obj instanceof Integer) {
      return (Integer) obj < 0;
    } else if (obj instanceof Long) {
      return (Long) obj < 0;
    } else if (obj instanceof Double) {
      return (Double) obj < 0;
    } else if (obj instanceof Float) {
      return (Float) obj < 0;
    } else if (obj instanceof Short) {
      return (Short) obj < 0;
    }
    return false;
  }

  /**
   * 小于等于 0（Integer、Long、Double、Float、Short）
   *
   * @param obj 值
   * @return 是否小于等于 0
   */
  public static boolean lessThanEqualToZero(Object obj) {
    if (obj instanceof Integer) {
      return (Integer) obj <= 0;
    } else if (obj instanceof Long) {
      return (Long) obj <= 0;
    } else if (obj instanceof Double) {
      return (Double) obj <= 0;
    } else if (obj instanceof Float) {
      return (Float) obj <= 0;
    } else if (obj instanceof Short) {
      return (Short) obj <= 0;
    }
    return false;
  }

  /**
   * 大于 0（Integer、Long、Double、Float、Short）
   *
   * @param obj 值
   * @return 是否大于 0
   */
  public static boolean greaterThanZero(Object obj) {
    if (obj instanceof Integer) {
      return (Integer) obj > 0;
    } else if (obj instanceof Long) {
      return (Long) obj > 0;
    } else if (obj instanceof Double) {
      return (Double) obj > 0;
    } else if (obj instanceof Float) {
      return (Float) obj > 0;
    } else if (obj instanceof Short) {
      return (Short) obj > 0;
    }
    return false;
  }

  /**
   * 大于等于 0（Integer、Long、Double、Float、Short）
   *
   * @param obj 值
   * @return 是否大于等于 0
   */
  public static boolean greaterThanEqualToZero(Object obj) {
    if (obj instanceof Integer) {
      return (Integer) obj >= 0;
    } else if (obj instanceof Long) {
      return (Long) obj >= 0;
    } else if (obj instanceof Double) {
      return (Double) obj >= 0;
    } else if (obj instanceof Float) {
      return (Float) obj >= 0;
    } else if (obj instanceof Short) {
      return (Short) obj >= 0;
    }
    return false;
  }

  /**
   * 是否为整数
   *
   * @param str
   * @return
   */
  public static boolean isInteger(String str) {
    return str.matches("^[-+]?[\\d]*$");
  }

  public static Long parseLong(String s) {
    try {
      return Long.parseLong(s);
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
      return 0L;
    }
  }
}
