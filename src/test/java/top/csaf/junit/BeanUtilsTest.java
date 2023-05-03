package top.csaf.junit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.PropertyFunction;
import top.csaf.bean.BeanUtils;

import java.io.Serializable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bean 工具类测试")
class BeanUtilsTest {

  @NoArgsConstructor
  @Data
  public static class TestSuperBean implements Serializable {
    private String superName;
  }

  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @Data
  public static class TestBean extends TestSuperBean implements Serializable {
    private String name;
    private Object deepObject;

    public TestBean(String name) {
      this.name = name;
    }
  }

  @DisplayName("toMap：Bean 转 Map、Bean List 转 Map List")
  @Test
  void toMap() {
    /** {@link BeanUtils#toMap(Object)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.toMap((Object) null));

    Map<String, Object> map1 = new HashMap<>();
    map1.put("name", "6");
    map1.put("testBean7", new TestBean("7"));
    TestBean testBean5 = new TestBean();
    testBean5.setName("5");
    testBean5.setDeepObject(map1);
    TestBean testBean4 = new TestBean();
    testBean4.setName("4");
    testBean4.setDeepObject(new TestBean[]{testBean5});
    TestBean testBean3 = new TestBean();
    testBean3.setName("3");
    testBean3.setDeepObject(Collections.singletonList(testBean4).iterator());
    TestBean testBean2 = new TestBean();
    testBean2.setName("2");
    testBean2.setDeepObject(Collections.singletonList(testBean3));
    Map<String, Object> map = new HashMap<>();
    map.put("name", "1");
    map.put("testBean2", testBean2);
    map.put("map1", map1);

    Map testBean22 = (Map) BeanUtils.toMap(map).get("testBean2");
    Map<String, Object> testBean33 = ((Iterator<Map<String, Object>>) testBean22.get("deepObject")).next();
    Map<String, Object> testBean44 = ((Iterator<Map<String, Object>>) testBean33.get("deepObject")).next();
    Map<String, Object> testBean55 = ((Map<String, Object>[]) testBean44.get("deepObject"))[0];
    Map<String, Object> testBean66 = (Map<String, Object>) ((Map<String, Object>) testBean55.get("deepObject")).get("testBean7");
    assertEquals("7", testBean66.get("name"));

    /** {@link BeanUtils#toMap(List)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.toMap((List) null));
    assertEquals(new ArrayList(), BeanUtils.toMap(new ArrayList()));

    // 上面测试时已经把 testBean3.deepObject 给 next 掉了，所以这里重新赋值
    testBean3.setDeepObject(Collections.singletonList(testBean4).iterator());
    testBean22 = (Map) BeanUtils.toMap(Collections.singletonList(map)).get(0).get("testBean2");
    testBean33 = ((Iterator<Map<String, Object>>) testBean22.get("deepObject")).next();
    testBean44 = ((Iterator<Map<String, Object>>) testBean33.get("deepObject")).next();
    testBean55 = ((Map<String, Object>[]) testBean44.get("deepObject"))[0];
    testBean66 = (Map<String, Object>) ((Map<String, Object>) testBean55.get("deepObject")).get("testBean7");
    assertEquals("7", testBean66.get("name"));
  }

  @DisplayName("根据属性名获取属性值")
  @Test
  void getPropertyName() {
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyName(null));
    assertEquals("name", BeanUtils.getPropertyName(TestBean::getName));
  }

  @DisplayName("获取列名")
  @Test
  void getColumnName() {
    assertThrows(NullPointerException.class, () -> BeanUtils.getColumnName(null));
    assertEquals("deep_object", BeanUtils.getColumnName(TestBean::getDeepObject));
  }

  @DisplayName("根据属性名获取属性类型")
  @Test
  void getPropertyClass() {
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyClass(null));
    assertEquals(String.class, BeanUtils.getPropertyClass(TestBean::getName));
  }

  @DisplayName("根据属性名获取属性值")
  @Test
  void getProperty() {
    TestBean testBean = new TestBean();
    testBean.setName("1");

    /** {@link BeanUtils#getProperty(Object, String) */
    assertThrows(NullPointerException.class, () -> BeanUtils.getProperty(null, "name"));
    assertThrows(NullPointerException.class, () -> BeanUtils.getProperty(testBean, (String) null));
    assertEquals(null, BeanUtils.getProperty(testBean, ""));
    assertEquals("1", BeanUtils.getProperty(testBean, "name"));
    assertNull(BeanUtils.getProperty(testBean, "name1"));

    /** {@link BeanUtils#getProperty(Object, PropertyFunction) */
    assertThrows(NullPointerException.class, () -> BeanUtils.getProperty(null, TestBean::getName));
    assertThrows(NullPointerException.class, () -> BeanUtils.getProperty(testBean, (PropertyFunction) null));
    assertEquals("1", BeanUtils.getProperty(testBean, TestBean::getName));

    /** {@link BeanUtils#getPropertyStr(Object, String) */
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyStr(null, "name"));
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyStr(testBean, (String) null));
    assertEquals(null, BeanUtils.getPropertyStr(testBean, ""));
    testBean.setDeepObject(2);
    assertEquals("2", BeanUtils.getPropertyStr(testBean, "deepObject"));

    /** {@link BeanUtils#getPropertyStr(Object, PropertyFunction) */
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyStr(null, TestBean::getName));
    assertThrows(NullPointerException.class, () -> BeanUtils.getPropertyStr(testBean, (PropertyFunction) null));
    assertEquals("1", BeanUtils.getPropertyStr(testBean, TestBean::getName));
  }

  @DisplayName("根据属性名设置属性值")
  @Test
  void setProperty() {
    TestBean testBean = new TestBean();
    testBean.setName("1");

    /** {@link BeanUtils#setProperty(Object, String, Object)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.setProperty(null, "name", "2"));
    assertThrows(NullPointerException.class, () -> BeanUtils.setProperty(testBean, (String) null, "2"));
    assertTrue(BeanUtils.setProperty(testBean, "name", "2"));
    assertEquals("2", testBean.getName());
    assertFalse(BeanUtils.setProperty(testBean, "name1", "2"));

    /** {@link BeanUtils#setProperty(Object, PropertyFunction, Object)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.setProperty(null, TestBean::getName, "3"));
    assertThrows(NullPointerException.class, () -> BeanUtils.setProperty(testBean, (PropertyFunction) null, "3"));
    assertTrue(BeanUtils.setProperty(testBean, TestBean::getName, "3"));
  }

  @Data
  class TestBeanNotHaveNoArgsConstructor {
    private String name;

    TestBeanNotHaveNoArgsConstructor(String name) {
      this.name = name;
    }
  }

  @DisplayName("复制属性到新类型对象中")
  @Test
  void copyProperties() {
    TestSuperBean testBean = new TestSuperBean();
    testBean.setSuperName("1");

    /** {@link BeanUtils#copyProperties(Object, Class)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.copyProperties(null, TestBean.class));
    assertThrows(NullPointerException.class, () -> BeanUtils.copyProperties(testBean, null));
    assertThrows(IllegalArgumentException.class, () -> BeanUtils.copyProperties(testBean, TestBeanNotHaveNoArgsConstructor.class));
    TestBean testBean1 = BeanUtils.copyProperties(testBean, TestBean.class);
    assertEquals("1", testBean1.getSuperName());

    /** {@link BeanUtils#copyProperties(List, Class)} */
    assertThrows(NullPointerException.class, () -> BeanUtils.copyProperties(null, TestBean.class));
    assertThrows(NullPointerException.class, () -> BeanUtils.copyProperties(Collections.singletonList(testBean), null));
    assertThrows(IllegalArgumentException.class, () -> BeanUtils.copyProperties(Collections.singletonList(testBean), TestBeanNotHaveNoArgsConstructor.class));
    List<TestBean> testBean2 = BeanUtils.copyProperties(Collections.singletonList(testBean), TestBean.class);
    assertEquals("1", testBean2.get(0).getSuperName());
  }
}
