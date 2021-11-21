package top.zhogjianhao;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilsTest {

  public static void main(String[] args) {
    List<String> list1 = null;
    System.out.println("集合是否为空（null）：" + CollectionUtils.sizeIsEmpty(list1));
    list1 = new ArrayList<>();
    System.out.println("集合是否为空（new）：" + CollectionUtils.sizeIsEmpty(list1));
    System.out.println("集合是否为空（new）：" + CollectionUtils.sizeIsEmpty(list1));
    list1.add(null);
    System.out.println("集合是否为空（add null）：" + CollectionUtils.sizeIsEmpty(list1));
  }
}
