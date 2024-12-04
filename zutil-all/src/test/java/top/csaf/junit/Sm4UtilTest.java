package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.crypto.DesUtil;
import top.csaf.crypto.Sm4Util;
import top.csaf.crypto.enums.Padding;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@DisplayName("SM4 加解密工具类测试")
class Sm4UtilTest {

  private static final String KEY = "1234567890abcdef";
  private static final String IV = "fedcba0987654321";
  private static final String DATA = "我爱中国";
  private static final String ENCRYPTED_DATA = "d5d51d8524407b0cc5eee2ce22479747";

  @DisplayName("加密")
  @Test
  void encrypt() {
    assertEquals(ENCRYPTED_DATA, Sm4Util.encryptHex(DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }

  @DisplayName("解密")
  @Test
  void decrypt() {
    assertEquals(DATA, Sm4Util.decryptHex(ENCRYPTED_DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }
}
