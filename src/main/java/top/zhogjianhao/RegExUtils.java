package top.zhogjianhao;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 *
 * @author duanluan
 */
@Slf4j
public class RegExUtils extends org.apache.commons.lang3.RegExUtils {

  private RegExUtils() {
  }

  /**
   * 替换特殊字符
   *
   * @param source 需要替换的内容
   * @return 替换后的内容
   */
  public static String replaceAllSpecialChar(String source) {
    return source.replaceAll("/", "\\/")
      .replaceAll("\\\\", "\\\\\\")
      .replaceAll("\\(", "\\\\(")
      .replaceAll("\\)", "\\\\)")
      .replaceAll("\\.", "\\\\.")
      .replaceAll("\\?", "\\\\?")
      .replaceAll("\\*", "\\\\*")
      .replaceAll("\\+", "\\\\+")
      .replaceAll("\\|", "\\\\|")
      .replaceAll("\\^", "\\\\^")
      .replaceAll("\\$", "\\\\$");
  }

  /**
   * 获取 Matcher 对象
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return Matcher 对象
   */
  public static Matcher getMatcher(@NotNull String text, @NotNull String regex, int flags) {
    Pattern pattern = Pattern.compile(regex, flags);
    return pattern.matcher(text);
  }

  /**
   * 获取 Matcher 对象
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @return Matcher 对象
   */
  public static Matcher getMatcher(@NotNull String text, @NotNull String regex) {
    return getMatcher(text, regex, 0);
  }

  /**
   * 获取下标
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @param flags 匹配模式
   * @return 匹配的下标
   */
  public static int indexOf(@NotNull String text, @NotNull String regex, int flags) {
    try {
      Matcher matcher = getMatcher(text, regex, flags);
      if (matcher.find()) {
        return matcher.start();
      }
      return -1;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return -1;
    }
  }

