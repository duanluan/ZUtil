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
 * DES 加密性能测试
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
public class DesEncryptHexTest {

  @Setup(Level.Trial)
  public void setUp() {
    Security.addProvider(new BouncyCastleProvider());
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{DesEncryptHexTest.class.getName()});
  }

  private static final String DATA = "1";
  private static final String KEY_STR = "12345678";
  private static final byte[] KEY = KEY_STR.getBytes();
  private static final String IV_STR = "12345678";
  private static final byte[] IV = IV_STR.getBytes();

  public static void main(String[] args) {
    assertEquals(new DES(cn.hutool.crypto.Mode.CBC, cn.hutool.crypto.Padding.PKCS5Padding, KEY, IV).encryptHex(DATA), DesUtil.encryptHex(DATA, KEY_STR, IV_STR, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5));
  }

  @Benchmark
  public String encryptByHutool() {
    return new DES(cn.hutool.crypto.Mode.CBC, cn.hutool.crypto.Padding.PKCS5Padding, KEY, IV).encryptHex(DATA);
  }

  @Benchmark
  public String encryptByZUtil() {
    return DesUtil.encryptHex(DATA, KEY_STR, IV_STR, top.csaf.crypto.enums.Mode.CBC, Padding.PKCS5);
  }
}

// Benchmark                                    Mode     Cnt     Score     Error   Units
// DesEncryptHexTest.encryptByHutool           thrpt       5     0.254 ±   0.010  ops/us
// DesEncryptHexTest.encryptByZUtil            thrpt       5     0.325 ±   0.010  ops/us
// DesEncryptHexTest.encryptByHutool            avgt       5     3.949 ±   0.184   us/op
// DesEncryptHexTest.encryptByZUtil             avgt       5     3.063 ±   0.060   us/op
// DesEncryptHexTest.encryptByHutool          sample   99502     3.329 ±   0.100   us/op
// DesEncryptHexTest.encryptByHutool:p0.00    sample             2.900             us/op
// DesEncryptHexTest.encryptByHutool:p0.50    sample             3.100             us/op
// DesEncryptHexTest.encryptByHutool:p0.90    sample             3.100             us/op
// DesEncryptHexTest.encryptByHutool:p0.95    sample             3.400             us/op
// DesEncryptHexTest.encryptByHutool:p0.99    sample             6.800             us/op
// DesEncryptHexTest.encryptByHutool:p0.999   sample            42.912             us/op
// DesEncryptHexTest.encryptByHutool:p0.9999  sample           256.396             us/op
// DesEncryptHexTest.encryptByHutool:p1.00    sample          2158.592             us/op
// DesEncryptHexTest.encryptByZUtil           sample  102916     3.238 ±   0.098   us/op
// DesEncryptHexTest.encryptByZUtil:p0.00     sample             2.800             us/op
// DesEncryptHexTest.encryptByZUtil:p0.50     sample             2.900             us/op
// DesEncryptHexTest.encryptByZUtil:p0.90     sample             3.100             us/op
// DesEncryptHexTest.encryptByZUtil:p0.95     sample             3.800             us/op
// DesEncryptHexTest.encryptByZUtil:p0.99     sample             6.600             us/op
// DesEncryptHexTest.encryptByZUtil:p0.999    sample            43.130             us/op
// DesEncryptHexTest.encryptByZUtil:p0.9999   sample           246.261             us/op
// DesEncryptHexTest.encryptByZUtil:p1.00     sample          1736.704             us/op
// DesEncryptHexTest.encryptByHutool              ss       5   286.500 ± 179.683   us/op
// DesEncryptHexTest.encryptByZUtil               ss       5   232.560 ± 118.243   us/op
