package top.csaf.jmh.base.json;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.ObjectUtils;
import top.csaf.junit.BeanUtilsTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 转 JSON 字符串性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class ToJsonTest {

  public static void main(String[] args) throws JsonProcessingException {
    // 结果是否相等
    ToJsonTest test = new ToJsonTest();
    System.out.println(ObjectUtils.isAllEquals(false, false, test.jackson(), test.fastjson(), test.gson(), test.hutool()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToJsonTest.class.getName()});
  }

  private static final BeanUtilsTest.TestBean testBean;

  static {
    testBean = new BeanUtilsTest.TestBean();
    List<BeanUtilsTest.TestBean> beanList = new ArrayList<>();
    beanList.add(new BeanUtilsTest.TestBean("1"));
    testBean.setBeanList(beanList);
  }

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Benchmark
  public String jackson() throws JsonProcessingException {
    // // 忽略为 null 的属性
    // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper.writeValueAsString(testBean);
  }

  @Benchmark
  public String fastjson() {
    return JSON.toJSONString(testBean);
  }

  private final static Gson gson = new Gson();

  @Benchmark
  public String gson() {
    return gson.toJson(testBean);
  }

  @Benchmark
  public String hutool() {
    return JSONUtil.toJsonStr(testBean);
  }
}

// Benchmark                               Mode     Cnt     Score     Error   Units
// ToJsonTest.fastjson                    thrpt       5    11.957 ±   6.027  ops/us
// ToJsonTest.gson                        thrpt       5     3.304 ±   0.089  ops/us
// ToJsonTest.hutool                      thrpt       5     0.455 ±   0.017  ops/us
// ToJsonTest.jackson                     thrpt       5     4.121 ±   0.130  ops/us
// ToJsonTest.fastjson                     avgt       5     0.064 ±   0.002   us/op
// ToJsonTest.gson                         avgt       5     0.301 ±   0.020   us/op
// ToJsonTest.hutool                       avgt       5     1.879 ±   0.052   us/op
// ToJsonTest.jackson                      avgt       5     0.247 ±   0.004   us/op
// ToJsonTest.fastjson                   sample  144968     0.099 ±   0.003   us/op
// ToJsonTest.fastjson:fastjson·p0.00    sample               ≈ 0             us/op
// ToJsonTest.fastjson:fastjson·p0.50    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.90    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.95    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.99    sample             0.200             us/op
// ToJsonTest.fastjson:fastjson·p0.999   sample             0.300             us/op
// ToJsonTest.fastjson:fastjson·p0.9999  sample            10.848             us/op
// ToJsonTest.fastjson:fastjson·p1.00    sample            94.208             us/op
// ToJsonTest.gson                       sample  128539     0.345 ±   0.019   us/op
// ToJsonTest.gson:gson·p0.00            sample             0.300             us/op
// ToJsonTest.gson:gson·p0.50            sample             0.300             us/op
// ToJsonTest.gson:gson·p0.90            sample             0.400             us/op
// ToJsonTest.gson:gson·p0.95            sample             0.400             us/op
// ToJsonTest.gson:gson·p0.99            sample             0.600             us/op
// ToJsonTest.gson:gson·p0.999           sample             1.700             us/op
// ToJsonTest.gson:gson·p0.9999          sample            33.139             us/op
// ToJsonTest.gson:gson·p1.00            sample           693.248             us/op
// ToJsonTest.hutool                     sample  140995     2.412 ±   0.094   us/op
// ToJsonTest.hutool:hutool·p0.00        sample             1.900             us/op
// ToJsonTest.hutool:hutool·p0.50        sample             2.100             us/op
// ToJsonTest.hutool:hutool·p0.90        sample             2.700             us/op
// ToJsonTest.hutool:hutool·p0.95        sample             2.900             us/op
// ToJsonTest.hutool:hutool·p0.99        sample             4.600             us/op
// ToJsonTest.hutool:hutool·p0.999       sample            40.002             us/op
// ToJsonTest.hutool:hutool·p0.9999      sample           253.652             us/op
// ToJsonTest.hutool:hutool·p1.00        sample          2054.144             us/op
// ToJsonTest.jackson                    sample  156329     0.286 ±   0.005   us/op
// ToJsonTest.jackson:jackson·p0.00      sample             0.200             us/op
// ToJsonTest.jackson:jackson·p0.50      sample             0.300             us/op
// ToJsonTest.jackson:jackson·p0.90      sample             0.300             us/op
// ToJsonTest.jackson:jackson·p0.95      sample             0.300             us/op
// ToJsonTest.jackson:jackson·p0.99      sample             0.600             us/op
// ToJsonTest.jackson:jackson·p0.999     sample             1.100             us/op
// ToJsonTest.jackson:jackson·p0.9999    sample            39.238             us/op
// ToJsonTest.jackson:jackson·p1.00      sample            65.664             us/op
// ToJsonTest.fastjson                       ss       5    59.080 ±  69.226   us/op
// ToJsonTest.gson                           ss       5    41.700 ±  25.267   us/op
// ToJsonTest.hutool                         ss       5   480.880 ± 988.628   us/op
// ToJsonTest.jackson                        ss       5   175.380 ± 850.865   us/op
