package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.YamlUtils;
import top.csaf.io.FileUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DisplayName("YAML 工具类测试")
class YamlUtilsTest {

  private static final String YML_FILE_PATH = FileUtils.getProjectPath() + "/src/test/java/top/csaf/assets/yaml/test.yml";

  @Test
  void test() {
    assertEquals("com.mysql.cj.jdbc.Driver", YamlUtils.get(YamlUtils.load(YML_FILE_PATH), "spring.datasource.hikari.driver-class-name"));
  }
}
