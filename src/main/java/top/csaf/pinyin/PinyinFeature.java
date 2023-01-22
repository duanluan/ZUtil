package top.csaf.pinyin;

import lombok.extern.slf4j.Slf4j;

/**
 * 拼音特性，决定着拼音工具类的处理方式，“_ALWAYS”结尾的总是生效
 * <p>
 * 默认值：<br>
 * 第一个单词首字母是否大写：false<br>
 * 第二个单词首字母是否大写：false<br>
 * 非拼音前后是否需要分隔符：false
 */
@Slf4j
public class PinyinFeature {

  /**
   * 第一个单词首字母是否大写
   */
  private static final ThreadLocal<Boolean> FIRST_WORD_INITIAL_CAP = new ThreadLocal<>();
  /**
   * 持久的第一个单词首字母是否大写
   */
  private static volatile Boolean FIRST_WORD_INITIAL_CAP_ALWAYS = null;

  /**
   * 设置第一个单词首字母是否大写
   *
   * @param firstWordInitialCap 第一个单词首字母是否大写
   */
  public static void setFirstWordInitialCap(final Boolean firstWordInitialCap) {
    FIRST_WORD_INITIAL_CAP.set(firstWordInitialCap);
  }

  /**
   * 设置持久的第一个单词首字母是否大写
   *
   * @param firstWordInitialCap 持久的第一个单词首字母是否大写
   */
  public static void setFirstWordInitialCapAlways(final Boolean firstWordInitialCap) {
    FIRST_WORD_INITIAL_CAP_ALWAYS = firstWordInitialCap;
  }

  /**
   * 获取第一个单词首字母是否大写
   *
   * @param firstWordInitialCap 第一个单词首字母是否大写
   * @return 第一个单词首字母是否大写，默认为 null
   */
  public static Boolean getFirstWordInitialCap(final Boolean firstWordInitialCap) {
    if (firstWordInitialCap != null) {
      return firstWordInitialCap;
    } else if (FIRST_WORD_INITIAL_CAP_ALWAYS != null) {
      return FIRST_WORD_INITIAL_CAP_ALWAYS;
    } else if (FIRST_WORD_INITIAL_CAP.get() != null) {
      Boolean firstWordInitialCap1 = FIRST_WORD_INITIAL_CAP.get();
      FIRST_WORD_INITIAL_CAP.remove();
      return firstWordInitialCap1;
    }
    return null;
  }

  /**
   * 获取第一个单词首字母是否大写
   *
   * @param firstWordInitialCap 第一个单词首字母是否大写
   * @return 第一个单词首字母是否大写，默认为形参
   */
  public static Boolean getFirstWordInitialCapLazy(final Boolean firstWordInitialCap) {
    if (FIRST_WORD_INITIAL_CAP_ALWAYS != null) {
      return FIRST_WORD_INITIAL_CAP_ALWAYS;
    } else if (FIRST_WORD_INITIAL_CAP.get() != null) {
      Boolean firstWordInitialCap1 = FIRST_WORD_INITIAL_CAP.get();
      FIRST_WORD_INITIAL_CAP.remove();
      return firstWordInitialCap1;
    }
    return firstWordInitialCap;
  }

  /**
   * 获取第一个单词首字母是否大写
   *
   * @return 第一个单词首字母是否大写，默认为 false
   */
  public static Boolean getFirstWordInitialCap() {
    if (FIRST_WORD_INITIAL_CAP_ALWAYS != null) {
      return FIRST_WORD_INITIAL_CAP_ALWAYS;
    } else if (FIRST_WORD_INITIAL_CAP.get() != null) {
      Boolean firstWordInitialCap1 = FIRST_WORD_INITIAL_CAP.get();
      FIRST_WORD_INITIAL_CAP.remove();
      return firstWordInitialCap1;
    }
    return false;
  }

  /**
   * 第二个单词首字母是否大写
   */
  protected static final ThreadLocal<Boolean> SECOND_WORD_INITIAL_CAP = new ThreadLocal<>();
  /**
   * 持久的第二个单词首字母是否大写
   */
  protected static volatile Boolean SECOND_WORD_INITIAL_CAP_ALWAYS = null;

  /**
   * 设置第二个单词首字母是否大写
   *
   * @param secondWordInitialCap 第二个单词首字母是否大写
   */
  public static void setSecondWordInitialCap(final Boolean secondWordInitialCap) {
    SECOND_WORD_INITIAL_CAP.set(secondWordInitialCap);
  }

  /**
   * 设置持久的第二个单词首字母是否大写
   *
   * @param secondWordInitialCap 持久的第二个单词首字母是否大写
   */
  public static void setSecondWordInitialCapAlways(final Boolean secondWordInitialCap) {
    SECOND_WORD_INITIAL_CAP_ALWAYS = secondWordInitialCap;
  }

