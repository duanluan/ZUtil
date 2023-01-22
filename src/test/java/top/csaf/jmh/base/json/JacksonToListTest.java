package top.csaf.jmh.base.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Jackson JSON 字符串转 List 的性能测试
 * <p>
 * 参考：https://blog.csdn.net/asdfgh0077/article/details/103888370
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class JacksonToListTest {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // 结果是否相等
    JacksonToListTest test = new JacksonToListTest();
    System.out.println(CollectionUtils.isAllEqualsSameIndex(false, null, test.test1(), test.test2(), test.test3(), test.test4(), test.test5(), test.test6()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{JacksonToListTest.class.getName()});
  }

  @NoArgsConstructor
  @Data
  private static class MyClass {
    private String id;
    private String stuff;
  }

  private static final String JSON_STR = "[{\"id\": \"junk\",\"stuff\": \"things\"},{\"id\": \"spam\",\"stuff\": \"eggs\"}]";

  @Benchmark
  public List<MyClass> test1() throws JsonProcessingException {
    return new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(JSON_STR, MyClass[].class)));
  }

  @Benchmark
  public List<MyClass> test2() throws IOException {
    return new ObjectMapper().readValue(new JsonFactory().createParser(JSON_STR), new TypeReference<List<MyClass>>() {
    });
  }

  @Benchmark
  public List<MyClass> test3() throws IOException, ClassNotFoundException {
    return new ArrayList<>(Arrays.asList(new ObjectMapper().readValue(JSON_STR, (Class<MyClass[]>) Class.forName("[L" + MyClass.class.getName() + ";"))));
  }

  @Benchmark
  public List<MyClass> test4() throws JsonProcessingException {
    return new ObjectMapper().reader().forType(new TypeReference<List<MyClass>>() {
    }).readValue(JSON_STR);
  }

  @Benchmark
  public List<MyClass> test5() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    return mapper.readValue(JSON_STR, mapper.getTypeFactory().constructCollectionType(List.class, MyClass.class));
  }

  @Benchmark
  public List<MyClass> test6() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(JSON_STR, mapper.getTypeFactory().constructCollectionType(List.class, MyClass.class));
  }
}

