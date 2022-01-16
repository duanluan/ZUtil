package top.zhogjianhao;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.*;
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
  void desJdkAndBouncyCastle() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidCipherTextException, InvalidKeySpecException, NoSuchProviderException {
    byte[] key = ArrayUtils.toBytes(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'});
    byte[] in = ArrayUtils.toBytes(new char[]{'中'});

    // JDK
    int startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    DESKeySpec desKey = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(desKey);
    // 使用 BouncyCastle 提供填充类型
    Security.addProvider(new BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding","BC");
    cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
    byte[] bytes = cipher.doFinal(in);
    println("JDK: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
    System.out.println(ByteUtils.toHexString(bytes));

    // SecurityUtils
    startTime = LocalDateTime.now().get(ChronoField.MILLI_OF_DAY);
    bytes = SecurityUtils.desEcb(key, in, true,new ZeroBytePadding() );
    println("BouncyCastle: " + (LocalDateTime.now().get(ChronoField.MILLI_OF_DAY) - startTime));
    System.out.println(ByteUtils.toHexString(bytes)); 
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
