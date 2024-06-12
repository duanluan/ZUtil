package top.csaf.lang;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Object 工具类
 */
public class ObjUtil extends org.apache.commons.lang3.ObjectUtils {

  /**
   * 基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   *
   * @param object 元素
   * @return 字符串
   */
  private static Object toStringByPrimitiveTypeAndNumber(Object object, boolean isByValue) {
    if (isByValue && (ClassUtil.isPrimitiveType(object) || object instanceof BigDecimal || object instanceof BigInteger)) {
      if (object instanceof Float || object instanceof Double || object instanceof BigDecimal) {
        object = new BigDecimal(String.valueOf(object)).stripTrailingZeros().toPlainString();
      } else {
        object = object.toString();
      }
    }
    return object;
  }

  /**
   * 全部相等
   *
   * @param isByValue      是否根据值来判断是否相等，基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   * @param isContinueNull 对象为 null 时不参与判断
   * @param objects        数组
   * @return 是否全部相等
   */
  public static boolean isAllEquals(boolean isByValue, boolean isContinueNull, @NonNull final Object... objects) {
    if (objects.length < 2) {
      throw new IllegalArgumentException("Values: length should be greater than 1");
    }
    for (int i = 1; i < objects.length; i++) {
      Object object = objects[i];
      if (isContinueNull && object == null) {
        continue;
      }
      if (!toStringByPrimitiveTypeAndNumber(object, isByValue).equals(toStringByPrimitiveTypeAndNumber(objects[i - 1], isByValue))) {
        return false;
      }
      if (i == objects.length - 2) {
        return true;
      }
    }
    return true;
  }
}
