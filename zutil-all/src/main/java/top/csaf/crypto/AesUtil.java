package top.csaf.crypto;

import lombok.NonNull;
import top.csaf.crypto.enums.BlockCipherType;
import top.csaf.crypto.enums.EncodingType;
import top.csaf.crypto.enums.Mode;
import top.csaf.crypto.enums.Padding;

/**
 * AES 加解密工具类
 * <p>
 * AES：Advanced Encryption Standard，又称 Rijndael 加密法。<br>
 * 这个标准用来替代原先的 DES（Data Encryption Standard），比 DES 有更高的安全性。 AES 算法采用固定长度的密钥（128 bits、192 bits 或 256 bits）来加密和解密数据块，加密和解密过程都是基于矩阵运算和字节替换等操作进行的。 加密时会将明文数据按 16 字节 (128 bits) 进行分组，不足 16 字节时将用特定的 Padding（如 PCKS7）字符进填充，所以不同的 Padding 方式密文最后一段可能不一样。
 */
public class AesUtil {

  private static final BlockCipher.Builder BUILDER = BlockCipher.builder(BlockCipherType.AES);

  /**
   * 加密
   *
   * @param data        明文
   * @param key         密钥
   * @param keyEncoding 密钥编码
   * @param iv          初始化向量
   * @param ivEncoding  初始化向量编码
   * @param mode        加密模式
   * @param padding     填充方式
   * @param encoding    编码类型
   * @return 密文
   */
  public static String encrypt(String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    BlockCipher.Builder builder = BUILDER;
    if (keyEncoding != null) {
      builder.keyEncoding(keyEncoding);
    }
    if (ivEncoding != null) {
      builder.ivEncoding(ivEncoding);
    }
    return builder.build().encrypt(data, key, iv, mode, padding, encoding);
  }

  /**
   * 加密
   *
   * @param data        明文
   * @param key         密钥
   * @param keyEncoding 密钥编码
   * @param iv          初始化向量
   * @param ivEncoding  初始化向量编码
   * @param mode        加密模式
   * @param padding     填充方式
   * @return 密文
   */
  public static String encrypt(String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, keyEncoding, iv, ivEncoding, mode, padding, null);
  }

  /**
   * 加密
   *
   * @param data     明文
   * @param key      密钥
   * @param iv       初始化向量
   * @param mode     加密模式
   * @param padding  填充方式
   * @param encoding 编码类型
   * @return 密文
   */
  public static String encrypt(String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    return encrypt(data, key, null, iv, null, mode, padding, encoding);
  }

  /**
   * 加密
   *
   * @param data    明文
   * @param key     密钥
   * @param iv      初始化向量
   * @param mode    加密模式
   * @param padding 填充方式
   * @return 密文
   */
  public static String encrypt(String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, iv, mode, padding, null);
  }

  /**
   * 加密
   *
   * @param data        明文
   * @param key         密钥
   * @param keyEncoding 密钥编码
   * @param iv          初始化向量
   * @param ivEncoding  初始化向量编码
   * @param mode        加密模式
   * @param padding     填充方式
   * @return 密文
   */
  public static String encryptBase64(String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.BASE_64);
  }

  /**
   * 加密
   *
   * @param data    明文
   * @param key     密钥
   * @param iv      初始化向量
   * @param mode    加密模式
   * @param padding 填充方式
   * @return 密文
   */
  public static String encryptBase64(String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, iv, mode, padding, EncodingType.BASE_64);
  }

  /**
   * 加密
   *
   * @param data        明文
   * @param key         密钥
   * @param keyEncoding 密钥编码
   * @param iv          初始化向量
   * @param ivEncoding  初始化向量编码
   * @param mode        加密模式
   * @param padding     填充方式
   * @return 密文
   */
  public static String encryptHex(String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.HEX);
  }

  /**
   * 加密
   *
   * @param data    明文
   * @param key     密钥
   * @param iv      初始化向量
   * @param mode    加密模式
   * @param padding 填充方式
   * @return 密文
   */
  public static String encryptHex(String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(data, key, iv, mode, padding, EncodingType.HEX);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param keyEncoding   密钥编码
   * @param iv            初始化向量
   * @param ivEncoding    初始化向量编码
   * @param mode          加密模式
   * @param padding       填充方式
   * @param encoding      编码类型
   * @return 明文
   */
  public static String decrypt(String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    BlockCipher.Builder builder = BUILDER;
    if (keyEncoding != null) {
      builder.keyEncoding(keyEncoding);
    }
    if (ivEncoding != null) {
      builder.ivEncoding(ivEncoding);
    }
    return builder.build().decrypt(encryptedData, key, iv, mode, padding, encoding);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param keyEncoding   密钥编码
   * @param iv            初始化向量
   * @param ivEncoding    初始化向量编码
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decrypt(String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, null);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param iv            初始化向量
   * @param mode          加密模式
   * @param padding       填充方式
   * @param encoding      编码类型
   * @return 明文
   */
  public static String decrypt(String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    return decrypt(encryptedData, key, null, iv, null, mode, padding, encoding);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param iv            初始化向量
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decrypt(String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, iv, mode, padding, null);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param keyEncoding   密钥编码
   * @param iv            初始化向量
   * @param ivEncoding    初始化向量编码
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decryptBase64(String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.BASE_64);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param iv            初始化向量
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decryptBase64(String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, iv, mode, padding, EncodingType.BASE_64);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param keyEncoding   密钥编码
   * @param iv            初始化向量
   * @param ivEncoding    初始化向量编码
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decryptHex(String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.HEX);
  }

  /**
   * 解密
   *
   * @param encryptedData 密文
   * @param key           密钥
   * @param iv            初始化向量
   * @param mode          加密模式
   * @param padding       填充方式
   * @return 明文
   */
  public static String decryptHex(String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(encryptedData, key, iv, mode, padding, EncodingType.HEX);
  }
}
