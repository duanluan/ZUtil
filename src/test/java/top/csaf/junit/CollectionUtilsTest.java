package top.csaf.junit;

import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.DefaultParameterNameDiscoverer;
import top.csaf.CollectionUtils;
import top.csaf.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("集合工具类测试")
public class CollectionUtilsTest {

  public static void main(String[] args) {
    genCollections4Fn();
  }

  /**
   * 调用 {@link org.apache.commons.collections4.CollectionUtils } 的方法
   */
  static void genCollections4Fn() {
    // 获取 org.apache.commons.collections4.CollectionUtils 中所有的方法名
    Class<?> clazz = org.apache.commons.collections4.CollectionUtils.class;
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

      // 方法描述，比如 public static <K,V> java.util.Map$Entry<K, V> org.apache.commons.collections4.CollectionUtils.get(java.util.Map<K, V>,int)
      String genericStr = method.toGenericString();
      // TODO 获取方法返回值泛型的继承类，比如 public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b) {
      // 将 java.util.Map$Entry<K, V> 改为 java.util.Map.Entry<K, V>
      genericStr = genericStr.replaceAll("\\$", ".");
      // 去除 org.apache.commons.collections4.CollectionUtils
      genericStr = genericStr.replace("org.apache.commons.collections4.CollectionUtils.", "");
      // 给参数类型加上参数名
      StringBuilder paramsStr = new StringBuilder();
      String paramsType = genericStr.substring(genericStr.indexOf("(") + 1, genericStr.indexOf(")"));
      if (StringUtils.isNotBlank(paramsType)) {
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
      System.out.println("  " + (method.getReturnType().getSimpleName().equals("void") ? "" : "return ") + "org.apache.commons.collections4.CollectionUtils." + method.getName() + "(" + StringUtils.join(paramNames, ", ") + ");");
      System.out.println("}\n");
    }
  }

  @DisplayName("原样调用 org.apache.commons.collections4.CollectionUtils 的方法")
  @Test
  void collections4() {
    List<Object> list = new ArrayList<>();
    list.add(1);
    assertEquals(1, CollectionUtils.get(list, 0));
    // TODO 其他方法
  }


  @DisplayName("isAllEmpty：是否 每个对象的 (所有元素都为 null || 没有元素)")
  @Test
  void isAllEmpty() {
    List<String> list = new ArrayList<>();
    list.add(null);
    System.out.println("[0] 为 null 的列表：" + CollectionUtils.isAllEmpty(list));
    Map<String, Object> map = new HashMap<>();
    map.put("", null);
    System.out.println("{\"\", null} 的键值对：" + CollectionUtils.isAllEmpty(map));
    System.out.println("[null] 的数组：" + CollectionUtils.isAllEmpty(new Object[]{null}));
    System.out.println("[0] 为 null 的迭代器（Iterator）：" + CollectionUtils.isAllEmpty(list.iterator()));
    Vector<String> vector = new Vector<>();
    vector.add(null);
    System.out.println("[0] 为 null 的枚举：" + CollectionUtils.isAllEmpty(vector.elements()));
  }

  @DisplayName("isAnyEmpty：是否 任意对象 (为 null || 没有元素 || 任意元素为 null)")
  @Test
  void isAnyEmpty() {
    List<String> list = new ArrayList<>();
    list.add(null);
    list.add("");
    System.out.println("[0] 为 null 的列表：" + CollectionUtils.isAnyEmpty(list));
    Map<String, Object> map = new HashMap<>();
    map.put("", null);
    System.out.println("{\"\", null} 的键值对：" + CollectionUtils.isAnyEmpty(map));
    System.out.println("[null] 的数组：" + CollectionUtils.isAnyEmpty(new Object[]{null, 1}));
    System.out.println("[0] 为 null 的迭代器（Iterator）：" + CollectionUtils.isAnyEmpty(list.iterator()));
    Vector<String> vector = new Vector<>();
    vector.add(null);
    vector.add("");
    System.out.println("[0] 为 null 的枚举：" + CollectionUtils.isAnyEmpty(vector.elements()));
  }

