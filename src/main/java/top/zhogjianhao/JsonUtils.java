package top.zhogjianhao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.NonNull;

import java.util.List;

/**
 * JSON 工具类
 */
public class JsonUtils {

  /**
   * 对象转 JSON 字符串
   *
   * @param object   对象
   * @param features 序列化特性
   * @return JSON 字符串
   */
  public static String toJson(@NonNull final Object object, final SerializerFeature... features) {
    // 设置输出特性
    if (CollectionUtils.sizeIsNotEmpty(features)) {
      // 输出值为 null 的字段
      return JSON.toJSONString(object, ArrayUtils.addFirst(features, SerializerFeature.WriteMapNullValue));
    }
    return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
  }

  /**
   * JSON 字符串转对象
   *
   * @param json     JSON 字符串
   * @param clazz    对象类型
   * @param features 反序列化特性
   * @param <T>      对象类型
   * @return 对象
   */
  public static <T> T parseObject(@NonNull final String json, @NonNull final Class<T> clazz, final Feature... features) {
    return JSONObject.parseObject(json, clazz, features);
  }

  /**
   * JSON 字符串转集合
   *
   * @param json         JSON 字符串
   * @param clazz        集合元素类型
   * @param parserConfig 反序列化配置
   * @param <T>          集合元素类型
   * @return 集合
   */
  public static <T> List<T> parseArray(@NonNull final String json, @NonNull final Class<T> clazz, @NonNull final ParserConfig parserConfig) {
    return JSON.parseArray(json, clazz, parserConfig);
  }

  /**
   * JSON 字符串转集合
   *
   * @param json  JSON 字符串
   * @param clazz 集合元素类型
   * @param <T>   集合元素类型
   * @return 集合
   */
  public static <T> List<T> parseArray(@NonNull final String json, @NonNull final Class<T> clazz) {
    return JSON.parseArray(json, clazz);
  }
}
