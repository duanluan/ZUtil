package top.csaf.junit;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("JSON 工具类测试")
class JsonUtilTest {

  @DisplayName("对象转 JSON 字符串")
  @Test
  void toJson() {
    /**
     * 正常带特性测试
     */
    // 测试对象
    TestObject testObject = new TestObject();
    testObject.setName("John Doe");
    testObject.setAge(30);
    // 测试特性
    JSONWriter.Feature[] features = {JSONWriter.Feature.WriteMapNullValue};
    // 调用被测方法
    String json = JsonUtil.toJson(testObject, features);
    // 验证结果
    assertNotNull(json);
    assertTrue(json.contains("\"name\":\"John Doe\""));
    assertTrue(json.contains("\"age\":30"));
    /**
     * 空值带特性测试
     */
    // 测试对象
    TestObject nullValueObject = new TestObject();
    nullValueObject.setName(null);
    nullValueObject.setAge(30);
    // 测试特性
    JSONWriter.Feature[] nullValuerFeatures = {JSONWriter.Feature.WriteMapNullValue};
    // 调用被测方法
    String nullValueJson = JsonUtil.toJson(nullValueObject, nullValuerFeatures);
    // 验证结果
    assertNotNull(nullValueJson);
    assertTrue(nullValueJson.contains("\"name\":null"));
    assertTrue(nullValueJson.contains("\"age\":30"));

    /**
     * 不带特性测试
     */
    // 测试对象
    TestObject normalTestObject = new TestObject();
    normalTestObject.setName("John Doe");
    normalTestObject.setAge(30);
    // 调用被测方法
    String normalJson = JsonUtil.toJson(normalTestObject);
    // 验证结果
    assertNotNull(normalJson);
    assertTrue(normalJson.contains("\"name\":\"John Doe\""));
    assertTrue(normalJson.contains("\"age\":30"));

  }

  @DisplayName("字符串转对象")
  @Test
  void parseObject() {
    // 正常解析
    String json = "{\"age\": 12, \"name\": \"zhangsan\"}";
    TestObject result = JsonUtil.parseObject(json, TestObject.class);
    assertNotNull(result);
    assertEquals(12, result.getAge());
    assertEquals("zhangsan", result.getName());

    /**
     * 非法json解析
     */
    String invalidJson = "{invalid json}";
    assertThrows(com.alibaba.fastjson2.JSONException.class, () -> {
      JsonUtil.parseObject(invalidJson, TestObject.class);
    });
    /**
     * null入参
     */
    assertThrows(NullPointerException.class, () -> {
      JsonUtil.parseObject(null, TestObject.class);
    });
    /**
     * null类型
     */
    String nullClassJson = "{\"age\": 12, \"name\": \"zhangsan\"}";
    assertThrows(NullPointerException.class, () -> {
      JsonUtil.parseObject(nullClassJson, null);
    });
  }

  @DisplayName("字符串转集合")
  @Test
  void parseArray() {
    // 正常解析
    String json = "[{\"name\":\"John\", \"age\":30}, {\"name\":\"Jane\", \"age\":25}]";
    Class<TestObject> clazz = TestObject.class;
    List<TestObject> result = JsonUtil.parseArray(json, clazz, JSONReader.Feature.AllowUnQuotedFieldNames);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("John", result.get(0).getName());
    assertEquals(30, result.get(0).getAge());
    assertEquals("Jane", result.get(1).getName());
    assertEquals(25, result.get(1).getAge());
    // 不带特性解析
    String noFeatureJson = "[{\"name\":\"John\", \"age\":30}, {\"name\":\"Jane\", \"age\":25}]";
    Class<TestObject> noFeatureClazz = TestObject.class;
    List<TestObject> noFeatureResult = JsonUtil.parseArray(noFeatureJson, noFeatureClazz);
    assertNotNull(result);
    assertEquals(2, noFeatureResult.size());
    assertEquals("John", noFeatureResult.get(0).getName());
    assertEquals(30, noFeatureResult.get(0).getAge());
    assertEquals("Jane", noFeatureResult.get(1).getName());
    assertEquals(25, noFeatureResult.get(1).getAge());
    // 空值解析
    String emptyJson = "[]";
    Class<TestObject> emptyClazz = TestObject.class;
    List<TestObject> emptyResult = JsonUtil.parseArray(emptyJson, emptyClazz);
    assertNotNull(emptyResult);
    assertTrue(emptyResult.isEmpty());
    // 非法值解析
    String invalidJson = "[xc{\"name\":\"John\", \"daage\":30}, {\"name\":\"Jane\", \"age\":25}]";
    Class<TestObject> invalidClazz = TestObject.class;
    Exception exception = assertThrows(Exception.class, () -> {
      JsonUtil.parseArray(invalidJson, invalidClazz);
    });
  }


  // 测试对象类
  public static class TestObject {
    private String name;
    private int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }
}
