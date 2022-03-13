package top.zhogjianhao.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * 时间格式
 */
public class DatePattern {

  public static final String UUUU_MM_DD_HH_MM_SS = "uuuu-MM-dd HH:mm:ss";
  public static final String UUUU_MM_DD_SLASH_HH_MM_SS = "uuuu/MM/dd HH:mm:ss";
  public static final String UUUU_MM_DD_DOT_HH_MM_SS = "uuuu.MM.dd HH:mm:ss";
  public static final String UUUUMMDDHHMMSS = "uuuuMMddHHmmss";
  public static final String UUUU_MM_DD_HH_MM = "uuuu-MM-dd HH:mm";
  public static final String UUUU_MM_DD_SLASH_HH_MM = "uuuu/MM/dd HH:mm";
  public static final String UUUU_MM_DD_DOT_HH_MM = "uuuu.MM.dd HH:mm";
  public static final String UUUU_MM_DD = "uuuu-MM-dd";
  public static final String UUUU_MM_DD_SLASH = "uuuu/MM/dd";
  public static final String UUUU_MM_DD_DOT = "uuuu.MM.dd";
  public static final String UUUU_MM = "uuuu-MM";
  public static final String UUUU_MM_SLASH = "uuuu/MM";
  public static final String UUUU_MM_DOT= "uuuu.MM";

  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYY_MM_DD_SLASH_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
  public static final String YYYY_MM_DD_DOT_HH_MM_SS = "yyyy.MM.dd HH:mm:ss";
  public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MM_DD_SLASH_HH_MM = "yyyy/MM/dd HH:mm";
  public static final String YYYY_MM_DD_DOT_HH_MM = "yyyy.MM.dd HH:mm";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
  public static final String YYYY_MM_DD_DOT = "yyyy.MM.dd";
  public static final String YYYY_MM = "yyyy-MM";
  public static final String YYYY_MM_SLASH = "yyyy/MM";
  public static final String YYYY_MM_DOT = "yyyy.MM";

  public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
  public static final String MM_DD_SLASH_HH_MM_SS = "MM/dd HH:mm:ss";
  public static final String MM_DD_DOT_HH_MM_SS = "MM.dd HH:mm:ss";
  public static final String MMDDHHMMSS = "MMddHHmmss";
  public static final String MM_DD_HH_MM = "MM-dd HH:mm";
  public static final String MM_DD_SLASH_HH_MM = "MM/dd HH:mm";
  public static final String MM_DD_DOT_HH_MM = "MM.dd HH:mm";
  public static final String MM_DD = "MM-dd";
  public static final String MM_DD_SLASH = "MM/dd";
  public static final String MM_DD_DOT = "MM.dd";
  public static final String HH_MM_SS = "HH:mm:ss";
  public static final String HH_MM = "HH:mm";

  public static final FastDateFormat FORMAT_YYYY_MM_DD_HH_MM_SS = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS);
  public static final FastDateFormat FORMAT_YYYY_MM_DD = FastDateFormat.getInstance(YYYY_MM_DD);
  public static final FastDateFormat FORMAT_HH_MM_SS = FastDateFormat.getInstance(HH_MM_SS);
  public static final FastDateFormat FORMAT_MM_US = FastDateFormat.getInstance("MM", Locale.US);
  public static final FastDateFormat FORMAT_MMM_US = FastDateFormat.getInstance("MMM", Locale.US);
  public static final FastDateFormat FORMAT_MMMM_US = FastDateFormat.getInstance("MMMM", Locale.US);
  public static final FastDateFormat FORMAT_MM_ZH_CN = FastDateFormat.getInstance("MM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat FORMAT_MMM_ZH_CN = FastDateFormat.getInstance("MMM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat FORMAT_MMMM_ZH_CN = FastDateFormat.getInstance("MMMM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat FORMAT_MM_ZH_TW = FastDateFormat.getInstance("MM", Locale.TRADITIONAL_CHINESE);
  public static final FastDateFormat FORMAT_MMM_ZH_TW = FastDateFormat.getInstance("MMM", Locale.TRADITIONAL_CHINESE);
  public static final FastDateFormat FORMAT_MMMM_ZH_TW = FastDateFormat.getInstance("MMMM", Locale.TRADITIONAL_CHINESE);

  /**
   * 默认解析模式：严格模式（https://rumenz.com/java-topic/java/date-time/resolverstyle-strict-date-parsing/index.html）
   */
  public static final ResolverStyle DEFAULT_RESOLVER_STYLE = ResolverStyle.STRICT;

  public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS).withResolverStyle(DEFAULT_RESOLVER_STYLE);
  public static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern(YYYY_MM_DD).withResolverStyle(DEFAULT_RESOLVER_STYLE);
  public static final DateTimeFormatter FORMATTER_HH_MM_SS = DateTimeFormatter.ofPattern(HH_MM_SS).withResolverStyle(DEFAULT_RESOLVER_STYLE);
}
