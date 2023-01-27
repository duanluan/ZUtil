package top.csaf.junit;

import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("集合工具类测试")
public class CollectionUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("isEmpty：是否 每个集合都 (为 null || 没有元素)")
  @Test
  void isEmpty() {
    List<String> list = new ArrayList<>();
    println("空列表：" + CollectionUtils.isEmpty(list));
    list.add(null);
    println("[0] 为 null 的列表：" + CollectionUtils.isEmpty(list));
  }

  @DisplayName("sizeIsEmpty：是否 每个对象都 (为 null || 没有元素)")
  @Test
  void sizeIsEmpty() {
    List<String> list = new ArrayList<>();
    println("空列表：" + CollectionUtils.sizeIsEmpty(list));
    list.add(null);
    println("[0] = null 的列表：" + CollectionUtils.sizeIsEmpty(list));
    Map<String, Object> map = new HashMap<>();
    println("空键值对：" + CollectionUtils.sizeIsEmpty(map));
    map.put(null, null);
    println("{null, null} 的键值对：" + CollectionUtils.sizeIsEmpty(map));
    // TODO ……
  }

  @DisplayName("isAllEmpty：是否 每个对象的 (所有元素都为 null || 没有元素)")
  @Test
  void isAllEmpty() {
    List<String> list = new ArrayList<>();
    list.add(null);
    println("[0] 为 null 的列表：" + CollectionUtils.isAllEmpty(list));
    Map<String, Object> map = new HashMap<>();
    map.put("", null);
    println("{\"\", null} 的键值对：" + CollectionUtils.isAllEmpty(map));
    println("[null] 的数组：" + CollectionUtils.isAllEmpty(new Object[]{null}));
    println("[0] 为 null 的迭代器（Iterator）：" + CollectionUtils.isAllEmpty(list.iterator()));
    Vector<String> vector = new Vector<>();
    vector.add(null);
    println("[0] 为 null 的枚举：" + CollectionUtils.isAllEmpty(vector.elements()));
  }

  @DisplayName("isAnyEmpty：是否 任意对象 (为 null || 没有元素 || 任意元素为 null)")
  @Test
  void isAnyEmpty() {
    List<String> list = new ArrayList<>();
    list.add(null);
    list.add("");
    println("[0] 为 null 的列表：" + CollectionUtils.isAnyEmpty(list));
    Map<String, Object> map = new HashMap<>();
    map.put("", null);
    println("{\"\", null} 的键值对：" + CollectionUtils.isAnyEmpty(map));
    println("[null] 的数组：" + CollectionUtils.isAnyEmpty(new Object[]{null, 1}));
    println("[0] 为 null 的迭代器（Iterator）：" + CollectionUtils.isAnyEmpty(list.iterator()));
    Vector<String> vector = new Vector<>();
    vector.add(null);
    vector.add("");
    println("[0] 为 null 的枚举：" + CollectionUtils.isAnyEmpty(vector.elements()));
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
