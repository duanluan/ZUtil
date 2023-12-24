package top.csaf.xml;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import top.csaf.json.JsonUtil;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class XmlUtil {

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param arg1      参数 1
   * @param arg2      参数 2
   * @return Document
   */
  protected static Document commonRead(SAXReader saxReader, Object arg1, Object arg2) {
    if (saxReader == null) {
      saxReader = new SAXReader();
    }
    try {
      if (arg1 instanceof URL) {
        return saxReader.read((URL) arg1);
      } else if (arg1 instanceof File) {
        return saxReader.read((File) arg1);
      } else if (arg1 instanceof Reader) {
        if (arg2 instanceof String) {
          return saxReader.read((Reader) arg1, (String) arg2);
        }
        return saxReader.read((Reader) arg1);
      } else if (arg1 instanceof InputSource) {
        return saxReader.read((InputSource) arg1);
      } else if (arg1 instanceof InputStream) {
        if (arg2 instanceof String) {
          return saxReader.read((InputStream) arg1, (String) arg2);
        }
        return saxReader.read((InputStream) arg1);
      } else if (arg1 instanceof String) {
        return saxReader.read((String) arg1);
      }
      throw new IllegalArgumentException("Arg1, Arg2: should be URL, File, Reader, InputSource, InputStream, String, Reader + String or InputStream + String");
    } catch (DocumentException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param url       URL
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final URL url) {
    return commonRead(saxReader, url, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param file      文件
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final File file) {
    return commonRead(saxReader, file, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param reader    字符流读取器
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final Reader reader) {
    return commonRead(saxReader, reader, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param in        输入源
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final InputSource in) {
    return commonRead(saxReader, in, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param in        输入流
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final InputStream in) {
    return commonRead(saxReader, in, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param systemId  文件名 URL
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final String systemId) {
    return commonRead(saxReader, systemId, null);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param reader    字符流读取器
   * @param systemId  文件名 URL
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final Reader reader, @NonNull final String systemId) {
    return commonRead(saxReader, reader, systemId);
  }

  /**
   * 读取
   *
   * @param saxReader 读取器
   * @param in        输入流
   * @param systemId  文件名 URL
   * @return Document
   */
  public static Document read(SAXReader saxReader, @NonNull final InputStream in, @NonNull final String systemId) {
    return commonRead(saxReader, in, systemId);
  }

  /**
   * 读取
   *
   * @param url URL
   * @return Document
   */
  public static Document read(@NonNull final URL url) {
    return commonRead(null, url, null);
  }

  /**
   * 读取
   *
   * @param file 文件
   * @return Document
   */
  public static Document read(@NonNull final File file) {
    return commonRead(null, file, null);
  }

  /**
   * 读取
   *
   * @param reader 字符流读取器
   * @return Document
   */
  public static Document read(@NonNull final Reader reader) {
    return commonRead(null, reader, null);
  }

  /**
   * 读取
   *
   * @param in 输入源
   * @return Document
   */
  public static Document read(@NonNull final InputSource in) {
    return commonRead(null, in, null);
  }

  /**
   * 读取
   *
   * @param in 输入流
   * @return Document
   */
  public static Document read(@NonNull final InputStream in) {
    return commonRead(null, in, null);
  }

  /**
   * 读取
   *
   * @param systemId 文件名 URL
   * @return Document
   */
  public static Document read(@NonNull final String systemId) {
    return commonRead(null, systemId, null);
  }

  /**
   * 读取
   *
   * @param reader   字符流读取器
   * @param systemId 文件名 URL
   * @return Document
   */
  public static Document read(@NonNull final Reader reader, @NonNull final String systemId) {
    return commonRead(null, reader, systemId);
  }

  /**
   * 读取
   *
   * @param in       输入流
   * @param systemId 文件名 URL
   * @return Document
   */
  public static Document read(@NonNull final InputStream in, @NonNull final String systemId) {
    return commonRead(null, in, systemId);
  }

  /**
   * 解析 XML 文本
   *
   * @param text XML 文本
   * @return Document
   */
  public static Document parse(@NonNull final String text) {
    try {
      return DocumentHelper.parseText(text);
    } catch (DocumentException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  /**
   * 转文件
   *
   * @param doc    Document
   * @param format 格式化对象
   * @param dir    保存目录
   */
  public static void toFile(@NonNull final Document doc, @NonNull final OutputFormat format, @NonNull final File dir) {
    try {
      XMLWriter writer = new XMLWriter(new FileOutputStream(dir), format);
      writer.write(doc);
      writer.close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 转文件
   *
   * @param doc     Document
   * @param format  格式化对象
   * @param dirPath 保存目录
   */
  public static void toFile(@NonNull final Document doc, @NonNull final OutputFormat format, @NonNull final String dirPath) {
    try {
      XMLWriter writer = new XMLWriter(new FileOutputStream(dirPath), format);
      writer.write(doc);
      writer.close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 转文件
   *
   * @param doc Document
   * @param dir 保存目录
   */
  public static void toFile(@NonNull final Document doc, @NonNull final File dir) {
    try {
      XMLWriter writer = new XMLWriter(new FileOutputStream(dir));
      writer.write(doc);
      writer.close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 转文件
   *
   * @param doc     Document
   * @param dirPath 保存目录
   */
  public static void toFile(@NonNull final Document doc, @NonNull final String dirPath) {
    try {
      XMLWriter writer = new XMLWriter(new FileOutputStream(dirPath));
      writer.write(doc);
      writer.close();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * Element 转 JSON 递归
   *
   * @param element 节点
   * @param isTrim  是否去除空格
   * @return JSON 字符串
   */
  protected static Object toJsonRecursion(@NonNull final Element element, final boolean isTrim) {
    Map<String, Object> map = new HashMap<>();
    List<Element> elementList = element.elements();
    // 最子节点
    if (elementList.size() == 0) {
      return isTrim ? element.getTextTrim() : element.getText();
    }
    // 单个子节点
    else if (elementList.size() == 1) {
      Map<String, Object> map1 = new HashMap<>();
      Element element1 = element.elements().get(0);
      map1.put(element1.getName(), toJsonRecursion(element1, isTrim));
      map.put(element.getName(), map1);
    }
    // 多个子节点
    else {
      // 如果子节点的名称不同，则还是一个 map，即一个节点中子节点的名称要么全部一样，要么全部不一样
      if (!elementList.get(0).getName().equals(elementList.get(1).getName())) {
        Map<String, Object> map1 = new HashMap<>();
        for (Element element1 : elementList) {
          // 假如为最子节点，此处定义了最子节点的 key
          map1.put(element1.getName(), toJsonRecursion(element1, isTrim));
        }
        return map1;
      } else {
        List<Object> list = new ArrayList<>();
        for (Element element1 : elementList) {
          list.add(toJsonRecursion(element1, isTrim));
        }
        return list;
      }
    }
    return map;
  }

  /**
   * Element 转 JSON
   *
   * @param element  节点
   * @param isTrim   是否去除空格
   * @param features 序列化行为
   * @return JSON 字符串
   */
  public static String toJson(@NonNull final Element element, final boolean isTrim, final JSONWriter.Feature... features) {
    Object object = toJsonRecursion(element, isTrim);
    // 如果转换后为 Map，且 Map 中 key 为传入的参数的 key，则将 value 转换为 JSON 字符串，保证一致性
    if (object instanceof Map) {
      Map.Entry<?, ?> entry = ((Map<?, ?>) object).entrySet().iterator().next();
      if (entry.getKey().equals(element.getName())) {
        return JsonUtil.toJson(entry.getValue(), features);
      }
    }
    return JsonUtil.toJson(object, features);
  }

  /**
   * Element 转对象
   *
   * @param element  此节点下的所有子节点
   * @param isTrim   是否去除空格
   * @param clazz    对象类型
   * @param features 反序列化行为
   * @param <T>      对象类型
   * @return 对象
   */
  public static <T> T parseObject(@NonNull final Element element, boolean isTrim, final Class<T> clazz, final JSONReader.Feature... features) {
    return JsonUtil.parseObject(toJson(element, isTrim), clazz, features);
  }

  /**
   * Element 转集合
   *
   * @param element  此节点下的所有子节点
   * @param isTrim   是否去除空格
   * @param clazz    对象类型
   * @param features 反序列化行为
   * @param <T>      对象类型
   * @return 集合
   */
  public static <T> List<T> parseArray(@NonNull final Element element, boolean isTrim, final Class<T> clazz, final JSONReader.Feature... features) {
    return JsonUtil.parseArray(toJson(element, isTrim), clazz, features);
  }
}
