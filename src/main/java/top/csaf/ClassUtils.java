package top.csaf;

/**
 * Class 工具类
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {

  /**
   * 是否为基本数据类型
   *
   * @param classes 多个类
   * @return 是否为基本数据类型
   */
  public static boolean isBasic(final Class<?>... classes) {
    if (classes == null) {
      return false;
    }
    if (classes.length == 0) {
      throw new IllegalArgumentException("Classes: length should be greater than 0");
    }
    for (Class<?> clazz : classes) {
      if (!((Integer.class.equals(clazz)) ||
        Long.class.equals(clazz) ||
        Double.class.equals(clazz) ||
        Float.class.equals(clazz) ||
        Boolean.class.equals(clazz) ||
        Byte.class.equals(clazz) ||
        Character.class.equals(clazz) ||
        Short.class.equals(clazz)
      )) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否为基本数据类型
   *
   * @param objects 多个对象
   * @return 是否为基本数据类型
   */
  public static boolean isBasic(final Object... objects) {
    if (objects == null) {
      return false;
    }
    if (objects.length == 0) {
      throw new IllegalArgumentException("Objects: length should be greater than 0");
    }
    for (Object object : objects) {
      if (!((object instanceof Integer) ||
        object instanceof Long ||
        object instanceof Double ||
        object instanceof Float ||
        object instanceof Boolean ||
        object instanceof Byte ||
        object instanceof Character ||
        object instanceof Short
      )) {
        return false;
      }
    }
    return true;
  }
}
