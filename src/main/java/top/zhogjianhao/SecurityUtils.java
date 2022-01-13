package top.zhogjianhao;

import lombok.NonNull;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

/**
 * 安全工具类
 * <p>
 * https://docs.oracle.com/en/java/javase/17/security/
 * <p>
 * 参考：https://github.com/itlgl/cryptoutil
 */
public class SecurityUtils {

  /**
   * DES 算法 ECB 模式加解密
   *
   * @param key        8 字节密钥，长度不足 8 时补 0
   * @param in         数据，长度不是 8 的整数倍时补 0，解密后删除为 0 的字节
   * @param encrypting 是否为加密
   * @return 加解密的字节数组
   * @throws InvalidCipherTextException 异常
   */
  public static byte[] desEcb(@NonNull byte[] key, @NonNull byte[] in, final boolean encrypting) throws InvalidCipherTextException {
    int keyLen = key.length;
    if (keyLen > 8 || keyLen < 1) {
      throw new IllegalArgumentException("DES key cannot be greater than 8 bytes or less than 1 byte");
    }
    int inLen = in.length;
    if (inLen == 0) {
      throw new IllegalArgumentException("in should not be empty");
    }

    // 密钥长度不足 8 补 0
    if (keyLen < 8) {
      key = ArrayUtils.fill(key, keyLen, 8, (byte) 0);
    }
    // 数据非 8 的倍数时
    int inLen8Remainder = inLen % 8;
    if (inLen8Remainder != 0) {
      // 按照最近的 8 的倍数补 0，即从原数组长度对应的下标开始，到下一个 8 的下标（下标要 -1，fill 方法又要 +1）
      in = ArrayUtils.fill(in, inLen, inLen + (8 - inLen8Remainder), (byte) 0);
      inLen = in.length;
    }
    byte[] result = new byte[inLen];

    // 创建 DES 算法（默认 ECB 模式）的 BufferedBlockCipher
    BufferedBlockCipher cipher = new BufferedBlockCipher(new DESEngine());
    // 根据密钥初始化
    cipher.init(encrypting, new KeyParameter(key));
    // 处理数据
    int outOff = cipher.processBytes(in, 0, inLen, result, 0);
    cipher.doFinal(result, outOff);
    // 解密后删除加密时补的 0
    if (!encrypting) {
      result = ArrayUtils.removeAllElements(result, (byte) 0);
    }
    return result;
  }

  /**
   * DES 算法 ECB 模式加解密
   *
   * @param key        密钥
   * @param in         数据
   * @param encrypting 是否为加密
   * @return 加解密的字符串
   * @throws InvalidCipherTextException 异常
   */
  public static String desEcb(@NonNull final String key, @NonNull final String in, final boolean encrypting) throws InvalidCipherTextException {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    if (encrypting) {
      return ByteUtils.toHexString(desEcb(keys, ArrayUtils.toBytes(in.toCharArray()), encrypting));
    }
    byte[] bytes = desEcb(keys, ByteUtils.fromHexString(in), encrypting);
    return new String(ArrayUtils.toChars(bytes));
  }

  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        8 字节密钥，长度不足 8 时补 0
   * @param icv        8 字节向量，长度不足 8 时补 0
   * @param in         数据，加密时长度不是 8 的整数倍时补 0，解密后删除为 0 的字节
   * @param encrypting 是否为加密
   * @return 加解密的字节数组
   * @throws InvalidCipherTextException 异常
   */
  public static byte[] desCbc(@NonNull byte[] key, @NonNull byte[] icv, @NonNull byte[] in, final boolean encrypting) throws InvalidCipherTextException {
    int keyLen = key.length;
    if (keyLen > 8 || keyLen < 1) {
      throw new IllegalArgumentException("DES key cannot be greater than 8 bytes or less than 1 byte");
    }
    int icvLen = icv.length;
    if (icvLen > 8 || icvLen < 1) {
      throw new IllegalArgumentException("DES icv cannot be greater than 8 bytes or less than 1 byte");
    }
    int inLen = in.length;
    if (inLen == 0) {
      throw new IllegalArgumentException("in should not be empty");
    }

    // 密钥长度不足 8 补 0
    if (keyLen < 8) {
      key = ArrayUtils.fill(key, keyLen, 8, (byte) 0);
    }
    // 密钥长度不足 8 补 0
    if (icvLen < 8) {
      icv = ArrayUtils.fill(icv, icvLen, 8, (byte) 0);
    }
    // 数据非 8 的倍数时
    int inLen8Remainder = inLen % 8;
    if (inLen8Remainder != 0) {
      // 按照最近的 8 的倍数补 0，即从原数组长度对应的下标开始，到下一个 8 的下标（下标要 -1，fill 方法又要 +1）
      in = ArrayUtils.fill(in, inLen, inLen + (8 - inLen8Remainder), (byte) 0);
      inLen = in.length;
    }
    byte[] result = new byte[inLen];

    // 创建 DES 算法 CBC 模式的 BufferedBlockCipher
    BufferedBlockCipher engine = new BufferedBlockCipher(new CBCBlockCipher(new DESEngine()));
    // 根据密钥和向量初始化
    engine.init(encrypting, new ParametersWithIV(new KeyParameter(key), icv));
    // 处理数据
    int len = engine.processBytes(in, 0, in.length, result, 0);
    engine.doFinal(result, len);
    // 解密后删除加密时补的 0
    if (!encrypting) {
      result = ArrayUtils.removeAllElements(result, (byte) 0);
    }
    return result;
  }


  /**
   * DES 算法 CBC 模式加解密
   *
   * @param key        密钥
   * @param icv        向量
   * @param in         数据
   * @param encrypting 是否为加密
   * @return 加解密的字符串
   * @throws InvalidCipherTextException 异常
   */
  public static String desCbc(@NonNull final String key, @NonNull final String icv, @NonNull final String in, final boolean encrypting) throws InvalidCipherTextException {
    byte[] keys = ArrayUtils.toBytes(key.toCharArray());
    byte[] icvs = ArrayUtils.toBytes(icv.toCharArray());
    if (encrypting) {
      return ByteUtils.toHexString(desCbc(keys, icvs, ArrayUtils.toBytes(in.toCharArray()), encrypting));
    }
    byte[] bytes = desCbc(keys, icvs, ByteUtils.fromHexString(in), encrypting);
    return new String(ArrayUtils.toChars(bytes));
  }
}
