![](https://socialify.git.ci/duanluan/ZUtil/image?description=1&font=Bitter&forks=1&issues=1&language=1&logo=https%3A%2F%2Fduanluan.github.io%2FZUtil%2Fimg%2Flogo.svg&name=1&owner=1&pattern=Floating%20Cogs&pulls=1&stargazers=1&theme=Light)

# ZUtil

[![](https://img.shields.io/maven-central/v/top.zhogjianhao/ZUtil?style=flat-square)](https://search.maven.org/artifact/top.zhogjianhao/ZUtil)
[![](https://img.shields.io/hexpm/l/plug?style=flat-square)](./LICENSE)
[![](https://img.shields.io/badge/JDK-8%2B-orange?style=flat-square)]()
[![](https://img.shields.io/badge/made%20with-%e2%9d%a4-ff69b4.svg?style=flat-square)](#)
[![](https://img.shields.io/badge/273743748-🐧-388adc.svg?style=flat-square)](https://jq.qq.com/?_wv=1027&k=pYzF0R18)
[![](https://img.shields.io/github/stars/duanluan/ZUtil?style=social)](https://github.com/duanluan/ZUtil)
[![star](https://gitee.com/duanluan/ZUtil/badge/star.svg?theme=white)](https://gitee.com/duanluan/ZUtil)

追求更快更全的 Java 工具类。

工具类使用请查看<a href='https://duanluan.github.io/ZUtil' target='_blank' style='font-size:25px'>文档</a>、[javadoc](https://apidoc.gitee.com/duanluan/ZUtil)。

和 Hutool 的性能对比测试请查看 [jmh.contrast](src/test/java/top/zhogjianhao/jmh/contrast)。

## 特性

* 更快：使用 JMH 进行[性能测试](https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/jmh)。
* 更全：[时间工具类](https://github.com/duanluan/ZUtil/blob/main/src/main/java/top/zhogjianhao/date/DateUtils.java)近 120 个方法，2500+ 行；[正则工具类](https://github.com/duanluan/ZUtil/blob/main/src/main/java/top/zhogjianhao/regex/RegExUtils.java)近 50 个方法，750+ 行。其他工具类也在持续更新中……
* 更安全：使用 JUnit 进行[套件测试](https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/junit)，JaCoCo 进行[代码覆盖率测试](https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/junit)，保证每行代码都符合预期，更少出 BUG。

## 如何看 JMH 性能对比测试结果？

```java
// Benchmark                                                 Mode     Cnt    Score    Error   Units
// ToPinyinTest.toPinyinByHutool                            thrpt       5    2.880 ±  0.160  ops/us
// ToPinyinTest.toPinyinByZUtil                             thrpt       5    4.577 ±  0.133  ops/us
// ToPinyinTest.toPinyinByHutool                             avgt       5    0.356 ±  0.012   us/op
// ToPinyinTest.toPinyinByZUtil                              avgt       5    0.216 ±  0.006   us/op
// ToPinyinTest.toPinyinByHutool                           sample  175058    0.435 ±  0.008   us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.00    sample            0.300            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.50    sample            0.400            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.90    sample            0.500            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.95    sample            0.500            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.99    sample            0.900            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.999   sample            1.600            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p0.9999  sample           40.900            us/op
// ToPinyinTest.toPinyinByHutool:toPinyinByHutool·p1.00    sample          277.504            us/op
// ToPinyinTest.toPinyinByZUtil                            sample  162384    0.393 ±  0.008   us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.00      sample            0.200            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.50      sample            0.300            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.90      sample            0.500            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.95      sample            0.600            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.99      sample            1.000            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.999     sample            2.500            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p0.9999    sample           45.425            us/op
// ToPinyinTest.toPinyinByZUtil:toPinyinByZUtil·p1.00      sample          170.496            us/op
// ToPinyinTest.toPinyinByHutool                               ss       5   30.880 ± 37.754   us/op
// ToPinyinTest.toPinyinByZUtil                                ss       5   23.060 ± 16.885   us/op
```

Mode 即为`org.openjdk.jmh.annotations.Mode`，分为：
* thrpt：**Throughput（吞吐量）**, ops/time，分数越大越好
* avgt：**Average time（平均时间）**, time/op，分数越小越好
* sample：**Sampling time（采样时间）**，分数越小越好
* ss：**Single shot invocation time（单次调用时间）**：分数越小越好

## Maven：

```xml
<dependency>
  <groupId>top.zhogjianhao</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.9.2</version>
</dependency>
```

## Gradle

```groovy
// groovy
implementation 'top.zhogjianhao:ZUtil:1.9.2'
// kotlin
implementation("top.zhogjianhao:ZUtil:1.9.2")
```

# Stargazers over time

[![Stargazers over time](https://starchart.cc/duanluan/ZUtil.svg)](https://starchart.cc/duanluan/ZUtil)
