# 不兼容变更

* 1.8.0：
  * `date.DateDuration`移到`date.constant`包下；
  * `date.DatePattern`移到`date.constant`包下；
  * `date.DatePatternRegex`移到`date.constant.DateRegexPattern` ；
  * `constant.CommonPatternConstant`重命名为`PatternConstant`;
  * `RegExUtils`移到`regex`包下。
* 1.7.0：`JsonUtils`的`toJson(Object, SerializerFeature...)`、`parseObject(String, Class<T>, Feature...)`、`parseObject(String, Class<T>, ParserConfig)`方法，SerializerFeature... 替换为 **JSONWriter.Feature...**，Feature... 和 ParserConfig 替换为 **JSONReader.Feature...**。
* 1.5.1：删除`ArrayUtils#indexOf(String[], String, int)`。
* 1.2.4：`DateUtils`变更为 <b>date.</b>DateUtils。
* 1.2.0：`CollectionUtils`的`moveForward、remove`方法，移到`ArrayUtils`中并重构优化。
