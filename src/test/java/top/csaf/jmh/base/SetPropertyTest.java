package top.csaf.jmh.base;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.junit.BeanUtilsTest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * 设置属性的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class SetPropertyTest {

  public static void main(String[] args) {
    // 结果是否相等
    SetPropertyTest test = new SetPropertyTest();
    try {
      System.out.println(test.spring().equals(test.apache()));
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      System.out.println(false);
      ;
    }
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{SetPropertyTest.class.getName()});
  }

  private static final BeanUtilsTest.TestBean testBean;

  static {
    testBean = new BeanUtilsTest.TestBean();
    testBean.setName("张三");
  }

  @Benchmark
  public BeanUtilsTest.TestBean apache() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    org.apache.commons.beanutils.BeanUtils.setProperty(testBean, "name", "张三");
    return testBean;
  }

  @Benchmark
  public BeanUtilsTest.TestBean spring() throws InvocationTargetException, IllegalAccessException {
    PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(testBean.getClass(), "name");
    propertyDescriptor.getWriteMethod().invoke(testBean, "张三");
    return testBean;
  }
}

// Benchmark                                Mode     Cnt    Score    Error   Units
// SetPropertyTest.apache                  thrpt       5    4.265 ±  0.042  ops/us
// SetPropertyTest.spring                  thrpt       5   57.092 ±  2.515  ops/us
// SetPropertyTest.apache                   avgt       5    0.219 ±  0.004   us/op
// SetPropertyTest.spring                   avgt       5    0.018 ±  0.002   us/op
// SetPropertyTest.apache                 sample  171494    0.344 ±  0.014   us/op
// SetPropertyTest.apache:apache·p0.00    sample            0.200            us/op
// SetPropertyTest.apache:apache·p0.50    sample            0.300            us/op
// SetPropertyTest.apache:apache·p0.90    sample            0.400            us/op
// SetPropertyTest.apache:apache·p0.95    sample            0.400            us/op
// SetPropertyTest.apache:apache·p0.99    sample            0.700            us/op
// SetPropertyTest.apache:apache·p0.999   sample            3.500            us/op
// SetPropertyTest.apache:apache·p0.9999  sample           19.464            us/op
// SetPropertyTest.apache:apache·p1.00    sample          694.272            us/op
// SetPropertyTest.spring                 sample  115216    0.149 ±  0.006   us/op
// SetPropertyTest.spring:spring·p0.00    sample              ≈ 0            us/op
// SetPropertyTest.spring:spring·p0.50    sample            0.100            us/op
// SetPropertyTest.spring:spring·p0.90    sample            0.200            us/op
// SetPropertyTest.spring:spring·p0.95    sample            0.300            us/op
// SetPropertyTest.spring:spring·p0.99    sample            0.400            us/op
// SetPropertyTest.spring:spring·p0.999   sample            2.200            us/op
// SetPropertyTest.spring:spring·p0.9999  sample           27.178            us/op
// SetPropertyTest.spring:spring·p1.00    sample          114.176            us/op
// SetPropertyTest.apache                     ss       5   44.280 ± 22.903   us/op
// SetPropertyTest.spring                     ss       5   11.340 ± 11.195   us/op
