package top.zhogjianhao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean 工具类测试")
public class BeanUtilsTest {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DisplayName("getProperty & setProperty")
  @Test
  void getSetProperty() {
    System.out.println(BeanUtils.getPropertyDescriptor(BeanUtilsTest.class, "name").getName());;
  }
}
