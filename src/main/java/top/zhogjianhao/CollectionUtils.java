package top.zhogjianhao;


import lombok.NonNull;

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
      return isAllEmpty(obj1.values());
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

  /**
   * 是否任意元素为 null
   *
   * @param obj
   * @return
   */
  public static boolean isAnyEmpty(Object obj) {
    if (sizeIsEmpty(obj)) {
      return true;
    }
    if (obj instanceof Collection) {
      Collection obj1 = (Collection) obj;
      return obj1.contains(null);
    } else if (obj instanceof Iterable) {
      for (Object o : (Iterable) obj) {
        if (o == null) {
          return true;
        }
      }
      return false;
    } else if (obj instanceof Map) {
      Map obj1 = (Map) obj;
      return obj1.containsValue(null);
    } else if (obj instanceof Object[]) {
      Object[] obj1 = (Object[]) obj;
      return Arrays.stream(obj1).anyMatch(Objects::isNull);
    } else if (obj instanceof Iterator) {
      Iterator obj1 = (Iterator) obj;
      while (obj1.hasNext()) {
        if (obj1.next() == null) {
          return true;
        }
      }
      return false;
    } else if (obj instanceof Enumeration) {
      Enumeration obj1 = (Enumeration) obj;
      while (obj1.hasMoreElements()) {
        if (obj1.nextElement() == null) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  /**
   * 是否任意元素为 null
   *
   * @param obj
   * @return
   */
  public static boolean isNotAnyEmpty(Object obj) {
    return !isAnyEmpty(obj);
  }

  /**
   * 从指定下标开始，将后面的元素往前复制指定位数
   *
   * @param arr        数组
   * @param arrLength  数组长度
   * @param startIndex 开始下标
   * @param length     位数
   * @return 数组
   */
  public static boolean moveForward(@NonNull Object arr, int arrLength, int startIndex, int length) {
    if (!(arr instanceof Object[] || arr instanceof int[] || arr instanceof long[] || arr instanceof double[] || arr instanceof float[] || arr instanceof boolean[] || arr instanceof short[] || arr instanceof byte[] || arr instanceof char[])) {
      return false;
    }
    if (arrLength - length - startIndex >= 0) {
      System.arraycopy(arr, startIndex + length, arr, startIndex, arrLength - length - startIndex);
      return true;
    }
    return false;
  }

  /**
   * 删除指定下标的元素
   *
   * @param arr              数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static boolean remove(@NonNull Object[] arr, int index, Object lastElementValue) {
    if (index < 0) {
      return false;
    }
    moveForward(arr, arr.length, index, 1);
    arr[arr.length - 1] = lastElementValue;
    return true;
  }

  /**
   * 删除指定下标的元素
   *
   * @param arr   数组
   * @param index 下标
   * @return 数组
   */
  public static boolean remove(@NonNull Object[] arr, int index) {
    return remove(arr, index, null);
  }

  /**
   * 删除指定下标的元素
   *
   * @param arr              数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  public static <T> boolean remove(@NonNull T arr, int index, Object lastElementValue) {
    if (arr instanceof int[]) {
      moveForward(arr, ((int[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((int[]) arr)[((int[]) arr).length - 1] = (int) lastElementValue;
      }
    } else if (arr instanceof long[]) {
      moveForward(arr, ((long[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((long[]) arr)[((long[]) arr).length - 1] = (long) lastElementValue;
      }
    } else if (arr instanceof double[]) {
      moveForward(arr, ((double[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((double[]) arr)[((double[]) arr).length - 1] = (double) lastElementValue;
      }
    } else if (arr instanceof float[]) {
      moveForward(arr, ((float[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((float[]) arr)[((float[]) arr).length - 1] = (float) lastElementValue;
      }
    } else if (arr instanceof boolean[]) {
      moveForward(arr, ((boolean[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((boolean[]) arr)[((boolean[]) arr).length - 1] = (boolean) lastElementValue;
      }
    } else if (arr instanceof short[]) {
      moveForward(arr, ((short[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((short[]) arr)[((short[]) arr).length - 1] = (short) lastElementValue;
      }
    } else if (arr instanceof byte[]) {
      moveForward(arr, ((byte[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((byte[]) arr)[((byte[]) arr).length - 1] = (byte) lastElementValue;
      }
    } else if (arr instanceof char[]) {
      moveForward(arr, ((char[]) arr).length, index, 1);
      if (lastElementValue != null) {
        ((char[]) arr)[((char[]) arr).length - 1] = (char) lastElementValue;
      }
    }
    return true;
  }

  /**
   * 删除指定下标的元素
   *
   * @param arr   数组
   * @param index 下标
   * @return 数组
   */
  public static <T> boolean remove(@NonNull T arr, int index) {
    return remove(arr, index, null);
  }
}
