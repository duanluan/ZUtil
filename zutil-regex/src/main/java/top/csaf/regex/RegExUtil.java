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
   * @param text             需要匹配的内容
   * @param pattern          正则
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int item, final int group, final boolean useLocalGrouping) {
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
        // 使用局部分组模式
        if (useLocalGrouping && group > 0) {
          int localGroup = 0;

          for (int j = 1; j <= matcher.groupCount(); j++) {
            if (matcher.group(j) != null) {
              localGroup++;
              if (localGroup == group) {
                return matcher.group(j);
              }
            }
          }
          return null;
        } else {
          return matcher.group(group);
        }
      }
      i++;
    }
    return null;
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param regex            正则
   * @param text             需要匹配的内容
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group, final int flags, final boolean useLocalGrouping) {
    return match(text, Pattern.compile(regex, flags), item, group, useLocalGrouping);
  }

  /**
   * 获取指定匹配项的指定捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String match(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int group, final boolean useLocalGrouping) {
    return match(text, Pattern.compile(regex), item, group, useLocalGrouping);
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
    return match(text, pattern, item, group, true);
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
    return match(text, Pattern.compile(regex, flags), item, group, true);
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
    return match(text, Pattern.compile(regex), item, group, true);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param pattern          正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int group, final boolean useLocalGrouping) {
    return match(text, pattern, 0, group, useLocalGrouping);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags, final boolean useLocalGrouping) {
    return match(text, regex, 0, group, flags, useLocalGrouping);
  }

  /**
   * 获取第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配值
   */
  public static String matchFirstItem(@NonNull final CharSequence text, @NonNull final String regex, final int group, final boolean useLocalGrouping) {
    return match(text, regex, 0, group, 0, useLocalGrouping);
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
    return match(text, pattern, 0, group, true);
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
    return match(text, regex, 0, group, flags, true);
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
    return match(text, regex, 0, group, 0, true);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param pattern          正则
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int item, final boolean useLocalGrouping) {
    return match(text, pattern, item, 1, useLocalGrouping);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final String regex, final int item, final int flags, final boolean useLocalGrouping) {
    return match(text, regex, item, 1, flags, useLocalGrouping);
  }

  /**
   * 获取指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstGroup(@NonNull final CharSequence text, @NonNull final String regex, final int item, final boolean useLocalGrouping) {
    return match(text, regex, item, 1, 0, useLocalGrouping);
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
    return match(text, pattern, item, 1, true);
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
    return match(text, regex, item, 1, flags, true);
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
    return match(text, regex, item, 1, 0, true);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param pattern          正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern, final boolean useLocalGrouping) {
    return match(text, pattern, 0, 1, useLocalGrouping);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final String regex, final int flags, final boolean useLocalGrouping) {
    return match(text, regex, 0, 1, flags, useLocalGrouping);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final String regex, final boolean useLocalGrouping) {
    return match(text, regex, 0, 1, 0, useLocalGrouping);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    return match(text, pattern, 0, 1, true);
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
    return match(text, regex, 0, 1, flags, true);
  }

  /**
   * 获取第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static String matchFirstItemGroup(@NonNull final CharSequence text, @NonNull final String regex) {
    return match(text, regex, 0, 1, 0, true);
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text             需要匹配的内容
   * @param pattern          正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final Pattern pattern, final int group, final boolean useLocalGrouping) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    List<String> resultList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      // 使用局部分组模式
      if (useLocalGrouping && group > 0) {
        int localGroup = 0;

        for (int j = 1; j <= matcher.groupCount(); j++) {
          if (matcher.group(j) != null) {
            localGroup++;
            if (localGroup == group) {
              resultList.add(matcher.group(j));
            }
          }
        }
      } else {
        resultList.add(matcher.group(group));
      }
    }
    return resultList;
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final String regex, final int group, final int flags, final boolean useLocalGrouping) {
    return matchAllItems(text, Pattern.compile(regex, flags), group, useLocalGrouping);
  }

  /**
   * 获取所有匹配项的指定捕获组的匹配值集合
   *
   * @param text             需要匹配的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 匹配集合
   */
  public static List<String> matchAllItems(@NonNull final CharSequence text, @NonNull final String regex, final int group, final boolean useLocalGrouping) {
    return matchAllItems(text, regex, group, 0, useLocalGrouping);
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
    return matchAllItems(text, pattern, group, true);
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
    return matchAllItems(text, Pattern.compile(regex, flags), group, true);
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
    return matchAllItems(text, regex, group, 0, true);
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
          String groupVal = matcher.group(j);
          if (groupVal != null) {
            resultList.add(groupVal);
          }
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
   * @param text              需要匹配的内容
   * @param pattern           正则
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final Pattern pattern, final boolean useMatchIfNoGroup) {
    List<String> resultList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      int groupCount = matcher.groupCount();
      // 使用匹配项本身
      if (useMatchIfNoGroup) {
        resultList.add(matcher.group(0));
      } else {
        int group = 1;
        while (group <= groupCount) {
          String groupVal = matcher.group(group);
          if (groupVal != null) {
            resultList.add(groupVal);
          }
          group++;
        }
      }
    }
    return resultList;
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text              需要匹配的内容
   * @param regex             正则
   * @param flags             匹配模式
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final String regex, final int flags, final boolean useMatchIfNoGroup) {
    return matchAll(text, Pattern.compile(regex, flags), useMatchIfNoGroup);
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text              需要匹配的内容
   * @param regex             正则
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final String regex, final boolean useMatchIfNoGroup) {
    return matchAll(text, Pattern.compile(regex), useMatchIfNoGroup);
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text    需要匹配的内容
   * @param pattern 正则
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final Pattern pattern) {
    return matchAll(text, pattern, false);
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
    return matchAll(text, Pattern.compile(regex, flags), false);
  }

  /**
   * 获取所有匹配项的所有捕获组的匹配值集合
   *
   * @param text  需要匹配的内容
   * @param regex 正则
   * @return 匹配集合
   */
  public static List<String> matchAll(@NonNull final CharSequence text, @NonNull final String regex) {
    return matchAll(text, Pattern.compile(regex), false);
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item, final int group, final boolean useLocalGrouping) {
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
        // 使用局部分组模式
        if (useLocalGrouping && group == 0) {
          int localGroup = 0;
          for (int j = 1; j <= matcher.groupCount(); j++) {
            if (matcher.group(j) != null) {
              localGroup++;
              if (localGroup == group) {
                int startIndex = matcher.start(j);
                int endIndex = matcher.end(j);
                return text.substring(0, startIndex) + replacement + text.substring(endIndex);
              }
            }
          }
        } else {
          int startIndex = matcher.start(group);
          int endIndex = matcher.end(group);
          return text.substring(0, startIndex) + replacement + text.substring(endIndex);
        }
      }
      i++;
    }
    return null;
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group, final int flags, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex, flags), replacement, item, group, useLocalGrouping);
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex), replacement, item, group, useLocalGrouping);
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param pattern     正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item, final int group) {
    return replace(text, pattern, replacement, item, group, true);
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
    return replace(text, Pattern.compile(regex, flags), replacement, item, group, true);
  }

  /**
   * 替换指定匹配项的指定捕获组的匹配值
   *
   * @param text        需要替换的内容
   * @param regex       正则
   * @param replacement 替换值
   * @param item        匹配项，从 0 开始
   * @param group       捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @return 替换后的内容
   */
  public static String replace(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int group) {
    return replace(text, Pattern.compile(regex), replacement, item, group, true);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int group, final boolean useLocalGrouping) {
    return replace(text, pattern, replacement, 0, group, useLocalGrouping);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex, flags), replacement, 0, group, useLocalGrouping);
  }

  /**
   * 替换第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItem(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex), replacement, 0, group, useLocalGrouping);
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
    return replace(text, pattern, replacement, 0, group, true);
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
    return replace(text, Pattern.compile(regex, flags), replacement, 0, group, true);
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
    return replace(text, Pattern.compile(regex), replacement, 0, group, true);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int item, final boolean useLocalGrouping) {
    return replace(text, pattern, replacement, item, 1, useLocalGrouping);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final int flags, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex, flags), replacement, item, 1, useLocalGrouping);
  }

  /**
   * 替换指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int item, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex), replacement, item, 1, useLocalGrouping);
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
    return replace(text, pattern, replacement, item, 1, true);
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
    return replace(text, Pattern.compile(regex, flags), replacement, item, 1, true);
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
    return replace(text, Pattern.compile(regex), replacement, item, 1, true);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final boolean useLocalGrouping) {
    return replace(text, pattern, replacement, 0, 1, useLocalGrouping);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex, flags), replacement, 0, 1, useLocalGrouping);
  }

  /**
   * 替换第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceFirstItemGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final boolean useLocalGrouping) {
    return replace(text, Pattern.compile(regex), replacement, 0, 1, useLocalGrouping);
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
    return replace(text, pattern, replacement, 0, 1, true);
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
    return replace(text, Pattern.compile(regex, flags), replacement, 0, 1, true);
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
    return replace(text, Pattern.compile(regex), replacement, 0, 1, true);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final int group, final boolean useLocalGrouping) {
    if (group < 0) {
      throw new IllegalArgumentException("Group: should be greater than 0");
    }
    // 每个匹配项的指定捕获组的匹配值的起始和结束下标
    List<long[]> startEndIndexList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      // 使用局部分组模式
      if (useLocalGrouping && group > 0) {
        int localGroup = 0;
        for (int j = 1; j <= matcher.groupCount(); j++) {
          if (matcher.group(j) != null) {
            localGroup++;
            if (localGroup == group) {
              long start = matcher.start(j);
              long end = matcher.end(j);
              startEndIndexList.add(new long[]{start, end});
            }
          }
        }
      } else {
        long start = matcher.start(group);
        long end = matcher.end(group);
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
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final int flags, final boolean useLocalGrouping) {
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, group, useLocalGrouping);
  }

  /**
   * 替换所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItems(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int group, final boolean useLocalGrouping) {
    return replaceAllItems(text, Pattern.compile(regex), replacement, group, useLocalGrouping);
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
    return replaceAllItems(text, pattern, replacement, group, true);
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
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, group, true);
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
    return replaceAllItems(text, Pattern.compile(regex), replacement, group, true);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param pattern          正则
   * @param replacement      替换值
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final boolean useLocalGrouping) {
    return replaceAllItems(text, pattern, replacement, 1, useLocalGrouping);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags, final boolean useLocalGrouping) {
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, 1, useLocalGrouping);
  }

  /**
   * 替换所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要替换的内容
   * @param regex            正则
   * @param replacement      替换值
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String replaceAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final boolean useLocalGrouping) {
    return replaceAllItems(text, Pattern.compile(regex), replacement, 1, useLocalGrouping);
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
    return replaceAllItems(text, pattern, replacement, 1, true);
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
    return replaceAllItems(text, Pattern.compile(regex, flags), replacement, 1, true);
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
    return replaceAllItems(text, Pattern.compile(regex), replacement, 1, true);
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
   * @param text              需要替换的内容
   * @param pattern           正则
   * @param replacement       替换值
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final Pattern pattern, @NonNull final String replacement, final boolean useMatchIfNoGroup) {
    // 每个匹配项的所有捕获组的匹配值的起始和结束下标
    List<long[]> startEndIndexList = new ArrayList<>();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      int groupCount = matcher.groupCount();
      // 使用匹配项本身
      if (useMatchIfNoGroup) {
        long start = matcher.start();
        long end = matcher.end();
        startEndIndexList.add(new long[]{start, end});
      } else {
        for (int j = 1; j <= groupCount; j++) {
          long start = matcher.start(j);
          if (start == -1) {
            continue;
          }
          long end = matcher.end(j);
          startEndIndexList.add(new long[]{start, end});
        }
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
   * @param text              需要替换的内容
   * @param regex             正则
   * @param replacement       替换值
   * @param flags             匹配模式
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final int flags, final boolean useMatchIfNoGroup) {
    return replaceAll(text, Pattern.compile(regex, flags), replacement, useMatchIfNoGroup);
  }

  /**
   * 替换所有匹配项的所有捕获组的匹配值
   *
   * @param text              需要替换的内容
   * @param regex             正则
   * @param replacement       替换值
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 替换后的内容
   */
  public static String replaceAll(@NonNull final String text, @NonNull final String regex, @NonNull final String replacement, final boolean useMatchIfNoGroup) {
    return replaceAll(text, Pattern.compile(regex), replacement, useMatchIfNoGroup);
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
    return replaceAll(text, pattern, replacement, false);
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
    return replaceAll(text, Pattern.compile(regex, flags), replacement, false);
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
    return replaceAll(text, Pattern.compile(regex), replacement, false);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final Pattern pattern, final int item, final int group, final boolean useLocalGrouping) {
    return replace(text, pattern, "", item, group, useLocalGrouping);
  }

  /**
   * 删除指定匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final String regex, final int item, final int group, final int flags, final boolean useLocalGrouping) {
    return replace(text, regex, "", item, group, flags, useLocalGrouping);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 替换后的内容
   */
  public static String remove(@NonNull final String text, @NonNull final String regex, final int item, final int group, final boolean useLocalGrouping) {
    return replace(text, regex, "", item, group, useLocalGrouping);
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
    return replace(text, pattern, "", item, group, true);
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
    return replace(text, regex, "", item, group, flags, true);
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
    return replace(text, regex, "", item, group, true);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final Pattern pattern, final int group, final boolean useLocalGrouping) {
    return replaceFirstItem(text, pattern, "", group, useLocalGrouping);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final String regex, final int group, final int flags, final boolean useLocalGrouping) {
    return replaceFirstItem(text, regex, "", group, flags, useLocalGrouping);
  }

  /**
   * 删除第一个匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItem(@NonNull final String text, @NonNull final String regex, final int group, final boolean useLocalGrouping) {
    return replaceFirstItem(text, regex, "", group, useLocalGrouping);
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
    return replaceFirstItem(text, pattern, "", group, true);
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
    return replaceFirstItem(text, regex, "", group, flags, true);
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
    return replaceFirstItem(text, regex, "", group, true);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, final int item, final boolean useLocalGrouping) {
    return replace(text, pattern, "", item, 1, useLocalGrouping);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final String regex, final int item, final int flags, final boolean useLocalGrouping) {
    return replaceFirstGroup(text, regex, "", item, flags, useLocalGrouping);
  }

  /**
   * 删除指定匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param item             匹配项，从 0 开始
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstGroup(@NonNull final String text, @NonNull final String regex, final int item, final boolean useLocalGrouping) {
    return replaceFirstGroup(text, regex, "", item, useLocalGrouping);
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
    return replace(text, pattern, "", item, 1, true);
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
    return replaceFirstGroup(text, regex, "", item, flags, true);
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
    return replaceFirstGroup(text, regex, "", item, true);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final Pattern pattern, final boolean useLocalGrouping) {
    return replaceFirstItemGroup(text, pattern, "", useLocalGrouping);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final String regex, final int flags, final boolean useLocalGrouping) {
    return replaceFirstItemGroup(text, regex, "", flags, useLocalGrouping);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final String regex, final boolean useLocalGrouping) {
    return replaceFirstItemGroup(text, regex, "", useLocalGrouping);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceFirstItemGroup(text, pattern, "", true);
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
    return replaceFirstItemGroup(text, regex, "", flags, true);
  }

  /**
   * 删除第一个匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeFirstItemGroup(@NonNull final String text, @NonNull final String regex) {
    return replaceFirstItemGroup(text, regex, "", true);
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final Pattern pattern, final int group, final boolean useLocalGrouping) {
    return replaceAllItems(text, pattern, "", group, useLocalGrouping);
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final String regex, final int group, final int flags, final boolean useLocalGrouping) {
    return replaceAllItems(text, regex, "", group, flags, useLocalGrouping);
  }

  /**
   * 删除所有匹配项的指定捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param group            捕获组，从 1 开始，0 为所在匹配项所有捕获组
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItems(@NonNull final String text, @NonNull final String regex, final int group, final boolean useLocalGrouping) {
    return replaceAllItems(text, regex, "", group, useLocalGrouping);
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
    return replaceAllItems(text, pattern, "", group, true);
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
    return replaceAllItems(text, regex, "", group, flags, true);
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
    return replaceAllItems(text, regex, "", group, true);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param pattern          正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final Pattern pattern, final boolean useLocalGrouping) {
    return replaceAllItemsFirstGroup(text, pattern, "", useLocalGrouping);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param flags            匹配模式
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, final int flags, final boolean useLocalGrouping) {
    return replaceAllItemsFirstGroup(text, regex, "", flags, useLocalGrouping);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text             需要删除的内容
   * @param regex            正则
   * @param useLocalGrouping 是否使用局部分组，即每个匹配项的捕获组序号从 1 开始
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex, final boolean useLocalGrouping) {
    return replaceAllItemsFirstGroup(text, regex, "", useLocalGrouping);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceAllItemsFirstGroup(text, pattern, "", true);
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
    return replaceAllItemsFirstGroup(text, regex, "", flags, true);
  }

  /**
   * 删除所有匹配项的第一个捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeAllItemsFirstGroup(@NonNull final String text, @NonNull final String regex) {
    return replaceAllItemsFirstGroup(text, regex, "", true);
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
   * @param text              需要删除的内容
   * @param pattern           正则
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final Pattern pattern, final boolean useMatchIfNoGroup) {
    return replaceAll(text, pattern, "", useMatchIfNoGroup);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text              需要删除的内容
   * @param regex             正则
   * @param flags             匹配模式
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final String regex, final int flags, final boolean useMatchIfNoGroup) {
    return replaceAll(text, regex, "", flags, useMatchIfNoGroup);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text              需要删除的内容
   * @param regex             正则
   * @param useMatchIfNoGroup 如果没有捕获组是否使用匹配项本身
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final String regex, final boolean useMatchIfNoGroup) {
    return replaceAll(text, regex, "", useMatchIfNoGroup);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text    需要删除的内容
   * @param pattern 正则
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final Pattern pattern) {
    return replaceAll(text, pattern, "", false);
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
    return replaceAll(text, regex, "", flags, false);
  }

  /**
   * 删除所有匹配项的所有捕获组的匹配值
   *
   * @param text  需要删除的内容
   * @param regex 正则
   * @return 删除后的内容
   */
  public static String removeAll(@NonNull final String text, @NonNull final String regex) {
    return replaceAll(text, regex, "", false);
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
