package top.csaf.jmh.base.tree;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.coll.CollUtil;
import top.csaf.tree.TreeNode;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 生成树节点列表性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class BuildTreeNodeListTest {

  public static void main(String[] args) {
    BuildTreeNodeListTest buildTreeNodeListTest = new BuildTreeNodeListTest();
    System.out.println(CollUtil.isAllEqualsSameIndex(true, null,
      buildTreeNodeListTest.build(),
      buildTreeNodeListTest.buildByRecursive(),
      buildTreeNodeListTest.buildByMap()
    ));
  }

  @Test
  void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{BuildTreeNodeListTest.class.getName()});
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
   * 双层循环
   * <p>
   * 来源：<a href="https://blog.csdn.net/massivestars/article/details/53911620/">将 List 转成树的两种方式（递归、循环）</a>
   *
   * @return 树列表
   */
  @Benchmark
  public List<TreeNode> build() {
    List<TreeNode> trees = new LinkedList<>();
    for (TreeNode treeNode : TREE_NODE_LIST) {
      // 如果是顶级节点则直接添加
      if ("0".equals(treeNode.getParentId())) {
        trees.add(treeNode);
      }
      // 双层循环
      for (TreeNode treeNode1 : TREE_NODE_LIST) {
        // 如果二层循环节点的父级 ID = 一层循环节点的 ID
        if (treeNode1.getParentId().equals(treeNode.getId())) {
          // 则将其添加到一层循环节点的 children 中
          if (treeNode.getChildren() == null) {
            treeNode.setChildren(new LinkedList<>());
          }
          treeNode.getChildren().add(treeNode1);
        }
      }
    }
    return trees;
  }

  public static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodes) {
    for (TreeNode it : treeNodes) {
      if (treeNode.getId().equals(it.getParentId())) {
        if (treeNode.getChildren() == null) {
          treeNode.setChildren(new ArrayList<TreeNode>());
        }
        treeNode.getChildren().add(findChildren(it, treeNodes));
      }
    }
    return treeNode;
  }

  /**
   * 递归
   * <p>
   * 来源：<a href="https://blog.csdn.net/massivestars/article/details/53911620/">将 List 转成树的两种方式（递归、循环）</a>
   *
   * @return 树列表
   */
  @Benchmark
  public List<TreeNode> buildByRecursive() {
    Set<TreeNode> trees = new HashSet<>();
    for (TreeNode treeNode : TREE_NODE_LIST) {
      if ("0".equals(treeNode.getParentId())) {
        trees.add(findChildren(treeNode, TREE_NODE_LIST));
      }
    }
    return new ArrayList<>(trees);
  }

  /**
   * Map
   * <p>
   * 来源：<a href="https://blog.csdn.net/imyc7/article/details/112380154">高效的将 List 转换成 Tree</a>
   *
   * @return 树列表
   */
  @Benchmark
  public List<TreeNode> buildByMap() {
    // id 为 key，value 为自身存储到 Map 中
    Map<String, TreeNode> treeNodeMap = new LinkedHashMap<>(TREE_NODE_LIST.size());
    for (TreeNode treeNode : TREE_NODE_LIST) {
      treeNodeMap.put(treeNode.getId().toString(), treeNode);
    }
    List<TreeNode> treeList = new ArrayList<>();
    for (TreeNode treeNode : TREE_NODE_LIST) {
      // 根据当前节点的父级 ID 获取父级节点
      TreeNode parent = treeNodeMap.get(treeNode.getParentId().toString());
      // 如果没有父节点
      if (parent == null) {
        // 则为顶级节点
        treeList.add(treeNode);
      } else {
        // 如果没有子节点列表，则创建列表
        if (parent.getChildren() == null) {
          parent.setChildren(new ArrayList<>());
        }
        // 将自身添加到父节点的子节点列表中
        parent.getChildren().add(treeNode);
      }
    }
    return treeList;
  }
}

// Benchmark                                                          Mode     Cnt        Score       Error   Units
// BuildTreeNodeListTest.build                                       thrpt       5        0.864 ±     1.543  ops/us
// BuildTreeNodeListTest.buildByMap                                  thrpt       5        1.697 ±     4.151  ops/us
// BuildTreeNodeListTest.buildByRecursive                            thrpt       5       ≈ 10⁻⁵              ops/us
// BuildTreeNodeListTest.build                                        avgt       5        2.182 ±    10.494   us/op
// BuildTreeNodeListTest.buildByMap                                   avgt       5        1.815 ±    10.738   us/op
// BuildTreeNodeListTest.buildByRecursive                             avgt       5   126878.579 ± 80184.045   us/op
// BuildTreeNodeListTest.build                                      sample  140719       41.806 ±   133.978   us/op
// BuildTreeNodeListTest.build:build·p0.00                          sample                0.900               us/op
// BuildTreeNodeListTest.build:build·p0.50                          sample                1.100               us/op
// BuildTreeNodeListTest.build:build·p0.90                          sample                1.100               us/op
// BuildTreeNodeListTest.build:build·p0.95                          sample                1.200               us/op
// BuildTreeNodeListTest.build:build·p0.99                          sample                2.300               us/op
// BuildTreeNodeListTest.build:build·p0.999                         sample                3.600               us/op
// BuildTreeNodeListTest.build:build·p0.9999                        sample               20.288               us/op
// BuildTreeNodeListTest.build:build·p1.00                          sample          5729419.264               us/op
// BuildTreeNodeListTest.buildByMap                                 sample  127848        0.446 ±     0.003   us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.00                sample                0.300               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.50                sample                0.400               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.90                sample                0.500               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.95                sample                0.500               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.99                sample                1.300               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.999               sample                2.900               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p0.9999              sample               23.065               us/op
// BuildTreeNodeListTest.buildByMap:buildByMap·p1.00                sample               41.984               us/op
// BuildTreeNodeListTest.buildByRecursive                           sample      43   124359.894 ± 11243.885   us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.00    sample            92667.904               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.50    sample           122814.464               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.90    sample           155608.678               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.95    sample           165884.723               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.99    sample           167247.872               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.999   sample           167247.872               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p0.9999  sample           167247.872               us/op
// BuildTreeNodeListTest.buildByRecursive:buildByRecursive·p1.00    sample           167247.872               us/op
// BuildTreeNodeListTest.build                                          ss       5       36.200 ±    13.494   us/op
// BuildTreeNodeListTest.buildByMap                                     ss       5       16.460 ±     9.240   us/op
// BuildTreeNodeListTest.buildByRecursive                               ss       5      139.360 ±    97.022   us/op
