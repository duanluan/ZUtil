package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.crypto.DesUtil;
import top.csaf.crypto.enums.Padding;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@DisplayName("加密解密工具类测试")
class DesUtilTest {

  private static final String KEY = "12345678";
  private static final String IV = "87654321";
  private static final String DATA = "我爱中国";
  private static final String ENCRYPTED_DATA = "3ef76bbdbd42334140329b65efe5864f";

  @DisplayName("DES 加密")
  @Test
  void encrypt() {
    assertEquals(ENCRYPTED_DATA, DesUtil.encryptHex(DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }

  @DisplayName("DES 解密")
  @Test
  void decrypt() {
    assertEquals(DATA, DesUtil.decryptHex(ENCRYPTED_DATA, KEY, IV, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }
}
