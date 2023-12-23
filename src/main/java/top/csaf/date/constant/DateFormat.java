package top.csaf.date.constant;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 日期格式，主要返回 {@link FastDateFormat}
 * <p>
 * FastDateFormat 中不含国家信息，所以无差异时直接用语言代替。
 */
public class DateFormat {

  public static final FastDateFormat YYYY_MM_DD_HH_MM_SS = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD_HH_MM_SS);
  public static final FastDateFormat YYYY_MM_DD = FastDateFormat.getInstance(DatePattern.YYYY_MM_DD);
}
