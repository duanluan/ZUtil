package top.csaf;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

/**
 * 集合工具类
 */
@Slf4j
public class CollectionUtils {

  /**
   * 是否 集合为 null 或 没有元素
   *
   * @param coll 集合
   * @return 是否 集合为 null 或 没有元素
   */
  public static boolean isEmpty(final Collection<?> coll) {
    return !org.apache.commons.collections4.CollectionUtils.isNotEmpty(coll);
  }

  /**
   * 是否 集合不为 null 且 有元素
   *
   * @param coll 集合
   * @return 是否 集合不为 null 且 有元素
   */
  public static boolean isNotEmpty(final Collection<?> coll) {
    return !isEmpty(coll);
  }

  /**
   * 是否 每个集合都 (为 null 或 没有元素)
   *
   * @param colls 多个集合
   * @return 是否 每个集合都 (为 null 或 没有元素)
   */
  public static boolean isEmptys(final Collection<?>... colls) {
    if (colls == null) {
      return true;
    }
    if (colls.length == 0) {
      throw new IllegalArgumentException("Colls: length should be greater than 0");
    }
    for (Collection<?> coll : colls) {
      // 如果 某个集合 (不为 null 且 有元素)
      if (isEmpty(coll)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否 每个集合都 (不为 null 且 有元素)
   *
   * @param colls 多个集合
   * @return 是否 每个集合都 (不为 null 且 有元素)
   */
  public static boolean isNotEmptys(final Collection<?>... colls) {
    return !isEmptys(colls);
  }

  /**
   * 是否 对象为 null 或 没有元素
   *
   * <ul>
   * <li>Collection - via collection isEmpty
   * <li>Map - via map isEmpty
   * <li>Array - using array size
   * <li>Iterator - via hasNext
   * <li>Enumeration - via hasMoreElements
   * </ul>
   *
   * @param object 对象
   * @return 是否 对象为 null 或 没有元素
   */
  public static boolean sizeIsEmpty(final Object object) {
    return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(object);
  }

  /**
   * 是否 对象不为 null 且 有元素
   *
   * @param object 对象
   * @return 是否 对象不为 null 且 有元素
   */
  public static boolean sizeIsNotEmpty(@NonNull final Object object) {
    return !sizeIsEmpty(object);
  }

  /**
   * 是否 每个对象都 (为 null 或 没有元素)
   *
   * <ul>
   * <li>Collection - via collection isEmpty
   * <li>Map - via map isEmpty
   * <li>Array - using array size
   * <li>Iterator - via hasNext
   * <li>Enumeration - via hasMoreElements
   * </ul>
   *
   * @param objects 多个对象
   * @return 是否 每个对象都 (为 null 或 没有元素)
   */
  public static boolean sizeIsEmptys(final Object... objects) {
    if (objects == null) {
      return true;
    }
    if (objects.length == 0) {
      throw new IllegalArgumentException("Objects: length should be greater than 0");
    }
    for (Object object : objects) {
      // 如果 某个对象 (不为 null 且 有元素)
      if (sizeIsEmpty(object)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否 每个对象都 (不为 null 且 有元素)
   *
   * @param objects 多个对象
   * @return 是否 每个对象都 (不为 null 且 有元素)
   */
  public static boolean sizeIsNotEmptys(@NonNull final Object... objects) {
    return !sizeIsEmptys(objects);
  }

  /**
   * 是否 对象所有元素都为 null 或 没有元素
   * <p>
   * 注意：会遍历 Iterator，后续使用需重新创建，但是和 {@link CollectionUtils#isAllEquals(boolean, Function, Object...)}、{@link CollectionUtils#isAllEqualsSameIndex(boolean, Function, Object...)} 使用时却无须担心，因为其内部会在调用此方法前就将 Iterator 转换为 List
   *
   * <ul>
   * <li>Collection - removeIf null, size() == 0
   * <li>Map - self(values())
   * <li>Array - noneMatch nonNull
   * <li>Iterator - !(next() != null)
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param object 对象
   * @return 是否 对象所有元素都为 null 或 没有元素)
   */
  public static boolean isAllEmpty(final Object object) {
    if (object == null) {
      return true;
    }
    if (object instanceof Collection) {
      Collection<?> obj1 = (Collection<?>) object;
      obj1.removeIf(Objects::isNull);
      return obj1.size() == 0;
    } else if (object instanceof Iterable<?>) {
      for (Object o : (Iterable<?>) object) {
        if (o != null) {
          return false;
        }
      }
    } else if (object instanceof Map<?, ?>) {
      return isAllEmptys(((Map<?, ?>) object).values());
    } else if (object instanceof Object[]) {
      Object[] obj1 = (Object[]) object;
      return Arrays.stream(obj1).noneMatch(Objects::nonNull);
    } else if (object instanceof Iterator<?>) {
      Iterator<?> obj1 = (Iterator<?>) object;
      while (obj1.hasNext()) {
        if (obj1.next() != null) {
          return false;
        }
      }
    } else if (object instanceof Enumeration<?>) {
      Enumeration<?> obj1 = (Enumeration<?>) object;
      while (obj1.hasMoreElements()) {
        if (obj1.nextElement() != null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 是否 对象所有元素都不为 null 且 有元素
   *
   * @param object 对象
   * @return 是否 对象元素都不为 null 且 有元素
   */
  public static boolean isNotAllEmpty(@NonNull final Object object) {
    return !isAllEmptys(object);
  }

  /**
   * 是否 每个对象的 (所有元素都为 null 或 没有元素)
   * <p>
   * 注意：会遍历 Iterator，后续使用需重新创建，但是和 {@link CollectionUtils#isAllEquals(boolean, Function, Object...)}、{@link CollectionUtils#isAllEqualsSameIndex(boolean, Function, Object...)} 使用时却无须担心，因为其内部会在调用此方法前就将 Iterator 转换为 List
   *
   * <ul>
   * <li>Collection - removeIf null, size() == 0
   * <li>Map - self(values())
   * <li>Array - noneMatch nonNull
   * <li>Iterator - !(next() != null)
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param objects 多个对象
   * @return 是否 每个对象的 (所有元素都为 null 或 没有元素)
   */
  public static boolean isAllEmptys(final Object... objects) {
    if (objects == null) {
      return true;
    }
    if (objects.length == 0) {
      throw new IllegalArgumentException("Objects: length should be greater than 0");
    }
    for (Object object : objects) {
      if (!isAllEmpty(object)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否 每个对象的 (所有元素都不为 null 且 有元素)
   *
   * @param objects 多个对象
   * @return 是否 每个对象的 (所有元素都不为 null 且 有元素)
   */
  public static boolean isNotAllEmptys(@NonNull final Object... objects) {
    return !isAllEmptys(objects);
  }

  /**
   * 是否 任意对象为 null 或 没有元素 或 任意元素为 null
   * <p>
   * 注意：会遍历 Iterator，后续使用需重新创建，但是和 {@link CollectionUtils#isAllEquals(boolean, Function, Object...)}、{@link CollectionUtils#isAllEqualsSameIndex(boolean, Function, Object...)} 使用时却无须担心，因为其内部会在调用此方法前就将 Iterator 转换为 List
   *
   * <ul>
   * <li>Collection - contains null
   * <li>Map - containsValue null
   * <li>Array - anyMatch null
   * <li>Iterator - next() == null
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param object 对象
   * @return 是否 任意对象为 null 或 没有元素 或 任意元素为 null
   */
  public static boolean isAnyEmpty(final Object object) {
    if (sizeIsEmpty(object)) {
      return true;
    }
    if (object instanceof Collection<?>) {
      Collection<?> obj1 = (Collection<?>) object;
      return obj1.contains(null);
    } else if (object instanceof Iterable<?>) {
      for (Object o : (Iterable<?>) object) {
        if (o == null) {
          return true;
        }
      }
    } else if (object instanceof Map<?, ?>) {
      return ((Map<?, ?>) object).containsValue(null);
    } else if (object instanceof Object[]) {
      Object[] obj1 = (Object[]) object;
      return Arrays.stream(obj1).anyMatch(Objects::isNull);
    } else if (object instanceof Iterator<?>) {
      Iterator<?> obj1 = (Iterator<?>) object;
      while (obj1.hasNext()) {
        if (obj1.next() == null) {
          return true;
        }
      }
    } else if (object instanceof Enumeration<?>) {
      Enumeration<?> obj1 = (Enumeration<?>) object;
      while (obj1.hasMoreElements()) {
        if (obj1.nextElement() == null) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否 对象不为 null 且 有元素 且 每个元素都不为 null
   *
   * @param object 对象
   * @return 是否 对象不为 null 且 有元素 且 每个元素都不为 null
   */
  public static boolean isNotAnyEmpty(final Object object) {
    return !isAnyEmpty(object);
  }

  /**
   * 是否 任意对象 (为 null 或 没有元素 或 任意元素为 null)
   * <p>
   * 注意：会遍历 Iterator，后续使用需重新创建，但是和 {@link CollectionUtils#isAllEquals(boolean, Function, Object...)}、{@link CollectionUtils#isAllEqualsSameIndex(boolean, Function, Object...)} 使用时却无须担心，因为其内部会在调用此方法前就将 Iterator 转换为 List
   *
   * <ul>
   * <li>Collection - contains null
   * <li>Map - containsValue null
   * <li>Array - anyMatch null
   * <li>Iterator - next() == null
   * <li>Enumeration - 同 Iterator
   * </ul>
   *
   * @param objects 多个对象
   * @return 是否 任意对象 (为 null 或 没有元素 或 任意元素为 null)
   */
  public static boolean isAnyEmptys(final Object... objects) {
    if (objects == null) {
      return true;
    }
    if (objects.length == 0) {
      throw new IllegalArgumentException("Objects: length should be greater than 0");
    }
    for (Object object : objects) {
      if(isAnyEmpty(object)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 是否 每个对象 (不为 null 且 有元素 且 每个元素都不为 null)
   *
   * @param objects 多个对象
   * @return 是否 每个对象 (不为 null 且 有元素 且 每个元素都不为 null)
   */
  public static boolean isNotAnyEmptys(final Object... objects) {
    return !isAnyEmptys(objects);
  }

  /**
   * 基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   *
   * @param object 元素
   * @return 字符串
   */
  private static Object toStringByBasic(Object object, boolean isByValue) {
    if (isByValue && (ClassUtils.isBasic(object) || object instanceof BigDecimal || object instanceof BigInteger)) {
      if (object instanceof Float || object instanceof Double || object instanceof BigDecimal) {
        object = new BigDecimal(object.toString()).stripTrailingZeros().toPlainString();
      } else {
        object = object.toString();
      }
    }
    return object;
  }

  /**
   * 是否 每个对象的每个元素都相等
   *
   * @param isByValue        是否根据值来判断是否相等，基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否 每个对象的每个元素都相等
   */
  public static boolean isAllEquals(boolean isByValue, Function<Object, Boolean> continueFunction, final Object... objects) {
    if (objects == null) {
      return true;
    }
    if (objects.length == 0) {
      throw new IllegalArgumentException("Objects: length should be greater than 0");
    }
    Object prevObj = null;
    for (Object object : objects) {
      // continueFunction 中可能已经使用了 Iterator，所以在调用前转换为 List
      if (object instanceof Iterator<?>) {
        object = IteratorUtils.toList((Iterator<?>) object);
      }
      // 对象指定条件时不参与判断
      if (continueFunction != null && continueFunction.apply(object)) {
        continue;
      }
      if (object instanceof Collection<?> || object instanceof Map<?, ?>) {
        Iterator<?> iterator;
        if (object instanceof Collection<?>) {
          iterator = ((Collection<?>) object).iterator();
        } else {
          iterator = (((Map<?, ?>) object).values()).iterator();
        }
        int i = 0;
        while (iterator.hasNext()) {
          // 基础类型转为 String
          Object nextObj = toStringByBasic(iterator.next(), isByValue);
          // 首次判断前跳过第一次循环
          if (prevObj == null && i == 0) {
            prevObj = nextObj;
            i = 1;
            continue;
          }
          if (!Objects.equals(prevObj, nextObj)) {
            return false;
          }
          prevObj = nextObj;
        }
      } else if (object instanceof Iterable<?>) {
        int i = 0;
        for (Object o : (Iterable<?>) object) {
          Object nextObj = toStringByBasic(o, isByValue);
          if (prevObj == null && i == 0) {
            prevObj = nextObj;
            i = 1;
            continue;
          }
          if (!Objects.equals(prevObj, nextObj)) {
            return false;
          }
          prevObj = nextObj;
        }
      } else if (object instanceof Object[]) {
        Object[] objects1 = (Object[]) object;
        for (int i = 0; i < objects1.length; i++) {
          Object nextObj = toStringByBasic(objects1[i], isByValue);
          if (prevObj == null && i == 0) {
            prevObj = nextObj;
            i = 1;
            continue;
          }
          if (!Objects.equals(prevObj, nextObj)) {
            return false;
          }
          prevObj = nextObj;
        }
      } else if (object instanceof Enumeration<?>) {
        Enumeration<?> enumeration = (Enumeration<?>) object;
        int i = 0;
        while (enumeration.hasMoreElements()) {
          Object nextObj = toStringByBasic(enumeration.nextElement(), isByValue);
          if (prevObj == null && i == 0) {
            prevObj = nextObj;
            i = 1;
            continue;
          }
          if (!Objects.equals(prevObj, nextObj)) {
            return false;
          }
          prevObj = nextObj;
        }
      }
    }
    return true;
  }

  /**
   * 是否不满足 每个对象的每个元素都相等
   *
   * @param isByValue        是否根据值来判断是否相等，基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否不满足 每个对象的每个元素都相等
   */
  public static boolean isNotAllEquals(boolean isByValue, Function<Object, Boolean> continueFunction, final Object... objects) {
    return !isAllEquals(isByValue, continueFunction, objects);
  }

  /**
   * 是否 每个对象的同一位置的元素都相等
   *
   * @param isByValue        是否根据值来判断是否相等，基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否 每个对象的同一位置的元素都相等
   */
  public static boolean isAllEqualsSameIndex(boolean isByValue, Function<Object, Boolean> continueFunction, final Object... objects) {
    if (objects == null) {
      return true;
    }
    if (objects.length < 2) {
      throw new IllegalArgumentException("Objects: length should be greater than 1");
    }

    Integer objectSize = null;
    for (Object object : objects) {
      // continueFunction 中可能已经使用了 Iterator，所以在调用前转换为 List
      if (object instanceof Iterator<?>) {
        object = IteratorUtils.toList((Iterator<?>) object);
      }
      // 对象指定条件时不参与判断
      if (continueFunction != null && continueFunction.apply(object)) {
        continue;
      }
      int size = -1;
      if (object instanceof Collection<?>) {
        size = ((Collection<?>) object).size();
      } else if (object instanceof Map<?, ?>) {
        size = ((Map<?, ?>) object).values().size();
      } else if (object instanceof Iterable<?>) {
        int i = 0;
        for (Object o : (Iterable<?>) object) {
          i++;
        }
        size = i;
      } else if (object instanceof Object[]) {
        size = ((Object[]) object).length;
      } else if (object instanceof Enumeration<?>) {
        Enumeration<?> enumeration = (Enumeration<?>) object;
        int i = 0;
        for (; enumeration.hasMoreElements(); i++) {
          enumeration.nextElement();
        }
        size = i;
      }
      // 元素长度不一致时退出
      if (objectSize != null && !objectSize.equals(size)) {
        return false;
      }
      objectSize = size;
    }

    List<Object> prevList = new LinkedList<>();
    for (Object object : objects) {
      if (object instanceof Collection<?> || object instanceof Map<?, ?>) {
        Iterator<?> iterator;
        if (object instanceof Collection<?>) {
          iterator = ((Collection<?>) object).iterator();
        } else {
          iterator = (((Map<?, ?>) object).values()).iterator();
        }
        int i = 0;
        for (; iterator.hasNext(); i++) {
          // 基础类型转为 String
          Object nextObj = toStringByBasic(iterator.next(), isByValue);
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            i = 1;
            continue;
          }
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
          prevList.set(i, nextObj);
        }
      } else if (object instanceof Iterable<?>) {
        int i = 0;
        for (Object o : (Iterable<?>) object) {
          Object nextObj = toStringByBasic(o, isByValue);
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            i = 1;
            continue;
          }
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
          prevList.set(i, nextObj);
          i++;
        }
      } else if (object instanceof Object[]) {
        Object[] objects1 = (Object[]) object;
        for (int i = 0; i < objects1.length; i++) {
          Object nextObj = toStringByBasic(objects1[i], isByValue);
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            i = 1;
            continue;
          }
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
          prevList.set(i, nextObj);
        }
      } else if (object instanceof Enumeration<?>) {
        Enumeration<?> enumeration = (Enumeration<?>) object;
        int i = 0;
        for (; enumeration.hasMoreElements(); i++) {
          Object nextObj = toStringByBasic(enumeration.nextElement(), isByValue);
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            i = 1;
            continue;
          }
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
          prevList.set(i, nextObj);
        }
      }
    }
    return true;
  }

  /**
   * 是否不满足 每个对象的同一位置的元素都相等
   *
   * @param isByValue        是否根据值来判断是否相等，基础类型和 BigDecimal、BigInteger 会被转换为 String，且小数点后无效的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否不满足 每个对象的同一位置的元素都相等
   */
  public static boolean isNotAllEqualsSameIndex(boolean isByValue, Function<Object, Boolean> continueFunction, final Object... objects) {
    return !isAllEqualsSameIndex(isByValue, continueFunction, objects);
  }
}
