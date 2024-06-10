package top.csaf.jmh.contrast.crypto;

import cn.hutool.crypto.symmetric.DES;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.csaf.crypto.BlockCipher;
import top.csaf.crypto.DesUtil;
import top.csaf.crypto.enums.Padding;

import java.security.Security;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DES 解密性能测试
 * <p>
 * 测试时注释了 {@link BlockCipher} 中的 Security.addProvider(new BouncyCastleProvider());
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class DesDecryptHexTest {

  @Setup(Level.Trial)
  public void setUp() {
    Security.addProvider(new BouncyCastleProvider());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{DesDecryptHexTest.class.getName()});
  }

  private static final String ENCRYPTED_DATA = "db7d5df1fd65aa2f";
  private static final String KEY_STR = "12345678";
  private static final byte[] KEY = KEY_STR.getBytes();
  private static final String IV_STR = "12345678";
  private static final byte[] IV = IV_STR.getBytes();

  public static void main(String[] args) {
    assertEquals(new String(new DES(cn.hutool.crypto.Mode.CBC, cn.hutool.crypto.Padding.PKCS5Padding, KEY, IV).decrypt(ENCRYPTED_DATA)), DesUtil.decryptHex(ENCRYPTED_DATA, KEY_STR, IV_STR, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }

  @Benchmark
  public String decryptByHutool() {
    return new String(new DES(cn.hutool.crypto.Mode.CBC, cn.hutool.crypto.Padding.PKCS5Padding, KEY, IV).decrypt(ENCRYPTED_DATA));
  }

  @Benchmark
  public String decryptByZUtil() {
    return DesUtil.decryptHex(ENCRYPTED_DATA, KEY_STR, IV_STR, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5);
  }
}

// Benchmark                                    Mode     Cnt     Score     Error   Units
// DesDecryptHexTest.decryptByHutool           thrpt       5     0.278 ±   0.005  ops/us
// DesDecryptHexTest.decryptByZUtil            thrpt       5     0.317 ±   0.011  ops/us
// DesDecryptHexTest.decryptByHutool            avgt       5     3.529 ±   0.097   us/op
// DesDecryptHexTest.decryptByZUtil             avgt       5     3.931 ±   0.027   us/op
// DesDecryptHexTest.decryptByHutool          sample  179216     3.622 ±   0.071   us/op
// DesDecryptHexTest.decryptByHutool:p0.00    sample             3.300             us/op
// DesDecryptHexTest.decryptByHutool:p0.50    sample             3.400             us/op
// DesDecryptHexTest.decryptByHutool:p0.90    sample             3.500             us/op
// DesDecryptHexTest.decryptByHutool:p0.95    sample             3.700             us/op
// DesDecryptHexTest.decryptByHutool:p0.99    sample             7.696             us/op
// DesDecryptHexTest.decryptByHutool:p0.999   sample            28.342             us/op
// DesDecryptHexTest.decryptByHutool:p0.9999  sample            84.126             us/op
// DesDecryptHexTest.decryptByHutool:p1.00    sample          1935.360             us/op
// DesDecryptHexTest.decryptByZUtil           sample  111881     3.475 ±   0.048   us/op
// DesDecryptHexTest.decryptByZUtil:p0.00     sample             3.000             us/op
// DesDecryptHexTest.decryptByZUtil:p0.50     sample             3.100             us/op
// DesDecryptHexTest.decryptByZUtil:p0.90     sample             4.200             us/op
// DesDecryptHexTest.decryptByZUtil:p0.95     sample             4.800             us/op
// DesDecryptHexTest.decryptByZUtil:p0.99     sample             7.000             us/op
// DesDecryptHexTest.decryptByZUtil:p0.999    sample            36.672             us/op
// DesDecryptHexTest.decryptByZUtil:p0.9999   sample           108.509             us/op
// DesDecryptHexTest.decryptByZUtil:p1.00     sample          1335.296             us/op
// DesDecryptHexTest.decryptByHutool              ss       5   366.360 ± 293.532   us/op
// DesDecryptHexTest.decryptByZUtil               ss       5   254.660 ± 242.567   us/op
