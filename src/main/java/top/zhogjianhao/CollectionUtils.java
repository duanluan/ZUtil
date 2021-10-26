package top.zhogjianhao;


import java.util.Collection;

/**
 * 集合工具类
 *
 * @author duanluan
 */
public class CollectionUtils {

  /**
   * 为 null 或没有元素
   *
   * @param coll
   * @return
   */
  public static boolean isEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isEmpty(coll);
  }

  /**
   * 不为 null 且有元素
   *
   * @param coll
   * @return
   */
  public static boolean isNotEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isNotEmpty(coll);
  }

  /**
   * 不为 null 但内容为空
   *
   * @param obj
   * @return
   */
  public static boolean sizeIsEmpty(Object obj) {
    return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(obj);
  }

  /**
   * 不为 null 且内容不为空
   *
   * @param obj
   * @return
   */
  public static boolean sizeIsNotEmpty(Object obj) {
    return !org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(obj);
  }

  /**
   * 不为 null 但只有空元素
   *
   * @param coll
   * @return
   */
  public static boolean allIsEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.removeAll(coll, null).size() == 0;
  }

  /**
   * 不为 null 且不只有空元素
   *
   * @param coll
   * @return
   */
  public static boolean allIsNotEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.removeAll(coll, null).size() > 0;
  }
}
