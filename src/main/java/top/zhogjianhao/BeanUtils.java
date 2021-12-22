package top.zhogjianhao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.function.Function;

/**
 * JavaBean 工具类
 */
@Slf4j
public class BeanUtils extends org.springframework.beans.BeanUtils {

  /**
   * Bean 转 Map
   *
   * @param obj Bean 对象
   * @return Map
   */
  public static HashMap<String, Object> toMap(Object obj) {
    return new HashMap(BeanMap.create(obj));
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
        return propertyDescriptor.getReadMethod().invoke(bean, null);
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
    Object obj = getProperty(bean, name);
    if (obj != null) {
      return obj.toString();
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
      method.setAccessible(Boolean.TRUE);
      // 利用 jdk 的 SerializedLambda 解析方法引用
      java.lang.invoke.SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
      String implMethodName = serializedLambda.getImplMethodName();
      if (implMethodName.startsWith("get")) {
        implMethodName = implMethodName.substring(3);
      } else if (implMethodName.startsWith("is")) {
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
}
