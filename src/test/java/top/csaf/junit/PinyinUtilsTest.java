package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.ArrayUtils;
import top.csaf.StringUtils;
import top.csaf.charset.StandardCharsets;
import top.csaf.http.HttpUtils;
import top.csaf.io.FileUtils;
import top.csaf.pinyin.PinyinFeature;
import top.csaf.pinyin.PinyinUtils;
import top.csaf.regex.RegExUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@DisplayName("拼音工具类测试")
class PinyinUtilsTest {

  private static final Pattern PINYIN_PATTERN_A = Pattern.compile("[āáǎà]");
  private static final Pattern PINYIN_PATTERN_O = Pattern.compile("[ōóǒò]");
  private static final Pattern PINYIN_PATTERN_E = Pattern.compile("[ēéěè]");
  private static final Pattern PINYIN_PATTERN_I = Pattern.compile("[īíǐì]");
  private static final Pattern PINYIN_PATTERN_U = Pattern.compile("[ūúǔù]");
  private static final Pattern PINYIN_PATTERN_V = Pattern.compile("[üǖǘǚǜ]");

  @DisplayName("转换拼音数据")
  @Test
  void convertPinyinData() throws IOException {
    List<String> result = new ArrayList<>();
    // 获取拼音数据文件
    String pinyinDataStr = HttpUtils.get("https://raw.githubusercontent.com/mozillazg/pinyin-data/master/pinyin.txt", String.class);
    String[] pinyinDataArr = pinyinDataStr.split("\n");
    for (int i = 2; i < pinyinDataArr.length; i++) {
      String[] pinyins = pinyinDataArr[i].split(":")[1].split("#");
      String pinyin = pinyins[0].trim();
      String pinyin1 = RegExUtils.replaceAll(pinyin, PINYIN_PATTERN_A, "a");
      pinyin1 = RegExUtils.replaceAll(pinyin1, PINYIN_PATTERN_O, "o");
      pinyin1 = RegExUtils.replaceAll(pinyin1, PINYIN_PATTERN_E, "e");
      pinyin1 = RegExUtils.replaceAll(pinyin1, PINYIN_PATTERN_I, "i");
      pinyin1 = RegExUtils.replaceAll(pinyin1, PINYIN_PATTERN_U, "u");
      pinyin1 = RegExUtils.replaceAll(pinyin1, PINYIN_PATTERN_V, "v");

      result.add(pinyin + "#" + pinyins[1].trim() + "#" + pinyin1);
    }
    File pinyinDataFile = new File(FileUtils.getTempDirectory() + "/pinyin.txt");
    FileUtils.writeLines(pinyinDataFile, result);

    // 从拼音数据文件中读取数据
    Map<String, String> pinyinDataWithToneMap = new HashMap<>();
    Map<String, String> pinyinDataMap = new HashMap<>();
    FileUtils.readLines(pinyinDataFile, StandardCharsets.UTF_8).forEach(line -> {
      String[] arr = StringUtils.split(line, "#");
      pinyinDataWithToneMap.put(arr[1], arr[0]);
      pinyinDataMap.put(arr[1], arr[2]);
    });

    // 去声调 Map 多音字去重
    for (Map.Entry<String, String> entry : pinyinDataMap.entrySet()) {
      String[] pinyins = StringUtils.split(entry.getValue(), ",");
      if (pinyins.length > 1) {
        pinyinDataMap.put(entry.getKey(), StringUtils.join(ArrayUtils.deduplicate(entry.getValue().split(",")), ","));
      }
    }

    // 将拼音数据的字节码写入文件
    try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(os)) {
      oos.writeObject(pinyinDataWithToneMap);
      byte[] bytes = os.toByteArray();
      FileUtils.writeByteArrayToFile(new File(FileUtils.getUserDir() + "/src/main/resources/pinyin/pinyinDataWithTone.dat"), bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try (ByteArrayOutputStream os = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(os)) {
      oos.writeObject(pinyinDataMap);
      byte[] bytes = os.toByteArray();
      FileUtils.writeByteArrayToFile(new File(FileUtils.getUserDir() + "/src/main/resources/pinyin/pinyinData.dat"), bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void test() {
    PinyinFeature.setFirstWordInitialCap(true);
    PinyinFeature.setSecondWordInitialCap(true);
    PinyinFeature.setHasSeparatorByNotPinyinAround(false);
    System.out.println(PinyinUtils.get("好好学习，，为国为民", false, false, " "));
    System.out.println(PinyinUtils.getFirst("好好学习，，为国为民", false));
  }
}
