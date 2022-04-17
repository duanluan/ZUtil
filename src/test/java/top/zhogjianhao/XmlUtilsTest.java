package top.zhogjianhao;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("XML 工具类测试")
public class XmlUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  private static final String XML_FILE_PATH = FileUtils.getProjectPath() + "/src/test/java/top/zhogjianhao/assets/xml/test.xml";

  private final Element rootElement;

  {
    Document doc = XmlUtils.read(XML_FILE_PATH);
    rootElement = doc.getRootElement();
  }

  @NoArgsConstructor
  @Data
  private static class TestBean {
    private String name;
    private Integer age;
  }

  @DisplayName("toJson：将 XML 转换为 JSON")
  @Test
  void toJson() {
    println(XmlUtils.toJson(rootElement, true, SerializerFeature.WriteMapNullValue, SerializerFeature.MapSortField));
    println(XmlUtils.toJson(rootElement.element("beans"), true, SerializerFeature.WriteMapNullValue));
    println(XmlUtils.toJson(rootElement.element("beans").element("bean"), true, SerializerFeature.WriteMapNullValue));
  }

  @DisplayName("parseObject：将 XML 转换为对象")
  @Test
  void parseObject() {
    println(XmlUtils.parseObject(rootElement.element("beans").element("bean"), true, TestBean.class).getName());
  }

  @DisplayName("parseArray：将 XML 转换为集合")
  @Test
  void parseArray() {
    println(XmlUtils.parseArray(rootElement.element("beans"), true, TestBean.class).get(0).getName());
  }
}
