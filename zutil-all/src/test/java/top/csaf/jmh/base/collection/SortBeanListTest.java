package top.csaf.jmh.base.collection;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.coll.CollUtil;
import top.csaf.tree.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 排序 Bean List 性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class SortBeanListTest {

  public static void main(String[] args) {
    SortBeanListTest sortBeanListTest = new SortBeanListTest();
    System.out.println(CollUtil.isAllEqualsSameIndex(true, null, sortBeanListTest.ascByBeanComparator(), sortBeanListTest.ascByComparator()));
    System.out.println(CollUtil.isAllEqualsSameIndex(true, null, sortBeanListTest.descByBeanComparator(), sortBeanListTest.descByComparator()));
  }

  @Test
  void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{SortBeanListTest.class.getName()});
  }

  private static final List<TreeNode> TREE_NODE_LIST = new ArrayList<>();

  static {
    TREE_NODE_LIST.add(new TreeNode("1", "1", 1, "0"));
    TREE_NODE_LIST.add(new TreeNode("2", "1.1", 1, "1"));
    TREE_NODE_LIST.add(new TreeNode("3", "1.2", 2, "1"));
    TREE_NODE_LIST.add(new TreeNode("7", "1.2.3", 3, "3"));
    TREE_NODE_LIST.add(new TreeNode("6", "1.2.2", 2, "3"));
    TREE_NODE_LIST.add(new TreeNode("5", "1.2.1", 1, "3"));
    TREE_NODE_LIST.add(new TreeNode("4", "1.3", 3, "1"));
    TREE_NODE_LIST.add(new TreeNode("8", "2", 2, "0"));
    TREE_NODE_LIST.add(new TreeNode("9", "2.1", 1, "8"));
    TREE_NODE_LIST.add(new TreeNode("12", "2.1.2", 2, "9"));
    TREE_NODE_LIST.add(new TreeNode("13", "2.1.2.1", 1, "12"));
    TREE_NODE_LIST.add(new TreeNode("15", "2.1.2.3", 3, "12"));
    TREE_NODE_LIST.add(new TreeNode("14", "2.1.2.2", 2, "12"));
    TREE_NODE_LIST.add(new TreeNode("11", "2.1.1", 1, "9"));
    TREE_NODE_LIST.add(new TreeNode("10", "2.2", 2, "8"));
  }

  /**
   * BeanComparator 顺序
   * <p>
   * 来源：<a href="https://www.cnblogs.com/china-li/archive/2013/04/28/3048739.html">对已有的 List<Bean> 进行排序</a>
   *
   * @return 排序结果
   */
  @Benchmark
  public List<TreeNode> ascByBeanComparator() {
    Comparator<?> comparator = ComparableComparator.getInstance();
    // 允许 null，null < !null
    comparator = ComparatorUtils.nullLowComparator(comparator);
    TREE_NODE_LIST.sort(new BeanComparator<>("order", comparator));
    return TREE_NODE_LIST;
  }

  /**
   * BeanComparator 逆序
   *
   * @return 排序结果
   */
  @Benchmark
  public List<TreeNode> descByBeanComparator() {
    Comparator<?> comparator = ComparableComparator.getInstance();
    // 允许 null，null < !null
    comparator = ComparatorUtils.nullLowComparator(comparator);
    // 逆序
    comparator = ComparatorUtils.reversedComparator(comparator);
    TREE_NODE_LIST.sort(new BeanComparator<>("order", comparator));
    return TREE_NODE_LIST;
  }

  /**
   * Comparator 顺序
   * <p>
   * 来源：<a href="https://www.cnblogs.com/china-li/archive/2013/04/28/3048739.html">对已有的 List<Bean> 进行排序</a>
   *
   * @return 排序结果
   */
  @Benchmark
  public List<TreeNode> ascByComparator() {
    TREE_NODE_LIST.sort(
      // 允许 null，null < !null
      Comparator.nullsFirst(
        // 顺序
        Comparator.comparingInt(TreeNode::getOrder))

    );
    return TREE_NODE_LIST;
  }

  /**
   * Comparator 逆序
   *
   * @return 排序结果
   */
  @Benchmark
  public List<TreeNode> descByComparator() {
    TREE_NODE_LIST.sort(
      // 允许 null，null < !null
      Comparator.nullsFirst(
        // 逆序
        (o1, o2) -> o2.getOrder() - o1.getOrder())
    );
    return TREE_NODE_LIST;
  }
}

// Benchmark                                                             Mode     Cnt     Score     Error   Units
// SortBeanListTest.ascByBeanComparator                                 thrpt       5     0.294 ±   0.046  ops/us
// SortBeanListTest.ascByComparator                                     thrpt       5     7.608 ±   0.643  ops/us
// SortBeanListTest.descByBeanComparator                                thrpt       5     0.291 ±   0.025  ops/us
// SortBeanListTest.descByComparator                                    thrpt       5     8.068 ±   0.176  ops/us
// SortBeanListTest.ascByBeanComparator                                  avgt       5     3.483 ±   0.670   us/op
// SortBeanListTest.ascByComparator                                      avgt       5     0.129 ±   0.003   us/op
// SortBeanListTest.descByBeanComparator                                 avgt       5     3.358 ±   0.387   us/op
// SortBeanListTest.descByComparator                                     avgt       5     0.123 ±   0.012   us/op
// SortBeanListTest.ascByBeanComparator                                sample  166093     3.571 ±   0.051   us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.00      sample             2.900             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.50      sample             3.100             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.90      sample             4.096             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.95      sample             4.600             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.99      sample             7.800             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.999     sample            43.072             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p0.9999    sample           188.404             us/op
// SortBeanListTest.ascByBeanComparator:ascByBeanComparator·p1.00      sample          1200.128             us/op
// SortBeanListTest.ascByComparator                                    sample  138421     0.203 ±   0.016   us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.00              sample             0.100             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.50              sample             0.200             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.90              sample             0.200             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.95              sample             0.200             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.99              sample             0.300             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.999             sample             7.956             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p0.9999            sample            68.890             us/op
// SortBeanListTest.ascByComparator:ascByComparator·p1.00              sample           295.424             us/op
// SortBeanListTest.descByBeanComparator                               sample  112834     3.613 ±   0.115   us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.00    sample             2.900             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.50    sample             3.100             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.90    sample             3.900             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.95    sample             4.496             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.99    sample             8.288             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.999   sample            63.626             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p0.9999  sample           373.179             us/op
// SortBeanListTest.descByBeanComparator:descByBeanComparator·p1.00    sample          2498.560             us/op
// SortBeanListTest.descByComparator                                   sample  164728     0.155 ±   0.007   us/op
// SortBeanListTest.descByComparator:descByComparator·p0.00            sample             0.100             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.50            sample             0.100             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.90            sample             0.200             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.95            sample             0.200             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.99            sample             0.300             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.999           sample             0.927             us/op
// SortBeanListTest.descByComparator:descByComparator·p0.9999          sample            14.619             us/op
// SortBeanListTest.descByComparator:descByComparator·p1.00            sample           247.808             us/op
// SortBeanListTest.ascByBeanComparator                                    ss       5   230.240 ± 966.572   us/op
// SortBeanListTest.ascByComparator                                        ss       5    21.320 ±  19.729   us/op
// SortBeanListTest.descByBeanComparator                                   ss       5   221.120 ±  68.058   us/op
// SortBeanListTest.descByComparator                                       ss       5    17.540 ±  14.103   us/op
