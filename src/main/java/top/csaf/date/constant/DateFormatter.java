package top.csaf.date.constant;

import top.csaf.date.DateFeature;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateFormatter {

  public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS;
  public static final DateTimeFormatter YYYY_MM_DD;
  public static final DateTimeFormatter HH_MM_SS;

  static {
    ResolverStyle resolverStyle = DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE);
    Locale locale = DateFeature.getLocale();

    DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS).withResolverStyle(resolverStyle);
    DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD).withResolverStyle(resolverStyle);
    DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofPattern(DatePattern.HH_MM_SS).withResolverStyle(resolverStyle);
    if (locale != null) {
      dateTimeFormatter1 = dateTimeFormatter1.withLocale(locale);
      dateTimeFormatter2 = dateTimeFormatter2.withLocale(locale);
      dateTimeFormatter3 = dateTimeFormatter3.withLocale(locale);
    }
    YYYY_MM_DD_HH_MM_SS = dateTimeFormatter1;
    YYYY_MM_DD = dateTimeFormatter2;
    HH_MM_SS = dateTimeFormatter3;
  }
}
