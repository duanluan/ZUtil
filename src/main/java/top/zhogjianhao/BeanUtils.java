package top.zhogjianhao;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * Bean 工具类
 */
@Slf4j
public class BeanUtils extends org.springframework.beans.BeanUtils {

  private static final String FIELD_METHOD_PREFIX_GET = "get";
  private static final String FIELD_METHOD_PREFIX_IS = "is";

  /**
   * Bean 转 Map
   *
   * @param object Bean 对象
   * @return Map
   */
  public static Map<String, Object> toMap(Object object) {
    Map<String, Object> map = new HashMap<>();
    BeanMap beanMap = BeanMap.create(object);
    for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) beanMap.entrySet()) {
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }

  /**
   * Bean List 转 Map List
   *
   * @param sourceList Bean List
   * @param <T>        Bean 类型
   * @return Map List
   */
  public static <T> List<Map<String, Object>> toMap(List<T> sourceList) {
    List<Map<String, Object>> result = new ArrayList<>();
    if (CollectionUtils.isEmpty(sourceList)) {
      return result;
    }
    for (T t : sourceList) {
      result.add(toMap(t));
    }
    return result;
  }

  /**
   * 深层 Bean 转 Map
   *
   * @param object Bean 对象
   * @return Map
   */
  public static Map<String, Object> deepToMap(Object object) {
    Map<String, Object> result = new HashMap<>();
    BeanMap beanMap = BeanMap.create(object);
    for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) beanMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      // 非基础类型
      if (value != null && !(value instanceof String) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Double) && !(value instanceof Boolean) && !(value instanceof Float) && !(value instanceof Byte) && !(value instanceof Character) && !(value instanceof Short)) {
        // 可循环
        Collection collection = null;
        if (value.getClass().isArray()) {
          collection = Arrays.asList(value);
        } else if (value instanceof List) {
          collection = (List) value;
        } else if (value instanceof Set) {
          collection = (Set) value;
        } else if (value instanceof Vector) {
          collection = (Vector) value;
        }
        if (collection != null) {
          List<Map<String, Object>> list = new ArrayList<>();
          for (Object item : collection) {
            list.add(deepToMap(item));
          }
          result.put(key, list);
        } else {
          result.put(key, deepToMap(value));
        }
      } else {
        result.put(key, value);
      }
    }
    return result;
  }

  /**
   * 深层 Bean List 转 Map List
   *
   * @param sourceList Bean List
   * @param <T>        Bean 类型
   * @return Map List
   */
  public static <T> List<Map<String, Object>> deepToMap(List<T> sourceList) {
    List<Map<String, Object>> result = new ArrayList<>();
    if (CollectionUtils.isEmpty(sourceList)) {
      return result;
    }
    for (T t : sourceList) {
      result.add(deepToMap(t));
    }
    return result;
  }

  /**
   * 根据属性名获取属性值
   *
   * @param bean 对象
   * @param name 属性名
   * @return 属性值
   */
  public static Object getProperty(Object bean, String name) {
    try {
      PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(bean.getClass(), name);
      if (propertyDescriptor != null) {
        return propertyDescriptor.getReadMethod().invoke(bean, (Object) null);
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 根据属性名获取属性值字符串
   *
   * @param bean 对象
   * @param name 属性名
   * @return 属性值字符串
   */
  public static String getPropertyStr(Object bean, String name) {
    Object object = getProperty(bean, name);
    if (object != null) {
      return object.toString();
    }
    return null;
  }

  /**
   * 根据属性名设置属性值
   *
   * @param bean  对象
   * @param name  属性名
   * @param value 属性值
   * @return 是否设置成功
   */
  public static boolean setProperty(Object bean, String name, Object value) {
    try {
      PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(bean.getClass(), name);
      if (propertyDescriptor != null) {
        propertyDescriptor.getWriteMethod().invoke(bean, value);
        return true;
      }
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
    }
    return false;
  }

  /**
   * 属性 Function
   *
   * @param <T> 输入类型
   * @param <R> 输出类型
   */
  @FunctionalInterface
  public interface FieldFunction<T, R> extends Function<T, R>, Serializable {
  }

  /**
   * 获取属性名（https://blog.csdn.net/qq_35410620/article/details/103007557）
   *
   * @param <T> 类
   * @param fn  属性 Getter
   * @return 属性名
   */
  public static <T> String getFieldName(FieldFunction<T, ?> fn) {
    try {
      // 通过获取对象方法，判断是否存在该方法
      Method method = fn.getClass().getDeclaredMethod("writeReplace");
      method.setAccessible(true);
      // 利用 jdk 的 SerializedLambda 解析方法引用
      SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
      String implMethodName = serializedLambda.getImplMethodName();
      if (implMethodName.startsWith(FIELD_METHOD_PREFIX_GET)) {
        implMethodName = implMethodName.substring(3);
      } else if (implMethodName.startsWith(FIELD_METHOD_PREFIX_IS)) {
        implMethodName = implMethodName.substring(2);
      }
      if (StringUtils.isNotBlank(implMethodName)) {
        return implMethodName.substring(0, 1).toLowerCase() + implMethodName.substring(1);
      }
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  /**
   * 获取列名
   *
   * @param <T> 类
   * @param fn  属性 Getter
   * @return 列名
   */
  public static <T> String getColumnName(FieldFunction<T, ?> fn) {
    return StringUtils.toUnderscore(getFieldName(fn));
  }

  /**
   * 复制属性到新类型对象中
   *
   * @param source      对象
   * @param targetClass 目标类
   * @param <T>         指定类型
   * @return 指定类型的新对象
   */
  public static <T> T copyProperties(@NonNull Object source, @NonNull Class<T> targetClass) {
    // 判断目标类是否存在无参构造函数
    boolean hasNoArgsConstructor = false;
    Constructor<?>[] constructors = targetClass.getConstructors();
    for (Constructor<?> constructor : constructors) {
      if (constructor.getParameterCount() == 0) {
        hasNoArgsConstructor = true;
        break;
      }
    }
    if (!hasNoArgsConstructor) {
      throw new IllegalArgumentException("targetClass: " + targetClass.getName() + " must have no args constructor");
    }

    T target = null;
    try {
      target = targetClass.newInstance();
      org.springframework.beans.BeanUtils.copyProperties(source, target);
    } catch (InstantiationException | IllegalAccessException e) {
      log.error(e.getMessage(), e);
    }
    return target;
  }

  /**
   * 复制属性到新类型对象列表中
   *
   * @param sourceList  对象列表
   * @param targetClass 目标类
   * @param <T>         目标类型
   * @return 指定类型的对象列表
   */
  public static <T> List<T> copyProperties(List<?> sourceList, Class<T> targetClass) {
    // 判断目标类是否存在无参构造函数
    boolean hasNoArgsConstructor = false;
    Constructor<?>[] constructors = targetClass.getConstructors();
    for (Constructor<?> constructor : constructors) {
      if (constructor.getParameterCount() == 0) {
        hasNoArgsConstructor = true;
        break;
      }
    }
    if (!hasNoArgsConstructor) {
      throw new IllegalArgumentException("targetClass: " + targetClass.getName() + " must have no args constructor");
    }

    List<T> targetList = new ArrayList<>();
    for (Object source : sourceList) {
      T target = null;
      try {
        target = targetClass.newInstance();
        org.springframework.beans.BeanUtils.copyProperties(source, target);
      } catch (InstantiationException | IllegalAccessException e) {
        log.error(e.getMessage(), e);
      }
      targetList.add(target);
    }
    return targetList;
  }
}
