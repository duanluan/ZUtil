package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.constant.CommonPattern;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {

  /**
   * 文件名后缀
   */
  public static final Pattern FILE_EXTENSION = Pattern.compile("\\.[a-zA-Z\\d]*$");

  /**
   * 获取工作目录路径
   * <p>
   * Tomcat：Tomcat 的 bin 目录
   * main：项目目录
   *
   * @return 工作目录路径
   */
  public static String getUserDir() {
    return System.getProperty("user.dir");
  }

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
    return clazz.getClassLoader().getResource("").getPath();
  }

  /**
   * 获取类的根路径（不含包）
   *
   * @return 类的根路径（不含包）
   */
  public static String getClassRootPath() {
    return Thread.currentThread().getContextClassLoader().getResource("").getPath();
  }

  /**
   * 获取类路径
   *
   * @param clazz 类
   * @return 类路径
   */
  public static String getClassPath(@NonNull final Class<?> clazz) {
    return clazz.getResource("").getPath();
  }

  /**
   * 根据文件路径获取目录和文件名
   *
   * @param filePath 文件路径
   * @return 目录和文件名数组：[0: 目录, 1: 文件名]
   */
  public static String[] getDirPathAndNameByPath(@NonNull final String filePath) {
    int lastFileSeparatorIndex = CommonPattern.BACKSLASH.matcher(filePath).replaceAll("/").lastIndexOf("/");
    if (lastFileSeparatorIndex == -1) {
      // 是否有后缀
      if (FILE_EXTENSION.matcher(filePath).find()) {
        return new String[]{"", filePath};
      }
      return new String[]{filePath, ""};
    }
    return new String[]{filePath.substring(0, lastFileSeparatorIndex), filePath.substring(lastFileSeparatorIndex + 1)};
  }

  /**
   * 根据文件路径获取目录
   *
   * @param filePath 文件路径
   * @return 目录
   */
  public static String getDirPathByPath(@NonNull final String filePath) {
    return getDirPathAndNameByPath(filePath)[0];
  }

  /**
   * 根据文件路径获取文件名
   *
   * @param filePath 文件路径
   * @return 文件名
   */
  public static String getNameByPath(@NonNull final String filePath) {
    return getDirPathAndNameByPath(filePath)[1];
  }
}
