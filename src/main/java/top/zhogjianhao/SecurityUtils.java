package top.zhogjianhao;

import lombok.NonNull;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.params.KeyParameter;

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
   * @param key        8 字节密钥，如果长度不足 8 则补 0
   * @param in         数据，如果长度不是 8 的整数倍则补 0
   * @param encrypting 是否为加密
   * @return 加解密的字节数组
   * @throws InvalidCipherTextException 异常
   */
  public static byte[] desEcb(@NonNull byte[] key, @NonNull byte[] in, boolean encrypting) throws InvalidCipherTextException {
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

    // 根据 DESEngine 创建 BufferedBlockCipher
    BufferedBlockCipher cipher = new BufferedBlockCipher(new DESEngine());
    // 根据密钥初始化
    cipher.init(encrypting, new KeyParameter(key));
    // 处理字节数组
    int outOff = cipher.processBytes(in, 0, inLen, result, 0);
    // 处理缓冲区
    cipher.doFinal(result, outOff);
    // 解密时删除加密时补的 0
    if (!encrypting) {
      result = ArrayUtils.removeAllElements(result, (byte) 0);
    }
    return result;
  }
}
