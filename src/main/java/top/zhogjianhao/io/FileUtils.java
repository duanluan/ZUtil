package top.zhogjianhao.io;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.zhogjianhao.StringUtils;
import top.zhogjianhao.constant.CommonPattern;
import top.zhogjianhao.regex.RegExUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {

  /**
   * 逆序的 文件扩展名
   */
  public static final Pattern PATTERN_REVERSE_FILE_EXTENSION = Pattern.compile("^([A-Za-z\\d]+)\\.");
  /**
   * 逆序的 文件名/
   */
  public static final Pattern PATTERN_REVERSE_SLASH_FILE_NAME = Pattern.compile("^(.*?\\..*?)[\\\\\\/]");

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

  // /**
  //  * 获取类的根路径（不含包）
  //  *
  //  * @param clazz 类
  //  * @return 类的根路径（不含包）
  //  */
  // public static String getResourcePath(@NonNull final Class<?> clazz) {
  //   String result = clazz.getClassLoader().getResource("").getPath();
  //   if (result.startsWith("/") || result.startsWith("\\")) {
  //     result = result.substring(1);
  //   }
  //   return result;
  // }
  //
  // /**
  //  * 获取类的根路径（不含包）
  //  *
  //  * @return 类的根路径（不含包）
  //  */
  // public static String getResourcePath() {
  //   String result = Thread.currentThread().getContextClassLoader().getResource("").getPath();
  //   if (result.startsWith("/") || result.startsWith("\\")) {
  //     result = result.substring(1);
  //   }
  //   return result;
  // }
  //
  // /**
  //  * 获取类路径
  //  *
  //  * @param clazz 类
  //  * @return 类路径
  //  */
  // public static String getClassPath(@NonNull final Class<?> clazz) {
  //   String result = clazz.getResource("").getPath();
  //   if (result.startsWith("/") || result.startsWith("\\")) {
  //     result = result.substring(1);
  //   }
  //   if (result.startsWith("file:/")) {
  //     result = result.substring(6);
  //   }
  //   return result;
  // }

  /**
   * 获取 resources 目录下的文件输入流
   *
   * @param path 文件路径
   * @return 文件输入流
   */
  public static InputStream getResourceAsStream(@NonNull Class<?> clazz, @NonNull final String path) {
    return clazz.getClassLoader().getResourceAsStream(path);
  }

  /**
   * 获取 resources 目录下的文件输入流
   *
   * @param path 文件路径
   * @return 文件输入流
   */
  public static InputStream getResourceAsStream(@NonNull final String path) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
  }

  /**
   * 获取文件扩展名
   *
   * @param filePath 文件路径
   * @return 文件扩展名
   */
  public static String getFileExtension(@NonNull final String filePath) {
    return new StringBuffer(RegExUtils.match(new StringBuffer(filePath).reverse().toString(), PATTERN_REVERSE_FILE_EXTENSION, 0, 1)).reverse().toString();
  }

  /**
   * 获取文件扩展名
   *
   * @param file 文件
   * @return 文件扩展名
   */
  public static String getFileExtension(@NonNull final File file) {
    return getFileExtension(file.getPath());
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
      if (PATTERN_REVERSE_FILE_EXTENSION.matcher(new StringBuffer(filePath).reverse().toString()).find()) {
        return new String[]{"", filePath};
      }
      return new String[]{filePath, ""};
    }
    return new String[]{filePath.substring(0, lastFileSeparatorIndex + 1), filePath.substring(lastFileSeparatorIndex + 1)};
  }

  /**
   * 根据文件路径获取目录
   *
   * @param filePath 文件路径
   * @return 目录
   */
  public static String getDirPathByPath(@NonNull final String filePath) {
    int index = filePath.lastIndexOf("/");
    if (index == -1) {
      index = filePath.lastIndexOf("\\");
    }
    if (index != -1) {
      return filePath.substring(0, index + 1);
    }
    return null;
  }

  /**
   * 根据文件路径获取文件名
   *
   * @param filePath 文件路径
   * @return 文件名
   */
  public static String getNameByPath(@NonNull final String filePath) {
    return new StringBuffer(RegExUtils.matchFirst(new StringBuffer(filePath).reverse().append("/").toString(), PATTERN_REVERSE_SLASH_FILE_NAME, 1)).reverse().toString();
  }

  /**
   * 替换文件路径
   *
   * @param filePath    文件路径
   * @param newFilePath 新文件路径，其中 $1 为 filePath 中的文件名，$2 为 filePath 中的文件扩展名
   * @return 新文件路径结果：如果 newFilePath 中含目录，则为目录 + 替换 $1、$2 后的文件名；如果 newFilePath 中不含目录，则为 filePath 中的目录 + 替换 $1、$2 后的文件名
   */
  public static String replace(@NonNull final String filePath, @NonNull final String newFilePath) {
    String[] dirPathAndName = getDirPathAndNameByPath(filePath);
    String fileName = dirPathAndName[1];
    if (StringUtils.isBlank(fileName)) {
      throw new IllegalArgumentException("FilePath: should be a file name and file extension");
    }
    String newDirPath = getDirPathByPath(newFilePath);
    boolean hasNewDirPath = StringUtils.isNotBlank(newDirPath);
    String[] fileNameAndExtension = fileName.split("\\.");
    return (hasNewDirPath ? newDirPath : dirPathAndName[0]) + (hasNewDirPath ? getNameByPath(newFilePath) : newFilePath).replaceAll("\\$1", fileNameAndExtension[0]).replaceAll("\\$2", fileNameAndExtension[1]);
  }
}
