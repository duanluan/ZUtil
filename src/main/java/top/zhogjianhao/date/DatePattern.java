package top.zhogjianhao.date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 时间格式
 */
public class DatePattern {

  public static final String UUUU_MM_DD_HH_MM_SS = "uuuu-MM-dd HH:mm:ss";
  public static final String UUUUMMDDHHMMSS = "uuuuMMddHHmmss";
  public static final String UUUU_MM_DD_HH_MM = "uuuu-MM-dd HH:mm";
  public static final String UUUU_MM_DD = "uuuu-MM-dd";
  public static final String UUUU_MM = "uuuu-MM";

  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYY_MM = "yyyy-MM";

  public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
  public static final String MMDDHHMMSS = "MMddHHmmss";
  public static final String MM_DD_HH_MM = "MM-dd HH:mm";
  public static final String MM_DD = "MM-dd";
  public static final String HH_MM_SS = "HH:mm:ss";
  public static final String HH_MM = "HH:mm";

  public static final FastDateFormat FORMAT_YYYY_MM_DD = FastDateFormat.getInstance(YYYY_MM_DD);
  public static final FastDateFormat FORMAT_YYYY_MM_DD_HH_MM_SS = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS);
}
