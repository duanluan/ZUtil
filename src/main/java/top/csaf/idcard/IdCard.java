package top.csaf.idcard;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 身份证信息
 */
@Data
public class IdCard implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 身份证号码
   */
  private String number;
  /**
   * 省级编码
   */
  private String provinceCode;
  /**
   * 市级编码
   */
  private String cityCode;
  /**
   * 区级编码
   */
  private String districtCode;
  /**
   * 出生日期
   */
  private LocalDate birthday;
  /**
   * 年龄
   */
  private int age;
  /**
   * 性别：1: 男, 2: 女
   */
  private Integer gender;
  private String checkCode;
}
