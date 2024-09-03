package top.csaf.regex;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.csaf.constant.CommonPattern;

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
public class RegExUtil {

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
   * 是否能匹配
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 是否能匹配
   */
  public static boolean isMatch(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    return pattern.matcher(text).find();
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
    return isMatch(text, Pattern.compile(regex, flags));
  }

  /**
   * 是否能匹配
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 是否能匹配
   */
  public static boolean isMatch(@NonNull final CharSequence text, @NonNull final String regex) {
    return isMatch(text, Pattern.compile(regex));
  }

  /**
   * 获取下标
   *
   * @param text    需要查找的内容
   * @param pattern 正则
   * @return 匹配的下标
   */
  public static int indexOf(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    Matcher matcher = pattern.matcher(text);
    if (matcher.find()) {
      return matcher.start();
    }
    return -1;
  }

  /**
   * 获取下标
   *
   * @param text  需要查找的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 匹配的下标
   */
  public static int indexOf(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    return indexOf(text, Pattern.compile(regex, flags));
  }

  /**
   * 获取下标
   *
   * @param text  需要查找的内容
   * @param regex 正则
   * @return 匹配的下标
   */
  public static int indexOf(@NonNull final CharSequence text, @NonNull final String regex) {
    return indexOf(text, Pattern.compile(regex));
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int item, final int group) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    Matcher matcher = pattern.matcher(text);
    int i = 0;
    while (matcher.find()) {
      if (item == i) {
        return matcher.group(group);
      }
      i++;
    }
    return null;
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param regex 正则
   * @param text  需要匹配的内容
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group, final int flags) {
    return match(text, Pattern.compile(regex, flags), item, group);
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group) {
    return match(text, Pattern.compile(regex), item, group);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int group) {
    return match(text, pattern, 0, group);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags) {
    return match(text, regex, 0, group, flags);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final String regex, final int group) {
    return match(text, regex, 0, group, 0);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int item) {
    return match(text, pattern, item, 1);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int flags) {
    return match(text, regex, item, 1, flags);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final String regex, final int item) {
    return match(text, regex, item, 1, 0);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    return match(text, pattern, 0, 1);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    return match(text, regex, 0, 1, flags);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final String regex) {
    return match(text, regex, 0, 1, 0);
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int group) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    List<String> resultList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
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
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags) {
    return matchAllItems(text, Pattern.compile(regex, flags), group);
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final String regex, final int group) {
    return matchAllItems(text, regex, group, 0);
  }

  /**
   * 获取所有匹配项的第一个捕获组的匹配值集合
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 匹配集合
   */
  public static List<String> matchAllItemsFirstGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    return matchAllItems(text, pattern, 1);
  }

  /**
   * 获取所有匹配项的第一个捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matchAllItemsFirstGroup(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    return matchAllItems(text, regex, 1, flags);
  }

  /**
   * 获取所有匹配项的第一个捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matchAllItemsFirstGroup(@NonNull final CharSequence text, @NonNull final String regex) {
    return matchAllItems(text, regex, 1, 0);
  }

  /**
   * 获取指定匹配项的所有捕获组的匹配值集合
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @return 匹配集合
   */
  public static List<String> matchAllGroups(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int item) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    List<String> resultList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    int i = 0;
    while (matcher.find()) {
      if (item == i) {
        for (int j = 1; j <= matcher.groupCount(); j++) {
          resultList.add(matcher.group(j));
        }
        return resultList;
      }
      i++;
    }
    return resultList;
  }

  /**
   * 获取指定匹配项的所有捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static List<String> matchAllGroups(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int flags) {
    return matchAllGroups(text, Pattern.compile(regex, flags), item);
  }

  /**
   * 获取指定匹配项的所有捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @return 匹配集合
   */
  public static List<String> matchAllGroups(@NonNull final CharSequence text, @NonNull final String regex, final int item) {
    return matchAllGroups(text, Pattern.compile(regex), item);
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    List<String> resultList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      int group = 1;
      while (group <= matcher.groupCount()) {
        resultList.add(matcher.group(group));
        group++;
      }
    }
    return resultList;
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final String regex, final int flags) {
    return matchAll(text, Pattern.compile(regex, flags));
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final String regex) {
    return matchAll(text, Pattern.compile(regex));
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item, final int group) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    Matcher matcher = pattern.matcher(text);
    int i = 0;
    while (matcher.find()) {
      if (i == item) {
        int startIndex = matcher.start(group);
        int endIndex = matcher.end(group);
        return text.substring(0, startIndex) + replacement + text.substring(endIndex);
      }
      i++;
    }
    return null;
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group, final int flags) {
    return replace(text, Pattern.compile(regex, flags), replacement, item, group);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group) {
    return replace(text, Pattern.compile(regex), replacement, item, group);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int group) {
    return replace(text, pattern, replacement, 0, group);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags) {
    return replace(text, Pattern.compile(regex, flags), replacement, 0, group);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group) {
    return replace(text, Pattern.compile(regex), replacement, 0, group);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @return 替换后的内容
   */
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item) {
    return replace(text, pattern, replacement, item, 1);
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
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int flags) {
    return replace(text, Pattern.compile(regex, flags), replacement, item, 1);
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
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item) {
    return replace(text, Pattern.compile(regex), replacement, item, 1);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement) {
    return replace(text, pattern, replacement, 0, 1);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags) {
    return replace(text, Pattern.compile(regex, flags), replacement, 0, 1);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement) {
    return replace(text, Pattern.compile(regex), replacement, 0, 1);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int group) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    // 每个匹配项的指定捕获组的匹配值的起始和结束下标
    List<long[]> startEndIndexList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      long start = matcher.start(group);
      long end = matcher.end(group);
      startEndIndexList.add(new long[]{start, end});
    }
    // 构建替换后的字符串
    StringBuilder result = new StringBuilder();
    int lastEnd = 0;
    for (long[] indices : startEndIndexList) {
      int start = (int) indices[0];
      int end = (int) indices[1];
      // 将前面部分（从 lastEnd 到 start）添加到结果中
      result.append(text, lastEnd, start);
      // 添加替换内容
      result.append(replacement);
      // 更新 lastEnd
      lastEnd = end;
    }
    // 将剩余部分添加到结果中
    result.append(text.substring(lastEnd));
    return result.toString();
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags) {
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, group);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group) {
    return replaceAllItems(text, Pattern.compile(regex), replacement, group);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement) {
    return replaceAllItems(text, pattern, replacement, 1);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags) {
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, 1);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement) {
    return replaceAllItems(text, Pattern.compile(regex), replacement, 1);
  }

  /**
   * 替换指定匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @return 替换后的内容
   */
  public static String replaceAllGroups(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item) {
    if (item < 0) {
      throw new IllegalArgumentException("Item: should be greater than 0");
    }
    // 每个匹配项的所有捕获组的匹配值的起始和结束下标
    List<long[]> startEndIndexList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    int i = 0;
    while (matcher.find()) {
      if (item == i) {
        for (int j = 1; j <= matcher.groupCount(); j++) {
          long start = matcher.start(j);
          long end = matcher.end(j);
          startEndIndexList.add(new long[]{start, end});
        }
        break;
      }
      i++;
    }
    // 构建替换后的字符串
    StringBuilder result = new StringBuilder();
    int lastEnd = 0;
    for (long[] indices : startEndIndexList) {
      int start = (int) indices[0];
      int end = (int) indices[1];
      // 将前面部分（从 lastEnd 到 start）添加到结果中
      result.append(text, lastEnd, start);
      // 添加替换内容
      result.append(replacement);
      // 更新 lastEnd
      lastEnd = end;
    }
    // 将剩余部分添加到结果中
    result.append(text.substring(lastEnd));
    return result.toString();
  }

  /**
   * 替换指定匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAllGroups(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int flags) {
    return replaceAllGroups(text, Pattern.compile(regex, flags), replacement, item);
  }

  /**
   * 替换指定匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @return 替换后的内容
   */
  public static String replaceAllGroups(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item) {
    return replaceAllGroups(text, Pattern.compile(regex), replacement, item);
  }

  /**
   * 替换所有匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement) {
    // 每个匹配项的所有捕获组的匹配值的起始和结束下标
    List<long[]> startEndIndexList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      for (int j = 1; j <= matcher.groupCount(); j++) {
        long start = matcher.start(j);
        long end = matcher.end(j);
        startEndIndexList.add(new long[]{start, end});
      }
    }
    // 构建替换后的字符串
    StringBuilder result = new StringBuilder();
    int lastEnd = 0;
    for (long[] indices : startEndIndexList) {
      int start = (int) indices[0];
      int end = (int) indices[1];
      // 将前面部分（从 lastEnd 到 start）添加到结果中
      result.append(text, lastEnd, start);
      // 添加替换内容
      result.append(replacement);
      // 更新 lastEnd
      lastEnd = end;
    }
    // 将剩余部分添加到结果中
    result.append(text.substring(lastEnd));
    return result.toString();
  }

  /**
   * 替换所有匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param flags       匹配模式
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags) {
    return replaceAll(text, Pattern.compile(regex, flags), replacement);
  }

  /**
   * 替换所有匹配项的所有捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement) {
    return replaceAll(text, Pattern.compile(regex), replacement);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final Pattern pattern, final int item, final int group) {
    return replace(text, pattern, "", item, group);
  }

  /**
   * 删除指定匹配项的指定捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final String regex, final int item, final int group, final int flags) {
    return replace(text, regex, "", item, group, flags);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final String regex, final int item, final int group) {
    return replace(text, regex, "", item, group);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final Pattern pattern, final int group) {
    return replaceFirstItem(text, pattern, "", group);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final String regex, final int group, final int flags) {
    return replaceFirstItem(text, regex, "", group, flags);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final String regex, final int group) {
    return replaceFirstItem(text, regex, "", group);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, final int item) {
    return replace(text, pattern, "", item, 1);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final String regex, final int item, final int flags) {
    return replaceFirstGroup(text, regex, "", item, flags);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final String regex, final int item) {
    return replaceFirstGroup(text, regex, "", item);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceFirstItemGroup(text, pattern, "");
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final String regex, final int flags) {
    return replaceFirstItemGroup(text, regex, "", flags);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final String regex) {
    return replaceFirstItemGroup(text, regex, "");
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @param group   捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final Pattern pattern, final int group) {
    return replaceAllItems(text, pattern, "", group);
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final String regex, final int group, final int flags) {
    return replaceAllItems(text, regex, "", group, flags);
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param group 捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final String regex, final int group) {
    return replaceAllItems(text, regex, "", group);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceAllItemsFirstGroup(text, pattern, "");
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, final int flags) {
    return replaceAllItemsFirstGroup(text, regex, "", flags);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex) {
    return replaceAllItemsFirstGroup(text, regex, "");
  }

  /**
   * 删除指定匹配项的所有捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @param item    匹配项，从 0 开始
   * @return 删除后的内容
   */
  public static String removeAllGroups(@NonNull final String text, @NonNull final Pattern pattern, final int item) {
    return replaceAllGroups(text, pattern, "", item);
  }

  /**
   * 删除指定匹配项的所有捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeAllGroups(@NonNull final String text, @NonNull final String regex, final int item, final int flags) {
    return replaceAllGroups(text, regex, "", item, flags);
  }

  /**
   * 删除指定匹配项的所有捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param item  匹配项，从 0 开始
   * @return 删除后的内容
   */
  public static String removeAllGroups(@NonNull final String text, @NonNull final String regex, final int item) {
    return replaceAllGroups(text, regex, "", item);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceAll(text, pattern, "");
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @param flags 匹配模式
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final String regex, final int flags) {
    return replaceAll(text, regex, "", flags);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final String regex) {
    return replaceAll(text, regex, "");
  }

  /**
   * 是否包含汉字
   *
   * @param source 需要匹配的内容
   * @return 是否包含汉字
   */
  public static boolean containsHanZi(@NonNull final String source) {
    return isMatch(source, CommonPattern.MULTIPLE_CHINESE_CHAR);
  }
}
