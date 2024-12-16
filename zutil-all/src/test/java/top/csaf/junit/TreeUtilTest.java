package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.tree.TreeConfig;
import top.csaf.tree.TreeNode;
import top.csaf.tree.TreeUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("树工具类测试")
class TreeUtilTest {

  private final List<TreeNode> TREE_NODE_LIST = new ArrayList<>();

  {
    TREE_NODE_LIST.add(new TreeNode(1, "1", 1, "0"));
    TREE_NODE_LIST.add(new TreeNode(2, "1.1", 1, "1"));
    TREE_NODE_LIST.add(new TreeNode(3, "1.2", 2, "1"));
    TREE_NODE_LIST.add(new TreeNode(7, "1.2.3", 3, "3"));
    TREE_NODE_LIST.add(new TreeNode(6, "1.2.2", 2, "3"));
    TREE_NODE_LIST.add(new TreeNode(5, "1.2.1", 1, "3"));
    TREE_NODE_LIST.add(new TreeNode(4, "1.3", 3, "1"));
    TREE_NODE_LIST.add(new TreeNode(8, "2", 2, "0"));
    TREE_NODE_LIST.add(new TreeNode(9, "2.1", 1, "8"));
    TREE_NODE_LIST.add(new TreeNode(12, "2.1.2", 2, "9"));
    TREE_NODE_LIST.add(new TreeNode(13, "2.1.2.1", 1, "12"));
    TREE_NODE_LIST.add(new TreeNode(15, "2.1.2.3", 3, "12"));
    TREE_NODE_LIST.add(new TreeNode(14, "2.1.2.2", 2, "12"));
    TREE_NODE_LIST.add(new TreeNode(11, "2.1.1", 1, "9"));
    TREE_NODE_LIST.add(new TreeNode(10, "2.2", 2, "8"));
    TREE_NODE_LIST.add(new TreeNode(999, "9", 999, "000"));
  }

  @DisplayName("构建树")
  @Test
  void build() {
    List<TreeNode> treeNodeList1 = new ArrayList<>();
    treeNodeList1.add(new TreeNode(null, "1", 1, "0"));
    assertThrows(IllegalArgumentException.class, () -> TreeUtil.build(treeNodeList1));
    List<TreeNode> treeNodeList2 = new ArrayList<>();
    treeNodeList2.add(new TreeNode(1, "", 1, (String) null));
    assertThrows(IllegalArgumentException.class, () -> TreeUtil.build(treeNodeList2, TreeConfig.builder().rootParentIdValues(new Object[]{"0"}).build()));

    /** {@link TreeUtil#bulid(List)} */
    List<TreeNode> treeNodeList = TreeUtil.build(TREE_NODE_LIST);
    // 默认不排序所以 1.2.3 在第一个
    assertEquals("1.2.3", treeNodeList.get(0).getChildren().get(1).getChildren().get(0).getName());

    /** {@link TreeUtil#bulid(List, TreeConfig)} */
    TreeConfig treeConfig = TreeConfig.builder().rootParentIdValues(new Object[]{"0"}).isIgnoreIdTypeMismatch(true).build();
    treeConfig.setSort(true);
    treeConfig.setComparator(null);
    assertThrows(IllegalArgumentException.class, () -> TreeUtil.build(TREE_NODE_LIST, treeConfig));
    // 排序后
    treeConfig.setComparator(Comparator.nullsFirst(Comparator.comparing(TreeNode::getId, Comparator.comparingInt(s -> Integer.parseInt(s.toString())))));
    assertEquals("1.2.1", TreeUtil.build(TREE_NODE_LIST, treeConfig).get(0).getChildren().get(1).getChildren().get(0).getName());
    // 排序前
    treeConfig.setSort(false);
    assertEquals("1.2.3", TreeUtil.build(TREE_NODE_LIST, treeConfig).get(0).getChildren().get(1).getChildren().get(0).getName());
    // 找不到父类也为顶级节点
    treeConfig.setRootByNullParent(true);
    assertEquals(999, TreeUtil.build(TREE_NODE_LIST, treeConfig).get(2).getId());
    // 级别
    treeConfig.setGenLevel(true);
    assertEquals(3, TreeUtil.build(TREE_NODE_LIST, treeConfig).get(0).getChildren().get(1).getChildren().get(0).get("level"));
    // 祖级 ID
    treeConfig.setGenAncestors(true);
    assertEquals("1,3,7", TreeUtil.build(TREE_NODE_LIST, treeConfig).get(0).getChildren().get(1).getChildren().get(0).get("ancestors"));
    // 是否有子级
    treeConfig.setGenHasChildren(true);
    assertEquals(true, TreeUtil.build(TREE_NODE_LIST, treeConfig).get(0).getChildren().get(1).get("hasChildren"));
  }
}
