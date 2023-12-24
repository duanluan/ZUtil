# PinyinFeat 拼音特性

可以通过临时或总是（Always）修改其静态成员变量，来决定`PinyinUtil`中方法对拼音的处理方式。

## FIRST_WORD_INITIAL_CAP - 第一个单词首字母是否大写

第一个汉字的拼音首字母是否大写，默认为`false`。

## SECOND_WORD_INITIAL_CAP - 第二个单词首字母是否大写

后续汉字的拼音首字母是否大写，默认为`false`。

## HAS_SEPARATOR_BY_NOT_PINYIN_AROUND - 非拼音前后是否需要分隔符

如果遇到非汉字，传参了分隔符时，非汉字的两侧是否需要此分隔符，默认为`false`。

```java
// 输出结果为 hǎo hǎo xué xí，tiān tiān xiàng shàng，可以看到“，”的两边没有空格
System.out.println(PinyinUtils.get("好好学习，天天向上", true, true, " "));

// 临时设置非拼音前后需要分隔符
  PinyinFeat.setHasSeparatorByNotPinyinAround(true);
// 输出结果为 hǎo hǎo xué xí ， tiān tiān xiàng shàng
System.out.println(PinyinUtils.get("好好学习，天天向上", true, true, " "));
```
