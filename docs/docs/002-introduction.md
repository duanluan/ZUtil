# 入门

## 安装

### Maven

```xml
<dependency>
  <groupId>top.csaf</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.14.0</version>
</dependency>
```

### Gradle

```groovy
// groovy
implementation 'top.csaf:ZUtil:1.14.0'
// kotlin
implementation("top.csaf:ZUtil:1.14.0")
```

## 安装注意

工具包中已使用 slf4j-api 和 slf4j-simple，和 spring-boot-starter-web 同时使用时会冲突，需要手动排除。

### Maven

```xml
<!-- 方式一：ZUtil 排除 slf4j -->
<dependency>
  <groupId>top.csaf</groupId>
  <artifactId>ZUtil</artifactId>
  <version>1.14.0</version>
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
  implementation('top.csaf:ZUtil:1.14.0') {
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
  implementation("top.csaf:ZUtil:1.14.0") {
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

* [Maven 中央库](https://search.maven.org/artifact/top.csaf/ZUtil)
* [Maven Repository](https://mvnrepository.com/artifact/top.csaf/ZUtil)
