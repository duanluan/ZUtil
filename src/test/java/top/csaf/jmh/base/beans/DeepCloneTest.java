package top.csaf.jmh.base.beans;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.junit.jupiter.api.Test;
import org.nustaq.serialization.FSTConfiguration;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import top.csaf.ObjectUtils;
import top.csaf.tree.TreeNode;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 深克隆的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class DeepCloneTest {

  @Test
  void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{DeepCloneTest.class.getName()});
  }

  TreeNode testBean;

  {
    testBean = new TreeNode();
    testBean.setId(1);
    testBean.setName("1");
    TreeNode treeNode2 = new TreeNode();
    treeNode2.setId(2);
    treeNode2.setName("1.1");
    TreeNode treeNode3 = new TreeNode();
    treeNode3.setId(3);
    treeNode3.setName("1.1.1");
    treeNode2.setChildren(Collections.singletonList(treeNode3));
    testBean.setChildren(Collections.singletonList(treeNode2));
  }

  public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
    DeepCloneTest test = new DeepCloneTest();
    System.out.println(ObjectUtils.isAllEquals(false, false,
      test.useApache(),
      test.useSpring(),
      test.useFastjson(),
      test.useByteArrayOutputStream(),
      test.useKryo(),
      test.useHessian(),
      test.useIoProtostuff(),
      test.useDyuprojectProtostuff()
    ));
  }

  /**
   * <a href="https://commons.apache.org/proper/commons-lang/">Apache Commons Lang</a>
   */
  @Benchmark
  public Object useApache() {
    byte[] objectData = org.apache.commons.lang3.SerializationUtils.serialize(testBean);
    TreeNode treeNode1 = org.apache.commons.lang3.SerializationUtils.deserialize(objectData);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * Spring BeanUtils
   */
  @Benchmark
  public Object useSpring() {
    TreeNode treeNode1 = cloneBySpring(testBean);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  public static <T> T cloneBySpring(T source) {
    if (source == null) {
      return null;
    }
    // 创建一个空的目标对象
    T target = (T) BeanUtils.instantiateClass(source.getClass());
    // 对象拷贝
    BeanUtils.copyProperties(source, target);

    // 如果存在嵌套的集合、数组、枚举等属性，需要对其进行特殊处理
    Field[] fields = target.getClass().getDeclaredFields();
    for (Field field : fields) {
      // 设置 private 字段可访问
      ReflectionUtils.makeAccessible(field);

      if (Iterable.class.isAssignableFrom(field.getType())) {
        Iterable<?> srcIterable = (Iterable<?>) ReflectionUtils.getField(field, source);
        if (srcIterable == null) {
          continue;
        }
        List<Object> targetList = new ArrayList<>();
        for (Object srcObj : srcIterable) {
          Object targetObj = cloneBySpring(srcObj);
          targetList.add(targetObj);
        }
        ReflectionUtils.setField(field, target, targetList);
      } else if (Iterator.class.isAssignableFrom(field.getType())) {
        Iterator<?> srcIterator = (Iterator<?>) ReflectionUtils.getField(field, source);
        if (srcIterator == null) {
          continue;
        }
        List<Object> targetList = new ArrayList<>();
        while (srcIterator.hasNext()) {
          Object srcObj = srcIterator.next();
          Object targetObj = cloneBySpring(srcObj);
          targetList.add(targetObj);
        }
        ReflectionUtils.setField(field, target, targetList.iterator());
      } else if (Object[].class.isAssignableFrom(field.getType())) {
        Object[] srcArray = (Object[]) ReflectionUtils.getField(field, source);
        if (srcArray == null) {
          continue;
        }
        Object[] targetArray = new Object[srcArray.length];
        for (int i = 0; i < srcArray.length; i++) {
          Object srcObj = srcArray[i];
          Object targetObj = cloneBySpring(srcObj);
          targetArray[i] = targetObj;
        }
        ReflectionUtils.setField(field, target, targetArray);
      } else if (Enumeration.class.isAssignableFrom(field.getType())) {
        Enumeration<?> srcEnumeration = (Enumeration<?>) ReflectionUtils.getField(field, source);
        if (srcEnumeration == null) {
          continue;
        }
        List<Object> targetList = new ArrayList<>();
        while (srcEnumeration.hasMoreElements()) {
          Object srcObj = srcEnumeration.nextElement();
          Object targetObj = cloneBySpring(srcObj);
          targetList.add(targetObj);
        }
        ReflectionUtils.setField(field, target, Collections.enumeration(targetList));
      } else if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
        Object srcObj = ReflectionUtils.getField(field, source);
        if (srcObj == null) {
          continue;
        }
        Object targetObj = cloneBySpring(srcObj);
        ReflectionUtils.setField(field, target, targetObj);
      }
    }
    return target;
  }

  /**
   * <a href="https://github.com/alibaba/fastjson2">alibaba/fastjson2: 🚄 FASTJSON2 is a Java JSON library with excellent performance.</a>
   */
  @Benchmark
  public Object useFastjson() {
    TreeNode treeNode1 = com.alibaba.fastjson2.JSON.parseObject(com.alibaba.fastjson2.JSON.toJSONString(testBean), TreeNode.class);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * {@link java.io.ByteArrayOutputStream}、{@link java.io.ByteArrayInputStream}
   */
  @Benchmark
  public Object useByteArrayOutputStream() {
    TreeNode treeNode1 = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(testBean);
      oos.flush();
      oos.close();
      bos.close();
      byte[] bytes = bos.toByteArray();
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bis);
      treeNode1 = (TreeNode) ois.readObject();
      ois.close();
      bis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * <a href="https://github.com/EsotericSoftware/kryo">EsotericSoftware/kryo: Java binary serialization and cloning: fast, efficient, automatic</a>
   */
  @Benchmark
  public Object useKryo() {
    com.esotericsoftware.kryo.Kryo kryo = new com.esotericsoftware.kryo.Kryo();
    kryo.setReferences(false);
    kryo.setRegistrationRequired(false);
    com.esotericsoftware.kryo.io.Output output = new com.esotericsoftware.kryo.io.Output(1, 4096);
    kryo.writeObject(output, testBean);
    output.flush();
    output.close();
    byte[] bytes = output.getBuffer();
    com.esotericsoftware.kryo.io.Input input = new com.esotericsoftware.kryo.io.Input(bytes);
    TreeNode treeNode1 = kryo.readObject(input, TreeNode.class);
    input.close();
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * <a href="https://github.com/RuedigerMoeller/fast-serialization">RuedigerMoeller/fast-serialization: FST: fast java serialization drop in-replacement</a>
   */
  @Benchmark
  public Object useFst() {
    FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();
    byte[] serialized = configuration.asByteArray(testBean);
    TreeNode treeNode1 = (TreeNode) configuration.asObject(serialized);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * <a href="http://hessian.caucho.com/">Hessian Binary Web Service Protocol</a>
   */
  @Benchmark
  public Object useHessian() {
    TreeNode treeNode1;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      HessianOutput output = new HessianOutput(bos);
      output.writeObject(testBean);
      output.close();
      HessianInput input = new HessianInput(new ByteArrayInputStream(bos.toByteArray()));
      treeNode1 = (TreeNode) input.readObject();
      input.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * <a href="https://github.com/protostuff/protostuff">protostuff/protostuff: Java serialization library, proto compiler, code generator</a>
   */
  @Benchmark
  public Object useIoProtostuff() {
    io.protostuff.runtime.RuntimeSchema schema = io.protostuff.runtime.RuntimeSchema.createFrom(testBean.getClass());
    byte[] data = io.protostuff.ProtobufIOUtil.toByteArray(testBean, schema, io.protostuff.LinkedBuffer.allocate(512));
    TreeNode treeNode1 = (TreeNode) schema.newMessage();
    io.protostuff.ProtobufIOUtil.mergeFrom(data, treeNode1, schema);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }

  /**
   * <a href="https://dyuproject.com/protostuff/">dyuproject/protostuff：A java serialization library</a>
   */
  @Benchmark
  public Object useDyuprojectProtostuff() {
    com.dyuproject.protostuff.runtime.RuntimeSchema schema = com.dyuproject.protostuff.runtime.RuntimeSchema.createFrom(testBean.getClass());
    byte[] data = com.dyuproject.protostuff.ProtobufIOUtil.toByteArray(testBean, schema, com.dyuproject.protostuff.LinkedBuffer.allocate(512));
    TreeNode treeNode1 = (TreeNode) schema.newMessage();
    com.dyuproject.protostuff.ProtobufIOUtil.mergeFrom(data, treeNode1, schema);
    treeNode1.getChildren().get(0).getChildren().get(0).setName("1.1.2");
    return testBean.getChildren().get(0).getChildren().get(0).getName();
  }
}


// Benchmark                                                                  Mode     Cnt     Score      Error   Units
// DeepCloneTest.useApache                                                   thrpt       5     0.079 ±    0.001  ops/us
// DeepCloneTest.useByteArrayOutputStream                                    thrpt       5     0.077 ±    0.002  ops/us
// DeepCloneTest.useDyuprojectProtostuff                                     thrpt       5     0.381 ±    0.009  ops/us
// DeepCloneTest.useFastjson                                                 thrpt       5     1.717 ±    0.028  ops/us
// DeepCloneTest.useFst                                                      thrpt       5     0.001 ±    0.001  ops/us
// DeepCloneTest.useHessian                                                  thrpt       5     0.066 ±    0.001  ops/us
// DeepCloneTest.useIoProtostuff                                             thrpt       5     0.329 ±    0.005  ops/us
// DeepCloneTest.useKryo                                                     thrpt       5     0.006 ±    0.001  ops/us
// DeepCloneTest.useSpring                                                   thrpt       5     0.097 ±    0.002  ops/us
// DeepCloneTest.useApache                                                    avgt       5    12.423 ±    0.252   us/op
// DeepCloneTest.useByteArrayOutputStream                                     avgt       5    12.938 ±    0.188   us/op
// DeepCloneTest.useDyuprojectProtostuff                                      avgt       5     2.636 ±    0.030   us/op
// DeepCloneTest.useFastjson                                                  avgt       5     0.566 ±    0.007   us/op
// DeepCloneTest.useFst                                                       avgt       5  1136.319 ±   34.589   us/op
// DeepCloneTest.useHessian                                                   avgt       5    15.271 ±    0.438   us/op
// DeepCloneTest.useIoProtostuff                                              avgt       5     2.994 ±    0.083   us/op
// DeepCloneTest.useKryo                                                      avgt       5   154.349 ±    3.157   us/op
// DeepCloneTest.useSpring                                                    avgt       5    10.041 ±    0.098   us/op
// DeepCloneTest.useApache                                                  sample   99163    12.916 ±    0.170   us/op
// DeepCloneTest.useApache:useApache·p0.00                                  sample            11.888              us/op
// DeepCloneTest.useApache:useApache·p0.50                                  sample            12.288              us/op
// DeepCloneTest.useApache:useApache·p0.90                                  sample            12.688              us/op
// DeepCloneTest.useApache:useApache·p0.95                                  sample            13.792              us/op
// DeepCloneTest.useApache:useApache·p0.99                                  sample            22.784              us/op
// DeepCloneTest.useApache:useApache·p0.999                                 sample            58.497              us/op
// DeepCloneTest.useApache:useApache·p0.9999                                sample          1080.343              us/op
// DeepCloneTest.useApache:useApache·p1.00                                  sample          1628.160              us/op
// DeepCloneTest.useByteArrayOutputStream                                   sample   97669    13.232 ±    0.220   us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.00    sample            12.096              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.50    sample            12.496              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.90    sample            12.896              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.95    sample            13.392              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.99    sample            23.200              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.999   sample            62.293              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p0.9999  sample          1288.987              us/op
// DeepCloneTest.useByteArrayOutputStream:useByteArrayOutputStream·p1.00    sample          2000.896              us/op
// DeepCloneTest.useDyuprojectProtostuff                                    sample  114281     2.941 ±    0.136   us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.00      sample             2.496              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.50      sample             2.700              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.90      sample             2.800              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.95      sample             3.000              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.99      sample             4.296              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.999     sample            27.413              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p0.9999    sample           652.365              us/op
// DeepCloneTest.useDyuprojectProtostuff:useDyuprojectProtostuff·p1.00      sample          1937.408              us/op
// DeepCloneTest.useFastjson                                                sample  132863     0.619 ±    0.005   us/op
// DeepCloneTest.useFastjson:useFastjson·p0.00                              sample             0.499              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.50                              sample             0.600              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.90                              sample             0.601              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.95                              sample             0.700              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.99                              sample             0.901              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.999                             sample             2.113              us/op
// DeepCloneTest.useFastjson:useFastjson·p0.9999                            sample            38.912              us/op
// DeepCloneTest.useFastjson:useFastjson·p1.00                              sample            89.856              us/op
// DeepCloneTest.useFst                                                     sample    4487  1125.837 ±    6.400   us/op
// DeepCloneTest.useFst:useFst·p0.00                                        sample          1052.672              us/op
// DeepCloneTest.useFst:useFst·p0.50                                        sample          1091.584              us/op
// DeepCloneTest.useFst:useFst·p0.90                                        sample          1185.792              us/op
// DeepCloneTest.useFst:useFst·p0.95                                        sample          1224.704              us/op
// DeepCloneTest.useFst:useFst·p0.99                                        sample          1759.724              us/op
// DeepCloneTest.useFst:useFst·p0.999                                       sample          2639.888              us/op
// DeepCloneTest.useFst:useFst·p0.9999                                      sample          3063.808              us/op
// DeepCloneTest.useFst:useFst·p1.00                                        sample          3063.808              us/op
// DeepCloneTest.useHessian                                                 sample   81288    15.807 ±    0.257   us/op
// DeepCloneTest.useHessian:useHessian·p0.00                                sample            14.288              us/op
// DeepCloneTest.useHessian:useHessian·p0.50                                sample            14.800              us/op
// DeepCloneTest.useHessian:useHessian·p0.90                                sample            15.488              us/op
// DeepCloneTest.useHessian:useHessian·p0.95                                sample            17.376              us/op
// DeepCloneTest.useHessian:useHessian·p0.99                                sample            31.307              us/op
// DeepCloneTest.useHessian:useHessian·p0.999                               sample            89.213              us/op
// DeepCloneTest.useHessian:useHessian·p0.9999                              sample          1331.136              us/op
// DeepCloneTest.useHessian:useHessian·p1.00                                sample          2150.400              us/op
// DeepCloneTest.useIoProtostuff                                            sample  110976     2.971 ±    0.106   us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.00                      sample             2.596              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.50                      sample             2.800              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.90                      sample             2.900              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.95                      sample             3.000              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.99                      sample             4.096              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.999                     sample            20.999              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p0.9999                    sample           254.991              us/op
// DeepCloneTest.useIoProtostuff:useIoProtostuff·p1.00                      sample          1511.424              us/op
// DeepCloneTest.useKryo                                                    sample   33256   151.117 ±    0.625   us/op
// DeepCloneTest.useKryo:useKryo·p0.00                                      sample           139.264              us/op
// DeepCloneTest.useKryo:useKryo·p0.50                                      sample           144.640              us/op
// DeepCloneTest.useKryo:useKryo·p0.90                                      sample           160.768              us/op
// DeepCloneTest.useKryo:useKryo·p0.95                                      sample           176.128              us/op
// DeepCloneTest.useKryo:useKryo·p0.99                                      sample           239.104              us/op
// DeepCloneTest.useKryo:useKryo·p0.999                                     sample           492.412              us/op
// DeepCloneTest.useKryo:useKryo·p0.9999                                    sample          1595.100              us/op
// DeepCloneTest.useKryo:useKryo·p1.00                                      sample          1898.496              us/op
// DeepCloneTest.useSpring                                                  sample  121825    10.434 ±    0.128   us/op
// DeepCloneTest.useSpring:useSpring·p0.00                                  sample             9.696              us/op
// DeepCloneTest.useSpring:useSpring·p0.50                                  sample             9.888              us/op
// DeepCloneTest.useSpring:useSpring·p0.90                                  sample            10.192              us/op
// DeepCloneTest.useSpring:useSpring·p0.95                                  sample            11.696              us/op
// DeepCloneTest.useSpring:useSpring·p0.99                                  sample            15.696              us/op
// DeepCloneTest.useSpring:useSpring·p0.999                                 sample            52.321              us/op
// DeepCloneTest.useSpring:useSpring·p0.9999                                sample           928.937              us/op
// DeepCloneTest.useSpring:useSpring·p1.00                                  sample          1576.960              us/op
// DeepCloneTest.useApache                                                      ss       5   299.820 ±   76.043   us/op
// DeepCloneTest.useByteArrayOutputStream                                       ss       5   310.360 ±  175.449   us/op
// DeepCloneTest.useDyuprojectProtostuff                                        ss       5   258.360 ±  654.062   us/op
// DeepCloneTest.useFastjson                                                    ss       5   255.380 ±  832.689   us/op
// DeepCloneTest.useFst                                                         ss       5  2737.540 ±  533.529   us/op
// DeepCloneTest.useHessian                                                     ss       5   306.600 ±  136.136   us/op
// DeepCloneTest.useIoProtostuff                                                ss       5   295.881 ±  759.082   us/op
// DeepCloneTest.useKryo                                                        ss       5   876.420 ±  667.648   us/op
// DeepCloneTest.useSpring                                                      ss       5   788.840 ± 2918.691   us/op
