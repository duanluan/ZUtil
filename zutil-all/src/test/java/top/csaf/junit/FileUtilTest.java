package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.io.FileUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("文件工具类测试")
class FileUtilTest {
  private static final String PROJECT_PATH = FileUtil.getUserDir();
  private static final String PROJECT_PATH_SLASH = FileUtil.getUserDir().replace("\\", "/");

  public static void main(String[] args) {
    // System.out.println(FileUtils.getResourcePath());
    // System.out.println(FileUtils.getResourcePath(FileUtilsTest.class));
    // System.out.println(FileUtils.getClassPath(FileUtilsTest.class));
  }

  @DisplayName("getPath：获取各种路径")
  @Test
  void getPath() {
    // assertThrows(NullPointerException.class, () -> FileUtils.getResourcePath(null));
    // assertThrows(NullPointerException.class, () -> FileUtils.getClassPath(null));
    assertThrows(NullPointerException.class, () -> FileUtil.getDirPathAndNameByPath(null));
    assertThrows(NullPointerException.class, () -> FileUtil.getDirPathByPath(null));
    assertThrows(NullPointerException.class, () -> FileUtil.getNameByPath(null));

    assertEquals(PROJECT_PATH, FileUtil.getUserDir());
    assertEquals(PROJECT_PATH, FileUtil.getProjectPath());
    // assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/", FileUtils.getResourcePath());
    // assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/", FileUtils.getResourcePath(FileUtilsTest.class));
    // assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/top/csaf/junit/", FileUtils.getClassPath(FileUtilsTest.class));

    assertEquals("1.txt", FileUtil.getDirPathAndNameByPath("1.txt")[1]);
    assertEquals("C:", FileUtil.getDirPathAndNameByPath("C:")[0]);
    assertEquals("1.txt", FileUtil.getDirPathAndNameByPath("C:/1.txt")[1]);
    assertEquals("C:/", FileUtil.getDirPathByPath("C:/1.txt"));
    assertEquals("1.txt", FileUtil.getNameByPath("C:/1.txt"));
  }
}
