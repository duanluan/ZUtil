package top.csaf.crypto.enums;

/**
 * 分组加解密算法类型
 */
public enum BlockCipherType {
  /**
   * 数据加密标准（Data Encryption Standard）
   */
  DES("DES"),
  ;

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  BlockCipherType(String value) {
    this.value = value;
  }
}
