package top.zhogjianhao.jmh.base.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.BeanUtilsTest;
import top.zhogjianhao.CollectionUtils;

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
    System.out.println(CollectionUtils.isAllEqualsSameIndex(false, null, test.jacksonToList(), test.fastjsonToList(), test.gsonToList()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToListTest.class.getName()});
  }

  private static final String JSON_STR = "[" + new ToJsonTest().fastjsonToJson() + "]";

  @Benchmark
  public List<BeanUtilsTest.TestBean> jacksonToList() throws JsonProcessingException {
    return new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> fastjsonToList() {
    return JSON.parseArray(JSON_STR, BeanUtilsTest.TestBean.class);
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> gsonToList() {
    return new ArrayList<>(Arrays.asList(new Gson().fromJson(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }
}

// Benchmark                                           Mode     Cnt     Score     Error   Units
// ToListTest.fastjsonToList                          thrpt       5     4.770 ±   0.130  ops/us
// ToListTest.gsonToList                              thrpt       5     0.143 ±   0.031  ops/us
// ToListTest.jacksonToList                           thrpt       5     0.117 ±   0.002  ops/us
// ToListTest.fastjsonToList                           avgt       5     0.218 ±   0.009   us/op
// ToListTest.gsonToList                               avgt       5     7.523 ±   2.505   us/op
// ToListTest.jacksonToList                            avgt       5     8.493 ±   0.222   us/op
// ToListTest.fastjsonToList                         sample  175166     0.264 ±   0.023   us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.00    sample             0.200             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.50    sample             0.200             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.90    sample             0.300             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.95    sample             0.300             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.99    sample             0.400             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.999   sample             1.883             us/op
// ToListTest.fastjsonToList:fastjsonToList·p0.9999  sample             8.693             us/op
// ToListTest.fastjsonToList:fastjsonToList·p1.00    sample           942.080             us/op
// ToListTest.gsonToList                             sample  104683     7.447 ±   0.147   us/op
// ToListTest.gsonToList:gsonToList·p0.00            sample             6.096             us/op
// ToListTest.gsonToList:gsonToList·p0.50            sample             6.600             us/op
// ToListTest.gsonToList:gsonToList·p0.90            sample             7.696             us/op
// ToListTest.gsonToList:gsonToList·p0.95            sample             8.592             us/op
// ToListTest.gsonToList:gsonToList·p0.99            sample            16.896             us/op
// ToListTest.gsonToList:gsonToList·p0.999           sample            77.184             us/op
// ToListTest.gsonToList:gsonToList·p0.9999          sample          1115.406             us/op
// ToListTest.gsonToList:gsonToList·p1.00            sample          1632.256             us/op
// ToListTest.jacksonToList                          sample  149717     8.538 ±   0.081   us/op
// ToListTest.jacksonToList:jacksonToList·p0.00      sample             7.200             us/op
// ToListTest.jacksonToList:jacksonToList·p0.50      sample             7.896             us/op
// ToListTest.jacksonToList:jacksonToList·p0.90      sample             8.688             us/op
// ToListTest.jacksonToList:jacksonToList·p0.95      sample            10.592             us/op
// ToListTest.jacksonToList:jacksonToList·p0.99      sample            17.984             us/op
// ToListTest.jacksonToList:jacksonToList·p0.999     sample            66.100             us/op
// ToListTest.jacksonToList:jacksonToList·p0.9999    sample           215.141             us/op
// ToListTest.jacksonToList:jacksonToList·p1.00      sample          1558.528             us/op
// ToListTest.fastjsonToList                             ss       5    42.040 ±  27.868   us/op
// ToListTest.gsonToList                                 ss       5   363.360 ± 438.892   us/op
// ToListTest.jacksonToList                              ss       5   787.380 ± 338.803   us/op
