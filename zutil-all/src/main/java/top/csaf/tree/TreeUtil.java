package top.csaf.tree;

import lombok.extern.slf4j.Slf4j;
import top.csaf.bean.BeanUtil;
import top.csaf.bean.ConvertUtil;
import top.csaf.lang.ArrayUtil;
import top.csaf.lang.StrUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

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

  /**
   * 将树结构拆分为平级列表
   *
   * @param treeNodes         树节点集合
   * @param childrenFieldName 子级列表的字段名
   * @param resultFactory     结果集合工厂方法，用于创建指定类型的集合
   * @param <T>               树节点类型
   * @param <R>               返回集合类型
   * @return 平级节点集合（类型由resultFactory决定）
   */
  public static <T, R extends Collection<T>> R flatten(Object treeNodes, String childrenFieldName, Supplier<R> resultFactory) {
    R result = resultFactory.get();
    // 处理空输入
    if (treeNodes == null) {
      return result;
    }

    // 将输入转换为迭代器
    Iterator<T> iterator;
    if (treeNodes instanceof Iterable) {
      iterator = ((Iterable<T>) treeNodes).iterator();
    } else if (treeNodes instanceof Iterator) {
      iterator = (Iterator<T>) treeNodes;
    } else if (treeNodes instanceof Object[]) {
      iterator = Arrays.stream((Object[]) treeNodes).map(obj -> (T) obj).iterator();
    } else {
      // 单个对象处理为只有一个元素的集合
      result.add((T) treeNodes);
      return result;
    }

    // 处理迭代器中的每个节点
    while (iterator.hasNext()) {
      T node = iterator.next();
      // 将当前节点添加到结果集合
      result.add(node);

      try {
        // 通过反射获取指定字段名的子级
        Field field = node.getClass().getDeclaredField(childrenFieldName);
        field.setAccessible(true);
        Object childrenObj = field.get(node);

        // 如果子级不为空，递归处理子级
        if (childrenObj != null) {
          // 递归调用并将结果添加到当前结果集合中
          result.addAll(flatten(childrenObj, childrenFieldName, resultFactory));
        }
      } catch (NoSuchFieldException | IllegalAccessException e) {
        log.error("Children field '{}' cannot be obtained from class: {}", childrenFieldName, node.getClass().getName(), e);
      }
    }

    return result;
  }

  /**
   * 将树结构拆分为平级数组
   *
   * @param treeNodes         树节点数组
   * @param <T>               树节点类型
   * @param childrenFieldName 子级列表的字段名
   * @return 平级节点数组
   */
  public static <T> T[] flatten(T[] treeNodes, String childrenFieldName) {
    if (treeNodes == null || treeNodes.length == 0) {
      return treeNodes;
    }

    // 使用ArrayList暂存结果
    List<T> tempResult = new ArrayList<>();

    for (T node : treeNodes) {
      // 将当前节点添加到结果列表
      tempResult.add(node);

      try {
        // 通过反射获取指定字段名的子级
        Field field = node.getClass().getDeclaredField(childrenFieldName);
        field.setAccessible(true);
        Object childrenObj = field.get(node);

        // 根据子级类型分别处理
        if (childrenObj instanceof Object[]) {
          // 如果子级是数组
          T[] childrenArray = (T[]) childrenObj;
          if (childrenArray.length > 0) {
            T[] flattenedChildren = flatten(childrenArray, childrenFieldName);
            tempResult.addAll(Arrays.asList(flattenedChildren));
          }
        } else if (childrenObj instanceof Iterable || childrenObj instanceof Iterator) {
          // 如果子级是集合或迭代器，先扁平化为集合，再添加到结果中
          List<T> flattenedChildren = flatten(childrenObj, childrenFieldName, ArrayList::new);
          tempResult.addAll(flattenedChildren);
        } else {
          // 如果子级是单个对象，直接添加到结果中
          tempResult.add((T) childrenObj);
        }
      } catch (NoSuchFieldException | IllegalAccessException e) {
        log.error("Children field '{}' cannot be obtained from class: {}", childrenFieldName, node.getClass().getName(), e);
      }
    }

    // 将ArrayList转换回数组类型
    // 获取原始数组的类型
    Class<? extends Object[]> arrayClass = treeNodes.getClass();
    T[] resultArray = (T[]) Array.newInstance(arrayClass.getComponentType(), tempResult.size());

    return tempResult.toArray(resultArray);
  }
}
