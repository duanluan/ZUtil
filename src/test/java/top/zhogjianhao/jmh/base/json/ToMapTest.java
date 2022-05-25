package top.zhogjianhao.jmh.base.json;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.ObjectUtils;

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
    System.out.println(ObjectUtils.isAllEquals(false, false, test.jackson(), test.fastjson(), test.gson(), test.hutool()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToMapTest.class.getName()});
  }

  private static final String JSON_STR = new ToJsonTest().fastjson();

  @Benchmark
  public Map jackson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(JSON_STR, Map.class);
  }

  @Benchmark
  public Map fastjson() {
    return JSON.parseObject(JSON_STR, Map.class);
  }

  @Benchmark
  public Map gson() {
    return new Gson().fromJson(JSON_STR, Map.class);
  }

  @Benchmark
  public Map hutool() {
    return JSONUtil.toBean(JSON_STR, Map.class);
  }
}

// Benchmark                              Mode     Cnt     Score     Error   Units
// ToMapTest.fastjson                    thrpt       5     6.356 ±   0.100  ops/us
// ToMapTest.gson                        thrpt       5     0.617 ±   0.137  ops/us
// ToMapTest.hutool                      thrpt       5     1.653 ±   0.132  ops/us
// ToMapTest.jackson                     thrpt       5     0.304 ±   0.105  ops/us
// ToMapTest.fastjson                     avgt       5     0.148 ±   0.002   us/op
// ToMapTest.gson                         avgt       5     1.552 ±   0.042   us/op
// ToMapTest.hutool                       avgt       5     0.584 ±   0.008   us/op
// ToMapTest.jackson                      avgt       5     3.152 ±   0.036   us/op
// ToMapTest.fastjson                   sample  116439     0.215 ±   0.029   us/op
// ToMapTest.fastjson:fastjson·p0.00    sample             0.100             us/op
// ToMapTest.fastjson:fastjson·p0.50    sample             0.200             us/op
// ToMapTest.fastjson:fastjson·p0.90    sample             0.200             us/op
// ToMapTest.fastjson:fastjson·p0.95    sample             0.300             us/op
// ToMapTest.fastjson:fastjson·p0.99    sample             0.400             us/op
// ToMapTest.fastjson:fastjson·p0.999   sample             1.456             us/op
// ToMapTest.fastjson:fastjson·p0.9999  sample            56.314             us/op
// ToMapTest.fastjson:fastjson·p1.00    sample           945.152             us/op
// ToMapTest.gson                       sample  191971     1.706 ±   0.054   us/op
// ToMapTest.gson:gson·p0.00            sample             1.400             us/op
// ToMapTest.gson:gson·p0.50            sample             1.600             us/op
// ToMapTest.gson:gson·p0.90            sample             1.800             us/op
// ToMapTest.gson:gson·p0.95            sample             1.900             us/op
// ToMapTest.gson:gson·p0.99            sample             2.500             us/op
// ToMapTest.gson:gson·p0.999           sample             8.499             us/op
// ToMapTest.gson:gson·p0.9999          sample            51.188             us/op
// ToMapTest.gson:gson·p1.00            sample          1701.888             us/op
// ToMapTest.hutool                     sample  134430     0.643 ±   0.039   us/op
// ToMapTest.hutool:hutool·p0.00        sample             0.500             us/op
// ToMapTest.hutool:hutool·p0.50        sample             0.600             us/op
// ToMapTest.hutool:hutool·p0.90        sample             0.600             us/op
// ToMapTest.hutool:hutool·p0.95        sample             0.700             us/op
// ToMapTest.hutool:hutool·p0.99        sample             0.900             us/op
// ToMapTest.hutool:hutool·p0.999       sample             4.400             us/op
// ToMapTest.hutool:hutool·p0.9999      sample            41.579             us/op
// ToMapTest.hutool:hutool·p1.00        sample           888.832             us/op
// ToMapTest.jackson                    sample   99893     3.279 ±   0.067   us/op
// ToMapTest.jackson:jackson·p0.00      sample             2.700             us/op
// ToMapTest.jackson:jackson·p0.50      sample             3.100             us/op
// ToMapTest.jackson:jackson·p0.90      sample             3.300             us/op
// ToMapTest.jackson:jackson·p0.95      sample             3.600             us/op
// ToMapTest.jackson:jackson·p0.99      sample             5.896             us/op
// ToMapTest.jackson:jackson·p0.999     sample            44.807             us/op
// ToMapTest.jackson:jackson·p0.9999    sample           145.697             us/op
// ToMapTest.jackson:jackson·p1.00      sample          1312.768             us/op
// ToMapTest.fastjson                       ss       5    25.240 ±  14.002   us/op
// ToMapTest.gson                           ss       5   111.460 ±  71.023   us/op
// ToMapTest.hutool                         ss       5    76.180 ±  34.963   us/op
// ToMapTest.jackson                        ss       5   488.720 ± 257.083   us/op
