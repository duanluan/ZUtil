package top.csaf.junit;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.json.JsonUtil;
import top.csaf.regex.RegExUtil;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("JSON 工具类测试")
class JsonUtilTest {

  @Data
  public static class TestObject {
    private String name;
    private int age;
  }

  @DisplayName("对象转 JSON 字符串")
  @Test
  void toJson() {
    // 带特性：输出值为 null 的字段
    assertEquals("[]", JsonUtil.toJson(new ArrayList<>(), JSONWriter.Feature.WriteNullListAsEmpty));
    // 不带特性，默认输出值为 null 的字段
    TestObject testObj = new TestObject();
    assertTrue(RegExUtil.isMatch(JsonUtil.toJson(testObj), "\\\"name\\\":null"));
    // 不带特性
    assertFalse(RegExUtil.isMatch(JsonUtil.toJsonNoFeature(testObj), "\\\"name\\\":null"));
  }

  @DisplayName("字符串转对象")
  @Test
  void parseObject() {
    String json = "{\"age\": 18}";
    // 带特性：初始化 String 字段为空字符串””
    assertEquals("", JsonUtil.parseObject(json, TestObject.class, JSONReader.Feature.InitStringFieldAsEmpty).getName());
    // 不带特性
    assertNull(JsonUtil.parseObject(json, TestObject.class).getName());
    // 解析失败
    assertThrows(JSONException.class, () -> {JsonUtil.parseObject("{invalid json}", TestObject.class);});
  }

  @DisplayName("字符串转集合")
  @Test
  void parseArray() {
    String json = "[{\"age\":18}]";
    // 带特性：初始化 String 字段为空字符串””
    assertEquals("", JsonUtil.parseArray(json, TestObject.class, JSONReader.Feature.InitStringFieldAsEmpty).get(0).getName());
    // 不带特性
    assertNull(JsonUtil.parseArray(json, TestObject.class).get(0).getName());
    // 解析失败
    assertThrows(JSONException.class, () -> {JsonUtil.parseArray("[invalid json]", TestObject.class);});
  }
}
