package top.csaf.tree;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点
 */
@NoArgsConstructor
@Data
public class TreeNode implements Serializable {
  /**
   * ID
   */
  private Object id;
  /**
   * 名称
   */
  private String name;
  /**
   * 顺序
   */
  private Integer order;
  /**
   * 父级 ID
   */
  private Object parentId;
  /**
   * 子级列表
   */
  private List<TreeNode> children;

  public TreeNode(Object id, String name, Integer order, Object parentId) {
    this.id = id;
    this.name = name;
    this.order = order;
    this.parentId = parentId;
  }

  public TreeNode(Object id, String name, Integer order, TreeNode parent) {
    this.id = id;
    this.name = name;
    this.order = order;
    this.parentId = parent.getId();
  }
}
