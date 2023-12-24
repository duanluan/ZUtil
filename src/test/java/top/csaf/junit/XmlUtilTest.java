package top.csaf.junit;

import com.alibaba.fastjson2.JSONWriter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.io.FileUtil;
import top.csaf.xml.XmlUtil;

@Slf4j
@DisplayName("XML 工具类测试")
class XmlUtilTest {

  private void println(Object source) {
    System.out.println(source);
  }

  private static final String XML_FILE_PATH = FileUtil.getProjectPath() + "/src/test/java/top/csaf/assets/xml/test.xml";

  private final Element rootElement;

  {
    Document doc = XmlUtil.read(XML_FILE_PATH);
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
    println(XmlUtil.toJson(rootElement, true, JSONWriter.Feature.WriteMapNullValue));
    println(XmlUtil.toJson(rootElement.element("beans"), true, JSONWriter.Feature.WriteMapNullValue));
    println(XmlUtil.toJson(rootElement.element("beans").element("bean"), true, JSONWriter.Feature.WriteMapNullValue));
  }

  @DisplayName("parseObject：将 XML 转换为对象")
  @Test
  void parseObject() {
    println(XmlUtil.parseObject(rootElement.element("beans").element("bean"), true, TestBean.class).getName());
  }

  @DisplayName("parseArray：将 XML 转换为集合")
  @Test
  void parseArray() {
    println(XmlUtil.parseArray(rootElement.element("beans"), true, TestBean.class).get(0).getName());
  }
}
