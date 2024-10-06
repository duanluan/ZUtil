package top.csaf.pinyin;

/**
 * 拼音特性配置，方便链式配置 {@link PinyinFeat}
 */
public class PinyinFeatConfig {

  private static final PinyinFeatConfig INSTANCE = new PinyinFeatConfig();

  private Boolean firstWordInitialCap;
  private Boolean firstWordInitialCapAlways;
  private Boolean secondWordInitialCap;
  private Boolean secondWordInitialCapAlways;
  private Boolean hasSeparatorByNotPinyinAround;
  private Boolean hasSeparatorByNotPinyinAroundAlways;

  private PinyinFeatConfig() {
  }

  public static PinyinFeatConfig setFirstWordInitialCap(Boolean firstWordInitialCap) {
    INSTANCE.firstWordInitialCap = firstWordInitialCap;
    return INSTANCE;
  }

  public static PinyinFeatConfig setFirstWordInitialCapAlways(Boolean firstWordInitialCap) {
    INSTANCE.firstWordInitialCapAlways = firstWordInitialCap;
    return INSTANCE;
  }

  public static PinyinFeatConfig setSecondWordInitialCap(Boolean secondWordInitialCap) {
    INSTANCE.secondWordInitialCap = secondWordInitialCap;
    return INSTANCE;
  }

  public static PinyinFeatConfig setSecondWordInitialCapAlways(Boolean secondWordInitialCap) {
    INSTANCE.secondWordInitialCapAlways = secondWordInitialCap;
    return INSTANCE;
  }

  public static PinyinFeatConfig setHasSeparatorByNotPinyinAround(Boolean hasSeparatorByNotPinyinAround) {
    INSTANCE.hasSeparatorByNotPinyinAround = hasSeparatorByNotPinyinAround;
    return INSTANCE;
  }

  public static PinyinFeatConfig setHasSeparatorByNotPinyinAroundAlways(Boolean hasSeparatorByNotPinyinAround) {
    INSTANCE.hasSeparatorByNotPinyinAroundAlways = hasSeparatorByNotPinyinAround;
    return INSTANCE;
  }

  public void apply() {
    if (firstWordInitialCap != null) {
      PinyinFeat.setFirstWordInitialCap(firstWordInitialCap);
    }
    if (firstWordInitialCapAlways != null) {
      PinyinFeat.setFirstWordInitialCapAlways(firstWordInitialCapAlways);
    }
    if (secondWordInitialCap != null) {
      PinyinFeat.setSecondWordInitialCap(secondWordInitialCap);
    }
    if (secondWordInitialCapAlways != null) {
      PinyinFeat.setSecondWordInitialCapAlways(secondWordInitialCapAlways);
    }
    if (hasSeparatorByNotPinyinAround != null) {
      PinyinFeat.setHasSeparatorByNotPinyinAround(hasSeparatorByNotPinyinAround);
    }
    if (hasSeparatorByNotPinyinAroundAlways != null) {
      PinyinFeat.setHasSeparatorByNotPinyinAroundAlways(hasSeparatorByNotPinyinAroundAlways);
    }
    INSTANCE.clear();
  }

  private void clear() {
    firstWordInitialCap = null;
    firstWordInitialCapAlways = null;
    secondWordInitialCap = null;
    secondWordInitialCapAlways = null;
    hasSeparatorByNotPinyinAround = null;
    hasSeparatorByNotPinyinAroundAlways = null;
  }
}
