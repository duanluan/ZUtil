![](https://socialify.git.ci/duanluan/ZUtil/image?description=1&font=Bitter&forks=1&issues=1&language=1&logo=https%3A%2F%2Fduanluan.github.io%2FZUtil%2Fimg%2Flogo.svg&name=1&owner=1&pattern=Floating%20Cogs&pulls=1&stargazers=1&theme=Light)

# ZUtil

[![](https://img.shields.io/maven-central/v/top.zhogjianhao/ZUtil?style=flat-square)](https://search.maven.org/artifact/top.zhogjianhao/ZUtil)
[![](https://img.shields.io/hexpm/l/plug?style=flat-square)](./LICENSE)
[![](https://img.shields.io/badge/JDK-8%2B-orange?style=flat-square)]()
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-duanluan-red.svg)](https://github.com/duanluan)
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

## Maven：

```xml
<dependency>
  <groupId>top.zhogjianhao</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.9.0</version>
</dependency>
```

## Gradle

```groovy
// groovy
implementation 'top.zhogjianhao:ZUtil:1.9.0'
// kotlin
implementation("top.zhogjianhao:ZUtil:1.9.0")
```

# Stargazers over time

[![Stargazers over time](https://starchart.cc/duanluan/ZUtil.svg)](https://starchart.cc/duanluan/ZUtil)
