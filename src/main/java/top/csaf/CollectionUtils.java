package top.csaf;


import com.google.gson.JsonPrimitive;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.collections4.IteratorUtils;

import java.math.BigDecimal;
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
      if (isAnyEmpty(object)) {
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
   * 转为字符串，Float、Double、BigDecimal 小数点后多余的 0 会被去除
   *
   * @param object     需转换的对象
   * @param isByString 是否转换
   * @return 字符串
   */
  private static Object stripTrailingZerosToString(Object object, boolean isByString) {
    if (isByString) {
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
   * @param isByString       是否根据 toString() 的值来判断是否相等，Float、Double、BigDecimal 小数点后多余的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否 每个对象的每个元素都相等
   */
  public static boolean isAllEquals(boolean isByString, Function<Object, Boolean> continueFunction, final Object... objects) {
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
          Object nextObj = stripTrailingZerosToString(iterator.next(), isByString);
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
          Object nextObj = stripTrailingZerosToString(o, isByString);
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
          Object nextObj = stripTrailingZerosToString(objects1[i], isByString);
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
          Object nextObj = stripTrailingZerosToString(enumeration.nextElement(), isByString);
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
   * @param isByString       是否根据 toString() 的值来判断是否相等，Float、Double、BigDecimal 小数点后多余的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否不满足 每个对象的每个元素都相等
   */
  public static boolean isNotAllEquals(boolean isByString, Function<Object, Boolean> continueFunction, final Object... objects) {
    return !isAllEquals(isByString, continueFunction, objects);
  }

  /**
   * 是否 每个对象的同一位置的元素都相等
   *
   * @param isByString       是否根据 toString() 的值来判断是否相等，
   *                         Float、Double、BigDecimal 会去除小数点后多余的 0。
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象。Iterator、Enumeration 会被使用掉。
   * @return 是否 每个对象的同一位置的元素都相等
   */
  public static boolean isAllEqualsSameIndex(final boolean isByString, final Function<Object, Boolean> continueFunction, @NonNull final Object... objects) {
    if (objects.length < 2) {
      throw new IllegalArgumentException("Objects: length must be greater than 1");
    }

    Integer prevObjSize = null;
    for (int i = 0; i < objects.length; i++) {
      Object object = objects[i];

      // Iterator、Enumeration 获取对象元素长度时可能需要循环，所以先转换为 List，避免后续无法循环
      if (object instanceof Iterator<?>) {
        object = IteratorUtils.toList((Iterator<?>) object);
        objects[i] = object;
      } else if (object instanceof Enumeration<?>) {
        object = EnumerationUtils.toList((Enumeration<?>) object);
        objects[i] = object;
      }
      // 满足条件时对象不参与判断
      if (continueFunction != null && Boolean.TRUE.equals(continueFunction.apply(object))) {
        continue;
      }

      int objSize = -1;
      // 不同类型获取元素长度
      if (object instanceof Iterable<?>) {
        if (object instanceof Collection<?>) {
          objSize = ((Collection<?>) object).size();
        } else {
          int j = 0;
          for (Object o : (Iterable<?>) object) {
            j++;
          }
          objSize = j;
        }
      } else if (object instanceof Map<?, ?>) {
        objSize = ((Map<?, ?>) object).values().size();
      } else if (object instanceof Object[]) {
        objSize = ((Object[]) object).length;
      }
      // 当前对象元素长度和上一次循环的对象元素长度不一致时退出
      if (prevObjSize != null && !prevObjSize.equals(objSize)) {
        return false;
      }
      prevObjSize = objSize;
    }

    List<Object> prevList = new LinkedList<>();
    for (Object object : objects) {
      // 满足条件时对象不参与判断
      if (continueFunction != null && Boolean.TRUE.equals(continueFunction.apply(object))) {
        continue;
      }

      // Iterable、Map
      if (object instanceof Iterable<?> || object instanceof Iterator<?> || object instanceof Map<?, ?>) {
        // 不同类型获取 Iterator
        Iterator<?> iterator;
        if (object instanceof Iterable<?>) {
          iterator = ((Iterable<?>) object).iterator();
        } else {
          iterator = (((Map<?, ?>) object).values()).iterator();
        }

        int i = 0;
        for (; iterator.hasNext(); i++) {
          Object nextObj = iterator.next();
          /** {@link com.google.gson.JsonArray } 循环时需转换为 JsonPrimitive 后处理 */
          if (nextObj instanceof JsonPrimitive) {
            JsonPrimitive jsonPrimitive = (JsonPrimitive) nextObj;
            nextObj = jsonPrimitive.isNumber() ? jsonPrimitive.getAsBigDecimal() : jsonPrimitive.getAsString();
          }

          // 基础类型转为 String
          nextObj = stripTrailingZerosToString(nextObj, isByString);
          // 如果上一次列表的长度 < i + 1，即 prevList（上一次列表）未填充完需要判断的第一个对象的所有元素
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            continue;
          }
          // 对比上一次列表和当前列表的元素
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
        }
      }
      // Object[]
      else if (object instanceof Object[]) {
        Object[] objects1 = (Object[]) object;
        for (int i = 0; i < objects1.length; i++) {
          Object nextObj = stripTrailingZerosToString(objects1[i], isByString);
          if (prevList.size() < i + 1) {
            prevList.add(nextObj);
            continue;
          }
          if (!Objects.equals(prevList.get(i), nextObj)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * 是否不满足 每个对象的同一位置的元素都相等
   *
   * @param isByString       是否根据 toString() 的值来判断是否相等，Float、Double、BigDecimal 小数点后多余的 0 会被去除
   * @param continueFunction 对象何时不参与判断
   * @param objects          多个对象
   * @return 是否不满足 每个对象的同一位置的元素都相等
   */
  public static boolean isNotAllEqualsSameIndex(boolean isByString, Function<Object, Boolean> continueFunction, final Object... objects) {
    return !isAllEqualsSameIndex(isByString, continueFunction, objects);
  }
}
