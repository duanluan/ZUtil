package top.zhogjianhao.jmh.base.json;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.ObjectUtils;
import top.zhogjianhao.junit.BeanUtilsTest;

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

  @Benchmark
  public String jackson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    // 忽略为 null 的属性
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper.writeValueAsString(testBean);
  }

  @Benchmark
  public String fastjson() {
    return JSON.toJSONString(testBean);
  }

  @Benchmark
  public String gson() {
    return new Gson().toJson(testBean);
  }

  @Benchmark
  public String hutool() {
    return JSONUtil.toJsonStr(testBean);
  }
}

// Benchmark                                           Mode     Cnt     Score     Error   Units
// ToJsonTest.fastjson                          thrpt       5    14.950 ±   2.641  ops/us
// ToJsonTest.gson                              thrpt       5     0.177 ±   0.025  ops/us
// ToJsonTest.hutool                            thrpt       5     0.568 ±   0.058  ops/us
// ToJsonTest.jackson                           thrpt       5     0.144 ±   0.005  ops/us
// ToJsonTest.fastjson                           avgt       5     0.092 ±   0.035   us/op
// ToJsonTest.gson                               avgt       5     6.987 ±   2.958   us/op
// ToJsonTest.hutool                             avgt       5     1.741 ±   0.034   us/op
// ToJsonTest.jackson                            avgt       5     7.394 ±   0.932   us/op
// ToJsonTest.fastjson                         sample  137928     0.116 ±   0.032   us/op
// ToJsonTest.fastjson:fastjson·p0.00    sample               ≈ 0             us/op
// ToJsonTest.fastjson:fastjson·p0.50    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.90    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.95    sample             0.100             us/op
// ToJsonTest.fastjson:fastjson·p0.99    sample             0.200             us/op
// ToJsonTest.fastjson:fastjson·p0.999   sample             0.400             us/op
// ToJsonTest.fastjson:fastjson·p0.9999  sample            23.036             us/op
// ToJsonTest.fastjson:fastjson·p1.00    sample          1343.488             us/op
// ToJsonTest.gson                             sample  109988     5.944 ±   0.145   us/op
// ToJsonTest.gson:gson·p0.00            sample             4.896             us/op
// ToJsonTest.gson:gson·p0.50            sample             5.696             us/op
// ToJsonTest.gson:gson·p0.90            sample             5.896             us/op
// ToJsonTest.gson:gson·p0.95            sample             6.000             us/op
// ToJsonTest.gson:gson·p0.99            sample            11.003             us/op
// ToJsonTest.gson:gson·p0.999           sample            49.792             us/op
// ToJsonTest.gson:gson·p0.9999          sample           984.311             us/op
// ToJsonTest.gson:gson·p1.00            sample          1667.072             us/op
// ToJsonTest.hutool                           sample  185566     1.739 ±   0.041   us/op
// ToJsonTest.hutool:hutool·p0.00        sample             1.500             us/op
// ToJsonTest.hutool:hutool·p0.50        sample             1.700             us/op
// ToJsonTest.hutool:hutool·p0.90        sample             1.800             us/op
// ToJsonTest.hutool:hutool·p0.95        sample             1.900             us/op
// ToJsonTest.hutool:hutool·p0.99        sample             2.500             us/op
// ToJsonTest.hutool:hutool·p0.999       sample             8.938             us/op
// ToJsonTest.hutool:hutool·p0.9999      sample            49.280             us/op
// ToJsonTest.hutool:hutool·p1.00        sample          1851.392             us/op
// ToJsonTest.jackson                          sample  170274     7.505 ±   0.081   us/op
// ToJsonTest.jackson:jackson·p0.00      sample             6.496             us/op
// ToJsonTest.jackson:jackson·p0.50      sample             7.000             us/op
// ToJsonTest.jackson:jackson·p0.90      sample             7.600             us/op
// ToJsonTest.jackson:jackson·p0.95      sample             9.200             us/op
// ToJsonTest.jackson:jackson·p0.99      sample            15.488             us/op
// ToJsonTest.jackson:jackson·p0.999     sample            52.765             us/op
// ToJsonTest.jackson:jackson·p0.9999    sample           211.278             us/op
// ToJsonTest.jackson:jackson·p1.00      sample          1644.544             us/op
// ToJsonTest.fastjson                             ss       5    21.320 ±  20.476   us/op
// ToJsonTest.gson                                 ss       5   280.760 ± 183.441   us/op
// ToJsonTest.hutool                               ss       5   295.500 ± 616.499   us/op
// ToJsonTest.jackson                              ss       5   663.360 ± 539.039   us/op
