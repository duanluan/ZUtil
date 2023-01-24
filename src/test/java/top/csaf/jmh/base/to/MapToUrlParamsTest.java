package top.csaf.jmh.base.to;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 键值对参数转换为 URL 参数的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class MapToUrlParamsTest {

  public static void main(String[] args) {
    // 结果是否相等
    MapToUrlParamsTest test = new MapToUrlParamsTest();
    System.out.println(test.toUrlParamsByKeySet().equals(test.toUrlParamsByKeySet1()) && test.toUrlParamsByKeySet1().equals(test.toUrlParamsByEntrySet()) && test.toUrlParamsByEntrySet().equals(test.toUrlParamsByEntrySet1()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{MapToUrlParamsTest.class.getName()});
  }

  private Map<String, Object> getMap() {
    Map<String, Object> map = new HashMap<>();
    for (int i = 0; i < 100000; i++) {
      map.put(String.valueOf(i), i + 1);
    }
    return map;
  }

  @Benchmark
  public String toUrlParamsByKeySet() {
    Map<String, Object> params = getMap();
    StringBuilder result = new StringBuilder();
    for (String key : params.keySet()) {
      String param = "";
      if (result.length() > 0) {
        param = "&";
      }
      result.append(param).append(key).append("=").append(params.get(key));
    }
    return result.toString();
  }

  @Benchmark
  public String toUrlParamsByKeySet1() {
    Map<String, Object> params = getMap();
    StringBuilder result = new StringBuilder();
    String firstKey = null;
    Set<String> keySet = params.keySet();
    for (String key : keySet) {
      firstKey = key;
      result.append(key).append("=").append(params.get(key));
      break;
    }
    params.remove(firstKey);
    for (String key : keySet) {
      result.append("&").append(key).append("=").append(params.get(key));
    }
    return result.toString();
  }

  @Benchmark
  public String toUrlParamsByEntrySet() {
    Map<String, Object> params = getMap();
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      String param = "";
      if (result.length() > 0) {
        param = "&";
      }
      result.append(param).append(entry.getKey()).append("=").append(entry.getValue());
    }
    return result.toString();
  }

  @Benchmark
  public String toUrlParamsByEntrySet1() {
    Map<String, Object> params = getMap();
    StringBuilder result = new StringBuilder();
    Map.Entry<String, Object> firstEntry = null;
    Set<Map.Entry<String, Object>> entrySet = params.entrySet();
    for (Map.Entry<String, Object> entry : entrySet) {
      firstEntry = entry;
      result.append(entry.getKey()).append("=").append(entry.getValue());
      break;
    }
    entrySet.remove(firstEntry);
    for (Map.Entry<String, Object> entry : entrySet) {
      result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
    }
    return result.toString();
  }
}

// Benchmark                                                                   Mode     Cnt     Score    Error   Units
// ToUrlParamsTest.toUrlParamsByEntrySet                                    thrpt       5     1.751 ±  0.024  ops/us
// ToUrlParamsTest.toUrlParamsByEntrySet1                                   thrpt       5     1.985 ±  0.020  ops/us
// ToUrlParamsTest.toUrlParamsByKeySet                                      thrpt       5     1.672 ±  0.027  ops/us
// ToUrlParamsTest.toUrlParamsByKeySet1                                     thrpt       5     1.848 ±  0.037  ops/us
// ToUrlParamsTest.toUrlParamsByEntrySet                                     avgt       5     0.561 ±  0.015   us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1                                    avgt       5     0.550 ±  0.015   us/op
// ToUrlParamsTest.toUrlParamsByKeySet                                       avgt       5     0.572 ±  0.012   us/op
// ToUrlParamsTest.toUrlParamsByKeySet1                                      avgt       5     0.552 ±  0.012   us/op
// ToUrlParamsTest.toUrlParamsByEntrySet                                   sample  146037     0.586 ±  0.042   us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.00      sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.50      sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.90      sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.95      sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.99      sample             1.000            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.999     sample             5.396            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p0.9999    sample            25.722            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet:toUrlParamsByEntrySet·p1.00      sample          1667.072            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1                                  sample  137203     0.630 ±  0.026   us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.00    sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.50    sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.90    sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.95    sample             0.700            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.99    sample             1.300            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.999   sample             7.979            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p0.9999  sample            62.697            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1:toUrlParamsByEntrySet1·p1.00    sample           968.704            us/op
// ToUrlParamsTest.toUrlParamsByKeySet                                     sample  142747     0.622 ±  0.068   us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.00          sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.50          sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.90          sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.95          sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.99          sample             1.200            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.999         sample             6.922            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p0.9999        sample            44.051            us/op
// ToUrlParamsTest.toUrlParamsByKeySet:toUrlParamsByKeySet·p1.00          sample          1880.064            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1                                    sample  140590     0.615 ±  0.045   us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.00        sample             0.500            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.50        sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.90        sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.95        sample             0.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.99        sample             1.200            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.999       sample             5.600            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p0.9999      sample            29.444            us/op
// ToUrlParamsTest.toUrlParamsByKeySet1:toUrlParamsByKeySet1·p1.00        sample          1118.208            us/op
// ToUrlParamsTest.toUrlParamsByEntrySet                                       ss       5    43.240 ± 50.503   us/op
// ToUrlParamsTest.toUrlParamsByEntrySet1                                      ss       5    43.500 ± 41.572   us/op
// ToUrlParamsTest.toUrlParamsByKeySet                                         ss       5    33.200 ± 16.636   us/op
// ToUrlParamsTest.toUrlParamsByKeySet1                                        ss       5    37.140 ± 51.068   us/op
