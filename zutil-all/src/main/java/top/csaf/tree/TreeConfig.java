package top.csaf.tree;

import lombok.Builder;
import lombok.Data;

import java.util.Comparator;

/**
 * 树配置
 */
@Builder
@Data
public class TreeConfig {
  /**
   * 是否排序
   */
  @Builder.Default
  private boolean isSort = false;
  /**
   * 排序规则
   */
  private Comparator<TreeNode> comparator;
  /**
   * 根节点的父级 ID，默认为 0, 0L, "0", null, "", 0d, 0f, (short) 0
   * <p>
   * 节点的父级 ID 满足这些值，则为顶级节点
   */
  @Builder.Default
  private Object[] rootParentIdValues = new Object[]{0, 0L, "0", null, "", 0d, 0f, (short) 0};
  /**
   * 父级 ID 有值，但不满足 {@link TreeConfig#rootParentIdValues} 中的值，而且查不到父级时，是否为顶级节点
   */
  @Builder.Default
  private boolean isRootByNullParent = false;
  /**
   * 是否忽略父级 ID 和 ID 的类型不同
   */
  @Builder.Default
  private boolean isIgnoreIdTypeMismatch = true;

  /**
   * “级别”字段名
   */
  @Builder.Default
  private String levelKey = "level";
  /**
   * 是否生成级别
   */
  @Builder.Default
  private boolean isGenLevel = false;
  /**
   * “祖级”字段名
   */
  @Builder.Default
  private String ancestorsKey = "ancestors";
  /**
   * 是否生成祖级，比如 ID 为 2，父级 ID 为 1，祖级为 1,2
   */
  @Builder.Default
  private boolean isGenAncestors = false;
  /**
   * “是否有子级”字段名
   */
  @Builder.Default
  private String hasChildrenKey = "hasChildren";
  /**
   * 是否生成“是否有子级”字段 hasChildren
   */
  @Builder.Default
  private boolean isGenHasChildren = false;
}
