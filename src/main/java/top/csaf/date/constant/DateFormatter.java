package top.csaf.date.constant;

import top.csaf.date.DateFeature;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateFormatter {

  public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS;
  public static final DateTimeFormatter YYYY_MM_DD;
  public static final DateTimeFormatter HH_MM_SS;
  public static final DateTimeFormatter M_EN;
  public static final DateTimeFormatter MM_EN;
  public static final DateTimeFormatter MMM_EN;
  public static final DateTimeFormatter MMMM_EN;
  public static final DateTimeFormatter M_ZH;
  public static final DateTimeFormatter MM_ZH;
  public static final DateTimeFormatter MMM_ZH;
  public static final DateTimeFormatter MMMM_ZH;

  static {
    ResolverStyle resolverStyle = DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE);

    YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS).withResolverStyle(resolverStyle);
    YYYY_MM_DD = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD).withResolverStyle(resolverStyle);
    HH_MM_SS = DateTimeFormatter.ofPattern(DatePattern.HH_MM_SS).withResolverStyle(resolverStyle);
    M_EN = DateTimeFormatter.ofPattern("M", Locale.ENGLISH).withResolverStyle(resolverStyle);
    MM_EN = DateTimeFormatter.ofPattern("MM", Locale.ENGLISH).withResolverStyle(resolverStyle);
    MMM_EN = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH).withResolverStyle(resolverStyle);
    MMMM_EN = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH).withResolverStyle(resolverStyle);
    M_ZH = DateTimeFormatter.ofPattern("M", Locale.CHINESE).withResolverStyle(resolverStyle);
    MM_ZH = DateTimeFormatter.ofPattern("MM", Locale.CHINESE).withResolverStyle(resolverStyle);
    MMM_ZH = DateTimeFormatter.ofPattern("MMM", Locale.CHINESE).withResolverStyle(resolverStyle);
    MMMM_ZH = DateTimeFormatter.ofPattern("MMMM", Locale.CHINESE).withResolverStyle(resolverStyle);
  }
}
