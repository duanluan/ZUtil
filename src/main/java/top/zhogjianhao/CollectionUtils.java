package top.zhogjianhao;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * 集合工具类
 */
@Slf4j
public class CollectionUtils {

  /**
   * 是否为 null 或没有元素
   *
   * @param coll 集合
   * @return 是否为 null 或没有元素
   */
  public static boolean isEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isEmpty(coll);
  }

  /**
   * 是否不为 null 且有元素
   *
   * @param coll 集合
   * @return 是否不为 null 且有元素
   */
  public static boolean isNotEmpty(Collection<?> coll) {
    return org.apache.commons.collections4.CollectionUtils.isNotEmpty(coll);
  }

  /**
   * 是否为 null 或没有元素
   *
   * <ul>
   * <li>Collection - via collection isEmpty
   * <li>Map - via map isEmpty
   * <li>Array - using array size
   * <li>Iterator - via hasNext
   * <li>Enumeration - via hasMoreElements
   * </ul>
   *
   * @param obj 对象
   * @return 是否为 null 或没有元素
   */
  public static boolean sizeIsEmpty(Object obj) {
    return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(obj);
  }

  /**
   * 是否不为 null 且有元素
   *
   * @param obj 对象
   * @return 是否不为 null 且有元素
   */
  public static boolean sizeIsNotEmpty(Object obj) {
    return !sizeIsEmpty(obj);
  }

  /**
   * 是否所有元素都为 null
   *
   * <ul>
   * <li>Collection - removeIf null, size() == 0
   * <li>Map - self(values())
   * <li>Array - noneMatch nonNull
   * <li>Iterator - !(next() != null)
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param obj 对象
   * @return 是否所有元素都为 null
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
   * @param obj 对象
   * @return 是否任意元素不为 null
   */
  public static boolean isNotAllEmpty(Object obj) {
    return !isAllEmpty(obj);
  }

  /**
   * 是否任意元素为 null
   *
   * <ul>
   * <li>Collection - contains null
   * <li>Map - containsValue null
   * <li>Array - anyMatch null
   * <li>Iterator - next() == null
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param obj 对象
   * @return 是否任意元素为 null
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
   * @param obj 对象
   * @return 是否任意元素为 null
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
  @Deprecated
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
  @Deprecated
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
  @Deprecated
  public static boolean remove(@NonNull Object[] arr, int index) {
    return remove(arr, index, null);
  }

  /**
   * 删除指定下标的元素
   *
   * @param <T>              基本数据类型
   * @param arr              数组
   * @param index            下标
   * @param lastElementValue 最后一个元素的值
   * @return 数组
   */
  @Deprecated
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
   * @param <T>   基本数据类型
   * @param arr   数组
   * @param index 下标
   * @return 数组
   */
  @Deprecated
  public static <T> boolean remove(@NonNull T arr, int index) {
    return remove(arr, index, null);
  }

  // /**
  //  * Object[] List 转换指定实体集合 List<Class>
  //  *
  //  * @param list
  //  * @param clazz
  //  * @param <T>
  //  * @return
  //  */
  // public static <T> List<T> to(List<Object[]> list, Class<T> clazz) {
  //   List<T> returnList = new ArrayList<T>();
  //   try {
  //     if (CollectionUtils.isEmpty(list)) {
  //       return returnList;
  //     }
  //     Object[] co = list.get(0);
  //     Class[] c2 = new Class[co.length];
  //     //确定构造方法
  //     for (int i = 0; i < co.length; i++) {
  //       if (co[i] != null) {
  //         c2[i] = co[i].getClass();
  //       } else {
  //         c2[i] = String.class;
  //       }
  //     }
  //     for (Object[] o : list) {
  //       Constructor<T> constructor = clazz.getConstructor(c2);
  //       returnList.add(constructor.newInstance(o));
  //     }
  //     return returnList;
  //   } catch (Exception e) {
  //     log.error("CollectionUtils.castEntity error!", e);
  //   }
  //   return returnList;
  // }

  /**
   * 复制List
   * @param source
   * @param target
   */
  public static void copyList(List source, List target){
    source.forEach(o -> {
      try {
        Object c = o.getClass().newInstance();
        BeanUtils.copyProperties(o, c);
        target.add(c);
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * 转换List
   * @param source
   * @param clazz
   */
  public static List copyListProperties(List source, Class clazz){
    List target = new ArrayList();
    source.forEach(o -> {
      try {
        target.add( JSONObject.parseObject(JSONObject.toJSONString(o),clazz));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return target;
  }

  /**
   * 转换List泛型
   * @param source 源列表
   * @param clazz 目标列表
   */
  public static <T> List<T> convertList(List source, Class<T> clazz){
    List<T> res = new ArrayList<>();
    source.forEach(o -> res.add(CollectionUtils.copyPropertiesThird(o, clazz)));
    return res;
  }



  /**
   * 源对象和目标对象的浅拷贝
   * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
   *
   * @param sourceObj 源对象
   * @param clazz     目标对象的Class对象
   * @param <T>
   * @return          目标对象
   */
  public static <T> T copyPropertiesThird(Object sourceObj,  Class<T> clazz) {
    if (Objects.isNull(sourceObj) || Objects.isNull(clazz)) {
      return null;
    }
    T c = null;
    try {
      c = clazz.newInstance();
      BeanUtils.copyProperties(sourceObj, c);
    } catch (InstantiationException | IllegalAccessException e) {
      log.error("KsBeanUtil -> copyPropertiesThird error ", e);
    }
    return c;
  }

  /**
   * 获取类所有属性（包含父类属性）
   * @param cls 类
   * @param fields 属性List
   * @return 所有fields
   */
  public static List<Field> getBeanFields(Class cls, List<Field> fields){
    fields.addAll(Arrays.asList(cls.getDeclaredFields()));
    if(Objects.nonNull(cls.getSuperclass())){
      fields = getBeanFields(cls.getSuperclass() , fields);
    }
    return fields;
  }

  /**
   * 将List<T>转换为List<Map<String, Object>>
   *
   * @param objList
   * @return
   */
  public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (objList != null && objList.size() > 0) {
      Map<String, Object> map = null;
      T bean = null;
      for (int i = 0, size = objList.size(); i < size; i++) {
        bean = objList.get(i);
        map = (Map<String, Object>) JSON.toJSON(bean);
        list.add(map);
      }
    }
    return list;
  }
}
