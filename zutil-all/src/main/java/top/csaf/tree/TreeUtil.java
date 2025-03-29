package top.csaf.tree;

import lombok.extern.slf4j.Slf4j;
import top.csaf.bean.BeanUtil;
import top.csaf.bean.ConvertUtil;
import top.csaf.lang.ArrayUtil;
import top.csaf.lang.StrUtil;

import java.util.*;

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
      Comparator<TreeNode> comparator = treeConfig.getComparator();
      if (comparator == null) {
        throw new IllegalArgumentException("Comparator: when sort is true, comparator can not be null");
      }
      treeNodes1.sort(comparator);
    }

    Class<?> idType = treeConfig.getIdType();
    // id 为 key，value 为自身存储到 Map 中
    Map<Object, TreeNode> treeNodeMap = new LinkedHashMap<>(treeNodes1.size());
    for (TreeNode treeNode : treeNodes1) {
      if (StrUtil.isBlank(treeNode.getId())) {
        throw new IllegalArgumentException("TreeNode: id can not be blank");
      }

      // 根据指定的 ID 类型转换 ID、父级 ID
      if (idType != null) {
        treeNode.setId(ConvertUtil.convert(treeNode.getId(), idType));
        treeNode.setParentId(ConvertUtil.convert(treeNode.getParentId(), idType));
      }

      Object key = treeNode.getId();
      if (idType == null && treeConfig.isIgnoreIdTypeMismatch()) {
        key = key.toString();
      }
      treeNodeMap.put(key, treeNode);
    }

    // 级别
    boolean isGenLevel = treeConfig.isGenLevel();
    String levelKey = null;
    if (isGenLevel) {
      levelKey = treeConfig.getLevelKey();
    }
    // 祖级
    boolean isGenAncestors = treeConfig.isGenAncestors();
    String ancestorsKey = null;
    if (isGenAncestors) {
      ancestorsKey = treeConfig.getAncestorsKey();
    }
    // 是否有子级
    boolean isGenHasChildren = treeConfig.isGenHasChildren();
    String hasChildrenKey = null;
    if (isGenHasChildren) {
      hasChildrenKey = treeConfig.getHasChildrenKey();
    }
    List<TreeNode> treeList = new ArrayList<>();
    for (TreeNode treeNode : treeNodes1) {
      // 如果父级 ID 满足顶级节点的值
      if (ArrayUtil.contains(treeConfig.getRootParentIdValues(), treeNode.getParentId())) {
        if (isGenLevel) {
          // 顶级节点的级别为 1
          treeNode.put(levelKey, 1);
        }
        if (isGenAncestors) {
          // 顶级节点的祖级为自身 ID
          treeNode.put(ancestorsKey, treeNode.getId());
        }
        // 为顶级节点
        treeList.add(treeNode);
        continue;
      }

      if (StrUtil.isBlank(treeNode.getParentId())) {
        throw new IllegalArgumentException("TreeNode: parentId can not be blank");
      }
      Object parentId = treeNode.getParentId();
      if (treeConfig.isIgnoreIdTypeMismatch()) {
        parentId = parentId.toString();
      }
      // 根据当前节点的父级 ID 获取父级节点
      TreeNode parent = treeNodeMap.get(parentId);
      // 如果没有父节点且配置为顶级节点
      if (treeConfig.isRootByNullParent() && parent == null) {
        if (isGenLevel) {
          // 顶级节点的级别为 1
          treeNode.put(levelKey, 1);
        }
        if (isGenAncestors) {
          // 顶级节点的祖级为自身 ID
          treeNode.put(ancestorsKey, treeNode.getId());
        }
        // 为顶级节点
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
      if (isGenLevel) {
        // 级别为父级节点的级别 + 1
        treeNode.put(levelKey, Integer.parseInt(parent.get(levelKey).toString()) + 1);
      }
      if (isGenAncestors) {
        // 祖级为父级节点的祖级（包含父级 ID） + 自身 ID
        treeNode.put(ancestorsKey, parent.get(ancestorsKey) + "," + treeNode.getId());
      }
      // 将自身添加到父节点的子节点列表中
      parent.getChildren().add(treeNode);
      if (isGenHasChildren) {
        // 是否有子级
        parent.put(hasChildrenKey, true);
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
    return build(treeNodes, TreeConfig.builder().build());
  }
}
