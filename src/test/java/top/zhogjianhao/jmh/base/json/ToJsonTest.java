package top.zhogjianhao.jmh.base.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
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
    System.out.println(test.jacksonToJson().equals(test.fastjsonToJson()) && test.fastjsonToJson().equals(test.gsonToJson()));
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
  public String jacksonToJson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    // 忽略为 null 的属性
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper.writeValueAsString(testBean);
  }

  @Benchmark
  public String fastjsonToJson() {
    return JSON.toJSONString(testBean);
  }

  @Benchmark
  public String gsonToJson() {
    return new Gson().toJson(testBean);
  }
}

// Benchmark                                               Mode     Cnt     Score      Error   Units
// JsonTest.fastjsonToJson                             thrpt       5     7.430 ±    0.267  ops/us
// JsonTest.gsonToJson                                 thrpt       5     0.166 ±    0.041  ops/us
// JsonTest.jacksonToJson                              thrpt       5     0.136 ±    0.007  ops/us
// JsonTest.fastjsonToJson                              avgt       5     0.136 ±    0.003   us/op
// JsonTest.gsonToJson                                  avgt       5     5.926 ±    0.140   us/op
// JsonTest.jacksonToJson                               avgt       5     7.282 ±    0.581   us/op
// JsonTest.fastjsonToJson                            sample  141533     0.182 ±    0.013   us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.00    sample             0.100              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.50    sample             0.200              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.90    sample             0.200              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.95    sample             0.200              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.99    sample             0.400              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.999   sample             3.300              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p0.9999  sample            19.979              us/op
// JsonTest.fastjsonToJson:fastjsonToJson·p1.00    sample           472.064              us/op
// JsonTest.gsonToJson                                sample  121447     7.528 ±    0.117   us/op
// JsonTest.gsonToJson:gsonToJson·p0.00            sample             5.296              us/op
// JsonTest.gsonToJson:gsonToJson·p0.50            sample             6.296              us/op
// JsonTest.gsonToJson:gsonToJson·p0.90            sample             8.992              us/op
// JsonTest.gsonToJson:gsonToJson·p0.95            sample             9.792              us/op
// JsonTest.gsonToJson:gsonToJson·p0.99            sample            20.288              us/op
// JsonTest.gsonToJson:gsonToJson·p0.999           sample           148.992              us/op
// JsonTest.gsonToJson:gsonToJson·p0.9999          sample           584.366              us/op
// JsonTest.gsonToJson:gsonToJson·p1.00            sample          1376.256              us/op
// JsonTest.jacksonToJson                             sample  169513     7.553 ±    0.075   us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.00      sample             6.400              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.50      sample             6.896              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.90      sample             7.600              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.95      sample             9.392              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.99      sample            16.192              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.999     sample            89.337              us/op
// JsonTest.jacksonToJson:jacksonToJson·p0.9999    sample           233.484              us/op
// JsonTest.jacksonToJson:jacksonToJson·p1.00      sample          1554.432              us/op
// JsonTest.fastjsonToJson                                ss       5    42.640 ±   28.330   us/op
// JsonTest.gsonToJson                                    ss       5   301.860 ±  315.595   us/op
// JsonTest.jacksonToJson                                 ss       5   962.260 ± 1079.058   us/op
