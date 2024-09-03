package top.csaf.jmh.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.coll.CollUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Gson JSON 字符串转 List 的性能测试
 * <p>
 * 参考：https://www.jianshu.com/p/701ae370f959
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class GsonToListTest {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // 结果是否相等
    GsonToListTest test = new GsonToListTest();
    System.out.println(CollUtil.isAllEqualsSameIndex(false, null, test.test1(), test.test2(), test.test3()));
    ;
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{GsonToListTest.class.getName()});
  }

  @NoArgsConstructor
  @Data
  private static class MyClass {
    private String id;
    private String stuff;
  }

  private static final String JSON_STR = "[{\"id\": \"junk\",\"stuff\": \"things\"},{\"id\": \"spam\",\"stuff\": \"eggs\"}]";

  @Benchmark
  public List<MyClass> test1() {
    return new ArrayList<>(Arrays.asList(new Gson().fromJson(JSON_STR, MyClass[].class)));
  }

  @Benchmark
  public List<MyClass> test2() {
    return new Gson().fromJson(JSON_STR, new TypeToken<List<MyClass>>() {
    }.getType());
  }

  private static class ParameterizedTypeImpl implements ParameterizedType {
    Class clazz;

    public ParameterizedTypeImpl(Class clz) {
      clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
      return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
      return List.class;
    }

    @Override
    public Type getOwnerType() {
      return null;
    }
  }

  @Benchmark
  public List<MyClass> test3() {
    return new Gson().fromJson(JSON_STR, new ParameterizedTypeImpl(MyClass.class));
  }
}

// Benchmark                             Mode     Cnt     Score     Error   Units
// GsonToListTest.test1                 thrpt       5     0.247 ±   0.012  ops/us
// GsonToListTest.test2                 thrpt       5     0.243 ±   0.027  ops/us
// GsonToListTest.test3                 thrpt       5     0.245 ±   0.009  ops/us
// GsonToListTest.test1                  avgt       5     3.986 ±   0.220   us/op
// GsonToListTest.test2                  avgt       5     4.072 ±   0.058   us/op
// GsonToListTest.test3                  avgt       5     4.074 ±   0.099   us/op
// GsonToListTest.test1                sample  158342     4.055 ±   0.095   us/op
// GsonToListTest.test1:test1·p0.00    sample             3.500             us/op
// GsonToListTest.test1:test1·p0.50    sample             3.800             us/op
// GsonToListTest.test1:test1·p0.90    sample             4.096             us/op
// GsonToListTest.test1:test1·p0.95    sample             4.296             us/op
// GsonToListTest.test1:test1·p0.99    sample             6.600             us/op
// GsonToListTest.test1:test1·p0.999   sample            14.063             us/op
// GsonToListTest.test1:test1·p0.9999  sample           107.178             us/op
// GsonToListTest.test1:test1·p1.00    sample          1742.848             us/op
// GsonToListTest.test2                sample  146846     4.446 ±   0.093   us/op
// GsonToListTest.test2:test2·p0.00    sample             3.700             us/op
// GsonToListTest.test2:test2·p0.50    sample             4.096             us/op
// GsonToListTest.test2:test2·p0.90    sample             4.496             us/op
// GsonToListTest.test2:test2·p0.95    sample             5.000             us/op
// GsonToListTest.test2:test2·p0.99    sample             8.896             us/op
// GsonToListTest.test2:test2·p0.999   sample            30.191             us/op
// GsonToListTest.test2:test2·p0.9999  sample           788.755             us/op
// GsonToListTest.test2:test2·p1.00    sample          1282.048             us/op
// GsonToListTest.test3                sample  150298     4.381 ±   0.106   us/op
// GsonToListTest.test3:test3·p0.00    sample             3.700             us/op
// GsonToListTest.test3:test3·p0.50    sample             4.096             us/op
// GsonToListTest.test3:test3·p0.90    sample             4.400             us/op
// GsonToListTest.test3:test3·p0.95    sample             4.600             us/op
// GsonToListTest.test3:test3·p0.99    sample             8.592             us/op
// GsonToListTest.test3:test3·p0.999   sample            24.704             us/op
// GsonToListTest.test3:test3·p0.9999  sample           892.690             us/op
// GsonToListTest.test3:test3·p1.00    sample          1507.328             us/op
// GsonToListTest.test1                    ss       5   269.920 ± 582.185   us/op
// GsonToListTest.test2                    ss       5   300.980 ± 425.391   us/op
// GsonToListTest.test3                    ss       5   287.920 ± 610.746   us/op
