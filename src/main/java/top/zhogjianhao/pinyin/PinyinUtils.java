package top.zhogjianhao.pinyin;

import lombok.NonNull;
import top.zhogjianhao.FileUtils;
import top.zhogjianhao.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * 拼音工具类
 * <p>
 * 数据来源：<a href="https://github.com/mozillazg/pinyin-data">mozillazg/pinyin-data: 汉字拼音数据</a>
 *
 * @author ZhongJianhao
 */
public class PinyinUtils {

  private static final Map<String, String> PINYIN_DATA_WITH_TONE;
  private static final Map<String, String> PINYIN_DATA;

  static {
    try {
      // PINYIN_DATA_WITH_TONE = ChronicleMapBuilder
      //   .of(String.class, String.class)
      //   .name("pinyinDataWithTone")
      //   .averageKeySize(1)
      //   .averageValueSize(3)
      //   .entries(41497)
      //   // .create();
      // .createOrRecoverPersistedTo(new File(FileUtils.getTempDirectory() + "pinyinDataWithTone.dat"));
      // // .recoverPersistedTo(new File(FileUtils.getUserDir() + "/src/main/resources/pinyin/pinyinDataWithTone.dat"), true);
      File file = new File(FileUtils.getUserDir() + "/src/main/resources/pinyin/pinyinDataWithTone.dat");
      byte[] fileBytes = FileUtils.readFileToByteArray(file);
      PINYIN_DATA_WITH_TONE = (Map<String, String>) new ObjectInputStream(new ByteArrayInputStream(fileBytes)).readObject();

      file = new File(FileUtils.getUserDir() + "/src/main/resources/pinyin/pinyinData.dat");
      fileBytes = FileUtils.readFileToByteArray(file);
      PINYIN_DATA = (Map<String, String>) new ObjectInputStream(new ByteArrayInputStream(fileBytes)).readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 汉字转拼音
   *
   * @param str             原始内容
   * @param isWithTone      是否带声调
   * @param isOnlyFirst     是否只取多音字的第一个拼音
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String get(@NonNull final String str, final boolean isWithTone, final boolean isOnlyFirst, final String pinyinSeparator) {
    if (StringUtils.isBlank(str)) {
      throw new IllegalArgumentException("Str: should not be blank");
    }
    // 是否包含声调
    Map<String, String> pinyinDataMap = isWithTone ? PINYIN_DATA_WITH_TONE : PINYIN_DATA;

    StringBuilder result = new StringBuilder();
    boolean hasPinyinSeparator = StringUtils.isNotBlank(pinyinSeparator);
    // 拼音工具类特性
    Boolean firstWordInitialCapFeature = PinyinFeature.getFirstWordInitialCap();
    Boolean secondWordInitialCapFeature = PinyinFeature.getSecondWordInitialCap();
    Boolean notPinyinAroundHasSeparator = PinyinFeature.getNotPinyinAroundHasSeparator();

    char[] arr = str.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      char singleChar = arr[i];
      String pinyin = pinyinDataMap.get(String.valueOf(singleChar));
      boolean pinyinNotBlank = StringUtils.isNotBlank(pinyin);

      // 是否只取多音字的第一个拼音
      if (pinyinNotBlank && isOnlyFirst) {
        int commaIndex = pinyin.indexOf(",");
        if (commaIndex != -1) {
          pinyin = pinyin.substring(0, commaIndex);
        }
        // 第二个单词首字母是否大写
        if (i > 0 && secondWordInitialCapFeature) {
          pinyin = StringUtils.toInitialUpperCase(pinyin);
        }
      }

      // 是否需要根据特性处理
      if (pinyinNotBlank && ((i == 0 && firstWordInitialCapFeature) || (i > 0 && secondWordInitialCapFeature))) {
        String[] singlePinyins;
        // 第一个单词首字母是否大写
        if (i == 0) {
          singlePinyins = StringUtils.split(pinyin, ",");

          StringBuilder singlePinyinSb = new StringBuilder();
          for (int j = 0; j < singlePinyins.length; j++) {
            singlePinyinSb.append(StringUtils.toInitialUpperCase(singlePinyins[j]));
            if (j < singlePinyins.length - 1) {
              singlePinyinSb.append(",");
            }
          }
          pinyin = singlePinyinSb.toString();
        }
        // 第二个单词首字母是否大写
        else {
          singlePinyins = StringUtils.split(pinyin, ",");

          StringBuilder singlePinyinSb = new StringBuilder();
          for (int j = 0; j < singlePinyins.length; j++) {
            singlePinyinSb.append(StringUtils.toInitialUpperCase(singlePinyins[j]));
            if (j < singlePinyins.length - 1) {
              singlePinyinSb.append(",");
            }
          }
          pinyin = singlePinyinSb.toString();
        }
      }

      // 拼音
      if (pinyinNotBlank) {
        // 拼音分隔符
        if (i > 0 && hasPinyinSeparator) {
          result.append(pinyinSeparator);
        }
        result.append(pinyin);
        hasPinyinSeparator = true;
      }
      // 非拼音
      else {
        // 是否需要拼音分隔符
        if (!notPinyinAroundHasSeparator) {
          hasPinyinSeparator = false;
        }
        if (hasPinyinSeparator) {
          result.append(pinyinSeparator);
        }
        result.append(singleChar);
      }
    }
    return result.toString();
  }

  /**
   * 汉字转拼音，取多音字的全部拼音
   *
   * @param str             原始内容
   * @param isWithTone      是否带声调
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getAll(@NonNull final String str, final boolean isWithTone, @NonNull final String pinyinSeparator) {
    return get(str, isWithTone, false, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的全部拼音
   *
   * @param str        原始内容
   * @param isWithTone 是否带声调
   * @return 汉字转拼音后的内容
   */
  public static String getAll(@NonNull final String str, final boolean isWithTone) {
    return get(str, isWithTone, false, null);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音
   *
   * @param str             原始内容
   * @param isWithTone      是否带声调
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getFirst(@NonNull final String str, final boolean isWithTone, @NonNull final String pinyinSeparator) {
    return get(str, isWithTone, true, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音
   *
   * @param str        原始内容
   * @param isWithTone 是否带声调
   * @return 汉字转拼音后的内容
   */
  public static String getFirst(@NonNull final String str, final boolean isWithTone) {
    return get(str, isWithTone, true, null);
  }

  /**
   * 汉字转拼音，带声调
   *
   * @param str             原始内容
   * @param isOnlyFirst     是否只取多音字的第一个拼音
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getWithTone(@NonNull final String str, final boolean isOnlyFirst, @NonNull final String pinyinSeparator) {
    return get(str, true, isOnlyFirst, pinyinSeparator);
  }

  /**
   * 汉字转拼音，带声调
   *
   * @param str         原始内容
   * @param isOnlyFirst 是否只取多音字的第一个拼音
   * @return 汉字转拼音后的内容
   */
  public static String getWithTone(@NonNull final String str, final boolean isOnlyFirst) {
    return get(str, true, isOnlyFirst, null);
  }

  /**
   * 汉字转拼音，不带声调
   *
   * @param str             原始内容
   * @param isOnlyFirst     是否只取多音字的第一个拼音
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getNotWithTone(@NonNull final String str, final boolean isOnlyFirst, @NonNull final String pinyinSeparator) {
    return get(str, false, isOnlyFirst, pinyinSeparator);
  }

  /**
   * 汉字转拼音，不带声调
   *
   * @param str         原始内容
   * @param isOnlyFirst 是否只取多音字的第一个拼音
   * @return 汉字转拼音后的内容
   */
  public static String getNotWithTone(@NonNull final String str, final boolean isOnlyFirst) {
    return get(str, false, isOnlyFirst, null);
  }

  /**
   * 汉字转拼音，取多音字的全部拼音，带声调
   *
   * @param str             原始内容
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getAllWithTone(@NonNull final String str, @NonNull final String pinyinSeparator) {
    return get(str, true, false, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的全部拼音，带声调
   *
   * @param str 原始内容
   * @return 汉字转拼音后的内容
   */
  public static String getAllWithTone(@NonNull final String str) {
    return get(str, true, false, null);
  }

  /**
   * 汉字转拼音，取多音字的全部拼音，带声调
   *
   * @param str             原始内容
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getAllNotWithTone(@NonNull final String str, @NonNull final String pinyinSeparator) {
    return get(str, false, false, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的全部拼音，带声调
   *
   * @param str 原始内容
   * @return 汉字转拼音后的内容
   */
  public static String getAllNotWithTone(@NonNull final String str) {
    return get(str, false, false, null);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音，不带声调
   *
   * @param str             原始内容
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getFirstWithTone(@NonNull final String str, @NonNull final String pinyinSeparator) {
    return get(str, true, true, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音，不带声调
   *
   * @param str 原始内容
   * @return 汉字转拼音后的内容
   */
  public static String getFirstWithTone(@NonNull final String str) {
    return get(str, true, true, null);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音，不带声调
   *
   * @param str             原始内容
   * @param pinyinSeparator 拼音分隔符
   * @return 汉字转拼音后的内容
   */
  public static String getFirstNotWithTone(@NonNull final String str, @NonNull final String pinyinSeparator) {
    return get(str, false, true, pinyinSeparator);
  }

  /**
   * 汉字转拼音，取多音字的第一个拼音，不带声调
   *
   * @param str 原始内容
   * @return 汉字转拼音后的内容
   */
  public static String getFirstNotWithTone(@NonNull final String str) {
    return get(str, false, true, null);
  }

  /**
   * 是否为多音字
   *
   * @return 是否为多音字
   */
  public static boolean isPolyphonicWord(final char c) {
    return PinyinUtils.get(String.valueOf(c), true, false, ",").contains(",");
  }
}
