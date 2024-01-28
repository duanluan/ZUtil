package top.csaf.crypto.enums;

/**
 * 来源自 {@link org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher#engineSetMode(String)}
 */
public enum Mode {
  /**
   * 电子密码本模式（ECB，Electronic Codebook）
   */
  ECB("ECB"),
  /**
   * 密码块链接模式（CBC，Cipher Block Chaining）
   */
  CBC("CBC"),
  /**
   * 输出反馈模式（OFB，Output Feedback）
   */
  OFB("OFB"),
  /**
   * 密文反馈模式（CFB，Cipher FeedBack）
   */
  CFB("CFB"),
  /**
   * 密码反馈模式（PGPCFB，Password FeedBack）
   */
  PGPCFB("PGPCFB"),
  /**
   * 密码反馈模式（PGPCFBWITHIV，Password FeedBack With IV）
   */
  PGPCFBWITHIV("PGPCFBWITHIV"),
  /**
   *
   */
  OPENPGPCFB("OPENPGPCFB"),
  FF1("FF1"),
  FF3_1("FF3-1"),
  SIC("SIC"),
  /**
   * 计数器模式（CTR，Counter）
   */
  CTR("CTR"),
  GOFB("GOFB"),
  GCFB("GCFB"),
  CTS("CTS"),
  CCM("CCM"),
  OCB("OCB"),
  EAX("EAX"),
  GCM_SIV("GCM-SIV"),
  GCM("GCM"),
  ;

  private String value;

  Mode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
