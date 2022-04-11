package top.zhogjianhao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JSON 工具类
 */
public class JsonUtils {

  /**
   * 转 JSON 字符串
   * <p>
   * src/test/java/top/zhogjianhao/jmh/base/json/ToJsonTest.java
   *
   * @param object 对象
   * @return JSON 字符串
   */
  public static String toJson(@NonNull final Object object, @NonNull final SerializerFeature... features) {
    // 设置序列化特性
    List<SerializerFeature> featureList = new ArrayList<>(Arrays.asList(features));
    // 序列化值为 null 的字段
    featureList.add(0, SerializerFeature.WriteMapNullValue);
    return JSON.toJSONString(object, featureList.toArray(new SerializerFeature[0]));
  }

  /**
   * 转对象
   * <p>
   * src/test/java/top/zhogjianhao/jmh/base/json/ToMapTest.java
   *
   * @param json  JSON 字符串
   * @param clazz 对象类型
   * @param <T>   对象类型
   * @return 对象
   */
  public static <T> T parseObject(@NonNull final String json, @NonNull final Class<T> clazz, @NonNull final Feature... features) {
    return JSONObject.parseObject(json, clazz, features);
  }

  /**
   * 转集合
   * <p>
   * src/test/java/top/zhogjianhao/jmh/base/json/ToListTest.java
   *
   * @param json         JSON 字符串
   * @param clazz        集合元素类型
   * @param parserConfig 配置
   * @param <T>          集合元素类型
   * @return 集合
   */
  public static <T> List<T> parseArray(@NonNull final String json, @NonNull final Class<T> clazz, @NonNull final ParserConfig parserConfig) {
    return JSON.parseArray(json, clazz, parserConfig);
  }
}