// Benchmark                                Mode     Cnt     Score     Error   Units
// JacksonToListTest.test1                 thrpt       5     0.244 ±   0.003  ops/us
// JacksonToListTest.test2                 thrpt       5     0.171 ±   0.003  ops/us
// JacksonToListTest.test3                 thrpt       5     0.186 ±   0.003  ops/us
// JacksonToListTest.test4                 thrpt       5     0.203 ±   0.005  ops/us
// JacksonToListTest.test5                 thrpt       5     0.206 ±   0.002  ops/us
// JacksonToListTest.test6                 thrpt       5     0.208 ±   0.003  ops/us
// JacksonToListTest.test1                  avgt       5     4.142 ±   0.113   us/op
// JacksonToListTest.test2                  avgt       5     6.011 ±   0.126   us/op
// JacksonToListTest.test3                  avgt       5     5.434 ±   0.119   us/op
// JacksonToListTest.test4                  avgt       5     4.787 ±   0.112   us/op
// JacksonToListTest.test5                  avgt       5     4.702 ±   0.044   us/op
// JacksonToListTest.test6                  avgt       5     4.752 ±   0.131   us/op
// JacksonToListTest.test1                sample  147859     4.434 ±   0.066   us/op
// JacksonToListTest.test1:test1·p0.00    sample             3.700             us/op
// JacksonToListTest.test1:test1·p0.50    sample             4.096             us/op
// JacksonToListTest.test1:test1·p0.90    sample             4.496             us/op
// JacksonToListTest.test1:test1·p0.95    sample             4.800             us/op
// JacksonToListTest.test1:test1·p0.99    sample             9.088             us/op
// JacksonToListTest.test1:test1·p0.999   sample            36.178             us/op
// JacksonToListTest.test1:test1·p0.9999  sample           117.047             us/op
// JacksonToListTest.test1:test1·p1.00    sample          1595.392             us/op
// JacksonToListTest.test2                sample  105516     6.293 ±   0.156   us/op
// JacksonToListTest.test2:test2·p0.00    sample             5.000             us/op
// JacksonToListTest.test2:test2·p0.50    sample             5.696             us/op
// JacksonToListTest.test2:test2·p0.90    sample             6.200             us/op
// JacksonToListTest.test2:test2·p0.95    sample             7.000             us/op
// JacksonToListTest.test2:test2·p0.99    sample            12.800             us/op
// JacksonToListTest.test2:test2·p0.999   sample            74.417             us/op
// JacksonToListTest.test2:test2·p0.9999  sample          1152.883             us/op
// JacksonToListTest.test2:test2·p1.00    sample          1544.192             us/op
// JacksonToListTest.test3                sample  115363     5.658 ±   0.128   us/op
// JacksonToListTest.test3:test3·p0.00    sample             4.800             us/op
// JacksonToListTest.test3:test3·p0.50    sample             5.200             us/op
// JacksonToListTest.test3:test3·p0.90    sample             5.600             us/op
// JacksonToListTest.test3:test3·p0.95    sample             6.000             us/op
// JacksonToListTest.test3:test3·p0.99    sample            11.600             us/op
// JacksonToListTest.test3:test3·p0.999   sample            52.794             us/op
// JacksonToListTest.test3:test3·p0.9999  sample           509.386             us/op
// JacksonToListTest.test3:test3·p1.00    sample          1611.776             us/op
// JacksonToListTest.test4                sample  130667     4.969 ±   0.075   us/op
// JacksonToListTest.test4:test4·p0.00    sample             4.200             us/op
// JacksonToListTest.test4:test4·p0.50    sample             4.600             us/op
// JacksonToListTest.test4:test4·p0.90    sample             5.000             us/op
// JacksonToListTest.test4:test4·p0.95    sample             5.400             us/op
// JacksonToListTest.test4:test4·p0.99    sample            10.192             us/op
// JacksonToListTest.test4:test4·p0.999   sample            44.010             us/op
// JacksonToListTest.test4:test4·p0.9999  sample           119.398             us/op
// JacksonToListTest.test4:test4·p1.00    sample          1699.840             us/op
// JacksonToListTest.test5                sample  130825     4.981 ±   0.053   us/op
// JacksonToListTest.test5:test5·p0.00    sample             4.200             us/op
// JacksonToListTest.test5:test5·p0.50    sample             4.600             us/op
// JacksonToListTest.test5:test5·p0.90    sample             5.000             us/op
// JacksonToListTest.test5:test5·p0.95    sample             5.600             us/op
// JacksonToListTest.test5:test5·p0.99    sample            10.400             us/op
// JacksonToListTest.test5:test5·p0.999   sample            50.721             us/op
// JacksonToListTest.test5:test5·p0.9999  sample           123.276             us/op
// JacksonToListTest.test5:test5·p1.00    sample          1427.456             us/op
// JacksonToListTest.test6                sample  128556     5.063 ±   0.084   us/op
// JacksonToListTest.test6:test6·p0.00    sample             4.296             us/op
// JacksonToListTest.test6:test6·p0.50    sample             4.696             us/op
// JacksonToListTest.test6:test6·p0.90    sample             5.000             us/op
// JacksonToListTest.test6:test6·p0.95    sample             5.400             us/op
// JacksonToListTest.test6:test6·p0.99    sample            10.400             us/op
// JacksonToListTest.test6:test6·p0.999   sample            39.324             us/op
// JacksonToListTest.test6:test6·p0.9999  sample           116.679             us/op
// JacksonToListTest.test6:test6·p1.00    sample          1636.352             us/op
// JacksonToListTest.test1                    ss       5   615.540 ± 917.279   us/op
// JacksonToListTest.test2                    ss       5   694.520 ± 784.805   us/op
// JacksonToListTest.test3                    ss       5   630.360 ± 904.465   us/op
// JacksonToListTest.test4                    ss       5   654.500 ± 896.107   us/op
// JacksonToListTest.test5                    ss       5   675.360 ± 971.519   us/op
// JacksonToListTest.test6                    ss       5   736.120 ± 880.625   us/op
