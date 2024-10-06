package top.csaf.date;

import java.time.ZoneId;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * 时间特性配置，方便链式配置 {@link DateFeat}
 */
public class DateFeatConfig {

  private static final DateFeatConfig INSTANCE = new DateFeatConfig();

  private ResolverStyle resolverStyle;
  private ResolverStyle resolverStyleAlways;
  private Locale locale;
  private Locale localeAlways;
  private ZoneId zoneId;
  private ZoneId zoneIdAlways;
  private Long minDateYear;
  private Long minDateYearAlways;

  private DateFeatConfig() {
  }

  public static DateFeatConfig set(ResolverStyle resolverStyle) {
    INSTANCE.resolverStyle = resolverStyle;
    return INSTANCE;
  }

  public static DateFeatConfig setAlways(ResolverStyle resolverStyle) {
    INSTANCE.resolverStyleAlways = resolverStyle;
    return INSTANCE;
  }

  public static DateFeatConfig set(Locale locale) {
    INSTANCE.locale = locale;
    return INSTANCE;
  }

  public static DateFeatConfig setAlways(Locale locale) {
    INSTANCE.localeAlways = locale;
    return INSTANCE;
  }

  public static DateFeatConfig set(ZoneId zoneId) {
    INSTANCE.zoneId = zoneId;
    return INSTANCE;
  }

  public static DateFeatConfig setAlways(ZoneId zoneId) {
    INSTANCE.zoneIdAlways = zoneId;
    return INSTANCE;
  }

  public static DateFeatConfig setMinDateYear(Long minDateYear) {
    INSTANCE.minDateYear = minDateYear;
    return INSTANCE;
  }

  public static DateFeatConfig setMinDateYearAlways(Long minDateYear) {
    INSTANCE.minDateYearAlways = minDateYear;
    return INSTANCE;
  }

  public void apply() {
    if (INSTANCE.resolverStyle != null) {
      DateFeat.set(INSTANCE.resolverStyle);
    }
    if (INSTANCE.resolverStyleAlways != null) {
      DateFeat.setAlways(INSTANCE.resolverStyleAlways);
    }
    if (INSTANCE.locale != null) {
      DateFeat.set(INSTANCE.locale);
    }
    if (INSTANCE.localeAlways != null) {
      DateFeat.setAlways(INSTANCE.localeAlways);
    }
    if (INSTANCE.zoneId != null) {
      DateFeat.set(INSTANCE.zoneId);
    }
    if (INSTANCE.zoneIdAlways != null) {
      DateFeat.setAlways(INSTANCE.zoneIdAlways);
    }
    if (INSTANCE.minDateYear != null) {
      DateFeat.setMinDateYear(INSTANCE.minDateYear);
    }
    if (INSTANCE.minDateYearAlways != null) {
      DateFeat.setMinDateYearAlways(INSTANCE.minDateYearAlways);
    }
    INSTANCE.clear();
  }

  private void clear() {
    INSTANCE.resolverStyle = null;
    INSTANCE.resolverStyleAlways = null;
    INSTANCE.locale = null;
    INSTANCE.localeAlways = null;
    INSTANCE.zoneId = null;
    INSTANCE.zoneIdAlways = null;
    INSTANCE.minDateYear = null;
    INSTANCE.minDateYearAlways = null;
  }
}
