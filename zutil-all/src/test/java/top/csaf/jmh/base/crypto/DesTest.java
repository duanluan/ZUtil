package top.csaf.jmh.base.crypto;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.crypto.DesUtil;
import top.csaf.crypto.enums.Padding;
import top.csaf.lang.ArrayUtil;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

/**
 * DES 算法 ECB 模式加密性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class DesTest {

  public static void main(String[] args) {
    // 结果是否相等
    DesTest test = new DesTest();
    try {
      System.out.println(test.jdk().equals(test.bouncyCastle()));
    } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | NoSuchProviderException | IllegalBlockSizeException | BadPaddingException | InvalidCipherTextException e) {
      System.out.println(false);
    }
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{DesTest.class.getName()});
  }

  byte[] key = ArrayUtil.toBytes(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'});
  byte[] in = ArrayUtil.toBytes(new char[]{'中'});

  @Benchmark
  public String jdk() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, NoSuchProviderException, IllegalBlockSizeException, BadPaddingException {
    DESKeySpec desKey = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(desKey);
    // 使用 BouncyCastle 提供填充类型
    Security.addProvider(new BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding", "BC");
    cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
    byte[] bytes = cipher.doFinal(in);
    return Hex.toHexString(bytes);
  }

  @Benchmark
  public String bouncyCastle() throws InvalidCipherTextException {
    return DesUtil.encryptHex(new String(in), new String(key), null, top.csaf.crypto.enums.Mode.ECB, Padding.ZERO);
  }
}

// Benchmark                       Mode     Cnt      Score      Error   Units
// DesTest.bouncyCastle           thrpt       5      0.306 ±    0.030  ops/us
// DesTest.jdk                    thrpt       5     ≈ 10⁻⁴             ops/us
// DesTest.bouncyCastle            avgt       5      3.123 ±    0.295   us/op
// DesTest.jdk                     avgt       5   5202.896 ±  255.643   us/op
// DesTest.bouncyCastle          sample  101022      3.255 ±    0.079   us/op
// DesTest.bouncyCastle:p0.00    sample              2.700              us/op
// DesTest.bouncyCastle:p0.50    sample              2.900              us/op
// DesTest.bouncyCastle:p0.90    sample              4.200              us/op
// DesTest.bouncyCastle:p0.95    sample              4.296              us/op
// DesTest.bouncyCastle:p0.99    sample              6.496              us/op
// DesTest.bouncyCastle:p0.999   sample             35.190              us/op
// DesTest.bouncyCastle:p0.9999  sample            126.164              us/op
// DesTest.bouncyCastle:p1.00    sample           1585.152              us/op
// DesTest.jdk                   sample     978   5150.096 ±   61.943   us/op
// DesTest.jdk:p0.00             sample           4595.712              us/op
// DesTest.jdk:p0.50             sample           4915.200              us/op
// DesTest.jdk:p0.90             sample           6119.424              us/op
// DesTest.jdk:p0.95             sample           6373.786              us/op
// DesTest.jdk:p0.99             sample           6981.304              us/op
// DesTest.jdk:p0.999            sample          10993.664              us/op
// DesTest.jdk:p0.9999           sample          10993.664              us/op
// DesTest.jdk:p1.00             sample          10993.664              us/op
// DesTest.bouncyCastle              ss       5    302.220 ±  127.069   us/op
// DesTest.jdk                       ss       5  10484.240 ± 3649.993   us/op
