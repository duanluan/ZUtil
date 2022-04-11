package top.zhogjianhao.jmh.base.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * JSON 字符串转 Map 性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToMapTest {

  public static void main(String[] args) throws JsonProcessingException {
    // 结果是否相等
    ToMapTest test = new ToMapTest();
    System.out.println(test.jacksonToMap().equals(test.fastjsonToMap()) && test.fastjsonToMap().equals(test.gsonToMap()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToMapTest.class.getName()});
  }

  private static final String JSON_STR = new ToJsonTest().fastjsonToJson();

  @Benchmark
  public Map jacksonToMap() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(JSON_STR, Map.class);
  }

  @Benchmark
  public Map fastjsonToMap() {
    return JSON.parseObject(JSON_STR, Map.class);
  }

  @Benchmark
  public Map gsonToMap() {
    return new Gson().fromJson(JSON_STR, Map.class);
  }
}

// Benchmark                                        Mode     Cnt     Score     Error   Units
// ToMapTest.fastjsonToMap                         thrpt       5     3.317 ±   0.064  ops/us
// ToMapTest.gsonToMap                             thrpt       5     0.601 ±   0.238  ops/us
// ToMapTest.jacksonToMap                          thrpt       5     0.309 ±   0.010  ops/us
// ToMapTest.fastjsonToMap                          avgt       5     0.302 ±   0.003   us/op
// ToMapTest.gsonToMap                              avgt       5     1.629 ±   0.628   us/op
// ToMapTest.jacksonToMap                           avgt       5     3.218 ±   0.482   us/op
// ToMapTest.fastjsonToMap                        sample  126525     0.366 ±   0.015   us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.00    sample             0.299             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.50    sample             0.300             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.90    sample             0.400             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.95    sample             0.401             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.99    sample             0.601             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.999   sample             7.096             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p0.9999  sample            41.586             us/op
// ToMapTest.fastjsonToMap:fastjsonToMap·p1.00    sample           280.576             us/op
// ToMapTest.gsonToMap                            sample  164926     1.841 ±   0.065   us/op
// ToMapTest.gsonToMap:gsonToMap·p0.00            sample             1.298             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.50            sample             1.500             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.90            sample             2.200             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.95            sample             2.400             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.99            sample             3.500             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.999           sample            24.576             us/op
// ToMapTest.gsonToMap:gsonToMap·p0.9999          sample           214.991             us/op
// ToMapTest.gsonToMap:gsonToMap·p1.00            sample          1536.000             us/op
// ToMapTest.jacksonToMap                         sample  177501     3.729 ±   0.077   us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.00      sample             2.896             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.50      sample             3.300             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.90      sample             3.700             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.95      sample             4.696             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.99      sample             8.384             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.999     sample            61.663             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p0.9999    sample           266.239             us/op
// ToMapTest.jacksonToMap:jacksonToMap·p1.00      sample          1638.400             us/op
// ToMapTest.fastjsonToMap                            ss       5    64.000 ±  72.871   us/op
// ToMapTest.gsonToMap                                ss       5   108.140 ±  78.790   us/op
// ToMapTest.jacksonToMap                             ss       5   554.920 ± 730.825   us/op
