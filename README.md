# ZUtil

Java 工具类，包括但不限于时间、正则、字符串、数字、集合、数组、Bean、随机数、加解密。

工具类使用请查看 [test](src/test/java/top/zhogjianhao)。

请使用最新版，无重大 bug 已经测试通过的方法的形参不会再更改。

所有性能测试全部由循环计时替换为 JMH。

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
  <version>1.2.3</version>
</dependency>
```

## Gradle

```groovy
// groovy
implementation 'top.zhogjianhao:ZUtil:1.2.3'
// kotlin
implementation("top.zhogjianhao:ZUtil:1.2.3")
```

# Deprecated

* v1.2.0：CollectionUtils 的 moveForward、remove 方法，移到 ArrayUtils 中并重构优化
