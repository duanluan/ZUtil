package top.zhogjianhao.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.BeanUtilsTest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * 获取属性的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class GetPropertyTest {

  public static void main(String[] args) {
    // 结果是否相等
    GetPropertyTest test = new GetPropertyTest();
    try {
      System.out.println(test.spring().equals(test.apache()));
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      System.out.println(false);
      ;
    }
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{GetPropertyTest.class.getName()});
  }

  private static final BeanUtilsTest.TestBean testBean;

  static {
    testBean = new BeanUtilsTest.TestBean();
    testBean.setName("张三");
  }

  @Benchmark
  public String apache() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    return org.apache.commons.beanutils.BeanUtils.getProperty(testBean, "name");
  }

  @Benchmark
  public String spring() throws InvocationTargetException, IllegalAccessException {
    PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(testBean.getClass(), "name");
    return propertyDescriptor.getReadMethod().invoke(testBean, null).toString();
  }
}

// Benchmark                                Mode     Cnt    Score    Error   Units
// GetPropertyTest.apache                  thrpt       5    8.211 ±  0.137  ops/us
// GetPropertyTest.spring                  thrpt       5   59.979 ±  1.280  ops/us
// GetPropertyTest.apache                   avgt       5    0.114 ±  0.003   us/op
// GetPropertyTest.spring                   avgt       5    0.017 ±  0.001   us/op
// GetPropertyTest.apache                 sample  153056    0.230 ±  0.004   us/op
// GetPropertyTest.apache:apache·p0.00    sample            0.100            us/op
// GetPropertyTest.apache:apache·p0.50    sample            0.200            us/op
// GetPropertyTest.apache:apache·p0.90    sample            0.300            us/op
// GetPropertyTest.apache:apache·p0.95    sample            0.300            us/op
// GetPropertyTest.apache:apache·p0.99    sample            0.500            us/op
// GetPropertyTest.apache:apache·p0.999   sample            2.500            us/op
// GetPropertyTest.apache:apache·p0.9999  sample           10.766            us/op
// GetPropertyTest.apache:apache·p1.00    sample          137.984            us/op
// GetPropertyTest.spring                 sample  108621    0.132 ±  0.008   us/op
// GetPropertyTest.spring:spring·p0.00    sample              ≈ 0            us/op
// GetPropertyTest.spring:spring·p0.50    sample            0.100            us/op
// GetPropertyTest.spring:spring·p0.90    sample            0.200            us/op
// GetPropertyTest.spring:spring·p0.95    sample            0.200            us/op
// GetPropertyTest.spring:spring·p0.99    sample            0.200            us/op
// GetPropertyTest.spring:spring·p0.999   sample            2.500            us/op
// GetPropertyTest.spring:spring·p0.9999  sample           28.123            us/op
// GetPropertyTest.spring:spring·p1.00    sample          179.968            us/op
// GetPropertyTest.apache                     ss       5   37.600 ± 13.084   us/op
// GetPropertyTest.spring                     ss       5    8.180 ±  6.733   us/op
