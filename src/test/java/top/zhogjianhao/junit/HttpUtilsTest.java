package top.zhogjianhao.junit;

import com.alibaba.fastjson2.JSONArray;
import com.ejlchina.okhttps.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.regex.RegExUtils;
import top.zhogjianhao.StringUtils;
import top.zhogjianhao.http.ContentTypeConstant;
import top.zhogjianhao.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@DisplayName("HTTP 工具类测试")
public class HttpUtilsTest {

  @DisplayName("获取 Media Types")
  @Test
  void getMediaTypes() {
    HttpResult.Body body = HttpUtils.get("https://www.iana.org/assignments/media-types/media-types.xhtml");
    if (body == null) {
      log.error("请求失败");
      return;
    }
    List<String> nameList = new ArrayList<>();
    List<String> valueList = new ArrayList<>();

    Document doc = Jsoup.parse(body.toString());
    Elements templates = doc.select("table[id^='table-'] tbody tr td:eq(1)");
    for (Element template : templates) {
      String text = template.text();
      if (StringUtils.isBlank(text)) {
        continue;
      }
      String[] texts = text.split("/");
      // 数字开头的变量名在前面加下划线
      String name = texts[1].replaceAll("[+\\-.]", "_").toUpperCase();
      if (RegExUtils.isMatch(name.split("")[0], "\\d")) {
        name = "_" + name;
      }
      // 如果变量名重名，则在最后加类型后缀
      int index = nameList.indexOf(name);
      if (index != -1) {
        String newName1 = nameList.get(index) + "_" + valueList.get(index).split("/")[0].toUpperCase();
        String newName2 = name + "_" + texts[0].toUpperCase();
        // 如果还是相等说明重复
        if (newName1.equals(newName2)) {
          continue;
        } else {
          nameList.set(index, newName1);
          nameList.add(newName2);
        }
      } else {
        nameList.add(name);
      }
      valueList.add(text);
    }
    for (int i = 0; i < nameList.size(); i++) {
      System.out.println("public static final String " + nameList.get(i) + " = \"" + valueList.get(i) + "\";");
    }
  }

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("sync：同步请求")
  @Test
  void sync() throws InterruptedException {
    String baseUrl = "http://localhost:3000/posts";
    // 新增
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userId", 1);
    paramMap.put("title", "title2");
    paramMap.put("content", "some content");
    HttpUtils.post(baseUrl, ContentTypeConstant.X_WWW_FORM_URLENCODED, paramMap);
    HttpUtils.post(baseUrl, paramMap);
    // 太快啦！等等 json-server
    Thread.sleep(500);
    // 更新
    paramMap = new HashMap<>();
    paramMap.put("content", "new content");
    HttpUtils.patch(baseUrl + "/1", paramMap);
    // 太快啦！等等 json-server
    Thread.sleep(500);
    HttpUtils.put(baseUrl + "/2", paramMap);
    // 太快啦！等等 json-server
    Thread.sleep(500);
    // 删除
    HttpUtils.delete(baseUrl + "/3");
    // 太快啦！等等 json-server
    Thread.sleep(500);
    // 查询
    paramMap = new HashMap<>();
    paramMap.put("userId", 1);
    println(HttpUtils.get(baseUrl, paramMap, JSONArray.class).toJSONString());
  }
}
