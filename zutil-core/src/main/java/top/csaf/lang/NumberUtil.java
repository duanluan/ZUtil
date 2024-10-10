package top.csaf.lang;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数字工具类
 */
@Slf4j
public class NumberUtil extends org.apache.commons.lang3.math.NumberUtils {

  /**
   * 对象转 BigDecimal，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return BigDecimal 变量
   */
  public static BigDecimal createBigDecimal(Object obj) {
    if (obj == null) {
      return null;
    }
    String str = obj.toString();
    return NumberUtils.createBigDecimal(str);
  }

  /**
   * 对象转 BigInteger，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return BigInteger 变量
   */
  public static BigInteger createBigInteger(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createBigInteger(obj.toString());
  }

  /**
   * 对象转 Double，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return Double 变量
   */
  public static Double createDouble(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createDouble(obj.toString());
  }

  /**
   * 对象转 Float，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return Float 变量
   */
  public static Float createFloat(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createFloat(obj.toString());
  }

  /**
   * 对象转 Integer，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return Integer 变量
   */
  public static Integer createInteger(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createInteger(obj.toString());
  }

  /**
   * 对象转 Long，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return Long 变量
   */
  public static Long createLong(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createLong(obj.toString());
  }

  /**
   * 对象转 Number，入参为 null 时返回 null
   *
   * @param obj 对象
   * @return Number 变量
   */
  public static Number createNumber(Object obj) {
    if (obj == null) {
      return null;
    }
    return NumberUtils.createNumber(obj.toString());
  }

  /**
   * 是否为 BigDecimal，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 BigDecimal
   */
  public static boolean isBigDecimal(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createBigDecimal(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 BigInteger，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 BigInteger
   */
  public static boolean isBigInteger(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createBigInteger(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Double，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 Double
   */
  public static boolean isDouble(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createDouble(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Float，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 Float
   */
  public static boolean isFloat(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createFloat(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Integer，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 Integer
   */
  public static boolean isInteger(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createInteger(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Long，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 Long
   */
  public static boolean isLong(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createLong(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Number，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否为 Number
   */
  public static boolean isNumber(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      createNumber(obj);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 是否为 Number，入参为 null 时返回 false
   *
   * @param str 字符串
   * @return 是否为 Number
   */
  public static boolean isNumber(final String str) {
    return isNumber((Object) str);
  }

  /**
   * 小于 0，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否小于 0
   */
  public static boolean ltZero(Object obj) {
    Number number = createNumber(obj);
    if (number == null) {
      return false;
    }
    return number.doubleValue() < 0;
  }

  /**
   * 小于等于 0，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否小于等于 0
   */
  public static boolean leZero(Object obj) {
    Number number = createNumber(obj);
    if (number == null) {
      return false;
    }
    return number.doubleValue() <= 0;
  }

  /**
   * 大于 0，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否大于 0
   */
  public static boolean gtZero(Object obj) {
    Number number = createNumber(obj);
    if (number == null) {
      return false;
    }
    return number.doubleValue() > 0;
  }

  /**
   * 大于等于 0，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否大于等于 0
   */
  public static boolean geZero(Object obj) {
    Number number = createNumber(obj);
    if (number == null) {
      return false;
    }
    return number.doubleValue() >= 0;
  }

  /**
   * 等于 0，入参为 null 时返回 false
   *
   * @param obj 对象
   * @return 是否等于 0
   */
  public static boolean eqZero(Object obj) {
    Number number = createNumber(obj);
    if (number == null) {
      return false;
    }
    return number.doubleValue() == 0;
  }
}
