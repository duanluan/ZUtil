package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {

  /**
   * 获取项目路径
   *
   * @return 项目路径
   */
  public static String getProjectPath() {
    File projectDir = new File("");
    try {
      return projectDir.getCanonicalPath();
    } catch (IOException e) {
      log.warn(e.getMessage());
      return null;
    }
  }

  /**
   * 获取类的根路径（不含包）
   *
   * @param clazz 类
   * @return 类的根路径（不含包）
   */
  public static String getClassRootPath(@NonNull final Class<?> clazz) {
    URL rootPathUrl = clazz.getClassLoader().getResource("");
    if (rootPathUrl == null) {
      return null;
    }
    return rootPathUrl.getPath();
  }

  /**
   * 获取类的根路径（不含包）
   *
   * @return 类的根路径（不含包）
   */
  public static String getClassRootPath() {
    URL rootPathUrl = Thread.currentThread().getContextClassLoader().getResource("");
    if (rootPathUrl == null) {
      return null;
    }
    return rootPathUrl.getPath();
  }

  /**
   * 获取类路径
   *
   * @param clazz 类
   * @return 类路径
   */
  public static String getClassPath(@NonNull final Class<?> clazz) {
    URL pathUrl = clazz.getResource("");
    if (pathUrl == null) {
      return null;
    }
    return pathUrl.getPath();
  }
}
