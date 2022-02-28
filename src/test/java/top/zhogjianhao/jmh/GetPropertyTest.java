package top.zhogjianhao.jmh;

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
