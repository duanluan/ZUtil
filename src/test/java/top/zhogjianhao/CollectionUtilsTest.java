package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
@DisplayName("集合工具类测试")
public class CollectionUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("isEmpty：是否为 null 或没有元素")
  @Test
  void isEmpty() {
    List<String> list = new ArrayList<>();
    println("空列表：" + CollectionUtils.isEmpty(list));
    list.add(null);
    println("[0] 为 null 的列表：" + CollectionUtils.isEmpty(list));
  }

  @DisplayName("sizeIsEmpty：是否为 null 或没有元素")
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

  @DisplayName("isAllEmpty：是否所有元素都为 null")
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

  @DisplayName("isAnyEmpty：是否任意元素为 null")
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
}
