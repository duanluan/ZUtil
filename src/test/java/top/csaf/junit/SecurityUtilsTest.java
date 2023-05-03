package top.csaf.junit;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import top.csaf.SecurityUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@DisplayName("加密解密工具类测试")
class SecurityUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("DES 算法加解密")
  @Test
  void des() throws InvalidCipherTextException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    String key = "12345678";
    String icv = "87654321";
    String in = "我爱中国";

    String result = SecurityUtils.desEcb(key, in, true, new PKCS7Padding());
    println("ECB 模式 PKCS7 填充加密：" + result);
    println("ECB 模式 PKCS7 填充解密：" + SecurityUtils.desEcb(key, result, false, new PKCS7Padding()));

    result = SecurityUtils.desCbc(key, icv, in, true, new PKCS7Padding());
    println("CBC 模式 PKCS7 填充加密：" + result);
    println("CBC 模式 PKCS7 填充解密：" + SecurityUtils.desCbc(key, icv, result, false, new PKCS7Padding()));
  }
}
