package top.csaf.awt;

import lombok.extern.slf4j.Slf4j;
import top.csaf.lang.StrUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * 剪切板工具类
 */
@Slf4j
public class ClipboardUtil {

  /**
   * 获取剪切板内容字符串
   *
   * @return 剪切板内容字符串
   */
  public static String getStr() {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    if (clipboard == null) {
      return null;
    }
    try {
      return (String) clipboard.getData(java.awt.datatransfer.DataFlavor.stringFlavor);
    } catch (Exception e) {
      log.error("Get clipboard String failed", e);
      return null;
    }
  }

  /**
   * 设置剪切板内容
   *
   * @param obj 内容
   * @return 是否成功
   */
  public static boolean set(final Object obj) {
    if (StrUtil.isBlank(obj)) {
      return false;
    }
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    if (clipboard == null) {
      log.warn("Clipboard is null");
      return false;
    }
    try {
      clipboard.setContents(new StringSelection(obj.toString()), null);
      return true;
    } catch (Exception e) {
      log.error("Set clipboard String failed", e);
    }
    return false;
  }
}
