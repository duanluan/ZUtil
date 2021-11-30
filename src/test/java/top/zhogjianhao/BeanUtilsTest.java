package top.zhogjianhao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@DisplayName("Bean 工具类测试")
public class BeanUtilsTest {

  public class TestBean {

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
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
    System.out.println("Spring get: " + (LocalDateTime.now().get(ChronoField.SECOND_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        String name = org.apache.commons.beanutils.BeanUtils.getProperty(testBean, "name");
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Apache get: " + (LocalDateTime.now().get(ChronoField.SECOND_OF_DAY) - startTime));
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
    System.out.println("Spring get: " + (LocalDateTime.now().get(ChronoField.SECOND_OF_DAY) - startTime));

    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    for (int i = 0; i < 1000000; i++) {
      try {
        org.apache.commons.beanutils.BeanUtils.setProperty(testBean, "name","张三");
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Apache get: " + (LocalDateTime.now().get(ChronoField.SECOND_OF_DAY) - startTime));
  }
}
