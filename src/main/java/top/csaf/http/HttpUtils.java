package top.csaf.http;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.SHttpTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import top.csaf.JsonUtils;
import top.csaf.MapUtils;
import top.csaf.StringUtils;
import top.csaf.constant.CommonPattern;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * HTTP 工具类
 */
@Slf4j
public class HttpUtils extends com.ejlchina.okhttps.HttpUtils {

  /**
   * 键值对参数转换为 URL 参数
   *
   * @param prefix 前缀，一般是“?”
   * @param params 参数
   * @return URL 参数
   */
  public static String toUrlParams(@NonNull final String prefix, final Map<String, Object> params) {
    if (MapUtils.isEmpty(params)) {
      return prefix;
    }
    Set<Map.Entry<String, Object>> entrySet = params.entrySet();
    StringBuilder result = new StringBuilder();
    Map.Entry<String, Object> firstEntry = null;
    for (Map.Entry<String, Object> entry : entrySet) {
      firstEntry = entry;
      result.append(entry.getKey()).append("=").append(entry.getValue());
      break;
    }
    entrySet.remove(firstEntry);
    for (Map.Entry<String, Object> entry : entrySet) {
      result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
    }
    return prefix + result;
  }

  /**
   * 键值对参数转换为 URL 参数，含前缀“?”
   *
   * @param params 参数
   * @return URL 参数
   */
  public static String toUrlParams(final Map<String, Object> params) {
    return toUrlParams("?", params);
  }

  /**
   * 获取请求参数长度
   *
   * @param bodyParams 请求参数
   * @return 请求参数长度
   */
  private static int getContentLength(@NonNull final Map<String, Object> bodyParams) {
    if (MapUtils.isEmpty(bodyParams)) {
      return 0;
    }
    String s = JsonUtils.toJson(bodyParams);
    s = CommonPattern.LEFT_CURLY_BRACES.matcher(s).replaceAll("%7B");
    s = CommonPattern.DOUBLE_QUOTATION_MARK.matcher(s).replaceAll("%22");
    s = CommonPattern.COLON.matcher(s).replaceAll("%3A");
    s = CommonPattern.LEFT_SQUARE_BRACKET.matcher(s).replaceAll("%5B");
    s = CommonPattern.RIGHT_SQUARE_BRACKET.matcher(s).replaceAll("%5D");
    s = CommonPattern.COMMA.matcher(s).replaceAll("%2C");
    s = CommonPattern.RIGHT_CURLY_BRACES.matcher(s).replaceAll("%7D");
    return s.length();
  }

