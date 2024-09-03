package top.csaf.jmh.base;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.lang.ObjUtil;

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
    System.out.println(ObjUtil.isAllEquals(false, false, test.jackson(), test.fastjson(), test.gson(), test.hutool()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToMapTest.class.getName()});
  }

  private static final String JSON_STR = new ToJsonTest().fastjson();

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Benchmark
  public Map jackson() throws JsonProcessingException {
    return objectMapper.readValue(JSON_STR, Map.class);
  }

  @Benchmark
  public Map fastjson() {
    return JSON.parseObject(JSON_STR, Map.class);
  }

  private final static Gson gson = new Gson();

  @Benchmark
  public Map gson() {
    return gson.fromJson(JSON_STR, Map.class);
  }

  @Benchmark
  public Map hutool() {
    return JSONUtil.toBean(JSON_STR, Map.class);
  }
}

// Benchmark                              Mode     Cnt     Score    Error   Units
// ToMapTest.fastjson                    thrpt       5     6.129 ±  0.153  ops/us
// ToMapTest.gson                        thrpt       5     1.926 ±  0.644  ops/us
// ToMapTest.hutool                      thrpt       5     1.705 ±  0.041  ops/us
// ToMapTest.jackson                     thrpt       5     3.280 ±  0.079  ops/us
// ToMapTest.fastjson                     avgt       5     0.147 ±  0.002   us/op
// ToMapTest.gson                         avgt       5     0.498 ±  0.014   us/op
// ToMapTest.hutool                       avgt       5     0.586 ±  0.023   us/op
// ToMapTest.jackson                      avgt       5     0.305 ±  0.009   us/op
// ToMapTest.fastjson                   sample  122826     0.212 ±  0.063   us/op
// ToMapTest.fastjson:fastjson·p0.00    sample             0.100            us/op
// ToMapTest.fastjson:fastjson·p0.50    sample             0.200            us/op
// ToMapTest.fastjson:fastjson·p0.90    sample             0.200            us/op
// ToMapTest.fastjson:fastjson·p0.95    sample             0.200            us/op
// ToMapTest.fastjson:fastjson·p0.99    sample             0.400            us/op
// ToMapTest.fastjson:fastjson·p0.999   sample             0.900            us/op
// ToMapTest.fastjson:fastjson·p0.9999  sample            29.559            us/op
// ToMapTest.fastjson:fastjson·p1.00    sample          1824.768            us/op
// ToMapTest.gson                       sample  151616     0.583 ±  0.035   us/op
// ToMapTest.gson:gson·p0.00            sample             0.300            us/op
// ToMapTest.gson:gson·p0.50            sample             0.500            us/op
// ToMapTest.gson:gson·p0.90            sample             0.700            us/op
// ToMapTest.gson:gson·p0.95            sample             0.800            us/op
// ToMapTest.gson:gson·p0.99            sample             1.100            us/op
// ToMapTest.gson:gson·p0.999           sample             4.812            us/op
// ToMapTest.gson:gson·p0.9999          sample            91.162            us/op
// ToMapTest.gson:gson·p1.00            sample          1202.176            us/op
// ToMapTest.hutool                     sample  109133     0.852 ±  0.114   us/op
// ToMapTest.hutool:hutool·p0.00        sample             0.500            us/op
// ToMapTest.hutool:hutool·p0.50        sample             0.600            us/op
// ToMapTest.hutool:hutool·p0.90        sample             0.900            us/op
// ToMapTest.hutool:hutool·p0.95        sample             1.200            us/op
// ToMapTest.hutool:hutool·p0.99        sample             2.000            us/op
// ToMapTest.hutool:hutool·p0.999       sample            27.983            us/op
// ToMapTest.hutool:hutool·p0.9999      sample           110.094            us/op
// ToMapTest.hutool:hutool·p1.00        sample          3690.496            us/op
// ToMapTest.jackson                    sample  117456     0.392 ±  0.016   us/op
// ToMapTest.jackson:jackson·p0.00      sample             0.300            us/op
// ToMapTest.jackson:jackson·p0.50      sample             0.300            us/op
// ToMapTest.jackson:jackson·p0.90      sample             0.500            us/op
// ToMapTest.jackson:jackson·p0.95      sample             0.500            us/op
// ToMapTest.jackson:jackson·p0.99      sample             0.700            us/op
// ToMapTest.jackson:jackson·p0.999     sample            10.688            us/op
// ToMapTest.jackson:jackson·p0.9999    sample            80.723            us/op
// ToMapTest.jackson:jackson·p1.00      sample           244.480            us/op
// ToMapTest.fastjson                       ss       5    50.240 ± 22.308   us/op
// ToMapTest.gson                           ss       5    33.320 ± 29.279   us/op
// ToMapTest.hutool                         ss       5    98.680 ± 65.044   us/op
// ToMapTest.jackson                        ss       5    54.980 ± 53.912   us/op
