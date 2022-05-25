package top.zhogjianhao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
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
   * @param features 序列化行为
   * @return JSON 字符串
   */
  public static String toJson(@NonNull final Object object, final JSONWriter.Feature... features) {
    // 设置输出行为
    if (CollectionUtils.sizeIsNotEmpty(features)) {
      // 输出值为 null 的字段
      return JSON.toJSONString(object, ArrayUtils.addFirst(features, JSONWriter.Feature.WriteMapNullValue));
    }
    return JSON.toJSONString(object, JSONWriter.Feature.WriteMapNullValue);
  }

  /**
   * JSON 字符串转对象
   *
   * @param json     JSON 字符串
   * @param clazz    对象类型
   * @param features 反序列化行为
   * @param <T>      对象类型
   * @return 对象
   */
  public static <T> T parseObject(@NonNull final String json, @NonNull final Class<T> clazz, final JSONReader.Feature... features) {
    if (features != null) {
      return JSON.parseObject(json, clazz, features);
    }
    return JSON.parseObject(json, clazz);
  }

  /**
   * JSON 字符串转集合
   *
   * @param json     JSON 字符串
   * @param clazz    集合元素类型
   * @param features 反序列化行为
   * @param <T>      集合元素类型
   * @return 集合
   */
  public static <T> List<T> parseArray(@NonNull final String json, @NonNull final Class<T> clazz, final JSONReader.Feature... features) {
    if (features != null) {
      return JSON.parseArray(json, clazz, features);
    }
    return JSON.parseArray(json, clazz);
  }
}
