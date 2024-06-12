package top.csaf.tree;

import lombok.Data;

import java.util.Comparator;

/**
 * 树节点配置
 */
@Data
public class TreeConfig {
  /**
   * 是否排序
   */
  private boolean isSort = false;
  /**
   * 排序规则，默认按照 ID 排序，null 值排在开始
   */
  private Comparator<TreeNode> comparator;
  /**
   * 根节点的父级 ID，默认为 0, 0L, "0", null, "", 0d, 0f, (short) 0
   * <p>
   * 节点的父级 ID 满足这些值，则为顶级节点
   */
  private Object[] rootParentIdValues = new Object[]{0, 0L, "0", null, "", 0d, 0f, (short) 0};
  /**
   * 父级 ID 有值，但不满足 {@link TreeConfig#rootParentIdValues} 中的值，而且查不到父级时，是否为顶级节点
   */
  private boolean isRootByNullParent = false;
}
