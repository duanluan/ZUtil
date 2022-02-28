package top.zhogjianhao.jmh;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import top.zhogjianhao.ArrayUtils;
import top.zhogjianhao.BeanUtilsTest;
import top.zhogjianhao.SecurityUtils;

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
    return ByteUtils.toHexString(bytes);
  }

  @Benchmark
  public String bouncyCastle() throws InvalidCipherTextException {
    byte[] bytes = SecurityUtils.desEcb(key, in, true, new ZeroBytePadding());
    return ByteUtils.toHexString(bytes);
  }
}
