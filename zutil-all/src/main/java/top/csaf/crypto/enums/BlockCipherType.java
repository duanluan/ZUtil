package top.csaf.crypto.enums;

/**
 * 分组加解密算法类型
 */
public enum BlockCipherType {
  /**
   * 数据加密标准（Data Encryption Standard）
   */
  DES("DES"),
  /**
   * 信息安全技术 SM4 分组密码算法：<a href="https://std.samr.gov.cn/gb/search/gbDetailed?id=71F772D81199D3A7E05397BE0A0AB82A">信息安全技术 SM4 分组密码算法 - 国家标准 - 全国标准信息公共服务平台</a>
   */
  SM4("SM4"),
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
