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

  @DisplayName("fori 手动往前移元素和 System.arraycopy 的性能")
  @Test
  void removeAndArraycopy() {
    Integer[] testInts = new Integer[1000000];
    for (int i = 0; i < testInts.length; i++) {
      testInts[i] = i + 1;
    }

    Integer[] ints = testInts;
    long currentTimeMillis = System.currentTimeMillis();
    for (int i = 3; i < ints.length - 1; i++) {
      ints[i] = ints[i + 1];
    }
    println("fori：" + (System.currentTimeMillis() - currentTimeMillis));
    println(ints[3]);

    ints = testInts;
    for (int i = 0; i < ints.length; i++) {
      ints[i] = i + 1;
    }
    currentTimeMillis = System.currentTimeMillis();
    if (ints.length - 1 - 3 >= 0) {
      System.arraycopy(ints, 3 + 1, ints, 3, ints.length - 1 - 3);
    }
    println("System.arraycopy：" + (System.currentTimeMillis() - currentTimeMillis));
    println(ints[3]);
  }

  @DisplayName("remove：删除元素")
  @Test
  void remove() {
    Integer[] ints = {1, 2, 3, 4, 5};
    CollectionUtils.remove(ints, 3, 0);
    println("删除下标为 3 的元素：" + ints[3]);
    println("删除下标为 3 的元素后，最后一个元素（给默认值）：" + ints[ints.length - 1]);
    CollectionUtils.remove(ints, 3);
    println("删除下标为 3 的元素后，最后一个元素（无默认值）：" + ints[ints.length - 1]);
    println("——————————————————");
    int[] ints1 = {1, 2, 3, 4, 5};
    CollectionUtils.remove(ints1, 3, 0);
    println("删除下标为 3 的元素：" + ints1[3]);
    println("删除下标为 3 的元素后，最后一个元素（给默认值）：" + ints[ints.length - 1]);
    CollectionUtils.remove(ints1, 3);
    println("删除下标为 3 的元素后，最后一个元素（无默认值）：" + ints[ints.length - 1]);
  }
}
