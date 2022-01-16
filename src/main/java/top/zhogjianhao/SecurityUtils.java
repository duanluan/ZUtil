package top.zhogjianhao;

import lombok.NonNull;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 安全工具类
 * <p>
 * https://docs.oracle.com/en/java/javase/17/security/
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
   * @throws InvalidCipherTextException 意想不到的异常
   */
  public static byte[] desEcb(@NonNull final byte[] key, @NonNull final byte[] in, final boolean encrypting, final BlockCipherPadding padding) throws InvalidCipherTextException {
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
    len += cipher.doFinal(out, len);
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
   * @throws InvalidCipherTextException 异常
   */
  public static String desEcb(@NonNull final String key, @NonNull final String in, final boolean encrypting, final BlockCipherPadding padding) throws InvalidCipherTextException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    if (keys.length < 8) {
      throw new IllegalArgumentException("Key: should be greater than 8 bytes");
    }
    // 取 key 的 [0]~[7]
    DESKeySpec keySpec = new DESKeySpec(keys);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "DES");
    keys = secretKeySpec.getEncoded();

    if (encrypting) {
      return ByteUtils.toHexString(desEcb(keys, ArrayUtils.toBytes(in.toCharArray()), encrypting, padding));
    }
    byte[] bytes = desEcb(keys, ByteUtils.fromHexString(in), encrypting, padding);
    return new String(ArrayUtils.toChars(bytes));
  }

  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        8 字节密钥
   * @param icv        8 字节向量
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字节数组
   * @throws InvalidCipherTextException 异常
   */
  public static byte[] desCbc(@NonNull final byte[] key, final @NonNull byte[] icv, final @NonNull byte[] in, final boolean encrypting, final BlockCipherPadding padding) throws InvalidCipherTextException {
    if (key.length != 8) {
      throw new IllegalArgumentException("Key: should be 8 bytes");
    }
    if (icv.length != 8) {
      throw new IllegalArgumentException("Icv: should be 8 bytes");
    }
    int inLen = in.length;
    if (inLen == 0) {
      throw new IllegalArgumentException("In: should not be empty");
    }

    // 创建 DES 算法（默认 CBC 模式）的 BufferedBlockCipher
    BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESEngine()), padding);
    // 根据密钥初始化
    cipher.init(encrypting, new KeyParameter(key));
    // 创建输出数组
    byte[] out = new byte[cipher.getOutputSize(in.length)];
    // 处理数据
    int len = cipher.processBytes(in, 0, inLen, out, 0);
    len += cipher.doFinal(out, len);
    // 移除填充的内容
    byte[] result = new byte[len];
    System.arraycopy(out, 0, result, 0, len);
    return result;
  }


  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        密钥
   * @param in         数据
   * @param encrypting 是否为加密
   * @param padding    填充类型
   * @return 加解密的字符串
   * @throws InvalidCipherTextException 异常
   */
  public static String desCbc(@NonNull final String key, @NonNull final String icv, @NonNull final String in, final boolean encrypting, final BlockCipherPadding padding) throws InvalidCipherTextException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    if (keys.length < 8) {
      throw new IllegalArgumentException("Key: should be greater than 8 bytes");
    }
    byte[] icvs = ArrayUtils.toBytes(key.toCharArray());
    if (icvs.length < 8) {
      throw new IllegalArgumentException("Icv: should be greater than 8 bytes");
    }
    // 取 key 的 [0]~[7]
    DESKeySpec keySpec = new DESKeySpec(keys);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "DES");
    keys = secretKeySpec.getEncoded();

    if (encrypting) {
      return ByteUtils.toHexString(desCbc(keys, icvs, ArrayUtils.toBytes(in.toCharArray()), encrypting, padding));
    }
    byte[] bytes = desCbc(keys, icvs, ByteUtils.fromHexString(in), encrypting, padding);
    return new String(ArrayUtils.toChars(bytes));
  }
}
