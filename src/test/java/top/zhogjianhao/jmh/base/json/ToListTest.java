package top.zhogjianhao.jmh.base.json;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.CollectionUtils;
import top.zhogjianhao.junit.BeanUtilsTest;

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

  @Benchmark
  public List<BeanUtilsTest.TestBean> jackson() throws JsonProcessingException {
    return new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> fastjson() {
    return JSON.parseArray(JSON_STR, BeanUtilsTest.TestBean.class);
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> gson() {
    return new ArrayList<>(Arrays.asList(new Gson().fromJson(JSON_STR, BeanUtilsTest.TestBean[].class)));
  }

  @Benchmark
  public List<BeanUtilsTest.TestBean> hutool() {
    return JSONUtil.toList(JSON_STR, BeanUtilsTest.TestBean.class);
  }
}

// Benchmark                               Mode     Cnt      Score     Error   Units
// ToListTest.fastjson                    thrpt       5      7.297 ±   0.133  ops/us
// ToListTest.gson                        thrpt       5      0.134 ±   0.010  ops/us
// ToListTest.hutool                      thrpt       5      0.530 ±   0.011  ops/us
// ToListTest.jackson                     thrpt       5      0.114 ±   0.005  ops/us
// ToListTest.fastjson                     avgt       5      0.133 ±   0.005   us/op
// ToListTest.gson                         avgt       5      8.064 ±   1.074   us/op
// ToListTest.hutool                       avgt       5      2.354 ±   0.707   us/op
// ToListTest.jackson                      avgt       5     11.029 ±  14.734   us/op
// ToListTest.fastjson                   sample  132038      0.294 ±   0.285   us/op
// ToListTest.fastjson:fastjson·p0.00    sample              0.100             us/op
// ToListTest.fastjson:fastjson·p0.50    sample              0.200             us/op
// ToListTest.fastjson:fastjson·p0.90    sample              0.200             us/op
// ToListTest.fastjson:fastjson·p0.95    sample              0.300             us/op
// ToListTest.fastjson:fastjson·p0.99    sample              0.300             us/op
// ToListTest.fastjson:fastjson·p0.999   sample              1.400             us/op
// ToListTest.fastjson:fastjson·p0.9999  sample             61.245             us/op
// ToListTest.fastjson:fastjson·p1.00    sample          11337.728             us/op
// ToListTest.gson                       sample  154598      8.236 ±   0.202   us/op
// ToListTest.gson:gson·p0.00            sample              6.896             us/op
// ToListTest.gson:gson·p0.50            sample              7.496             us/op
// ToListTest.gson:gson·p0.90            sample              8.592             us/op
// ToListTest.gson:gson·p0.95            sample             10.496             us/op
// ToListTest.gson:gson·p0.99            sample             16.672             us/op
// ToListTest.gson:gson·p0.999           sample             76.109             us/op
// ToListTest.gson:gson·p0.9999          sample            903.354             us/op
// ToListTest.gson:gson·p1.00            sample           7716.864             us/op
// ToListTest.hutool                     sample  152957      2.248 ±   0.059   us/op
// ToListTest.hutool:hutool·p0.00        sample              1.800             us/op
// ToListTest.hutool:hutool·p0.50        sample              2.000             us/op
// ToListTest.hutool:hutool·p0.90        sample              2.500             us/op
// ToListTest.hutool:hutool·p0.95        sample              2.800             us/op
// ToListTest.hutool:hutool·p0.99        sample              4.096             us/op
// ToListTest.hutool:hutool·p0.999       sample             24.908             us/op
// ToListTest.hutool:hutool·p0.9999      sample            144.052             us/op
// ToListTest.hutool:hutool·p1.00        sample           1298.432             us/op
// ToListTest.jackson                    sample  133984      9.643 ±   0.120   us/op
// ToListTest.jackson:jackson·p0.00      sample              8.000             us/op
// ToListTest.jackson:jackson·p0.50      sample              8.688             us/op
// ToListTest.jackson:jackson·p0.90      sample             10.400             us/op
// ToListTest.jackson:jackson·p0.95      sample             12.896             us/op
// ToListTest.jackson:jackson·p0.99      sample             22.400             us/op
// ToListTest.jackson:jackson·p0.999     sample             84.106             us/op
// ToListTest.jackson:jackson·p0.9999    sample            966.463             us/op
// ToListTest.jackson:jackson·p1.00      sample           1466.368             us/op
// ToListTest.fastjson                       ss       5     96.020 ± 460.501   us/op
// ToListTest.gson                           ss       5    377.620 ± 680.412   us/op
// ToListTest.hutool                         ss       5    279.420 ± 549.296   us/op
// ToListTest.jackson                        ss       5    846.320 ± 467.520   us/op
