package top.csaf.jmh.base.json;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.CollectionUtils;
import top.csaf.junit.BeanUtilsTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JSON 字符串转 List 性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToListTest {

  public static void main(String[] args) throws JsonProcessingException {
    // 结果是否相等
    ToListTest test = new ToListTest();
    System.out.println(CollectionUtils.isAllEqualsSameIndex(false, null, test.jackson(), test.fastjson(), test.gson()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToListTest.class.getName()});
  }

  private static final String JSON_STR = "[" + new ToJsonTest().fastjson() + "]";

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Benchmark
  public List<BeanUtilsTest.TestBean> jackson() throws JsonProcessingException {
    return new ArrayList<>(Arrays.asList(objectMapper.readValue(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> fastjson() {
    return JSON.parseArray(JSON_STR, BeanUtilsTest.TestBean.class);
  }

  private final static Gson gson = new Gson();

  @Benchmark
  public List<BeanUtilsTest.TestBean> gson() {
    return new ArrayList<>(Arrays.asList(gson.fromJson(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> hutool() {
    return JSONUtil.toList(JSON_STR, BeanUtilsTest.TestBean.class);
  }
}

// Benchmark                               Mode     Cnt     Score     Error   Units
// ToListTest.fastjson                    thrpt       5     6.642 ±   0.588  ops/us
// ToListTest.gson                        thrpt       5     1.490 ±   0.047  ops/us
// ToListTest.hutool                      thrpt       5     0.539 ±   0.037  ops/us
// ToListTest.jackson                     thrpt       5     2.603 ±   0.055  ops/us
// ToListTest.fastjson                     avgt       5     0.137 ±   0.003   us/op
// ToListTest.gson                         avgt       5     0.665 ±   0.060   us/op
// ToListTest.hutool                       avgt       5     1.871 ±   0.088   us/op
// ToListTest.jackson                      avgt       5     0.345 ±   0.023   us/op
// ToListTest.fastjson                   sample  141452     0.176 ±   0.005   us/op
// ToListTest.fastjson:fastjson·p0.00    sample             0.100             us/op
// ToListTest.fastjson:fastjson·p0.50    sample             0.200             us/op
// ToListTest.fastjson:fastjson·p0.90    sample             0.200             us/op
// ToListTest.fastjson:fastjson·p0.95    sample             0.200             us/op
// ToListTest.fastjson:fastjson·p0.99    sample             0.300             us/op
// ToListTest.fastjson:fastjson·p0.999   sample             0.700             us/op
// ToListTest.fastjson:fastjson·p0.9999  sample            18.660             us/op
// ToListTest.fastjson:fastjson·p1.00    sample           120.704             us/op
// ToListTest.gson                       sample  117870     0.720 ±   0.011   us/op
// ToListTest.gson:gson·p0.00            sample             0.500             us/op
// ToListTest.gson:gson·p0.50            sample             0.600             us/op
// ToListTest.gson:gson·p0.90            sample             0.900             us/op
// ToListTest.gson:gson·p0.95            sample             1.000             us/op
// ToListTest.gson:gson·p0.99            sample             1.300             us/op
// ToListTest.gson:gson·p0.999           sample             8.204             us/op
// ToListTest.gson:gson·p0.9999          sample            52.370             us/op
// ToListTest.gson:gson·p1.00            sample           142.080             us/op
// ToListTest.hutool                     sample  171556     1.970 ±   0.047   us/op
// ToListTest.hutool:hutool·p0.00        sample             1.600             us/op
// ToListTest.hutool:hutool·p0.50        sample             1.900             us/op
// ToListTest.hutool:hutool·p0.90        sample             2.100             us/op
// ToListTest.hutool:hutool·p0.95        sample             2.200             us/op
// ToListTest.hutool:hutool·p0.99        sample             4.096             us/op
// ToListTest.hutool:hutool·p0.999       sample            11.538             us/op
// ToListTest.hutool:hutool·p0.9999      sample            51.255             us/op
// ToListTest.hutool:hutool·p1.00        sample          1576.960             us/op
// ToListTest.jackson                    sample  110739     0.391 ±   0.009   us/op
// ToListTest.jackson:jackson·p0.00      sample             0.300             us/op
// ToListTest.jackson:jackson·p0.50      sample             0.400             us/op
// ToListTest.jackson:jackson·p0.90      sample             0.400             us/op
// ToListTest.jackson:jackson·p0.95      sample             0.400             us/op
// ToListTest.jackson:jackson·p0.99      sample             0.600             us/op
// ToListTest.jackson:jackson·p0.999     sample             1.400             us/op
// ToListTest.jackson:jackson·p0.9999    sample            42.340             us/op
// ToListTest.jackson:jackson·p1.00      sample           144.384             us/op
// ToListTest.fastjson                       ss       5    95.600 ± 446.742   us/op
// ToListTest.gson                           ss       5   152.580 ± 568.943   us/op
// ToListTest.hutool                         ss       5   432.440 ± 692.130   us/op
// ToListTest.jackson                        ss       5   202.800 ± 641.450   us/op
