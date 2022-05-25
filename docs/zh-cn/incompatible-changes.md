# Deprecated

* 1.7.0：`top.zhogjianhao.JsonUtils`的`toJson(Object, SerializerFeature...)`、`parseObject(String, Class<T>, Feature...)`、`parseObject(String, Class<T>, ParserConfig)`方法，SerializerFeature... 替换为 **JSONWriter.Feature...**，Feature... 和 ParserConfig 替换为 **JSONReader.Feature...**
* 1.5.1：删除`top.zhogjianhao.ArrayUtils#indexOf(String[], String, int)`
* 1.2.4：`top.zhogjianhao.DateUtils`变更为 top.zhogjianhao.<b>date.</b>DateUtils
* 1.2.0：`top.zhogjianhao.CollectionUtils`的`moveForward、remove`方法，移到`top.zhogjianhao.ArrayUtils`中并重构优化
