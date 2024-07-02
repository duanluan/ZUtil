package top.csaf.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.idcard.IdCardUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description IdCard工具类测试
 * @Author Rick
 * @Date 2024/6/14 10:52
 **/
@DisplayName("IdCard工具类测试")
public class IdCardUtilTest {


  @DisplayName("校验身份证号码最后一位校验码")
  @Test
  void validateCheckCode() {
    // 校验最后一位为X
    String number = "11010519491231002X";
    boolean Xresult = IdCardUtil.validateCheckCode(number);
    assertTrue(Xresult);
    // 校验最后一位为Y
    number = "11010519491231002Y";
    boolean Yresult = IdCardUtil.validateCheckCode(number);
    assertFalse(Yresult);
    // 校验长度超过18位的情况
    String longNumber = "11010519491231002";
    assertThrows(IllegalArgumentException.class, () -> {
      IdCardUtil.validateCheckCode(longNumber);
    });
    String nullNumber = null;
    // 校验null值
    assertThrows(NullPointerException.class, () -> {
      IdCardUtil.validateCheckCode(nullNumber);
    });
  }

  @DisplayName("校验")
  @Test
  void validate() {
    // 校验最后一位为X
    String number = "11010519491231002X";
    boolean Xresult = IdCardUtil.validateCheckCode(number);
    assertTrue(Xresult);
    // 第17位不通过校验
    String validIdCard = "110101199003050276";
    assertFalse(IdCardUtil.validate(validIdCard));
    // 短
    String invalidLengthIdCard = "11010119900305027";
    assertFalse(IdCardUtil.validate(invalidLengthIdCard));
    // 长
    String invalidLengthIdCard2 = "1101011990030502766";
    assertFalse(IdCardUtil.validate(invalidLengthIdCard2));
    // 以0开头
    String invalidStartingZeroIdCard = "010101199003050276";
    assertFalse(IdCardUtil.validate(invalidStartingZeroIdCard));
    // 非法的省份
    String invalidProvinceCodeIdCard = "110101199003050276";
    assertFalse(IdCardUtil.validate(invalidProvinceCodeIdCard));
    // 非法的日期
    String invalidDateFormatIdCard = "110101199003330276";
    assertFalse(IdCardUtil.validate(invalidDateFormatIdCard));
    // 第15号校验码非法
    String invalidOrderCodeIdCard15 = "1101011990030002";
    assertFalse(IdCardUtil.validate(invalidOrderCodeIdCard15));
  }

  @DisplayName("获取省级编码")
  @Test
  void getProvinceCode() {
    // 带校验获取，校验通过
    String validId = "110101199001013515";
    String expectedProvinceCode = "11";
    String actualProvinceCode = IdCardUtil.getProvinceCode(validId, true);
    assertEquals(expectedProvinceCode, actualProvinceCode);
    // 带校验获取，校验不通过
    String invalidId = "1101011990101010013"; // Invalid check code
    String invalidProvinceCode = IdCardUtil.getProvinceCode(invalidId, true);
    assertNull(invalidProvinceCode);
    // 不带校验获取
    String noValidateId = "1101011990101010013";
    String noValidateProvinceCode = IdCardUtil.getProvinceCode(noValidateId, false);
    assertEquals(expectedProvinceCode, noValidateProvinceCode);
    // 入参给空字符
    String emptyId = "";
    String emptyProvinceCode = IdCardUtil.getProvinceCode(emptyId, true);
    assertNull(emptyProvinceCode);
    // 入参给null
    String nullId = null;
    assertThrows(NullPointerException.class, () -> {
      IdCardUtil.getProvinceCode(nullId, true);
    });
  }

  @DisplayName("获取市级编码")
  @Test
  void getCityCode() {
    // 带校验获取，校验通过
    String validId = "110101199001013515";
    String expectedCityCode = "01";
    String actualCityCode = IdCardUtil.getCityCode(validId, true);
    assertEquals(expectedCityCode, actualCityCode);
    // 带校验获取，校验不通过
    String invalidId = "1101011990101010013";
    String invalidCityCode = IdCardUtil.getCityCode(invalidId, true);
    assertNull(invalidCityCode);
    // 不带校验获取
    String noValidateId = "1101011990101010013";
    String noValidateCityCode = IdCardUtil.getCityCode(noValidateId, false);
    assertEquals(expectedCityCode, noValidateCityCode);
    // 入参给空字符
    String emptyId = "";
    String emptyCityCode = IdCardUtil.getCityCode(emptyId, true);
    assertNull(emptyCityCode);
    // 入参给null
    String nullId = null;
    assertThrows(NullPointerException.class, () -> {
      IdCardUtil.getCityCode(nullId, true);
    });
  }

