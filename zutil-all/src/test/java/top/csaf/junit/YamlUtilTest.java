package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.io.FileUtil;
import top.csaf.yaml.YamlFeat;
import top.csaf.yaml.YamlUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("YAML 工具类测试")
class YamlUtilTest {

  private static final String YML_FILE_PATH = FileUtil.getProjectPath() + "/src/test/java/top/csaf/assets/yaml/test.yml";
  private static final File YML_FILE = new File(YML_FILE_PATH);

  private static final String C1_VALUE = "aa";
  private static final String C1_VALUE_1 = "${b.b1.b11}${b.b1.b11}";

  @Test
  void load() throws IOException {
    // Reader
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtil.load(new FileReader(YML_FILE_PATH)).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtil.load(new FileReader(YML_FILE_PATH), false).get("c")).get("c1"));
    // filePath
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtil.load(YML_FILE_PATH).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtil.load(YML_FILE_PATH, false).get("c")).get("c1"));
    assertEquals(Collections.emptyMap(), YamlUtil.load(""));
    // file
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtil.load(YML_FILE).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtil.load(YML_FILE, false).get("c")).get("c1"));
    assertEquals(Collections.emptyMap(), YamlUtil.load(new File("")));
    // InputStream
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtil.load(Files.newInputStream(YML_FILE.toPath())).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtil.load(Files.newInputStream(YML_FILE.toPath()), false).get("c")).get("c1"));

    // 替换 ${xxx} 为对应的值，如果没有找到对应的值，替换为空字符串
    YamlFeat.setEscapeNotFoundReplacement("");
    assertEquals("", ((Map<?, ?>) YamlUtil.load(new FileReader(YML_FILE_PATH)).get("d")).get("d1"));
    // 替换 ${xxx} 为对应的值，如果没有找到对应的值，抛出异常
    YamlFeat.setEscapeNotFoundThrowExceptionAlways(true);
    assertThrows(IllegalArgumentException.class, () -> YamlUtil.load(new FileReader(YML_FILE_PATH)));
  }

  @Test
  void get() throws IOException {
    // Reader
    assertEquals(C1_VALUE_1, YamlUtil.get(new FileReader(YML_FILE_PATH), "c.c1", false));
    assertEquals(C1_VALUE, YamlUtil.get(new FileReader(YML_FILE_PATH), "c.c1"));
    // filePath
    assertEquals(C1_VALUE_1, YamlUtil.get(YML_FILE_PATH, "c.c1", false));
    assertEquals(C1_VALUE, YamlUtil.get(YML_FILE_PATH, "c.c1"));
    // file
    assertEquals(C1_VALUE_1, YamlUtil.get(YML_FILE, "c.c1", false));
    assertEquals(C1_VALUE, YamlUtil.get(YML_FILE, "c.c1"));
    // InputStream
    assertEquals(C1_VALUE_1, YamlUtil.get(Files.newInputStream(YML_FILE.toPath()), "c.c1", false));
    assertEquals(C1_VALUE, YamlUtil.get(Files.newInputStream(YML_FILE.toPath()), "c.c1"));
  }
}
