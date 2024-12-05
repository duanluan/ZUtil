package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.crypto.AesUtil;
import top.csaf.crypto.DesUtil;
import top.csaf.crypto.enums.Padding;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@DisplayName("AES 加解密工具类测试")
class AesUtilTest {

  private static final String KEY = "1234567890abcdef";
  private static final String IV = "fedcba0987654321";
  private static final String DATA = "我爱中国";
  private static final String ENCRYPTED_DATA = "26779cd3ad389f817a116f08965b82ad";

  @DisplayName("加密")
  @Test
  void encrypt() {
    assertEquals(ENCRYPTED_DATA, AesUtil.encryptHex(DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }

  @DisplayName("解密")
  @Test
  void decrypt() {
    assertEquals(DATA, AesUtil.decryptHex(ENCRYPTED_DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }
}
