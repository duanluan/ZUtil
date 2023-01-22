package top.csaf;

/**
 * {@code java.lang.System} 工具类
 */
public class SystemUtils extends org.apache.commons.lang3.SystemUtils {

  /**
   * 获取临时目录路径
   *
   * @return 临时目录路径
   */
  public static String getTempDir() {
    return getJavaIoTmpDir().getPath();
  }
}