  /**
   * 获取下标
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @return 匹配的下标
   */
  public static int indexOf(@NotNull String text, @NotNull String regex) {
    try {
      Matcher matcher = getMatcher(text, regex);
      if (matcher.find()) {
        return matcher.start();
      }
      return -1;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return -1;
    }
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @param item  匹配项
   * @param group 捕获组
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String match(@NotNull String text, @NotNull String regex, int item, int group, int flags) {
    if (item < 0 || group < 0) {
      log.warn("match 或 group 参数错误！");
      return null;
    }
    String result = null;
    try {
      Matcher matcher = getMatcher(text, regex, flags);
      int i = 0;
      while (matcher.find()) {
        if (item == i) {
          return matcher.group(group);
        }
        i++;
      }
      return result;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项
   * @param group 捕获组
   * @return 匹配值
   */
  public static String match(@NotNull String text, @NotNull String regex, int item, int group) {
    return match(text, regex, item, group, 0);
  }

  /**
   * 获取指定匹配项的全部匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项
   * @return 匹配值
   */
  public static String match(@NotNull String text, @NotNull String regex, int item) {
    return match(text, regex, item, 0);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String matchFirst(@NotNull String text, @NotNull String regex, int group, int flags) {
    return match(text, regex, 0, group, flags);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组
   * @return 匹配值
   */
  public static String matchFirst(@NotNull String text, @NotNull String regex, int group) {
    return matchFirst(text, regex, group, 0);
  }

  /**
   * 获取第一个匹配项的所有捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配值
   */
  public static String matchFirst(@NotNull String text, @NotNull String regex) {
    return matchFirst(text, regex, 0);
  }

  /**
   * 是否能匹配
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 是否能匹配
   */
  public static boolean isMatch(@NotNull String text, @NotNull String regex, int flags) {
    return StringUtils.isNotBlank(matchFirst(text, regex, 0, flags));
  }

  /**
   * 是否能匹配
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 是否能匹配
   */
  public static boolean isMatch(@NotNull String text, @NotNull String regex) {
    return StringUtils.isNotBlank(matchFirst(text, regex, 0));
  }


  /**
   * 获取匹配集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static List<String> matches(@NotNull String text, @NotNull String regex, int group, int flags) {
    List<String> resultList = new ArrayList<>();
    try {
      Matcher matcher = getMatcher(text, regex, flags);
      while (matcher.find()) {
        resultList.add(matcher.group(group));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      resultList = new ArrayList<>();
    }
    return resultList;
  }

  /**
   * 获取匹配集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组
   * @return 匹配集合
   */
  public static List<String> matches(@NotNull String text, @NotNull String regex, int group) {
    return matches(text, regex, group, 0);
  }

  /**
   * 获取匹配集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matches(@NotNull String text, @NotNull String regex) {
    return matches(text, regex, 0);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项
   * @param group       捕获组
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replace(@NotNull String text, @NotNull String regex, @NotNull String replacement, int item, int group, int flags) {
    if (item < 0 || group < 0) {
      log.warn("match 或 group 参数错误！");
      return null;
    }
    Matcher matcher = getMatcher(text, regex, flags);
    int i = 0;
    while (matcher.find()) {
      if (i == item) {
        int startIndex = matcher.start(group);
        return text.substring(0, startIndex) + replacement + text.substring(startIndex + matcher.group(group).length());
      }
      i++;
    }
    return null;
  }

  /**
   * 替换指定匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replace(@NotNull String text, @NotNull String regex, @NotNull String replacement, int item, int flags) {
    return replace(text, regex, replacement, item, 0, flags);
  }

  /**
   * 替换指定匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项
   * @return 替换后的内容
   */
  public static String replace(@NotNull String text, @NotNull String regex, @NotNull String replacement, int item) {
    return replace(text, regex, replacement, item, 0);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组
   * @return 替换后的内容
   */
  public static String replaceFirst(@NotNull String text, @NotNull String regex, @NotNull String replacement, int group, int flags) {
    return replace(text, regex, replacement, 0, group, flags);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组
   * @return 替换后的内容
   */
  public static String replaceFirst(@NotNull String text, @NotNull String regex, @NotNull String replacement, int group) {
    return replaceFirst(text, regex, replacement, group, 0);
  }

  /**
   * 替换第一个匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceFirst(@NotNull String text, @NotNull String regex, @NotNull String replacement) {
    return replaceFirst(text, regex, replacement, 0, 0);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAll(@NotNull String text, @NotNull String regex, @NotNull String replacement, int group, int flags) {
    if (group < 0) {
      log.warn("group 参数错误！");
      return null;
    }
    StringBuilder result = new StringBuilder();

    Matcher matcher = getMatcher(text, regex, flags);
    if (matcher.find()) {
      // 首次匹配
      int startIndex = matcher.start(group);
      String firstMatchText = text.substring(0, startIndex) + replacement + text.substring(startIndex + matcher.group(group).length());
      result.append(firstMatchText, 0, startIndex + replacement.length());
      int i = 0;

      while (true) {
        // 先截取掉已经替换过内容的捕获组
        String text1 = firstMatchText.substring(result.toString().length());
        // 然后再去匹配
        Matcher matcher1 = getMatcher(text1, regex, flags);
        if (matcher1.find()) {
          int startIndex1 = matcher1.start(group);
          String nextMatchText = text1.substring(0, startIndex1) + replacement + text1.substring(startIndex1 + matcher1.group(group).length());
          // 匹配后更新条件，以便下一次循环
          firstMatchText = result + nextMatchText;
          result.append(nextMatchText, 0, startIndex1 + replacement.length());
          i++;
        } else {
          break;
        }
      }

      // 获取最后一次匹配的结尾文本
      matcher = getMatcher(text, regex, flags);
      int j = 0;
      while (matcher.find()) {
        if (i == j) {
          result.append(text.substring(matcher.start(group) + 1));
        }
        j++;
      }
    }

    return result.toString();
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组
   * @return 替换后的内容
   */
  public static String replaceAll(@NotNull String text, @NotNull String regex, @NotNull String replacement, int group) {
    return replaceAll(text, regex, replacement, group, 0);
  }

  /**
   * 是否包含汉字
   *
   * @param source 需要匹配的内容
   * @return 是否包含汉字
   */
  public static boolean containsHanZi(String source) {
    return isMatch("[\u4e00-\u9fa5]", source);
  }
}
