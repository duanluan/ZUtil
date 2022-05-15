package top.zhogjianhao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * YAML 工具类
 *
 * @author ZhongJianhao
 */
@Slf4j
public class YamlUtils {

  /**
   * 加载 YAML 文件为 Map
   *
   * @param io Reader
   * @return Map
   */
  public static Map<String, Object> load(@NonNull final Reader io) {
    return new Yaml().loadAs(io, Map.class);
  }

  /**
   * 加载 YAML 文件为 Map
   *
   * @param filePath 文件路径
   * @return Map
   */
  public static Map<String, Object> load(@NonNull final String filePath) {
    try {
      return new Yaml().loadAs(Files.newInputStream(Paths.get(filePath)), Map.class);
    } catch (IOException e) {
      log.error("load yaml file error", e);
      return null;
    }
  }

  /**
   * 加载 YAML 文件为 Map
   *
   * @param input InputStream
   * @return Map
   */
  public static Map<String, Object> load(@NonNull final InputStream input) {
    return new Yaml().loadAs(input, Map.class);
  }

  /**
   * 根据 key 从 YAML Map 中获取 value
   *
   * @param yamlMap YAML Map
   * @param key     key
   * @return value
   */
  public static Object get(@NonNull final Map<String, Object> yamlMap, @NonNull final String key) {
    String[] keys = key.split("\\.");
    Map<String, Object> searchMap = yamlMap;
    for (int i = 0; i < keys.length; i++) {
      Object value = searchMap.get(keys[i]);
      if (value == null) {
        return null;
      } else if (value instanceof Map) {
        searchMap = (Map<String, Object>) value;
      }
      if (i == keys.length - 1) {
        return value;
      }
    }
    return null;
  }

  /**
   * 根据 key 从 YAML 文件中获取 value
   *
   * @param io  Reader
   * @param key key
   * @return value
   */
  public static Object get(@NonNull final Reader io, @NonNull final String key) {
    return get(load(io), key);
  }

  /**
   * 根据 key 从 YAML 文件中获取 value
   *
   * @param filePath 文件路径
   * @param key      key
   * @return value
   */
  public static Object get(@NonNull final String filePath, @NonNull final String key) {
    return get(load(filePath), key);
  }

  /**
   * 根据 key 从 YAML 文件中获取 value
   *
   * @param input InputStream
   * @param key   key
   * @return value
   */
  public static Object get(@NonNull final InputStream input, @NonNull final String key) {
    return get(load(input), key);
  }

  // TODO 修改文件
}
