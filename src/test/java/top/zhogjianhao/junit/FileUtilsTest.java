package top.zhogjianhao.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.FileUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("文件工具类测试")
public class FileUtilsTest {

  private static final String HOME_PATH = "C:\\Users\\duanluan";
  private static final String PROJECT_PATH = "E:\\duanluan\\WorkSpaces\\My\\ZUtil";
  private static final String PROJECT_PATH_SLASH = "/E:/duanluan/WorkSpaces/My/ZUtil";

  public static void main(String[] args) {
    System.out.println(FileUtils.getClassRootPath());
    System.out.println(FileUtils.getClassRootPath(FileUtilsTest.class));
    System.out.println(FileUtils.getClassPath(FileUtilsTest.class));
  }

  @DisplayName("getPath：获取各种路径")
  @Test
  void getPath() {
    assertThrows(NullPointerException.class, () -> FileUtils.getClassRootPath(null));
    assertThrows(NullPointerException.class, () -> FileUtils.getClassPath(null));
    assertThrows(NullPointerException.class, () -> FileUtils.getDirPathAndNameByPath(null));
    assertThrows(NullPointerException.class, () -> FileUtils.getDirPathByPath(null));
    assertThrows(NullPointerException.class, () -> FileUtils.getNameByPath(null));

    assertEquals(PROJECT_PATH, FileUtils.getUserDir());
    assertEquals(PROJECT_PATH, FileUtils.getProjectPath());
    assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/", FileUtils.getClassRootPath());
    assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/", FileUtils.getClassRootPath(FileUtilsTest.class));
    assertEquals(PROJECT_PATH_SLASH + "/target/test-classes/top/zhogjianhao/junit/", FileUtils.getClassPath(FileUtilsTest.class));

    assertEquals("1.txt", FileUtils.getDirPathAndNameByPath("1.txt")[1]);
    assertEquals("C:", FileUtils.getDirPathAndNameByPath("C:")[0]);
    assertEquals("1.txt", FileUtils.getDirPathAndNameByPath("C:/1.txt")[1]);
    assertEquals("C:/", FileUtils.getDirPathByPath("C:/1.txt"));
    assertEquals("1.txt", FileUtils.getNameByPath("C:/1.txt"));
  }
}