  /**
   * 同步请求
   *
   * @param requestMethod 请求方法
   * @param url           请求地址
   * @param contentType   内容类型
   * @param params        参数
   * @param headers       消息头
   * @param resultClass   响应体需要转换的类型
   * @param <T>           返回类型
   * @return 响应体
   */
  protected static <T> T sync(@NonNull final String requestMethod, @NonNull final String url, final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    if (StringUtils.isBlank(url)) {
      throw new IllegalArgumentException("Url: should not be blank");
    }
    SHttpTask task = sync(url);
    // 添加内容类型
    if (StringUtils.isNotBlank(contentType)) {
      task.bodyType(contentType);
    }
    // 添加参数
    if (MapUtils.isNotEmpty(params)) {
      if (ReqMethodConstant.GET.equals(requestMethod) || ReqMethodConstant.DELETE.equals(requestMethod)) {
        task = task.addUrlPara(params);
      } else if (ReqMethodConstant.POST.equals(requestMethod) || ReqMethodConstant.PATCH.equals(requestMethod) || ReqMethodConstant.PUT.equals(requestMethod)) {
        task.addBodyPara(params);
      }
    }
    // 添加 Header
    if (MapUtils.isNotEmpty(headers)) {
      task.addHeader(headers);
    }
    // 替换 OkHttp 默认的 Accept
    if (MapUtils.isEmpty(headers) || headers.get(HeaderConstant.USER_AGENT) == null) {
      task.addHeader(HeaderConstant.USER_AGENT, HeaderConstant.USER_AGENT_X);
    }
    // 请求
    HttpResult result = task.request(requestMethod);
    if (result.getState() != HttpResult.State.RESPONSED) {
      IOException error = result.getError();
      log.error(error.getMessage(), error);
      return null;
    }
    HttpResult.Body body = result.getBody();

    // 根据不同返回类型返回结果
    if (HttpResult.Body.class.equals(resultClass)) {
      return (T) body;
    }
    // String
    else if (String.class.equals(resultClass)) {
      return (T) body.toString();
    }
    // jackson
    else if (ObjectNode.class.equals(resultClass) || JsonNode.class.equals(resultClass) || ArrayNode.class.equals(resultClass)) {
      try {
        return (T) new ObjectMapper().readTree(body.toString());
      } catch (JsonProcessingException e) {
        log.error(e.getMessage(), e);
      }
    }
    // fastjson
    else if (JSON.class.equals(resultClass)) {
      return (T) JSON.parseObject(body.toString());
    } else if (JSONObject.class.equals(resultClass)) {
      return (T) JSON.parseObject(body.toString());
    } else if (JSONArray.class.equals(resultClass)) {
      return (T) JSON.parseArray(body.toString());
    }
    // gson
    else if (JsonElement.class.equals(resultClass)) {
      return (T) JsonParser.parseString(body.toString());
    } else if (JsonObject.class.equals(resultClass)) {
      JsonElement jsonElement = JsonParser.parseString(body.toString());
      return (T) jsonElement.getAsJsonObject();
    } else if (JsonArray.class.equals(resultClass)) {
      JsonElement jsonElement = JsonParser.parseString(body.toString());
      return (T) jsonElement.getAsJsonArray();
    }
    return body.toBean(resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T getByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, contentType, params, headers, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T getByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, null, params, headers, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T getByHeader(@NonNull final String url, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, null, null, headers, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url     请求地址
   * @param params  参数
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body getByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.GET, url, null, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 GET
   *
   * @param url     请求地址
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body getByHeader(@NonNull final String url, final Map<String, String> headers) {
    return sync(ReqMethodConstant.GET, url, null, null, headers, HttpResult.Body.class);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T get(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, contentType, params, null, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T get(@NonNull final String url, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, null, params, null, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url         请求地址
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T get(@NonNull final String url, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.GET, url, null, null, null, resultClass);
  }

  /**
   * 同步 GET
   *
   * @param url    请求地址
   * @param params 参数
   * @return 响应体
   */
  public static HttpResult.Body get(@NonNull final String url, final Map<String, Object> params) {
    return sync(ReqMethodConstant.GET, url, null, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 GET
   *
   * @param url 请求地址
   * @return 响应体
   */
  public static HttpResult.Body get(@NonNull final String url) {
    return sync(ReqMethodConstant.GET, url, null, null, null, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T postByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, contentType, params, headers, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T postByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, null, params, headers, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T postByHeader(@NonNull final String url, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, null, null, headers, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @return 响应体
   */
  public static HttpResult.Body postByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.POST, url, contentType, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url     请求地址
   * @param params  参数
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body postByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.POST, url, null, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url     请求地址
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body postByHeader(@NonNull final String url, final Map<String, String> headers) {
    return sync(ReqMethodConstant.POST, url, null, null, headers, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T post(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, contentType, params, null, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T post(@NonNull final String url, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, null, params, null, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T post(@NonNull final String url, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.POST, url, null, null, null, resultClass);
  }

  /**
   * 同步 POST
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @return 响应体
   */
  public static HttpResult.Body post(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params) {
    return sync(ReqMethodConstant.POST, url, contentType, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url    请求地址
   * @param params 参数
   * @return 响应体
   */
  public static HttpResult.Body post(@NonNull final String url, final Map<String, Object> params) {
    return sync(ReqMethodConstant.POST, url, null, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 POST
   *
   * @param url 请求地址
   * @return 响应体
   */
  public static HttpResult.Body post(@NonNull final String url) {
    return sync(ReqMethodConstant.POST, url, null, null, null, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T putByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, contentType, params, headers, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T putByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, null, params, headers, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T putByHeader(@NonNull final String url, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, null, null, headers, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @return 响应体
   */
  public static HttpResult.Body putByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PUT, url, contentType, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url     请求地址
   * @param params  参数
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body putByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PUT, url, null, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url     请求地址
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body putByHeader(@NonNull final String url, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PUT, url, null, null, headers, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T put(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, contentType, params, null, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T put(@NonNull final String url, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, null, params, null, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T put(@NonNull final String url, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PUT, url, null, null, null, resultClass);
  }

  /**
   * 同步 PUT
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @return 响应体
   */
  public static HttpResult.Body put(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params) {
    return sync(ReqMethodConstant.PUT, url, contentType, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url    请求地址
   * @param params 参数
   * @return 响应体
   */
  public static HttpResult.Body put(@NonNull final String url, final Map<String, Object> params) {
    return sync(ReqMethodConstant.PUT, url, null, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 PUT
   *
   * @param url 请求地址
   * @return 响应体
   */
  public static HttpResult.Body put(@NonNull final String url) {
    return sync(ReqMethodConstant.PUT, url, null, null, null, HttpResult.Body.class);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patchByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, contentType, params, headers, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patchByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, null, params, headers, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patchByHeader(@NonNull final String url, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, null, null, headers, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @return 响应体
   */
  public static HttpResult.Body patchByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PATCH, url, contentType, params, headers, HttpResult.Body.class);
  }


  /**
   * 同步 PATCH
   *
   * @param url     请求地址
   * @param params  参数
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body patchByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PATCH, url, null, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 PATCH
   *
   * @param url     请求地址
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body patchByHeader(@NonNull final String url, final Map<String, String> headers) {
    return sync(ReqMethodConstant.PATCH, url, null, null, headers, HttpResult.Body.class);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patch(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, contentType, params, null, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patch(@NonNull final String url, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, null, params, null, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T patch(@NonNull final String url, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.PATCH, url, null, null, null, resultClass);
  }

  /**
   * 同步 PATCH
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @return 响应体
   */
  public static HttpResult.Body patch(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params) {
    return sync(ReqMethodConstant.PATCH, url, contentType, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 PATCH
   *
   * @param url    请求地址
   * @param params 参数
   * @return 响应体
   */
  public static HttpResult.Body patch(@NonNull final String url, final Map<String, Object> params) {
    return sync(ReqMethodConstant.PATCH, url, null, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 PATCH
   *
   * @param url 请求地址
   * @return 响应体
   */
  public static HttpResult.Body patch(@NonNull final String url) {
    return sync(ReqMethodConstant.PATCH, url, null, null, null, HttpResult.Body.class);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T deleteByHeader(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, contentType, params, headers, resultClass);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param params      参数
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T deleteByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, null, params, headers, resultClass);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param headers     消息头
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T deleteByHeader(@NonNull final String url, final Map<String, String> headers, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, null, null, headers, resultClass);
  }


  /**
   * 同步 DELETE
   *
   * @param url     请求地址
   * @param params  参数
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body deleteByHeader(@NonNull final String url, final Map<String, Object> params, final Map<String, String> headers) {
    return sync(ReqMethodConstant.DELETE, url, null, params, headers, HttpResult.Body.class);
  }

  /**
   * 同步 DELETE
   *
   * @param url     请求地址
   * @param headers 消息头
   * @return 响应体
   */
  public static HttpResult.Body deleteByHeader(@NonNull final String url, final Map<String, String> headers) {
    return sync(ReqMethodConstant.DELETE, url, null, null, headers, HttpResult.Body.class);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param contentType 内容类型
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T delete(@NonNull final String url, @NonNull final String contentType, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, contentType, params, null, resultClass);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param params      参数
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T delete(@NonNull final String url, final Map<String, Object> params, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, null, params, null, resultClass);
  }

  /**
   * 同步 DELETE
   *
   * @param url         请求地址
   * @param resultClass 响应体需要转换的类型
   * @param <T>         返回类型
   * @return 响应体
   */
  public static <T> T delete(@NonNull final String url, @NonNull final Class<T> resultClass) {
    return sync(ReqMethodConstant.DELETE, url, null, null, null, resultClass);
  }

  /**
   * 同步 DELETE
   *
   * @param url    请求地址
   * @param params 参数
   * @return 响应体
   */
  public static HttpResult.Body delete(@NonNull final String url, final Map<String, Object> params) {
    return sync(ReqMethodConstant.DELETE, url, null, params, null, HttpResult.Body.class);
  }

  /**
   * 同步 DELETE
   *
   * @param url 请求地址
   * @return 响应体
   */
  public static HttpResult.Body delete(@NonNull final String url) {
    return sync(ReqMethodConstant.DELETE, url, null, null, null, HttpResult.Body.class);
  }
}
