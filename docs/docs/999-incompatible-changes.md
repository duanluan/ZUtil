# 不兼容变更

* 1.9.4：
  * 删除`FileUtils.getClassRootPath`；
  * 删除`FileUtils.getClassPath`。
* 1.8.1：
  * `date.DateFeature`中静态变量的赋值方式由直接赋值改为通过`set`、`setAlways`方法赋值；
  * `DateUtils.defaultLocalDatePattern`移至`DateConstant.DEFAULT_LOCAL_DATE_PATTERN`；
  * `DateUtils.defaultLocalDateTimePattern`移至`DateConstant.DEFAULT_LOCAL_DATE_TIME_PATTERN`；
  * `DateUtils.defaultLocalTimePattern`移至`DateConstant.DEFAULT_LOCAL_TIME_PATTERN`。
* 1.8.0：
  * `date.DateDuration`移至`date.constant`包下；
  * `date.DatePattern`移至`date.constant`包下；
  * `date.DatePatternRegex`移至`date.constant.DateRegexPattern` ；
  * `constant.CommonPatternConstant`重命名为`PatternConstant`;
  * `RegExUtils`移至`regex`包下。
* 1.7.0：`JsonUtils`的`toJson(Object, SerializerFeature...)`、`parseObject(String, Class<T>, Feature...)`、`parseObject(String, Class<T>, ParserConfig)`方法，SerializerFeature... 替换为 **JSONWriter.Feature...**，Feature... 和 ParserConfig 替换为 **JSONReader.Feature...**。
* 1.5.1：删除`ArrayUtils#indexOf(String[], String, int)`。
* 1.2.4：`DateUtils`变更为 <b>date.</b>DateUtils。
* 1.2.0：`CollectionUtils`的`moveForward、remove`方法，移至`ArrayUtils`中并重构优化。
