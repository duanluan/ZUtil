package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Slf4j
@DisplayName("加密解密工具类测试")
public class SecurityUtilsTest {

  private void println(Object source) {
    System.out.println(source);
  }

  @DisplayName("JDK 和 BouncyCastle 的 DES 算法 ECB 模式加密性能测试")
  @Test
  void desJdkAndBouncyCastle() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException, InvalidKeySpecException {
    byte[] key = ArrayUtils.toBytes(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'});
    byte[] in = ArrayUtils.toBytes(new char[]{'中'});

    // JDK
    int startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    DESKeySpec desKey = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(desKey);
    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
    int inLen = in.length;
    byte[] bytes = cipher.doFinal(ArrayUtils.fill(in, inLen, inLen + (8 - inLen % 8), (byte) 0));
    println("JDK: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
    System.out.println(ByteUtils.toHexString(bytes));

    // SecurityUtils
    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    bytes = SecurityUtils.desEcb(key, in, true);
    println("BouncyCastle: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
    System.out.println(ByteUtils.toHexString(bytes));
  }

  @DisplayName("DES 算法加解密")
  @Test
  void des() throws InvalidCipherTextException {
    String key = "123";
    String icv = "456";
    String in = "我爱中国";

    String result = SecurityUtils.desEcb(key, in, true);
    println("ECB 模式加密：" + result);
    System.out.println("ECB 模式解密：" + SecurityUtils.desEcb(key, result, false));

    result = SecurityUtils.desCbc(key, icv, in, true);
    println("CBC 模式加密：" + result);
    System.out.println("CBC 模式解密：" + SecurityUtils.desCbc(key, icv, result, false));
  }
}
