package top.csaf.tree;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 树节点
 */
@NoArgsConstructor
@Getter
public class TreeNode extends LinkedHashMap<String, Object> implements Serializable {
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

  @Override
  public Object put(String key, Object value) {
    switch (key) {
      case "id":
        this.id = value;
        break;
      case "name":
        this.name = (String) value;
        break;
      case "order":
        this.order = (Integer) value;
        break;
      case "parentId":
        this.parentId = value;
        break;
      case "children":
        this.children = (List<TreeNode>) value;
        break;
      default:
    }
    return super.put(key, value);
  }

  @Override
  public Object get(Object key) {
    switch (key.toString()) {
      case "id":
        return this.id;
      case "name":
        return this.name;
      case "order":
        return this.order;
      case "parentId":
        return this.parentId;
      case "children":
        return this.children;
      default:
        return super.get(key);
    }
  }

  public void setId(Object id) {
    this.put("id", id);
  }

  public void setName(String name) {
    this.put("name", name);
  }

  public void setOrder(Integer order) {
    this.put("order", order);
  }

  public void setParentId(Object parentId) {
    this.put("parentId", parentId);
  }

  public void setChildren(List<TreeNode> children) {
    this.put("children", children);
  }

  public TreeNode(Object id, String name, Integer order, Object parentId) {
    this.setId(id);
    this.setName(name);
    this.setOrder(order);
    this.setParentId(parentId);
  }

  public TreeNode(Object id, String name, Integer order, TreeNode parent) {
    this.setId(id);
    this.setName(name);
    this.setOrder(order);
    this.setParentId(parent.getId());
  }
}
