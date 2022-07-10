package top.zhogjianhao.date.constant;

import top.zhogjianhao.date.DateFeature;

import java.time.format.DateTimeFormatter;

public class DateFormatter {

  public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS).withResolverStyle(DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE));
  public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD).withResolverStyle(DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE));
  public static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern(DatePattern.HH_MM_SS).withResolverStyle(DateFeature.get(DateConstant.DEFAULT_RESOLVER_STYLE));
}
