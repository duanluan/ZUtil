package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.DefaultParameterNameDiscoverer;
import top.csaf.coll.MapUtil;
import top.csaf.lang.StrUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description MapUtil 工具类测试
 * @Author Rick
 * @Date 2024/5/30 09:21
 **/
@Slf4j
@DisplayName("MapUtil 工具类测试")
public class MapUtilTest {

  public static void main(String[] args) {
    genMapUtilsFn();
  }

  /**
   * 调用 {@link org.apache.commons.collections4.MapUtils } 的方法
   */
  static void genMapUtilsFn() {
    // 获取 org.apache.commons.collections4.MapUtils 中所有的方法名
    Class<?> clazz = org.apache.commons.collections4.MapUtils.class;
    Method[] methods = clazz.getDeclaredMethods();
    // 根据方法生成调用方法代码
    for (Method method : methods) {
      // 修饰符不为 public static 时跳过
      if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC)) {
        continue;
      }
      // 有 @Deprecated 注解时跳过
      if (method.getAnnotation(Deprecated.class) != null) {
        continue;
      }
      // 参数名
      String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(method);

      // 方法描述，比如 public static <K,V> java.util.Map$Entry<K, V> org.apache.commons.collections4.MapUtils.get(java.util.Map<K, V>,int)
      String genericStr = method.toGenericString();
      // TODO 获取方法返回值泛型的继承类，比如 public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b) {
      // 将 java.util.Map$Entry<K, V> 改为 java.util.Map.Entry<K, V>
      genericStr = genericStr.replaceAll("\\$", ".");
      // 去除 org.apache.commons.collections4.MapUtils
      genericStr = genericStr.replace("org.apache.commons.collections4.MapUtils.", "");
      // 给参数类型加上参数名
      StringBuilder paramsStr = new StringBuilder();
      String paramsType = genericStr.substring(genericStr.indexOf("(") + 1, genericStr.indexOf(")"));
      if (StrUtil.isNotBlank(paramsType)) {
        String[] paramTypes = paramsType.split(",");
        int j = 0;
        for (int i = 0; i < paramTypes.length; i++) {
          String param = paramTypes[i];
          // 如果为泛型，当前参数为当前参数 + 下一个参数
          if (param.contains("<") && !param.contains(">")) {
            param = param + "," + paramTypes[i + 1];
            i++;
          }
          paramsStr.append(param).append(" ").append(paramNames[j]);
          if (i != paramTypes.length - 1) {
            paramsStr.append(", ");
          }
          j++;
        }
      }
      // 生成代码
      System.out.println(genericStr.replace(genericStr.substring(genericStr.indexOf("("), genericStr.indexOf(")") + 1), "(" + paramsStr + ")") + " {");
      System.out.println("  " + (method.getReturnType().getSimpleName().equals("void") ? "" : "return ") + "org.apache.commons.collections4.MapUtils." + method.getName() + "(" + StrUtil.join(paramNames, ", ") + ");");
      System.out.println("}\n");
    }
  }
}
