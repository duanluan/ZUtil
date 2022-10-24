package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.ArrayUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 数组去重测试
 * <p>
 * 参考来源：
 * <ul>
 *   <li><a href="https://www.jb51.net/article/227338.htm">java 去除数组重复元素的四种方法</a></li>
 *   <li><a href="https://icode.best/i/32803137102614">java list 去重 相同的相加_Java 数组与列表去重的 18 种方法</a></li>
 * </ul>
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ArrayDeduplicateTest {

  public static void main(String[] args) {
    System.out.println("原数组：" + Arrays.toString(ARRAY));
    ArrayDeduplicateTest test = new ArrayDeduplicateTest();
    System.out.println("保留第一个重复元素：");
    System.out.println("test2" + Arrays.toString(test.test2()));
    System.out.println("test3" + Arrays.toString(test.test3()));
    System.out.println("test5" + Arrays.toString(test.test5()));
    System.out.println("test6" + Arrays.toString(test.test6()));
    System.out.println("test7" + Arrays.toString(test.test7()));
    System.out.println("test8" + Arrays.toString(test.test8()));
    System.out.println("test9" + Arrays.toString(test.test9()));
    System.out.println("test11" + Arrays.toString(test.test11()));
    System.out.println("test13" + Arrays.toString(test.test13()));
    System.out.println("test17" + Arrays.toString(test.test17()));
    System.out.println("test18" + Arrays.toString(test.test18()));
    System.out.println("test19" + Arrays.toString(test.test19()));
    System.out.println("test20" + Arrays.toString(test.test20()));
    System.out.println("test21" + Arrays.toString(test.test21()));
    System.out.println("test22" + Arrays.toString(test.test22()));
    System.out.println("ArrayUtils.deduplicate：" + Arrays.toString(ArrayUtils.deduplicate(ARRAY)));
    System.out.println("保留最后一个重复元素：");
    System.out.println("test1" + Arrays.toString(test.test1()));
    System.out.println("ArrayUtils.deduplicatePreceding：" + Arrays.toString(ArrayUtils.deduplicatePreceding(ARRAY)));
    System.out.println("倒序：");
    System.out.println("test14" + Arrays.toString(test.test14()));
    System.out.println("test16" + Arrays.toString(test.test16()));
    System.out.println("ArrayUtils.deduplicateReverse：" + Arrays.toString(ArrayUtils.deduplicateReverse(ARRAY)));
    System.out.println("hash 值排序：");
    System.out.println("test4" + Arrays.toString(test.test4()));
    System.out.println("test10" + Arrays.toString(test.test10()));
    System.out.println("test12" + Arrays.toString(test.test12()));
    System.out.println("test15" + Arrays.toString(test.test15()));
    System.out.println("ArrayUtils.deduplicateHashSort：" + Arrays.toString(ArrayUtils.deduplicateHashSort(ARRAY)));
  }

  @Test
  public void benchark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ArrayDeduplicateTest.class.getName()});
  }

  private static final String[] ARRAY = new String[]{"f", "c", "a", "e", "a", "e", "a", "d", "a", "f", "a", "d", "a", "c", "a"};

  /**
   * 循环 + arraycopy
   *
   * @return 去重后的数组，保留最后一个重复元素
   */
  @Benchmark
  public String[] test1() {
    // 用来记录去除重复之后的数组长度和给临时数组作为下标索引
    int t = 0;
    // 临时数组
    String[] tempArr = new String[ARRAY.length];
    // 遍历原数组
    for (int i = 0; i < ARRAY.length; i++) {
      // 声明一个标记，并每次重置
      boolean isTrue = true;
      // 内层循环将原数组的元素逐个对比
      for (int j = i + 1; j < ARRAY.length; j++) {
        // 如果发现有重复元素，改变标记状态并结束当次内层循环
        if (ARRAY[i].equals(ARRAY[j])) {
          isTrue = false;
          break;
        }
      }
      // 判断标记是否被改变，如果没被改变就是没有重复元素
      if (isTrue) {
        // 没有元素就将原数组的元素赋给临时数组
        tempArr[t] = ARRAY[i];
        // 走到这里证明当前元素没有重复，那么记录自增
        t++;
      }
    }
    // 声明需要返回的数组，这个才是去重后的数组
    String[] newArr = new String[t];
    // 用 arraycopy 方法将刚才去重的数组拷贝到新数组并返回
    System.arraycopy(tempArr, 0, newArr, 0, t);
    return newArr;
  }

  /**
   * stream distinct
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test2() {
    return Arrays.asList(ARRAY).parallelStream().distinct().toArray(String[]::new);
  }

  /**
   * 循环 + List contains
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test3() {
    // 创建一个集合
    List<String> list = new ArrayList<>();
    for (String s : ARRAY) {
      // 如果集合里面没有相同的元素才往里存
      if (!list.contains(s)) {
        list.add(s);
      }
    }

    // toArray() 方法会返回一个包含集合所有元素的 Object 类型数组
    return list.toArray(new String[0]);
  }

  /**
   * Set 去重
   *
   * @return 去重后的数组，hash 值排序
   */
  @Benchmark
  public String[] test4() {
    // 实例化一个 set 集合并存入数组，如果元素已存在则不会重复存入
    Set<String> set = new HashSet<>(Arrays.asList(ARRAY));
    // 返回 Set 集合的数组形式
    return set.toArray(new String[0]);
  }

  /**
   * Set add 判断
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test5() {
    Set<String> set = new HashSet<>();
    List<String> list = new ArrayList<>();
    for (String str : ARRAY) {
      // 重复的话返回false
      if (set.add(str)) {
        list.add(str);
      }
    }
    return list.toArray(new String[0]);
  }

  /**
   * 双循环遍历全部成员，将当前项目与左边项逐个进行对比，如果值相同且下标相同表示唯一，其他则认为是重复项进行忽略
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test6() {
    String[] newArr = new String[ARRAY.length];
    int x = 0;
    for (int i = 0, l = ARRAY.length; i < l; i++) {
      for (int j = 0; j <= i; j++) {
        if (Objects.equals(ARRAY[i], ARRAY[j])) {
          if (i == j) {
            newArr[x] = ARRAY[i];
            x++;
          }
          break;
        }
      }
    }
    return Arrays.copyOf(newArr, x);
  }

  /**
   * 先将数组转换为List，利用List的indexOf方法查找下标，当下标匹配时表示唯一，添加到新列表中
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test7() {
    int x = 0;
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    int l = list.size();
    for (int i = 0; i < l; i++) {
      if (list.indexOf(ARRAY[i]) == i) {
        list.add(ARRAY[i]);
        x++;
      }
    }
    // 返回取出的非重复项
    return list.subList(list.size() - x, list.size()).toArray(new String[0]);
  }

  /**
   * 在原有列表上移除重复项目。自后往前遍历，逐个与前面项比较，如果值相同且下标相同，则移除当前项。
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test8() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    int l = list.size();
    while (l-- > 0) {
      int i = l;
      while (i-- > 0) {
        if (list.get(l).equals(list.get(i))) {
          list.remove(l);
          break;
        }
      }
    }
    return list.toArray(new String[0]);
  }

  /**
   * 在原有列表上移除重复项目。自前往后遍历，逐个与前面项比较，如果值相同且下标相同，则移除前面项。
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test9() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    int l = list.size();
    for (int i = 1; i < l; i++) {
      for (int j = 0; j < i; j++) {
        if (list.get(i).equals(list.get(j))) {
          list.remove(i);
          i--;
          l--;
          break;
        }
      }
    }
    return list.toArray(new String[0]);
  }

  /**
   * 利用hashMap属性唯一性来实现去重复
   *
   * @return 去重后的数组，hash 值排序
   */
  @Benchmark
  public String[] test10() {
    Map<String, String> map = new HashMap<>();
    for (String item : ARRAY) {
      if (map.containsKey(item)) {
        continue;
      }
      map.put(item, item);
    }
    List<String> list = new ArrayList<>(map.values());
    return list.toArray(new String[0]);
  }

  static List<String> unique7newArr = new ArrayList<>();

  static boolean unique7contains(String item) {
    if (!unique7newArr.contains(item)) {
      unique7newArr.add(item);
      return true;
    }
    return false;
  }

  /**
   * 利用filter表达式，即把不符合条件的过滤掉。需要借助外部列表存储不重复项
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test11() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    return list.stream().filter(ArrayDeduplicateTest::unique7contains).collect(Collectors.toList()).toArray(new String[ArrayDeduplicateTest.unique7newArr.size()]);
  }

  /**
   * 利用hashSet数据结构直接去重复项。无序非同步
   *
   * @return 去重后的数组，hash 值排序
   */
  @Benchmark
  public String[] test12() {
    Set<String> set = new HashSet<>(Arrays.asList(ARRAY));
    return new ArrayList<>(set).toArray(new String[set.size()]);
  }

  /**
   * 利用LinkedHashSet数据结构直接去重复项。有序链表
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test13() {
    Set<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(ARRAY));
    return new ArrayList<>(linkedHashSet).toArray(new String[linkedHashSet.size()]);
  }

  /**
   * 利用TreeSet数据结构直接去重复项。自然排序和定制排序
   *
   * @return 去重后的数组，倒序
   */
  @Benchmark
  public String[] test14() {
    Set<String> treeSet = new TreeSet<>(Arrays.asList(ARRAY)).descendingSet();
    return new ArrayList<>(treeSet).toArray(new String[treeSet.size()]);
  }

  /**
   * 提前排序，从后向前遍历，将当前项与前一项对比，如果重复则移除当前项
   *
   * @return 去重后的数组，hash 值排序
   */
  @Benchmark
  public String[] test15() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    Collections.sort(list);
    for (int l = list.size() - 1; l > 0; l--) {
      if (list.get(l).equals(list.get(l - 1))) {
        list.remove(l);
      }
    }
    return new ArrayList<>(list).toArray(new String[list.size()]);
  }

  /**
   * 提前排序，自前往后遍历，将当前项与后一项对比，如果重复则移除当前项
   *
   * @return 去重后的数组，倒序
   */
  @Benchmark
  public String[] test16() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    list.sort(Collections.reverseOrder());
    int l = list.size() - 1;
    for (int i = 0; i < l; i++) {
      if (list.get(i).equals(list.get(i + 1))) {
        list.remove(i);
        i--;
        l--;
      }
    }
    return new ArrayList<>(list).toArray(new String[list.size()]);
  }

  /**
   * 转为stream，利用distinct方法去重复
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test17() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    list = list.stream().distinct().collect(Collectors.toList());
    return new ArrayList<>(list).toArray(new String[list.size()]);
  }

  /**
   * 双循环自右往左逐个与左侧项对比，如遇相同则跳过当前项，下一项为当前项，继续逐个与左侧项对比
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test18() {
    int len = ARRAY.length;
    String[] result = new String[len];
    int x = len;
    for (int i = len - 1; i >= 0; i--) {
      for (int j = i - 1; j >= 0; j--) {
        if (ARRAY[i].equals(ARRAY[j])) {
          i--;
          j = i;
        }
      }
      // 非重复项的为唯一，追加到新数组
      result[--x] = ARRAY[i];
    }
    return Arrays.copyOfRange(result, x, len);
  }

  /**
   * 利用Interator来遍历List，如果不在新列表中则添加
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test19() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    List<String> result = new ArrayList<>();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      String item = it.next();
      if (!result.contains(item)) {
        result.add(item);
      }
    }
    return new ArrayList<>(result).toArray(new String[result.size()]);
  }

  String[] uniqueRecursion1(String[] arr, int len, List<String> result) {
    int last = len - 1;
    String lastItem = arr[last];
    int l = last;
    boolean isRepeat = false;
    if (len <= 1) {
      result.add(0, lastItem);
      return new ArrayList<>(result).toArray(new String[result.size()]);
    }
    while (l-- > 0) {
      if (lastItem.equals(arr[l])) {
        isRepeat = true;
        break;
      }
    }
    // 如果不重复表示唯一，则添加到新数组中
    if (!isRepeat) {
      result.add(0, lastItem);
    }
    return uniqueRecursion1(arr, len - 1, result);
  }

  /**
   * 利用递归调用来去重复。递归自后往前逐个调用，当长度为1时终止。
   * 当后一项与前任一项相同说明有重复，则删除当前项。相当于利用自我调用来替换循环
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test20() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    return uniqueRecursion1(ARRAY, list.size(), new ArrayList<>());
  }

  List<String> uniqueRecursion2(List<String> arr, int len) {
    if (len <= 1) {
      return arr;
    }
    int last = len - 1;
    int l = last - 1;
    boolean isRepeat = false;
    String lastItem = arr.get(last);
    while (l >= 0) {
      if (lastItem.equals(arr.get(l))) {
        isRepeat = true;
        break;
      }
      l--;
    }
    // 如果不重复则添加到临时列表，最后将全部结果拼接
    List<String> result = new ArrayList<>();
    arr.remove(last);
    if (!isRepeat) {
      result.add(lastItem);
    }
    return Stream.concat(uniqueRecursion2(arr, len - 1).stream(), result.stream()).collect(Collectors.toList());
  }

  /**
   * 利用递归调用来去重复的另外一种方式。递归自后往前逐个调用，当长度为1时终止。
   * 与上一个递归不同，这里将不重复的项目作为结果拼接起来
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test21() {
    List<String> list = new ArrayList<>(Arrays.asList(ARRAY));
    List<String> result = uniqueRecursion2(list, list.size());
    return new ArrayList<>(result).toArray(new String[result.size()]);
  }

  /**
   * 双重循环，将左侧项逐个与当前项比较。如果遇到值想等则比较下标，下标相同则追加到新数组。这里与第1个方案稍微的不同
   *
   * @return 去重后的数组，保留第一个重复元素
   */
  @Benchmark
  public String[] test22() {
    String[] newArr = new String[ARRAY.length];
    int x = 0;
    for (int i = 0; i < ARRAY.length; i++) {
      int j = 0;
      ;
      for (; j < i; j++) {
        if (ARRAY[i].equals(ARRAY[j])) {
          break;
        }
      }
      if (i == j) {
        newArr[x] = ARRAY[i];
        x++;
      }
    }
    return Arrays.copyOf(newArr, x);
  }

}

