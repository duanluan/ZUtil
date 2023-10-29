package top.csaf;

import lombok.NonNull;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 安全工具类
 * <p>
 * <a href="https://docs.oracle.com/en/java/javase/17/security/">Java Platform, Standard Edition Security Developer’s Guide, Release 17</a>
 */
public class SecurityUtils {

  /**
   * DES 算法 ECB 模式加解密
   *
   * @param key        8 字节密钥
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字节数组
   */
  public static byte[] desEcb(@NonNull final byte[] key, @NonNull final byte[] in, final boolean encrypting, final BlockCipherPadding padding) {
    if (key.length != 8) {
      throw new IllegalArgumentException("Key: should be 8 bytes");
    }
    int inLen = in.length;
    if (inLen == 0) {
      throw new IllegalArgumentException("In: should not be empty");
    }

    // 创建 DES 算法（默认 ECB 模式）的 BufferedBlockCipher
    BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new DESEngine(), padding);
    // 根据密钥初始化
    cipher.init(encrypting, new KeyParameter(key));
    // 创建输出数组
    byte[] out = new byte[cipher.getOutputSize(in.length)];
    // 处理数据
    int len = cipher.processBytes(in, 0, inLen, out, 0);
    try {
      len += cipher.doFinal(out, len);
    } catch (InvalidCipherTextException e) {
      throw new RuntimeException(e);
    }
    // 移除填充的内容
    byte[] result = new byte[len];
    System.arraycopy(out, 0, result, 0, len);
    return result;
  }

  /**
   * DES 算法 ECB 模式加解密
   *
   * @param key        密钥
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字符串
   */
  public static String desEcb(@NonNull final String key, @NonNull final String in, final boolean encrypting, final BlockCipherPadding padding) {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    if (keys.length < 8) {
      throw new IllegalArgumentException("Key: should be greater than 8 bytes");
    }
    SecretKeySpec secretKeySpec;
    try {
      // 取 key 的 [0]~[7]
      DESKeySpec keySpec = new DESKeySpec(keys);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      secretKeySpec = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "DES");
    } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    keys = secretKeySpec.getEncoded();

    if (encrypting) {
      return Hex.toHexString(desEcb(keys, ArrayUtils.toBytes(in.toCharArray()), encrypting, padding));
    }
    byte[] bytes = desEcb(keys, Hex.decode(in), encrypting, padding);
    return new String(ArrayUtils.toChars(bytes));
  }

  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        8 字节密钥
   * @param iv         8 字节向量
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字节数组
   */
  public static byte[] desCbc(@NonNull final byte[] key, @NonNull final byte[] iv, @NonNull final byte[] in, final boolean encrypting, final BlockCipherPadding padding) {
    if (key.length != 8) {
      throw new IllegalArgumentException("Key: should be 8 bytes");
    }
    if (iv.length != 8) {
      throw new IllegalArgumentException("Icv: should be 8 bytes");
    }
    int inLen = in.length;
    if (inLen == 0) {
      throw new IllegalArgumentException("In: should not be empty");
    }

    // 创建 DES 算法（默认 CBC 模式）的 BufferedBlockCipher
    BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESEngine()), padding);
    // 根据密钥初始化
    cipher.init(encrypting, new ParametersWithIV(new KeyParameter(key), iv));
    // 创建输出数组
    byte[] out = new byte[cipher.getOutputSize(in.length)];
    // 处理数据
    int len = cipher.processBytes(in, 0, inLen, out, 0);
    try {
      len += cipher.doFinal(out, len);
    } catch (InvalidCipherTextException e) {
      throw new RuntimeException(e);
    }
    // 移除填充的内容
    byte[] result = new byte[len];
    System.arraycopy(out, 0, result, 0, len);
    return result;
  }


  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        密钥
   * @param iv         向量
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字符串
   */
  public static String desCbc(@NonNull final String key, @NonNull final String iv, @NonNull final String in, final boolean encrypting, final BlockCipherPadding padding) {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    if (keys.length < 8) {
      throw new IllegalArgumentException("Key: should be greater than 8 bytes");
    }
    byte[] ivs = ArrayUtils.toBytes(iv.toCharArray());
    if (ivs.length < 8) {
      throw new IllegalArgumentException("Icv: should be greater than 8 bytes");
    }
    SecretKeySpec secretKeySpec;
    try {
      // 取 key 的 [0]~[7]
      DESKeySpec keySpec = new DESKeySpec(keys);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      secretKeySpec = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "DES");
    } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    keys = secretKeySpec.getEncoded();

    if (encrypting) {
      return Hex.toHexString(desCbc(keys, ivs, ArrayUtils.toBytes(in.toCharArray()), encrypting, padding));
    }
    byte[] bytes = desCbc(keys, ivs, Hex.decode(in), encrypting, padding);
    return new String(ArrayUtils.toChars(bytes));
  }
}
