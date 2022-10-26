package top.zhogjianhao.junit;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DisplayName("Bean 工具类测试")
public class BeanUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @NoArgsConstructor
  @Data
  public static class TestSuperBean{
    private String superName;
  }

  @NoArgsConstructor
  @Data
  public static class TestBean extends TestSuperBean {
    private String name;
    private List<TestBean> beanList;

    public TestBean(String name) {
      this.name = name;
    }
  }

  @NoArgsConstructor
  @Data
  public static class CopyPropertieTestBean extends TestSuperBean{
    private String name;
    private List<TestBean> beanList;
  }

  @DisplayName("copyProperties：复制属性到新类型对象中")
  @Test
  void copyProperties() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    testBean.setSuperName("2");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);
    testBean.setBeanList(beanList);

    CopyPropertieTestBean testBean1 = BeanUtils.copyProperties(testBean, CopyPropertieTestBean.class);
    println(testBean1.getName());
    // 如果 List<T> 中的 T 不同的话，就不会复制
    println(testBean1.getBeanList().get(0).getSuperName());
  }

  @DisplayName("copyPropertiesByList：复制属性到新类型对象列表中")
  @Test
  void copyPropertiesByList() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    testBean.setSuperName("2");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);

    List<CopyPropertieTestBean> beanList1 = BeanUtils.copyProperties(beanList, CopyPropertieTestBean.class);
    println(beanList1.get(0).getSuperName());
  }

  @DisplayName("toMap：Bean 转 Map")
  @Test
  void toMap() {
    TestBean testBean = new TestBean();
    testBean.setName("1");

    Map<String, Object> map = BeanUtils.toMap(testBean);
    println(map.get("name"));
  }

  @DisplayName("toMapList：Bean List 转 Map List")
  @Test
  void toMapList() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);

    List<Map<String, Object>> mapList = BeanUtils.toMap(beanList);
    println(mapList.get(0).get("name"));
  }

  @DisplayName("deepToMap：深层 Bean 转 Map")
  @Test
  void deepToMap() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);
    TestBean testBean1 = new TestBean();
    testBean1.setBeanList(beanList);

    Map<String, Object> map = BeanUtils.deepToMap(testBean1);
    println(((List<Map<String, Object>>) map.get("beanList")).get(0).get("name"));
  }

  @DisplayName("deepToMapList：深层 Bean List 转 Map List")
  @Test
  void deepToMapList() {
    TestBean testBean = new TestBean();
    testBean.setName("1");
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(testBean);
    TestBean testBean1 = new TestBean();
    testBean1.setBeanList(beanList);
    List<TestBean> beanList1 = new ArrayList<>();
    beanList1.add(testBean1);

    List<Map<String, Object>> mapList = BeanUtils.deepToMap(beanList1);
    println(((List<Map<String, Object>>) mapList.get(0).get("beanList")).get(0).get("name"));
  }
}
