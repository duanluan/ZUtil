package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("文件工具类测试")
public class FileUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  private static final Class<FileUtilsTest> clazz = FileUtilsTest.class;

  @DisplayName("getPath：获取各种路径")
  @Test
  void getPath() {
    println(FileUtils.getProjectPath());
    println(FileUtils.getClassRootPath(clazz));
    println(FileUtils.getClassRootPath());
    println(FileUtils.getClassPath(clazz));
  }
}