  @DisplayName("是否 每个对象的每个元素都相等")
  @Test
  void isAllEquals() {
    /** {@link CollectionUtils#isAllEquals(boolean, Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isAllEquals(true, null, null));
    assertThrows(IllegalArgumentException.class, () -> CollectionUtils.isAllEquals(true, null, new Object[]{}));

    List<Object> list = new ArrayList<>();
    list.add("1");
    List<Object> list1 = new ArrayList<>();
    list1.add(new BigInteger("1"));
    JsonArray jsonArray = new JsonArray();
    jsonArray.add('1');
    Map<String, Object> map = new HashMap<>();
    map.put("1", new BigDecimal("1"));
    Object[] array = new Object[]{1.0};
    Vector<Object> vector = new Vector<>();
    vector.add(1.0f);
    assertTrue(CollectionUtils.isAllEquals(true, CollectionUtils::sizeIsEmpty, list, null, list1.iterator(), jsonArray, map, array, vector.elements()));
    // continueFunction 为 null，后续判断到 Iterator。数组排在第一个用于填充上一个元素。
    assertTrue(CollectionUtils.isAllEquals(true, null, array, list1.iterator()));
    // continueFunction 为 null，后续判断到 Enumeration。Enumeration 排在第一个用于填充上一个元素。
    assertTrue(CollectionUtils.isAllEquals(true, null, vector.elements(), list));
    // Iterable 元素不一致结束
    list.set(0, 2);
    assertFalse(CollectionUtils.isAllEquals(true, null, list, list1));
    // 数组元素不一致结束
    array[0] = 2;
    assertFalse(CollectionUtils.isAllEquals(true, null, list1, array));
    // Enumeration 元素不一致结束
    vector.set(0, 2);
    assertFalse(CollectionUtils.isAllEquals(true, null, list1, vector.elements()));
    // isToString = false
    assertFalse(CollectionUtils.isAllEquals(false, null, list, null));

    /** {@link CollectionUtils#isAllEquals(Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isAllEquals(null, null));
    assertFalse(CollectionUtils.isAllEquals(null, new ArrayList<>(), null));
  }

  @DisplayName("是否不满足 每个对象的每个元素都相等")
  @Test
  void isNotAllEquals() {
    /** {@link CollectionUtils#isNotAllEquals(boolean, Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isNotAllEquals(true, null, null));

    List<Object> list = new ArrayList<>();
    list.add(1);
    List<Object> list1 = new ArrayList<>();
    list1.add(2);
    assertTrue(CollectionUtils.isNotAllEquals(true, null, list, list1));

    /** {@link CollectionUtils#isNotAllEquals(Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isNotAllEquals(null, null));
    assertTrue(CollectionUtils.isNotAllEquals(null, new ArrayList<>(), null));
  }

  @DisplayName("是否 每个对象的同一位置的元素都相等")
  @Test
  void isAllEqualsSameIndex() {
    /** {@link CollectionUtils#isAllEquals(boolean, Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isAllEqualsSameIndex(true, null, null));
    assertThrows(IllegalArgumentException.class, () -> CollectionUtils.isAllEqualsSameIndex(true, null, new ArrayList<>()));

    List<Object> list = new ArrayList<>();
    list.add("1");
    list.add(2);
    list.add(new BigDecimal("3.0"));
    List<Object> list1 = new ArrayList<>();
    list1.add(new BigInteger("1"));
    list1.add(2.0f);
    list1.add(3L);
    JsonArray jsonArray = new JsonArray();
    jsonArray.add('1');
    jsonArray.add(2.0);
    jsonArray.add(3);
    Map<String, Object> map = new HashMap<>();
    map.put("1", '1');
    map.put("2", 2.0);
    map.put("3", 3);
    Object[] array = new Object[]{'1', 2.0, 3};
    Vector<Object> vector = new Vector<>();
    vector.add('1');
    vector.add(2.0);
    vector.add(3);
    assertTrue(CollectionUtils.isAllEqualsSameIndex(true, CollectionUtils::sizeIsEmpty, list, null, list1.iterator(), jsonArray, map, array, vector.elements()));
    // 长度不一致判断结束
    list.remove(2);
    assertFalse(CollectionUtils.isAllEqualsSameIndex(true, null, new ArrayList<>(), list));
    // 列表元素不一致结束
    list.add(4);
    assertFalse(CollectionUtils.isAllEqualsSameIndex(true, null, list, list1.iterator()));
    // 数组排在第一个用于填充上一次列表
    assertTrue(CollectionUtils.isAllEqualsSameIndex(true, CollectionUtils::sizeIsEmpty, array, list1));
    // 数组元素不一致结束
    array[2] = 4;
    assertFalse(CollectionUtils.isAllEqualsSameIndex(true, CollectionUtils::sizeIsEmpty, list1, array));

    /** {@link CollectionUtils#isAllEquals(Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isAllEqualsSameIndex(null, null));
    assertFalse(CollectionUtils.isAllEqualsSameIndex(null, new ArrayList<>(), null));
  }

  @DisplayName("是否不满足 每个对象的同一位置的元素都相等")
  @Test
  void isNotAllEqualsSameIndex() {
    /** {@link CollectionUtils#isNotAllEqualsSameIndex(boolean, Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isNotAllEqualsSameIndex(true, null, null));

    List<Object> list = new ArrayList<>();
    list.add("1");
    list.add(2);
    list.add(new BigDecimal("3.0"));
    List<Object> list1 = new ArrayList<>();
    list1.add(new BigInteger("1"));
    list1.add(2.0f);
    list1.add(3L);
    assertFalse(CollectionUtils.isNotAllEqualsSameIndex(true, null, list, list1));

    /** {@link CollectionUtils#isNotAllEqualsSameIndex(Function, Object...)} */
    assertThrows(NullPointerException.class, () -> CollectionUtils.isNotAllEqualsSameIndex(null, null));
    assertTrue(CollectionUtils.isNotAllEqualsSameIndex(null, new ArrayList<>(), null));
  }
}
