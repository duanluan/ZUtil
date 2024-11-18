package top.csaf.tree;

import lombok.extern.slf4j.Slf4j;
import top.csaf.bean.BeanUtil;
import top.csaf.lang.ArrayUtil;
import top.csaf.lang.StrUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树工具类
 */
@Slf4j
public class TreeUtil {

  /**
   * 构建树
   *
   * @param treeNodes  树节点列表
   * @param treeConfig 树配置
   * @return 树列表
   */
  public static List<TreeNode> build(List<TreeNode> treeNodes, TreeConfig treeConfig) {
    if (treeConfig == null) {
      throw new IllegalArgumentException("TreeConfig: can not be null");
    }
    if (ArrayUtil.isEmpty(treeConfig.getRootParentIdValues())) {
      throw new IllegalArgumentException("TreeConfig: rootParentIdValues can not be empty");
    }
    List<TreeNode> treeNodes1 = BeanUtil.deepClone(treeNodes);

    // 排序
    if (treeConfig.isSort()) {
      if (treeConfig.getComparator() == null) {
        throw new IllegalArgumentException("Comparator: when sort is true, comparator can not be null");
      }
      treeNodes1.sort(treeConfig.getComparator());
    }

    // id 为 key，value 为自身存储到 Map 中
    Map<Object, TreeNode> treeNodeMap = new LinkedHashMap<>(treeNodes1.size());
    for (TreeNode treeNode : treeNodes1) {
      if (StrUtil.isBlank(treeNode.getId())) {
        throw new IllegalArgumentException("TreeNode: id can not be blank");
      }
      Object key = treeNode.getId();
      if (treeConfig.isIgnoreIdTypeMismatch()) {
        key = key.toString();
      }
      treeNodeMap.put(key, treeNode);
    }
    List<TreeNode> treeList = new ArrayList<>();
    for (TreeNode treeNode : treeNodes1) {
      // 如果父级 ID 满足顶级节点的值
      if (ArrayUtil.contains(treeConfig.getRootParentIdValues(), treeNode.getParentId())) {
        // 则为顶级节点
        treeList.add(treeNode);
      } else {
        if (StrUtil.isBlank(treeNode.getParentId())) {
          throw new IllegalArgumentException("TreeNode: parentId can not be blank");
        }
        Object parentId = treeNode.getParentId();
        if (treeConfig.isIgnoreIdTypeMismatch()) {
          parentId = parentId.toString();
        }
        // 根据当前节点的父级 ID 获取父级节点
        TreeNode parent = treeNodeMap.get(parentId);
        // 如果没有父节点
        if (treeConfig.isRootByNullParent() && parent == null) {
          // 则为顶级节点
          treeList.add(treeNode);
          continue;
        }
        if (parent == null) {
          log.warn("TreeNode: parent is null, id: {}, parentId: {}", treeNode.getId(), treeNode.getParentId());
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

  /**
   * 构建树
   * <p>
   * 默认配置：
   * <ul>
   *   <li>不排序</li>
   *   <li>根节点的父级 ID：0, 0L, "0", null, "", 0d, 0f, (short) 0</li>
   *   <li>父级 ID 有值，但不满足根节点的值，而且查不到父级时，不为顶级节点</li>
   *   <li>忽略父级 ID 和 ID 的类型不同</li>
   * </ul>
   *
   * @param treeNodes 树节点列表
   * @return 树列表
   */
  public static List<TreeNode> build(List<TreeNode> treeNodes) {
    return build(treeNodes, TreeConfig.builder()
      .isSort(false)
      .rootParentIdValues(new Object[]{0, 0L, "0", null, "", 0d, 0f, (short) 0})
      .isRootByNullParent(false)
      .isIgnoreIdTypeMismatch(true)
      .build());
  }
}
