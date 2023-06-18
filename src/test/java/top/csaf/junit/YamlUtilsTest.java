package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.io.FileUtils;
import top.csaf.yaml.YamlFeature;
import top.csaf.yaml.YamlUtils;

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
class YamlUtilsTest {

  private static final String YML_FILE_PATH = FileUtils.getProjectPath() + "/src/test/java/top/csaf/assets/yaml/test.yml";
  private static final File YML_FILE = new File(YML_FILE_PATH);

  private static final String C1_VALUE = "aa";
  private static final String C1_VALUE_1 = "${b.b1.b11}${b.b1.b11}";

  @Test
  void load() throws IOException {
    // Reader
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtils.load(new FileReader(YML_FILE_PATH)).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtils.load(new FileReader(YML_FILE_PATH), false).get("c")).get("c1"));
    // filePath
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtils.load(YML_FILE_PATH).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtils.load(YML_FILE_PATH, false).get("c")).get("c1"));
    assertEquals(Collections.emptyMap(), YamlUtils.load(""));
    // file
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtils.load(YML_FILE).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtils.load(YML_FILE, false).get("c")).get("c1"));
    assertEquals(Collections.emptyMap(), YamlUtils.load(new File("")));
    // InputStream
    assertEquals(C1_VALUE, ((Map<?, ?>) YamlUtils.load(Files.newInputStream(YML_FILE.toPath())).get("c")).get("c1"));
    assertEquals(C1_VALUE_1, ((Map<?, ?>) YamlUtils.load(Files.newInputStream(YML_FILE.toPath()), false).get("c")).get("c1"));

    // 替换 ${xxx} 为对应的值，如果没有找到对应的值，替换为空字符串
    YamlFeature.setEscapeNotFoundReplacement("");
    assertEquals("", ((Map<?, ?>) YamlUtils.load(new FileReader(YML_FILE_PATH)).get("d")).get("d1"));
    // 替换 ${xxx} 为对应的值，如果没有找到对应的值，抛出异常
    YamlFeature.setEscapeNotFoundThrowExceptionAlways(true);
    assertThrows(IllegalArgumentException.class, () -> YamlUtils.load(new FileReader(YML_FILE_PATH)));
  }

  @Test
  void get() throws IOException {
    // Reader
    assertEquals(C1_VALUE_1, YamlUtils.get(new FileReader(YML_FILE_PATH), "c.c1", false));
    assertEquals(C1_VALUE, YamlUtils.get(new FileReader(YML_FILE_PATH), "c.c1"));
    // filePath
    assertEquals(C1_VALUE_1, YamlUtils.get(YML_FILE_PATH, "c.c1", false));
    assertEquals(C1_VALUE, YamlUtils.get(YML_FILE_PATH, "c.c1"));
    // file
    assertEquals(C1_VALUE_1, YamlUtils.get(YML_FILE, "c.c1", false));
    assertEquals(C1_VALUE, YamlUtils.get(YML_FILE, "c.c1"));
    // InputStream
    assertEquals(C1_VALUE_1, YamlUtils.get(Files.newInputStream(YML_FILE.toPath()), "c.c1", false));
    assertEquals(C1_VALUE, YamlUtils.get(Files.newInputStream(YML_FILE.toPath()), "c.c1"));
  }
}
