package top.csaf.date;

import lombok.NonNull;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * 时间格式化工具类
 */
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

  public static String format(final Date date, @NonNull final FastDateFormat df) {
    return df.format(date);
  }
}
