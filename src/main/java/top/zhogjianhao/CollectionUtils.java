package top.zhogjianhao;


import java.util.*;

/**
 * 集合工具类
 *
 * @author duanluan
 */
public class CollectionUtils {

  /**
   * 是否为 null 或没有元素
   *
   * @param coll
   * @return
   */
  public static boolean isEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isEmpty(coll);
  }

  /**
   * 是否不为 null 且有元素
   *
   * @param coll
   * @return
   */
  public static boolean isNotEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isNotEmpty(coll);
  }

  /**
   * 是否为 null 或没有元素
   *
   * @param obj
   * @return
   */
  public static boolean sizeIsEmpty(Object obj) {
    return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(obj);
  }

  /**
   * 是否不为 null 且有元素
   *
   * @param obj
   * @return
   */
  public static boolean sizeIsNotEmpty(Object obj) {
    return !org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(obj);
  }

  /**
   * 是否所有元素都为 null
   *
   * @param obj
   * @return
   */
  public static boolean isAllEmpty(Object obj) {
    if (sizeIsEmpty(obj)) {
      return true;
    }
    if (obj instanceof Collection) {
      Collection obj1 = (Collection) obj;
      obj1.removeIf(Objects::isNull);
      return obj1.size() == 0;
    } else if (obj instanceof Iterable) {
      for (Object o : (Iterable) obj) {
        if (o != null) {
          return false;
        }
      }
      return true;
    } else if (obj instanceof Map) {
      Map obj1 = (Map) obj;
      obj1.keySet().removeIf(Objects::isNull);
      return obj1.size() == 0;
    } else if (obj instanceof Object[]) {
      Object[] obj1 = (Object[]) obj;
      return Arrays.stream(obj1).noneMatch(Objects::nonNull);
    } else if (obj instanceof Iterator) {
      Iterator obj1 = (Iterator) obj;
      while (obj1.hasNext()) {
        if (obj1.next() != null) {
          return false;
        }
      }
      return true;
    } else if (obj instanceof Enumeration) {
      Enumeration obj1 = (Enumeration) obj;
      while (obj1.hasMoreElements()) {
        if (obj1.nextElement() != null) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * 是否任意元素不为 null
   *
   * @param obj
   * @return
   */
  public static boolean isNotAllEmpty(Object obj) {
    return !isAllEmpty(obj);
  }
}
