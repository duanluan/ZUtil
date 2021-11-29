package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DisplayName("集合工具类测试")
public class CollectionUtilsTest {

  @DisplayName("sizeIsEmpty")
  @Test
  void sizeIsEmpty(){
    List<String> list1 = null;
    System.out.println("集合是否为空（null）：" + CollectionUtils.sizeIsEmpty(list1));
    list1 = new ArrayList<>();
    System.out.println("集合是否为空（new）：" + CollectionUtils.sizeIsEmpty(list1));
    System.out.println("集合是否为空（new）：" + CollectionUtils.sizeIsEmpty(list1));
    list1.add(null);
    System.out.println("集合是否为空（add null）：" + CollectionUtils.sizeIsEmpty(list1));
  }
}
