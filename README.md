![](https://socialify.git.ci/duanluan/ZUtil/image?description=1&font=Bitter&forks=1&issues=1&language=1&logo=https%3A%2F%2Fduanluan.github.io%2FZUtil%2Fimg%2Flogo.png&name=1&owner=1&pattern=Floating%20Cogs&pulls=1&stargazers=1&theme=Light)

# ZUtil

[![](https://img.shields.io/maven-central/v/top.csaf/ZUtil?style=flat-square)](https://central.sonatype.com/artifact/top.csaf/zutil-all)
[![](https://img.shields.io/hexpm/l/plug?style=flat-square)](./LICENSE)
[![](https://img.shields.io/badge/JDK-8%2B-orange?style=flat-square)]()
[![](https://img.shields.io/badge/273743748-加%20Q%20群-388adc.svg?style=flat-square)](https://jq.qq.com/?_wv=1027&k=pYzF0R18)
[![](https://img.shields.io/github/stars/duanluan/ZUtil?style=social)](https://github.com/duanluan/ZUtil)
[![star](https://gitee.com/duanluan/ZUtil/badge/star.svg?theme=white)](https://gitee.com/duanluan/ZUtil)

追求更快更全的 Java 工具类。

工具类使用请查看<a href='https://duanluan.github.io/ZUtil' target='_blank' style='font-size:25px'>文档</a>、[javadoc](https://apidoc.gitee.com/duanluan/ZUtil)。

和 Hutool 的性能对比测试请查看 [jmh.comparison](zutil-all/src/test/java/top/csaf/jmh/comparison)。

## 特性

* 更快：使用 [JMH](https://openjdk.org/projects/code-tools/jmh/) 进行[性能测试](https://github.com/duanluan/ZUtil/tree/main/zutil-all/src/test/java/top/csaf/jmh)。
* 更全：[时间工具类](https://github.com/duanluan/ZUtil/blob/main/zutil-date/src/main/java/top/csaf/date/DateUtil.java) 130+ 个方法，2700+ 行；[正则工具类](https://github.com/duanluan/ZUtil/blob/main/zutil-regex/src/main/java/top/csaf/regex/RegExUtil.java) 80+ 个方法，1100+ 行。
* 更安全：使用 [JUnit](https://junit.org/junit5) 进行套件测试，[JaCoCo](https://www.jacoco.org/jacoco/index.html) 进行[代码覆盖率测试](https://github.com/duanluan/ZUtil/tree/main/zutil-all/src/test/java/top/csaf/junit)，保证每行代码都符合预期，更少出 BUG。

## Stargazers over time

[![Stargazers over time](https://starchart.cc/duanluan/ZUtil.svg)](https://starchart.cc/duanluan/ZUtil)

## 说明

### 安装

#### Maven

```xml
<dependency>
  <groupId>top.csaf</groupId>
  <artifactId>ZUtil</artifactId>
  <version>2.0.0-alpha9</version>
</dependency>
```

#### Gradle

```groovy
// groovy
implementation 'top.csaf:ZUtil:2.0.0-alpha9'
// kotlin
implementation("top.csaf:ZUtil:2.0.0-alpha9")
```

### 安装注意

工具包中已使用 slf4j-api 和 slf4j-simple，和 spring-boot-starter-web 同时使用时会冲突，需要手动排除。

#### Maven

```xml
<!-- 方式一：ZUtil 排除 slf4j -->
<dependency>
  <groupId>top.csaf</groupId>
  <artifactId>ZUtil</artifactId>
   <version>2.0.0-alpha9</version>
  <exclusions>
    <exclusion>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
    </exclusion>
    <exclusion>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<!-- 方式二：spring-boot-starter-web 排除 Logback -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

#### Gradle

参考：[Excluding transitive dependencies - Gradle User Manual](https://docs.gradle.org/current/userguide/dependency_downgrade_and_exclude.html#sec:excluding-transitive-deps)

```groovy
// groovy
dependencies {
  // 方式一：ZUtil 排除 slf4j
   implementation('top.csaf:ZUtil:2.0.0-alpha9') {
    exclude group: 'org.slf4j', module: 'slf4j-api'
    exclude group: 'org.slf4j', module: 'slf4j-simple'
  }
  // 方式二：spring-boot-starter-web 排除 Logback
  implementation('org.springframework.boot:spring-boot-starter-web') {
    exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
  }
}

// kotlin
dependencies {
  // 方式一：ZUtil 排除 slf4j
   implementation("top.csaf:ZUtil:2.0.0-alpha9") {
    exclude(group = "org.slf4j", module = "slf4j-api")
    exclude(group = "org.slf4j", module = "slf4j-simple")
  }
  // 方式二：spring-boot-starter-web 排除 Logback
  implementation("org.springframework.boot:spring-boot-starter-web") {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
}
```

### JMH 性能对比测试结果解释

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

### 参与开发

福利：***JetBrains* 全系列许可证**。

1. **[Fork](https://github.com/duanluan/ZUtil/fork)** 并 **Clone** 项目到本地。
2. 开发内容：
   * **新增类或方法**需提前[加群](https://jq.qq.com/?_wv=1027&k=pYzF0R18)沟通。
   * **修复 BUG**（fix）、**优化性能**（perf）或**新增/更正测试**（test）。
3. 测试步骤：
   * 使用`org.junit.jupiter.api.Assertions`进行**代码覆盖率测试**：
    ```java
    ……
    import top.csaf.id.NanoIdUtil;
    import static org.junit.jupiter.api.Assertions.*;
    
    @Slf4j
    @DisplayName("NanoId 工具类测试")
    class NanoIdUtilTest {
    
      @DisplayName("生成 NanoID")
      @Test
      void randomNanoId() {
        /** {@link NanoIdUtil#randomNanoId(int, char[], java.util.Random) } */
        assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, (char[]) null, NanoIdUtils.DEFAULT_ID_GENERATOR));
        assertThrows(NullPointerException.class, () -> NanoIdUtils.randomNanoId(0, new char[0], null));
        assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(0, new char[0], NanoIdUtils.DEFAULT_ID_GENERATOR));
        assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(1, new char[0], NanoIdUtils.DEFAULT_ID_GENERATOR));
        assertThrows(IllegalArgumentException.class, () -> NanoIdUtils.randomNanoId(1, new char[256], NanoIdUtils.DEFAULT_ID_GENERATOR));
        assertDoesNotThrow(() -> NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_SIZE, NanoIdUtils.DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_ID_GENERATOR));
      }
    }
    ```
   * `mvn test -Dtest=要测试的类名`进行测试，测试后会在`target`下生成`jacoco.exec`。
   * `mvn jacoco:report`生成代码覆盖率测试报告，在`target/site`目录下。
   * 查看更新的类或方法，覆盖率在 **90%** 以上时提交。
   * `lombok.NonNull`的参数校验可以忽略。
4. 提交时遵循 **[Angular 提交消息规范](https://github.com/angular/angular/blob/22b96b9/CONTRIBUTING.md#-commit-message-guidelines)**，提交后新建 **pull request** 即可。