  @DisplayName("获取区级编码")
  @Test
  void getDistrictCode() {
    // 带校验获取，校验通过
    String validId = "110101199001013515";
    String expectedDistrictCode = "01";
    String actualDistrictCode = IdCardUtil.getDistrictCode(validId, true);
    assertEquals(expectedDistrictCode, actualDistrictCode);
    // 带校验获取，校验不通过
    String invalidId = "1101011990101010013";
    String invalidDistrictCode = IdCardUtil.getDistrictCode(invalidId, true);
    assertNull(invalidDistrictCode);
    // 不带校验获取
    String noValidateId = "1101011990101010013";
    String noValidateDistrictCode = IdCardUtil.getDistrictCode(noValidateId, false);
    assertEquals(expectedDistrictCode, noValidateDistrictCode);
    // 入参给空字符
    String emptyId = "";
    String emptyDistrictCode = IdCardUtil.getDistrictCode(emptyId, true);
    assertNull(emptyDistrictCode);
    // 入参给null
    String nullId = null;
    assertThrows(NullPointerException.class, () -> {
      IdCardUtil.getDistrictCode(nullId, true);
    });
  }

  @DisplayName("获取出生日期")
  @Test
  void getBirthDay() {
    // 直接通过
    String idCard = "110101199001013515";
    LocalDate result = IdCardUtil.getBirthday(idCard, LocalDate.class, true);
    assertNotNull(result);
    assertEquals(LocalDate.of(1990, 1, 1), result);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    LocalDateTime LongResult = IdCardUtil.getBirthday(LongIdCard, LocalDateTime.class, true);
    assertNotNull(LongResult);
    assertEquals(LocalDateTime.of(1988, 9, 23, 0, 0, 0), LongResult);
  }

  @DisplayName("获取年龄")
  @Test
  void getAge() {
    // 直接通过
    String validIdCard = "110101199001013515"; // 一个有效的身份证号码
    int expectedAge = Period.between(LocalDate.of(1990, 1, 1), LocalDate.now()).getYears();
    int actualAge = IdCardUtil.getAge(validIdCard, true);
    assertEquals(expectedAge, actualAge);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    int longAge = IdCardUtil.getAge(LongIdCard, true);
    assertNotNull(longAge);
    assertEquals(LocalDateTime.of(1988, 9, 23, 0, 0, 0), longAge);
  }

  @DisplayName("获取性别编码")
  @Test
  void getGenderCode() {
    // 直接通过
    String validIdCard = "110101199001013515";
    Integer expectedGenderCode = 1;
    Integer actualGenderCode = IdCardUtil.getGenderCode(validIdCard, true);
    assertEquals(expectedGenderCode, actualGenderCode);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    Integer longGenderCode = IdCardUtil.getGenderCode(LongIdCard, true);
    assertNotNull(longGenderCode);
    assertEquals(longGenderCode, actualGenderCode);
  }

  @DisplayName("获取性别")
  @Test
  void getGender() {
    // 直接通过
    String validIdCard = "110101199001013515";
    Integer expectedGenderCode = 1;
    Integer actualGenderCode = IdCardUtil.getGender(validIdCard, true);
    assertEquals(expectedGenderCode, actualGenderCode);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    Integer longGenderCode = IdCardUtil.getGender(LongIdCard, true);
    assertNotNull(longGenderCode);
    assertEquals(longGenderCode, actualGenderCode);
  }

  @DisplayName("是否男性")
  @Test
  void isMale() {
    // 直接通过
    String validIdCard = "110101199001013515";
    boolean expectedGender = true;
    boolean isMale = Boolean.TRUE.equals(IdCardUtil.isMale(validIdCard, true));
    assertEquals(expectedGender, isMale);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    boolean isMaleLong = Boolean.TRUE.equals(IdCardUtil.isMale(LongIdCard, true));
    assertNotNull(isMaleLong);
    assertEquals(isMaleLong, expectedGender);
  }


  @DisplayName("是否女性")
  @Test
  void isFemale() {
    // 直接通过
    String validIdCard = "110101199001019642";
    boolean expectedGender = true;
    boolean isFeMale = Boolean.TRUE.equals(IdCardUtil.isFemale(validIdCard, true));
    assertEquals(expectedGender, isFeMale);
    // 带校验，且校验不通过
    String LongIdCard = "3307219880923456789";
    boolean isFeMaleLong = Boolean.TRUE.equals(IdCardUtil.isFemale(LongIdCard, true));
    assertNotNull(isFeMaleLong);
    assertEquals(isFeMaleLong, expectedGender);
  }
}
