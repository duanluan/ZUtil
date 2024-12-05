package top.csaf.crypto;

import lombok.NonNull;
import top.csaf.crypto.enums.BlockCipherType;
import top.csaf.crypto.enums.EncodingType;
import top.csaf.crypto.enums.Mode;
import top.csaf.crypto.enums.Padding;
import top.csaf.lang.StrUtil;

/**
 * 分组密码工具类
 */
public class BlockCipherUtil {

  private static final BlockCipher.Builder BUILDER = BlockCipher.builder();


  /**
   * 加密
   *
   * @param type        算法类型
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
  public static String encrypt(BlockCipherType type, @NonNull String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    BlockCipher.Builder builder = BUILDER.type(type).keyLength(StrUtil.length(key)).ivLength(StrUtil.length(iv));
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
  public static String encrypt(BlockCipherType type, @NonNull String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, keyEncoding, iv, ivEncoding, mode, padding, null);
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
  public static String encrypt(BlockCipherType type, @NonNull String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    return encrypt(type, data, key, null, iv, null, mode, padding, encoding);
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
  public static String encrypt(BlockCipherType type, @NonNull String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, iv, mode, padding, null);
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
  public static String encryptBase64(BlockCipherType type, @NonNull String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.BASE_64);
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
  public static String encryptBase64(BlockCipherType type, @NonNull String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, iv, mode, padding, EncodingType.BASE_64);
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
  public static String encryptHex(BlockCipherType type, @NonNull String data, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.HEX);
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
  public static String encryptHex(BlockCipherType type, @NonNull String data, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return encrypt(type, data, key, iv, mode, padding, EncodingType.HEX);
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
  public static String decrypt(BlockCipherType type, String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    BlockCipher.Builder builder = BUILDER.type(type).keyLength(StrUtil.length(key)).ivLength(StrUtil.length(iv));
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
  public static String decrypt(BlockCipherType type, String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, null);
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
  public static String decrypt(BlockCipherType type, String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
    return decrypt(type, encryptedData, key, null, iv, null, mode, padding, encoding);
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
  public static String decrypt(BlockCipherType type, String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, iv, mode, padding, null);
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
  public static String decryptBase64(BlockCipherType type, String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.BASE_64);
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
  public static String decryptBase64(BlockCipherType type, String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, iv, mode, padding, EncodingType.BASE_64);
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
  public static String decryptHex(BlockCipherType type, String encryptedData, @NonNull String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, keyEncoding, iv, ivEncoding, mode, padding, EncodingType.HEX);
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
  public static String decryptHex(BlockCipherType type, String encryptedData, @NonNull String key, String iv, @NonNull Mode mode, @NonNull Padding padding) {
    return decrypt(type, encryptedData, key, iv, mode, padding, EncodingType.HEX);
  }

}
