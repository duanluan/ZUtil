package top.csaf.date.constant;

import java.util.regex.Pattern;

/**
 * 时间格式正则
 */
public class DateRegExPattern {

  public static final Pattern WEEK_OF_MONTH = Pattern.compile("(?<![W])W(?![W])");
  public static final Pattern WEEK_OF_MONTH2 = Pattern.compile("(?<![W])[W]{2}(?![W])");
  public static final Pattern DAY_OF_MONTH = Pattern.compile("(?<![d])d(?![d])");
  public static final Pattern DAY_OF_MONTH2 = Pattern.compile("(?<![d])[d]{2}(?![d])");
  public static final Pattern HOUR_OF_DAY = Pattern.compile("(?<![H])H(?![H])");
  public static final Pattern HOUR_OF_DAY2 = Pattern.compile("(?<![H])[H]{2}(?![H])");
  public static final Pattern CLOCK_HOUR_OF_AM_PM12 = Pattern.compile("(?<![h])h(?![h])");
  public static final Pattern CLOCK_HOUR_OF_AM_PM12_2 = Pattern.compile("(?<![h])[h]{2}(?![h])");
  public static final Pattern HOUR_OF_AM_PM = Pattern.compile("(?<![K])K(?![K])");
  public static final Pattern HOUR_OF_AM_PM2 = Pattern.compile("(?<![K])[K]{2}(?![K])");
  public static final Pattern CLOCK_HOUR_OF_AM_PM24 = Pattern.compile("(?<![k])k(?![k])");
  public static final Pattern CLOCK_HOUR_OF_AM_PM24_2 = Pattern.compile("(?<![k])[k]{2}(?![k])");
  public static final Pattern MINUTE_OF_HOUR = Pattern.compile("(?<![m])m(?![m])");
  public static final Pattern MINUTE_OF_HOUR2 = Pattern.compile("(?<![m])[m]{2}(?![m])");
  public static final Pattern SECOND_OF_MINUTE = Pattern.compile("(?<![s])s(?![s])");
  public static final Pattern SECOND_OF_MINUTE2 = Pattern.compile("(?<![s])[s]{2}(?![s])");
  public static final Pattern FRACTION_OF_SECOND3 = Pattern.compile("(?<![S])[S]{3}(?![S])");
}
