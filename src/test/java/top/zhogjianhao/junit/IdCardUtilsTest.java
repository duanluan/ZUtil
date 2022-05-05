package top.zhogjianhao.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.zhogjianhao.idcard.IdCardUtils;

@Slf4j
@DisplayName("身份证工具类测试")
public class IdCardUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  private static final String NUMBER_CHINA_1 = "320412200001001";
  private static final String NUMBER_CHINA_2 = "32041220000101039X";

  @DisplayName("getProvinceCityDistrictCode：获取省市区编码")
  @Test
  void getProvinceCityDistrictCode() {
    println("中国一代身份证省级编码：" + IdCardUtils.getProvinceCode(NUMBER_CHINA_1));
    println("中国二代身份证省级编码：" + IdCardUtils.getProvinceCode(NUMBER_CHINA_2));
    println("中国一代身份证市级编码：" + IdCardUtils.getCityCode(NUMBER_CHINA_1));
    println("中国二代身份证市级编码：" + IdCardUtils.getCityCode(NUMBER_CHINA_2));
    println("中国一代身份证区级编码：" + IdCardUtils.getDistrictCode(NUMBER_CHINA_1));
    println("中国二代身份证区级编码：" + IdCardUtils.getDistrictCode(NUMBER_CHINA_2));
  }
}
