package top.zhogjianhao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Bean 工具类测试")
public class BeanUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @NoArgsConstructor
  @Data
  public static class TestBean {

    private String name;
    private List<TestBean> beanList;

    public TestBean(String name) {
      this.name = name;
    }
  }

  @NoArgsConstructor
  @Data
  public static class CopyPropertieTestBean {

    private String name;
    private List<TestBean> beanList;
  }

  @DisplayName("copyProperties：复制属性到新类型对象中")
  @Test
  void copyProperties() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);
    testBean.setBeanList(beanList);

    CopyPropertieTestBean testBean1 = BeanUtils.copyProperties(testBean, CopyPropertieTestBean.class);
    println(testBean1.getName());
    // 如果 List<T> 中的 T 不同的话，就不会复制
    println(testBean1.getBeanList().get(0).getName());
  }

  @DisplayName("copyPropertiesByList：复制属性到新类型对象列表中")
  @Test
  void copyPropertiesByList() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);

    List<CopyPropertieTestBean> beanList1 = BeanUtils.copyProperties(beanList, CopyPropertieTestBean.class);
    println(beanList1.get(0).getName());
  }
}
