package top.csaf.crypto.enums;

/**
 * 来源自 {@link org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher#engineSetPadding(java.lang.String)}
 */
public enum Padding {
  /**
   * No padding.
   */
  NO("NoPadding"),
  ZERO("ZEROBYTEPADDING"),
  PKCS5("PKCS5PADDING"),
  PKCS7("PKCS7PADDING"),
  ISO_10126("ISO10126PADDING"),
  ISO_10126_2("ISO10126-2PADDING"),
  ANSI_X923("X923PADDING"),
  ISO_7816_4("ISO7816-4PADDING"),
  ISO_9797_1("ISO9797-1PADDING"),
  TBCPADDING("TBCPADDING"),
  ;

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  Padding(String value) {
    this.value = value;
  }
}
