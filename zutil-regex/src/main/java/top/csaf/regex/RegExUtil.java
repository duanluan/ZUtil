package top.csaf.regex;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.csaf.constant.CommonPattern;
import top.csaf.lang.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * <p>
 * 注意：没有捕获组时则为匹配项本身
 */
@Slf4j
public class RegExUtil extends org.apache.commons.lang3.RegExUtils {

  public static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("([/\\\\()\\[\\]{},.?*+|^$])");

  /**
   * 替换特殊字符
   *
   * @param text 需要替换的内容
   * @return 替换后的内容
   */
  public static String replaceAllSpecialChar(@NonNull final CharSequence text) {
    return SPECIAL_CHAR_PATTERN.matcher(text).replaceAll("\\\\$1");
  }

  /**
   * 获取 Matcher 对象
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return Matcher 对象
   */
  public static Matcher getMatcher(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    Pattern pattern = Pattern.compile(regex, flags);
    return pattern.matcher(text);
  }

  /**
   * 获取 Matcher 对象
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return Matcher 对象
   */
  public static Matcher getMatcher(@NonNull final CharSequence text, @NonNull final String regex) {
    return getMatcher(text, regex, 0);
  }

  /**
   * 获取下标
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 匹配的下标
   */
  public static int indexOf(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    Matcher matcher = getMatcher(text, regex, flags);
    if (matcher.find()) {
      return matcher.start();
    }
    return -1;
  }

  /**
   * 获取下标
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配的下标
   */
  public static int indexOf(@NonNull final CharSequence text, @NonNull final String regex) {
    Matcher matcher = getMatcher(text, regex);
    if (matcher.find()) {
      return matcher.start();
    }
    return -1;
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group, final int flags) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    String result = null;
    Matcher matcher = getMatcher(text, regex, flags);
    int i = 0;
    while (matcher.find()) {
      if (item == i) {
        return matcher.group(group);
      }
      i++;
    }
    return result;
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group) {
    return match(text, regex, item, group, 0);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item) {
    return match(text, regex, item, 1);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String matchFirst(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags) {
    return match(text, regex, 0, group, flags);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始
   * @return 匹配值
   */
  public static String matchFirst(@NonNull final CharSequence text, @NonNull final String regex, final int group) {
    return matchFirst(text, regex, group, 0);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配值
   */
  public static String matchFirst(@NonNull final CharSequence text, @NonNull final String regex) {
    return matchFirst(text, regex, 1);
  }

  /**
   * 是否能匹配
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 是否能匹配
   */
  public static boolean isMatch(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    return StrUtil.isNotBlank(matchFirst(text, regex, 1, flags));
  }

  /**
   * 是否能匹配
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 是否能匹配
   */
  public static boolean isMatch(@NonNull final CharSequence text, @NonNull final String regex) {
    return Pattern.compile(regex).matcher(text).find();
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static List<String> matches(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    List<String> resultList = new ArrayList<>();
    Matcher matcher = getMatcher(text, regex, flags);
    while (matcher.find()) {
      resultList.add(matcher.group(group));
    }
    return resultList;
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始
   * @return 匹配集合
   */
  public static List<String> matches(@NonNull final CharSequence text, @NonNull final String regex, final int group) {
    return matches(text, regex, group, 0);
  }

  /**
   * 获取所有匹配项的第一个捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matches(@NonNull final CharSequence text, @NonNull final String regex) {
    return matches(text, regex, 1);
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group, final int flags) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    Matcher matcher = getMatcher(text, regex, flags);
    int i = 0;
    while (matcher.find()) {
      if (i == item) {
        int startIndex = matcher.start(group);
        if (startIndex == -1) {
          continue;
        }
        int endIndex = matcher.end(group);
        return text.substring(0, startIndex) + replacement + text.substring(endIndex);
      }
      i++;
    }
    return null;
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int flags) {
    return replace(text, regex, replacement, item, 1, flags);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item) {
    return replace(text, regex, replacement, item, 0);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceFirst(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags) {
    return replace(text, regex, replacement, 0, group, flags);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirst(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group) {
    return replaceFirst(text, regex, replacement, group, 0);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceFirst(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement) {
    return replaceFirst(text, regex, replacement, 1, 0);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
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
   * @param group       捕获组，从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group) {
    return replaceAll(text, regex, replacement, group, 0);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement) {
    return replaceAll(text, regex, replacement, 1, 0);
  }

  /**
   * 是否包含汉字
   *
   * @param source 需要匹配的内容
   * @return 是否包含汉字
   */
  public static boolean containsHanZi(@NonNull final String source) {
    return isMatch(source, CommonPattern.MULTIPLE_CHINESE_CHAR.pattern());
  }
}
