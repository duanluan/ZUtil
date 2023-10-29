package top.csaf.jmh.base.security;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.ArrayUtils;
import top.csaf.SecurityUtils;
import top.csaf.junit.BeanUtilsTest;

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

  private static final BeanUtilsTest.TestBean testBean;

  static {
    testBean = new BeanUtilsTest.TestBean();
    testBean.setName("张三");
  }

  byte[] key = ArrayUtils.toBytes(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'});
  byte[] in = ArrayUtils.toBytes(new char[]{'中'});

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
    byte[] bytes = SecurityUtils.desEcb(key, in, true, new ZeroBytePadding());
    return Hex.toHexString(bytes);
  }
}

// Benchmark                                    Mode     Cnt     Score      Error   Units
// DesTest.bouncyCastle                        thrpt       5     0.570 ±    0.008  ops/us
// DesTest.jdk                                 thrpt       5     0.001 ±    0.001  ops/us
// DesTest.bouncyCastle                         avgt       5     1.834 ±    0.035   us/op
// DesTest.jdk                                  avgt       5   795.029 ±  459.099   us/op
// DesTest.bouncyCastle                       sample  169977     1.875 ±    0.006   us/op
// DesTest.bouncyCastle:bouncyCastle·p0.00    sample             1.700              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.50    sample             1.800              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.90    sample             1.900              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.95    sample             1.900              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.99    sample             2.800              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.999   sample             8.192              us/op
// DesTest.bouncyCastle:bouncyCastle·p0.9999  sample            28.385              us/op
// DesTest.bouncyCastle:bouncyCastle·p1.00    sample           170.752              us/op
// DesTest.jdk                                sample    6071   827.406 ±    9.084   us/op
// DesTest.jdk:jdk·p0.00                      sample           567.296              us/op
// DesTest.jdk:jdk·p0.50                      sample           812.032              us/op
// DesTest.jdk:jdk·p0.90                      sample          1062.912              us/op
// DesTest.jdk:jdk·p0.95                      sample          1146.880              us/op
// DesTest.jdk:jdk·p0.99                      sample          1550.909              us/op
// DesTest.jdk:jdk·p0.999                     sample          2385.904              us/op
// DesTest.jdk:jdk·p0.9999                    sample          3375.104              us/op
// DesTest.jdk:jdk·p1.00                      sample          3375.104              us/op
// DesTest.bouncyCastle                           ss       5    70.560 ±   12.627   us/op
// DesTest.jdk                                    ss       5  2709.700 ± 1149.087   us/op
