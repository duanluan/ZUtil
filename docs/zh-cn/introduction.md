# 入门

## 简介

追求更快的 Java 工具类，一度想将名字改成 FastUtil，但自知能力不够，不敢妄自菲薄。

大多以 Apache 工具类为基础进行扩展，再加上其他工具类为追求更快更全，所以引入了比较多的依赖包。

## 安装

### Maven：

```xml
<dependency>
  <groupId>top.zhogjianhao</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.7.0</version>
</dependency>
```

### Gradle

```groovy
// groovy
implementation 'top.zhogjianhao:ZUtil:1.7.0'
// kotlin
implementation("top.zhogjianhao:ZUtil:1.7.0")
```

## 注意

工具包中已使用 slf4j-api 和 slf4j-simple，和 spring-boot-starter-web 同时使用时会冲突，需要手动排除。

### Maven

```xml
<!-- 方式一：ZUtil 排除 slf4j -->
<dependency>
  <groupId>top.zhogjianhao</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.7.0</version>
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

### Gradle
参考：[Excluding transitive dependencies - Gradle User Manual](https://docs.gradle.org/current/userguide/dependency_downgrade_and_exclude.html#sec:excluding-transitive-deps)

```groovy
// groovy
dependencies {
  // 方式一：ZUtil 排除 slf4j
  implementation('top.zhogjianhao:ZUtil:1.7.0') {
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
  implementation("top.zhogjianhao:ZUtil:1.7.0") {
    exclude(group = "org.slf4j", module = "slf4j-api")
    exclude(group = "org.slf4j", module = "slf4j-simple")
  }
  // 方式二：spring-boot-starter-web 排除 Logback
  implementation("org.springframework.boot:spring-boot-starter-web") {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
}

```

## 资源

* [Github](https://github.com/duanluan/ZUtil)
* [Gitee](https://gitee.com/duanluan/ZUtil)
* [Maven 中央库](https://search.maven.org/artifact/top.zhogjianhao/ZUtil)
* [Maven Repository](https://mvnrepository.com/artifact/top.zhogjianhao/ZUtil)
