package top.csaf.date.constant;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Locale;

public class DateFormat {

  public static final FastDateFormat YYYY_MM_DD_HH_MM_SS = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD_HH_MM_SS);
  public static final FastDateFormat YYYY_MM_DD = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD);
  public static final FastDateFormat HH_MM_SS = FastDateFormat.getInstance(DatePattern.HH_MM_SS);
  public static final FastDateFormat MM_US = FastDateFormat.getInstance("MM", Locale.US);
  public static final FastDateFormat MMM_US = FastDateFormat.getInstance("MMM", Locale.US);
  public static final FastDateFormat MMMM_US = FastDateFormat.getInstance("MMMM", Locale.US);
  public static final FastDateFormat MM_ZH_CN = FastDateFormat.getInstance("MM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat MMM_ZH_CN = FastDateFormat.getInstance("MMM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat MMMM_ZH_CN = FastDateFormat.getInstance("MMMM", Locale.SIMPLIFIED_CHINESE);
  public static final FastDateFormat MM_ZH_TW = FastDateFormat.getInstance("MM", Locale.TRADITIONAL_CHINESE);
  public static final FastDateFormat MMM_ZH_TW = FastDateFormat.getInstance("MMM", Locale.TRADITIONAL_CHINESE);
  public static final FastDateFormat MMMM_ZH_TW = FastDateFormat.getInstance("MMMM", Locale.TRADITIONAL_CHINESE);
}
