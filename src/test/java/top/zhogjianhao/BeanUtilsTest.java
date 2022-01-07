package top.zhogjianhao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    TestBean(String name) {
      this.name = name;
    }
  }

  @DisplayName("BeanMap 三种转 Map 的性能测试")
  @Test
  void testToMap() {
    TestBean testBean = new TestBean();
    List<TestBean> beanList = new ArrayList<>();
    beanList.add(new TestBean("1"));
    testBean.setBeanList(beanList);

    int startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      new HashMap<String, Object>(BeanMap.create(testBean));
    }
    println("Constructor: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      Map<String, Object> map = new HashMap<>();
      BeanMap beanMap = BeanMap.create(testBean);
      for (Object key : beanMap.keySet()) {
        map.put(key.toString(), beanMap.get(key));
      }
    }
    println("foreach: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      HashMap<String, Object> map1 = new HashMap<>();
      map1.putAll(BeanMap.create(testBean));
    }
    println("putAll: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
  }

  @DisplayName("Spring 和 Apache getProperty 性能测试（Spring > Apache）")
  @Test
  void testSpringAndApacheGetProperty() {
    TestBean testBean = new TestBean();
    testBean.setName("张三");

    int startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(testBean.getClass(), "name");
        if (propertyDescriptor != null) {
          String name = propertyDescriptor.getReadMethod().invoke(testBean, null).toString();
        }
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    println("Spring get: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        String name = org.apache.commons.beanutils.BeanUtils.getProperty(testBean, "name");
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    println("Apache get: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
  }

  @DisplayName("Spring 和 Apache setProperty 性能测试（Spring > Apache）")
  @Test
  void testSpringAndApacheSetProperty() {
    TestBean testBean = new TestBean();

    int startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(testBean.getClass(), "name");
        if (propertyDescriptor != null) {
          propertyDescriptor.getWriteMethod().invoke(testBean, "张三");
        }
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    println("Spring get: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        org.apache.commons.beanutils.BeanUtils.setProperty(testBean, "name", "张三");
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    println("Apache get: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
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
    // 如果 targetClass 没有无参构造函数，IDEA 会有虚线提示
    CopyPropertieTestBean testBean1 = BeanUtils.copyProperties(testBean, CopyPropertieTestBean.class);
    println(testBean1.getName());
    // 如果 List<T> 中的 T 不同的话，就不会复制
    println(testBean1.getBeanList().get(0).getName());
  }
}
