# ZUtil

Java 工具类，包括但不限于时间、正则、字符串、数字、集合、数组、Bean、随机数、加解密。

QQ 交流群：[273743748](https://jq.qq.com/?_wv=1027&k=yZfCzQ8f)，微信群加入 Q 群后艾特群主。

工具类使用请查看 [test](src/test/java/top/zhogjianhao)，和 Hutool 的性能对比测试请查看 [jmh.contrast](src/test/java/top/zhogjianhao/jmh/contrast)。

请使用最新版，无重大 bug 已经测试通过的方法的形参不会再更改。

# 资源

* Github：https://github.com/duanluan/ZUtil
* Gitee：https://gitee.com/duanluan/ZUtil
* Maven（中央库）：https://search.maven.org/artifact/top.zhogjianhao/ZUtil
* Maven Repository（版本有延迟）：https://mvnrepository.com/artifact/top.zhogjianhao/ZUtil

## Maven：

```xml
<dependency>
  <groupId>top.zhogjianhao</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.4.1</version>
</dependency>
```

## Gradle

```groovy
// groovy
implementation 'top.zhogjianhao:ZUtil:1.4.1'
// kotlin
implementation("top.zhogjianhao:ZUtil:1.4.1")
```

# Stargazers over time

[![Stargazers over time](https://starchart.cc/duanluan/ZUtil.svg)](https://starchart.cc/duanluan/ZUtil)

# Deprecated

* v1.2.4：top.zhogjianhao.DateUtils 变更为 top.zhogjianhao.date.DateUtils
* v1.2.0：CollectionUtils 的 moveForward、remove 方法，移到 ArrayUtils 中并重构优化
