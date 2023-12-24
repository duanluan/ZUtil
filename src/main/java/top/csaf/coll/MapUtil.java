package top.csaf.coll;

import java.util.Map;

public class MapUtil {

  public static boolean isEmpty(final Map<?,?> map) {
    return org.apache.commons.collections4.MapUtils.isEmpty(map);
  }

  public static boolean isNotEmpty(final Map<?,?> map) {
    return org.apache.commons.collections4.MapUtils.isNotEmpty(map);
  }
}
