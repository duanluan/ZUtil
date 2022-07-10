package top.zhogjianhao.date;

import top.zhogjianhao.date.constant.DateConstant;

import java.time.ZoneId;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * 时间特性，决定着时间工具类细微处的处理方式，“_ALWAYS”结尾的总是生效
 */
public class DateFeature {

  /**
   * 模式
   */
  public static ResolverStyle RESOLVER_STYLE = null;
  /**
   * 模式
   */
  public static ResolverStyle RESOLVER_STYLE_ALWAYS = null;

  public static ResolverStyle get(ResolverStyle resolverStyle) {
    if (resolverStyle != null) {
      return resolverStyle;
    } else if (RESOLVER_STYLE_ALWAYS != null) {
      return RESOLVER_STYLE_ALWAYS;
    } else if (RESOLVER_STYLE != null) {
      ResolverStyle resolverStyle1 = RESOLVER_STYLE;
      RESOLVER_STYLE = null;
      return resolverStyle1;
    }
    return null;
  }

  public static ResolverStyle getResolverStyle() {
    if (RESOLVER_STYLE_ALWAYS != null) {
      return RESOLVER_STYLE_ALWAYS;
    } else if (RESOLVER_STYLE != null) {
      ResolverStyle resolverStyle1 = RESOLVER_STYLE;
      RESOLVER_STYLE = null;
      return resolverStyle1;
    }
    return DateConstant.DEFAULT_RESOLVER_STYLE;
  }

  /**
   * 区域，比如月份是中文还是英文
   */
  public static Locale LOCALE = null;
  /**
   * 区域，比如月份是中文还是英文
   */
  public static Locale LOCALE_ALWAYS = null;

  public static Locale get(Locale locale) {
    if (locale != null) {
      return locale;
    } else if (LOCALE_ALWAYS != null) {
      return LOCALE_ALWAYS;
    } else if (LOCALE != null) {
      Locale locale1 = LOCALE;
      LOCALE = null;
      return locale1;
    }
    return null;
  }

  public static Locale getLocale() {
    if (LOCALE_ALWAYS != null) {
      return LOCALE_ALWAYS;
    } else if (LOCALE != null) {
      Locale locale1 = LOCALE;
      LOCALE = null;
      return locale1;
    }
    return DateConstant.DEFAULT_LOCALE;
  }

  /**
   * 时区
   */
  public static ZoneId ZONE_ID = null;
  /**
   * 时区
   */
  public static ZoneId ZONE_ID_ALWAYS = null;

  public static ZoneId get(ZoneId zoneId) {
    if (zoneId != null) {
      return zoneId;
    } else if (ZONE_ID_ALWAYS != null) {
      return ZONE_ID_ALWAYS;
    } else if (LOCALE != null) {
      ZoneId zoneId1 = ZONE_ID;
      LOCALE = null;
      return zoneId1;
    }
    return null;
  }

  public static ZoneId getZoneId() {
    if (ZONE_ID_ALWAYS != null) {
      return ZONE_ID_ALWAYS;
    } else if (LOCALE != null) {
      ZoneId zoneId1 = ZONE_ID;
      LOCALE = null;
      return zoneId1;
    }
    return DateConstant.SYSTEM_ZONE_ID;
  }

  /**
   * 最小 Date 年
   */
  public static Long MIN_DATE_YEAR = null;
  /**
   * 最小 Date 年
   */
  public static Long MIN_DATE_YEAR_ALWAYS = null;

  public static Long getMinDateYear(Long minDateYear) {
    if (minDateYear > 0) {
      return minDateYear;
    } else if (MIN_DATE_YEAR_ALWAYS > 0) {
      return MIN_DATE_YEAR_ALWAYS;
    } else if (MIN_DATE_YEAR > 0) {
      Long minDateYear1 = MIN_DATE_YEAR;
      MIN_DATE_YEAR = null;
      return minDateYear1;
    }
    return null;
  }

  public static Long getMinDateYear() {
    if (MIN_DATE_YEAR_ALWAYS > 0) {
      return MIN_DATE_YEAR_ALWAYS;
    } else if (MIN_DATE_YEAR > 0) {
      Long minDateYear1 = MIN_DATE_YEAR;
      MIN_DATE_YEAR = null;
      return minDateYear1;
    }
    return DateConstant.DEFAULT_MIN_DATE_YEAR;
  }
}