  /**
   * 获取第二个单词首字母是否大写
   *
   * @param secondWordInitialCap 第二个单词首字母是否大写
   * @return 第二个单词首字母是否大写，默认为 null
   */
  public static Boolean getSecondWordInitialCap(final Boolean secondWordInitialCap) {
    if (secondWordInitialCap != null) {
      return secondWordInitialCap;
    } else if (SECOND_WORD_INITIAL_CAP_ALWAYS != null) {
      return SECOND_WORD_INITIAL_CAP_ALWAYS;
    } else if (SECOND_WORD_INITIAL_CAP.get() != null) {
      Boolean secondWordInitialCap1 = SECOND_WORD_INITIAL_CAP.get();
      SECOND_WORD_INITIAL_CAP.remove();
      return secondWordInitialCap1;
    }
    return null;
  }

  /**
   * 获取第二个单词首字母是否大写
   *
   * @param secondWordInitialCap 第二个单词首字母是否大写
   * @return 第二个单词首字母是否大写，默认为形参
   */
  public static Boolean getSecondWordInitialCapLazy(final Boolean secondWordInitialCap) {
    if (SECOND_WORD_INITIAL_CAP_ALWAYS != null) {
      return SECOND_WORD_INITIAL_CAP_ALWAYS;
    } else if (SECOND_WORD_INITIAL_CAP.get() != null) {
      Boolean secondWordInitialCap1 = SECOND_WORD_INITIAL_CAP.get();
      SECOND_WORD_INITIAL_CAP.remove();
      return secondWordInitialCap1;
    }
    return secondWordInitialCap;
  }

  /**
   * 获取第二个单词首字母是否大写
   *
   * @return 第二个单词首字母是否大写，默认为 false
   */
  public static Boolean getSecondWordInitialCap() {
    if (SECOND_WORD_INITIAL_CAP_ALWAYS != null) {
      return SECOND_WORD_INITIAL_CAP_ALWAYS;
    } else if (SECOND_WORD_INITIAL_CAP.get() != null) {
      Boolean secondWordInitialCap1 = SECOND_WORD_INITIAL_CAP.get();
      SECOND_WORD_INITIAL_CAP.remove();
      return secondWordInitialCap1;
    }
    return false;
  }

  /**
   * 非拼音前后是否需要分隔符
   */
  private static final ThreadLocal<Boolean> HAS_SEPARATOR_BY_NOT_PINYIN_AROUND = new ThreadLocal<>();
  /**
   * 持久的非拼音前后是否需要分隔符
   */
  private static volatile Boolean HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS = null;

  /**
   * 设置非拼音前后是否需要分隔符
   *
   * @param hasSeparatorByNotPinyinAround 非拼音前后是否需要分隔符
   */
  public static void setHasSeparatorByNotPinyinAround(final Boolean hasSeparatorByNotPinyinAround) {
    HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.set(hasSeparatorByNotPinyinAround);
  }

  /**
   * 设置持久的非拼音前后是否需要分隔符
   *
   * @param hasSeparatorByNotPinyinAround 持久的非拼音前后是否需要分隔符
   */
  public static void setHasSeparatorByNotPinyinAroundAlways(final Boolean hasSeparatorByNotPinyinAround) {
    HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS = hasSeparatorByNotPinyinAround;
  }

  /**
   * 获取非拼音前后是否需要分隔符
   *
   * @param hasSeparatorByNotPinyinAround 非拼音前后是否需要分隔符
   * @return 非拼音前后是否需要分隔符，默认为 false
   */
  public static Boolean getHasSeparatorByNotPinyinAround(final Boolean hasSeparatorByNotPinyinAround) {
    if (hasSeparatorByNotPinyinAround != null) {
      return hasSeparatorByNotPinyinAround;
    } else if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS != null) {
      return HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS;
    } else if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get() != null) {
      Boolean hasSeparatorByNotPinyinAround1 = HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get();
      HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.remove();
      return hasSeparatorByNotPinyinAround1;
    }
    return false;
  }

  /**
   * 获取非拼音前后是否需要分隔符
   *
   * @param hasSeparatorByNotPinyinAround 非拼音前后是否需要分隔符
   * @return 非拼音前后是否需要分隔符，默认为形参
   */
  public static Boolean getHasSeparatorByNotPinyinAroundLazy(final Boolean hasSeparatorByNotPinyinAround) {
    if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS != null) {
      return HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS;
    } else if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get() != null) {
      Boolean hasSeparatorByNotPinyinAround1 = HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get();
      HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.remove();
      return hasSeparatorByNotPinyinAround1;
    }
    return hasSeparatorByNotPinyinAround;
  }

  /**
   * 获取非拼音前后是否需要分隔符
   *
   * @return 非拼音前后是否需要分隔符，默认为 false
   */
  public static Boolean getHasSeparatorByNotPinyinAround() {
    if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS != null) {
      return HAS_SEPARATOR_BY_NOT_PINYIN_AROUND_ALWAYS;
    } else if (HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get() != null) {
      Boolean hasSeparatorByNotPinyinAround1 = HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.get();
      HAS_SEPARATOR_BY_NOT_PINYIN_AROUND.remove();
      return hasSeparatorByNotPinyinAround1;
    }
    return false;
  }
}
