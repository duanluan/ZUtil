package top.csaf.tree;

import top.csaf.ArrayUtils;
import top.csaf.bean.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树工具类
 */
public class TreeUtils {

  /**
   * 构建树
   * <p>
   * 如果节点的父级 ID 满足 {@link TreeConfig#getRootParentIdValues()} 中的值，则为顶级节点
   *
   * @param treeNodes 树节点列表
   * @return 树列表
   */
  public static List<TreeNode> build(List<TreeNode> treeNodes) {
    List<TreeNode> treeNodes1 = BeanUtils.deepClone(treeNodes);

    // id 为 key，value 为自身存储到 Map 中
    Map<String, TreeNode> treeNodeMap = new LinkedHashMap<>(treeNodes1.size());
    for (TreeNode treeNode : treeNodes1) {
      treeNodeMap.put(treeNode.getId().toString(), treeNode);
    }
    List<TreeNode> treeList = new ArrayList<>();
    for (TreeNode treeNode : treeNodes1) {
      // 根据当前节点的父级 ID 获取父级节点
      TreeNode parent = treeNodeMap.get(treeNode.getParentId().toString());
      // 如果满足顶级节点的条件
      if (ArrayUtils.contains(new TreeConfig().getRootParentIdValues(), parent)) {
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

  /**
   * 构建树
   *
   * @param treeNodes 树节点列表
   * @param treeConfig 树配置
   * @return 树列表
   */
  public static List<TreeNode> build(List<TreeNode> treeNodes, TreeConfig treeConfig) {
    List<TreeNode> treeNodes1 = BeanUtils.deepClone(treeNodes);

    // 排序
    if (treeConfig.isSort()) {
      if (treeConfig.getComparator() == null) {
        throw new IllegalArgumentException("Comparator: when sort is true, comparator can not be null");
      }
      treeNodes1.sort(treeConfig.getComparator());
    }

    // id 为 key，value 为自身存储到 Map 中
    Map<String, TreeNode> treeNodeMap = new LinkedHashMap<>(treeNodes1.size());
    for (TreeNode treeNode : treeNodes1) {
      treeNodeMap.put(treeNode.getId().toString(), treeNode);
    }
    List<TreeNode> treeList = new ArrayList<>();
    for (TreeNode treeNode : treeNodes1) {
      String parentId = treeNode.getParentId().toString();
      // 如果父级 ID 满足顶级节点的值
      if (ArrayUtils.contains(treeConfig.getRootParentIdValues(), parentId)) {
        // 则为顶级节点
        treeList.add(treeNode);
      } else {
        // 根据当前节点的父级 ID 获取父级节点
        TreeNode parent = treeNodeMap.get(parentId);
        // 如果没有父节点
        if (treeConfig.isRootByNullParent() && parent == null) {
          // 则为顶级节点
          treeList.add(treeNode);
          continue;
        }
        if (parent == null) {
          // 丢弃该节点
          continue;
        }
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
