package top.csaf.date.constant;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Locale;

/**
 * 日期格式，主要返回 {@link FastDateFormat}
 * <p>
 * FastDateFormat 中不含国家信息，所以无差异时直接用语言代替。
 */
public class DateFormat {

  public static final FastDateFormat YYYY_MM_DD_HH_MM_SS = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD_HH_MM_SS);
  public static final FastDateFormat YYYY_MM_DD = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD);
  public static final FastDateFormat HH_MM_SS = FastDateFormat.getInstance(DatePattern.HH_MM_SS);
  public static final FastDateFormat MM_EN = FastDateFormat.getInstance("MM", Locale.ENGLISH);
  public static final FastDateFormat MMM_EN = FastDateFormat.getInstance("MMM", Locale.ENGLISH);
  public static final FastDateFormat MMMM_EN = FastDateFormat.getInstance("MMMM", Locale.ENGLISH);
  public static final FastDateFormat MM_ZH = FastDateFormat.getInstance("MM", Locale.CHINESE);
  public static final FastDateFormat MMM_ZH = FastDateFormat.getInstance("MMM", Locale.CHINESE);
}