// Benchmark                                       Mode     Cnt      Score     Error   Units
// ArrayDeduplicateTest.test1                   thrpt       5      7.162 ±   2.881  ops/us
// ArrayDeduplicateTest.test10                  thrpt       5      7.262 ±   1.617  ops/us
// ArrayDeduplicateTest.test11                  thrpt       5      6.934 ±   0.670  ops/us
// ArrayDeduplicateTest.test12                  thrpt       5      3.950 ±   1.758  ops/us
// ArrayDeduplicateTest.test13                  thrpt       5      4.287 ±   0.087  ops/us
// ArrayDeduplicateTest.test14                  thrpt       5      3.701 ±   0.339  ops/us
// ArrayDeduplicateTest.test15                  thrpt       5      2.995 ±   0.175  ops/us
// ArrayDeduplicateTest.test16                  thrpt       5      2.443 ±   1.327  ops/us
// ArrayDeduplicateTest.test17                  thrpt       5      3.025 ±   2.207  ops/us
// ArrayDeduplicateTest.test18                  thrpt       5      7.172 ±   0.582  ops/us
// ArrayDeduplicateTest.test19                  thrpt       5      5.262 ±   0.460  ops/us
// ArrayDeduplicateTest.test2                   thrpt       5      0.028 ±   0.001  ops/us
// ArrayDeduplicateTest.test20                  thrpt       5      4.976 ±   0.258  ops/us
// ArrayDeduplicateTest.test21                  thrpt       5      0.695 ±   0.019  ops/us
// ArrayDeduplicateTest.test22                  thrpt       5      9.961 ±   0.853  ops/us
// ArrayDeduplicateTest.test3                   thrpt       5      7.596 ±   0.196  ops/us
// ArrayDeduplicateTest.test4                   thrpt       5      4.873 ±   0.111  ops/us
// ArrayDeduplicateTest.test5                   thrpt       5      6.958 ±   0.150  ops/us
// ArrayDeduplicateTest.test6                   thrpt       5      8.129 ±   1.247  ops/us
// ArrayDeduplicateTest.test7                   thrpt       5      5.324 ±   0.194  ops/us
// ArrayDeduplicateTest.test8                   thrpt       5      5.566 ±   0.164  ops/us
// ArrayDeduplicateTest.test9                   thrpt       5      5.626 ±   0.157  ops/us
// ArrayDeduplicateTest.test1                    avgt       5      0.124 ±   0.006   us/op
// ArrayDeduplicateTest.test10                   avgt       5      0.115 ±   0.003   us/op
// ArrayDeduplicateTest.test11                   avgt       5      0.144 ±   0.003   us/op
// ArrayDeduplicateTest.test12                   avgt       5      0.224 ±   0.005   us/op
// ArrayDeduplicateTest.test13                   avgt       5      0.241 ±   0.053   us/op
// ArrayDeduplicateTest.test14                   avgt       5      0.255 ±   0.025   us/op
// ArrayDeduplicateTest.test15                   avgt       5      0.347 ±   0.013   us/op
// ArrayDeduplicateTest.test16                   avgt       5      0.352 ±   0.029   us/op
// ArrayDeduplicateTest.test17                   avgt       5      0.281 ±   0.003   us/op
// ArrayDeduplicateTest.test18                   avgt       5      0.130 ±   0.003   us/op
// ArrayDeduplicateTest.test19                   avgt       5      0.177 ±   0.008   us/op
// ArrayDeduplicateTest.test2                    avgt       5     34.981 ±   3.132   us/op
// ArrayDeduplicateTest.test20                   avgt       5      0.244 ±   0.052   us/op
// ArrayDeduplicateTest.test21                   avgt       5      1.581 ±   0.381   us/op
// ArrayDeduplicateTest.test22                   avgt       5      0.101 ±   0.001   us/op
// ArrayDeduplicateTest.test3                    avgt       5      0.132 ±   0.010   us/op
// ArrayDeduplicateTest.test4                    avgt       5      0.215 ±   0.010   us/op
// ArrayDeduplicateTest.test5                    avgt       5      0.146 ±   0.008   us/op
// ArrayDeduplicateTest.test6                    avgt       5      0.113 ±   0.031   us/op
// ArrayDeduplicateTest.test7                    avgt       5      0.199 ±   0.007   us/op
// ArrayDeduplicateTest.test8                    avgt       5      0.189 ±   0.029   us/op
// ArrayDeduplicateTest.test9                    avgt       5      0.181 ±   0.013   us/op
// ArrayDeduplicateTest.test1                  sample  115749      0.303 ±   0.005   us/op
// ArrayDeduplicateTest.test1:test1·p0.00      sample              0.100             us/op
// ArrayDeduplicateTest.test1:test1·p0.50      sample              0.300             us/op
// ArrayDeduplicateTest.test1:test1·p0.90      sample              0.400             us/op
// ArrayDeduplicateTest.test1:test1·p0.95      sample              0.500             us/op
// ArrayDeduplicateTest.test1:test1·p0.99      sample              0.600             us/op
// ArrayDeduplicateTest.test1:test1·p0.999     sample              1.000             us/op
// ArrayDeduplicateTest.test1:test1·p0.9999    sample             21.591             us/op
// ArrayDeduplicateTest.test1:test1·p1.00      sample             90.496             us/op
// ArrayDeduplicateTest.test10                 sample  141790      0.168 ±   0.003   us/op
// ArrayDeduplicateTest.test10:test10·p0.00    sample              0.100             us/op
// ArrayDeduplicateTest.test10:test10·p0.50    sample              0.200             us/op
// ArrayDeduplicateTest.test10:test10·p0.90    sample              0.200             us/op
// ArrayDeduplicateTest.test10:test10·p0.95    sample              0.200             us/op
// ArrayDeduplicateTest.test10:test10·p0.99    sample              0.400             us/op
// ArrayDeduplicateTest.test10:test10·p0.999   sample              0.600             us/op
// ArrayDeduplicateTest.test10:test10·p0.9999  sample             18.592             us/op
// ArrayDeduplicateTest.test10:test10·p1.00    sample             83.584             us/op
// ArrayDeduplicateTest.test11                 sample  125514      0.328 ±   0.025   us/op
// ArrayDeduplicateTest.test11:test11·p0.00    sample              0.100             us/op
// ArrayDeduplicateTest.test11:test11·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test11:test11·p0.90    sample              0.400             us/op
// ArrayDeduplicateTest.test11:test11·p0.95    sample              0.500             us/op
// ArrayDeduplicateTest.test11:test11·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test11:test11·p0.999   sample              1.000             us/op
// ArrayDeduplicateTest.test11:test11·p0.9999  sample             35.221             us/op
// ArrayDeduplicateTest.test11:test11·p1.00    sample            941.056             us/op
// ArrayDeduplicateTest.test12                 sample  164573      0.275 ±   0.027   us/op
// ArrayDeduplicateTest.test12:test12·p0.00    sample              0.200             us/op
// ArrayDeduplicateTest.test12:test12·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test12:test12·p0.90    sample              0.300             us/op
// ArrayDeduplicateTest.test12:test12·p0.95    sample              0.300             us/op
// ArrayDeduplicateTest.test12:test12·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test12:test12·p0.999   sample              0.800             us/op
// ArrayDeduplicateTest.test12:test12·p0.9999  sample             21.296             us/op
// ArrayDeduplicateTest.test12:test12·p1.00    sample           1331.200             us/op
// ArrayDeduplicateTest.test13                 sample  159350      0.287 ±   0.018   us/op
// ArrayDeduplicateTest.test13:test13·p0.00    sample              0.200             us/op
// ArrayDeduplicateTest.test13:test13·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test13:test13·p0.90    sample              0.300             us/op
// ArrayDeduplicateTest.test13:test13·p0.95    sample              0.300             us/op
// ArrayDeduplicateTest.test13:test13·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test13:test13·p0.999   sample              0.900             us/op
// ArrayDeduplicateTest.test13:test13·p0.9999  sample             39.798             us/op
// ArrayDeduplicateTest.test13:test13·p1.00    sample            829.440             us/op
// ArrayDeduplicateTest.test14                 sample  155256      0.305 ±   0.042   us/op
// ArrayDeduplicateTest.test14:test14·p0.00    sample              0.200             us/op
// ArrayDeduplicateTest.test14:test14·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test14:test14·p0.90    sample              0.300             us/op
// ArrayDeduplicateTest.test14:test14·p0.95    sample              0.300             us/op
// ArrayDeduplicateTest.test14:test14·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test14:test14·p0.999   sample              0.900             us/op
// ArrayDeduplicateTest.test14:test14·p0.9999  sample             23.035             us/op
// ArrayDeduplicateTest.test14:test14·p1.00    sample           1587.200             us/op
// ArrayDeduplicateTest.test15                 sample  105974      0.519 ±   0.089   us/op
// ArrayDeduplicateTest.test15:test15·p0.00    sample              0.300             us/op
// ArrayDeduplicateTest.test15:test15·p0.50    sample              0.400             us/op
// ArrayDeduplicateTest.test15:test15·p0.90    sample              0.500             us/op
// ArrayDeduplicateTest.test15:test15·p0.95    sample              0.500             us/op
// ArrayDeduplicateTest.test15:test15·p0.99    sample              1.000             us/op
// ArrayDeduplicateTest.test15:test15·p0.999   sample              2.402             us/op
// ArrayDeduplicateTest.test15:test15·p0.9999  sample             40.822             us/op
// ArrayDeduplicateTest.test15:test15·p1.00    sample           1953.792             us/op
// ArrayDeduplicateTest.test16                 sample  109443      0.474 ±   0.043   us/op
// ArrayDeduplicateTest.test16:test16·p0.00    sample              0.300             us/op
// ArrayDeduplicateTest.test16:test16·p0.50    sample              0.400             us/op
// ArrayDeduplicateTest.test16:test16·p0.90    sample              0.500             us/op
// ArrayDeduplicateTest.test16:test16·p0.95    sample              0.500             us/op
// ArrayDeduplicateTest.test16:test16·p0.99    sample              1.000             us/op
// ArrayDeduplicateTest.test16:test16·p0.999   sample              2.756             us/op
// ArrayDeduplicateTest.test16:test16·p0.9999  sample             39.200             us/op
// ArrayDeduplicateTest.test16:test16·p1.00    sample           1404.928             us/op
// ArrayDeduplicateTest.test17                 sample  136460      0.323 ±   0.004   us/op
// ArrayDeduplicateTest.test17:test17·p0.00    sample              0.200             us/op
// ArrayDeduplicateTest.test17:test17·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test17:test17·p0.90    sample              0.300             us/op
// ArrayDeduplicateTest.test17:test17·p0.95    sample              0.400             us/op
// ArrayDeduplicateTest.test17:test17·p0.99    sample              0.700             us/op
// ArrayDeduplicateTest.test17:test17·p0.999   sample              1.400             us/op
// ArrayDeduplicateTest.test17:test17·p0.9999  sample             27.518             us/op
// ArrayDeduplicateTest.test17:test17·p1.00    sample             78.592             us/op
// ArrayDeduplicateTest.test18                 sample  139572      0.281 ±   0.025   us/op
// ArrayDeduplicateTest.test18:test18·p0.00    sample              0.100             us/op
// ArrayDeduplicateTest.test18:test18·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test18:test18·p0.90    sample              0.300             us/op
// ArrayDeduplicateTest.test18:test18·p0.95    sample              0.400             us/op
// ArrayDeduplicateTest.test18:test18·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test18:test18·p0.999   sample              0.800             us/op
// ArrayDeduplicateTest.test18:test18·p0.9999  sample             32.025             us/op
// ArrayDeduplicateTest.test18:test18·p1.00    sample           1037.312             us/op
// ArrayDeduplicateTest.test19                 sample  137055      0.376 ±   0.039   us/op
// ArrayDeduplicateTest.test19:test19·p0.00    sample              0.100             us/op
// ArrayDeduplicateTest.test19:test19·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test19:test19·p0.90    sample              0.400             us/op
// ArrayDeduplicateTest.test19:test19·p0.95    sample              0.600             us/op
// ArrayDeduplicateTest.test19:test19·p0.99    sample              0.700             us/op
// ArrayDeduplicateTest.test19:test19·p0.999   sample              1.100             us/op
// ArrayDeduplicateTest.test19:test19·p0.9999  sample             44.390             us/op
// ArrayDeduplicateTest.test19:test19·p1.00    sample           1077.248             us/op
// ArrayDeduplicateTest.test2                  sample   81499     31.285 ±   0.826   us/op
// ArrayDeduplicateTest.test2:test2·p0.00      sample              1.900             us/op
// ArrayDeduplicateTest.test2:test2·p0.50      sample             34.496             us/op
// ArrayDeduplicateTest.test2:test2·p0.90      sample             44.096             us/op
// ArrayDeduplicateTest.test2:test2·p0.95      sample             47.552             us/op
// ArrayDeduplicateTest.test2:test2·p0.99      sample             58.048             us/op
// ArrayDeduplicateTest.test2:test2·p0.999     sample            175.488             us/op
// ArrayDeduplicateTest.test2:test2·p0.9999    sample           2805.350             us/op
// ArrayDeduplicateTest.test2:test2·p1.00      sample          15646.720             us/op
// ArrayDeduplicateTest.test20                 sample  173125      0.382 ±   0.046   us/op
// ArrayDeduplicateTest.test20:test20·p0.00    sample              0.200             us/op
// ArrayDeduplicateTest.test20:test20·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test20:test20·p0.90    sample              0.400             us/op
// ArrayDeduplicateTest.test20:test20·p0.95    sample              0.500             us/op
// ArrayDeduplicateTest.test20:test20·p0.99    sample              0.700             us/op
// ArrayDeduplicateTest.test20:test20·p0.999   sample              1.300             us/op
// ArrayDeduplicateTest.test20:test20·p0.9999  sample             23.268             us/op
// ArrayDeduplicateTest.test20:test20·p1.00    sample           1904.640             us/op
// ArrayDeduplicateTest.test21                 sample  107081      1.609 ±   0.054   us/op
// ArrayDeduplicateTest.test21:test21·p0.00    sample              1.300             us/op
// ArrayDeduplicateTest.test21:test21·p0.50    sample              1.500             us/op
// ArrayDeduplicateTest.test21:test21·p0.90    sample              1.700             us/op
// ArrayDeduplicateTest.test21:test21·p0.95    sample              1.800             us/op
// ArrayDeduplicateTest.test21:test21·p0.99    sample              3.300             us/op
// ArrayDeduplicateTest.test21:test21·p0.999   sample             16.790             us/op
// ArrayDeduplicateTest.test21:test21·p0.9999  sample             62.368             us/op
// ArrayDeduplicateTest.test21:test21·p1.00    sample           1693.696             us/op
// ArrayDeduplicateTest.test22                 sample  128543      0.356 ±   0.165   us/op
// ArrayDeduplicateTest.test22:test22·p0.00    sample              0.100             us/op
// ArrayDeduplicateTest.test22:test22·p0.50    sample              0.300             us/op
// ArrayDeduplicateTest.test22:test22·p0.90    sample              0.400             us/op
// ArrayDeduplicateTest.test22:test22·p0.95    sample              0.400             us/op
// ArrayDeduplicateTest.test22:test22·p0.99    sample              0.600             us/op
// ArrayDeduplicateTest.test22:test22·p0.999   sample              1.300             us/op
// ArrayDeduplicateTest.test22:test22·p0.9999  sample             70.932             us/op
// ArrayDeduplicateTest.test22:test22·p1.00    sample           6332.416             us/op
// ArrayDeduplicateTest.test4                  sample  187734      0.238 ±   0.004   us/op
// ArrayDeduplicateTest.test4:test4·p0.00      sample              0.200             us/op
// ArrayDeduplicateTest.test4:test4·p0.50      sample              0.200             us/op
// ArrayDeduplicateTest.test4:test4·p0.90      sample              0.300             us/op
// ArrayDeduplicateTest.test4:test4·p0.95      sample              0.300             us/op
// ArrayDeduplicateTest.test4:test4·p0.99      sample              0.500             us/op
// ArrayDeduplicateTest.test4:test4·p0.999     sample              0.700             us/op
// ArrayDeduplicateTest.test4:test4·p0.9999    sample             17.622             us/op
// ArrayDeduplicateTest.test4:test4·p1.00      sample            121.344             us/op
// ArrayDeduplicateTest.test5                  sample  131512      0.182 ±   0.006   us/op
// ArrayDeduplicateTest.test5:test5·p0.00      sample              0.100             us/op
// ArrayDeduplicateTest.test5:test5·p0.50      sample              0.200             us/op
// ArrayDeduplicateTest.test5:test5·p0.90      sample              0.200             us/op
// ArrayDeduplicateTest.test5:test5·p0.95      sample              0.200             us/op
// ArrayDeduplicateTest.test5:test5·p0.99      sample              0.400             us/op
// ArrayDeduplicateTest.test5:test5·p0.999     sample              0.800             us/op
// ArrayDeduplicateTest.test5:test5·p0.9999    sample             19.507             us/op
// ArrayDeduplicateTest.test5:test5·p1.00      sample            156.160             us/op
// ArrayDeduplicateTest.test6                  sample  164774      0.264 ±   0.038   us/op
// ArrayDeduplicateTest.test6:test6·p0.00      sample              0.100             us/op
// ArrayDeduplicateTest.test6:test6·p0.50      sample              0.200             us/op
// ArrayDeduplicateTest.test6:test6·p0.90      sample              0.300             us/op
// ArrayDeduplicateTest.test6:test6·p0.95      sample              0.400             us/op
// ArrayDeduplicateTest.test6:test6·p0.99      sample              0.600             us/op
// ArrayDeduplicateTest.test6:test6·p0.999     sample              0.700             us/op
// ArrayDeduplicateTest.test6:test6·p0.9999    sample             31.965             us/op
// ArrayDeduplicateTest.test6:test6·p1.00      sample           1878.016             us/op
// ArrayDeduplicateTest.test7                  sample  170100      0.382 ±   0.038   us/op
// ArrayDeduplicateTest.test7:test7·p0.00      sample              0.200             us/op
// ArrayDeduplicateTest.test7:test7·p0.50      sample              0.300             us/op
// ArrayDeduplicateTest.test7:test7·p0.90      sample              0.500             us/op
// ArrayDeduplicateTest.test7:test7·p0.95      sample              0.600             us/op
// ArrayDeduplicateTest.test7:test7·p0.99      sample              0.700             us/op
// ArrayDeduplicateTest.test7:test7·p0.999     sample              1.200             us/op
// ArrayDeduplicateTest.test7:test7·p0.9999    sample             25.165             us/op
// ArrayDeduplicateTest.test7:test7·p1.00      sample           1445.888             us/op
// ArrayDeduplicateTest.test8                  sample  104688      0.335 ±   0.039   us/op
// ArrayDeduplicateTest.test8:test8·p0.00      sample              0.100             us/op
// ArrayDeduplicateTest.test8:test8·p0.50      sample              0.300             us/op
// ArrayDeduplicateTest.test8:test8·p0.90      sample              0.400             us/op
// ArrayDeduplicateTest.test8:test8·p0.95      sample              0.400             us/op
// ArrayDeduplicateTest.test8:test8·p0.99      sample              0.600             us/op
// ArrayDeduplicateTest.test8:test8·p0.999     sample              2.393             us/op
// ArrayDeduplicateTest.test8:test8·p0.9999    sample             39.500             us/op
// ArrayDeduplicateTest.test8:test8·p1.00      sample           1210.368             us/op
// ArrayDeduplicateTest.test9                  sample  105000      0.319 ±   0.006   us/op
// ArrayDeduplicateTest.test9:test9·p0.00      sample              0.100             us/op
// ArrayDeduplicateTest.test9:test9·p0.50      sample              0.300             us/op
// ArrayDeduplicateTest.test9:test9·p0.90      sample              0.400             us/op
// ArrayDeduplicateTest.test9:test9·p0.95      sample              0.400             us/op
// ArrayDeduplicateTest.test9:test9·p0.99      sample              0.600             us/op
// ArrayDeduplicateTest.test9:test9·p0.999     sample              1.300             us/op
// ArrayDeduplicateTest.test9:test9·p0.9999    sample             36.704             us/op
// ArrayDeduplicateTest.test9:test9·p1.00      sample             48.960             us/op
// ArrayDeduplicateTest.test1                      ss       5      6.300 ±   5.459   us/op
// ArrayDeduplicateTest.test10                     ss       5     16.460 ±  19.086   us/op
// ArrayDeduplicateTest.test11                     ss       5     60.900 ±  96.826   us/op
// ArrayDeduplicateTest.test12                     ss       5     28.360 ±  30.576   us/op
// ArrayDeduplicateTest.test13                     ss       5     33.280 ±  32.766   us/op
// ArrayDeduplicateTest.test14                     ss       5     62.120 ± 203.103   us/op
// ArrayDeduplicateTest.test15                     ss       5     23.800 ±  24.697   us/op
// ArrayDeduplicateTest.test16                     ss       5     36.880 ±  38.563   us/op
// ArrayDeduplicateTest.test17                     ss       5     66.480 ±  44.630   us/op
// ArrayDeduplicateTest.test18                     ss       5      8.760 ±   9.300   us/op
// ArrayDeduplicateTest.test19                     ss       5     17.320 ±   8.521   us/op
// ArrayDeduplicateTest.test2                      ss       5    299.620 ± 558.696   us/op
// ArrayDeduplicateTest.test20                     ss       5     16.740 ±   6.134   us/op
// ArrayDeduplicateTest.test21                     ss       5    136.340 ±  39.700   us/op
// ArrayDeduplicateTest.test22                     ss       5      9.320 ±   6.145   us/op
// ArrayDeduplicateTest.test3                      ss       5     10.260 ±   5.098   us/op
// ArrayDeduplicateTest.test4                      ss       5     26.120 ±  18.701   us/op
// ArrayDeduplicateTest.test5                      ss       5     13.860 ±  11.271   us/op
// ArrayDeduplicateTest.test6                      ss       5     11.880 ±  21.366   us/op
// ArrayDeduplicateTest.test7                      ss       5     27.060 ±  23.716   us/op
// ArrayDeduplicateTest.test8                      ss       5     30.320 ±  27.840   us/op
// ArrayDeduplicateTest.test9                      ss       5     32.060 ±  43.995   us/op
