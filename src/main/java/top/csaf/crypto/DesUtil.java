package top.csaf.crypto;

import top.csaf.crypto.enums.BlockCipherType;
import top.csaf.crypto.enums.EncodingType;
import top.csaf.crypto.enums.Mode;
import top.csaf.crypto.enums.Padding;

/**
 * DES 加解密工具类
 */
public class DesUtil {

  private static final BlockCipher.Builder BUILDER = BlockCipher.builder(BlockCipherType.DES);
  private static final BlockCipher BLOCK_CIPHER = BUILDER.build();

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
  public static String encrypt(String data, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding, EncodingType encoding) {
    return BUILDER.keyEncoding(keyEncoding).ivEncoding(ivEncoding).build().encrypt(data, key, iv, mode, padding, encoding);
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
  public static String encrypt(String data, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String encrypt(String data, String key, String iv, Mode mode, Padding padding, EncodingType encoding) {
    return BLOCK_CIPHER.encrypt(data, key, iv, mode, padding, encoding);
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
  public static String encrypt(String data, String key, String iv, Mode mode, Padding padding) {
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
  public static String encryptBase64(String data, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String encryptBase64(String data, String key, String iv, Mode mode, Padding padding) {
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
  public static String encryptHex(String data, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String encryptHex(String data, String key, String iv, Mode mode, Padding padding) {
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
  public static String decrypt(String encryptedData, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding, EncodingType encoding) {
    return BUILDER.keyEncoding(keyEncoding).ivEncoding(ivEncoding).build().decrypt(encryptedData, key, iv, mode, padding, encoding);
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
  public static String decrypt(String encryptedData, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String decrypt(String encryptedData, String key, String iv, Mode mode, Padding padding, EncodingType encoding) {
    return BLOCK_CIPHER.decrypt(encryptedData, key, iv, mode, padding, encoding);
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
  public static String decrypt(String encryptedData, String key, String iv, Mode mode, Padding padding) {
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
  public static String decryptBase64(String encryptedData, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String decryptBase64(String encryptedData, String key, String iv, Mode mode, Padding padding) {
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
  public static String decryptHex(String encryptedData, String key, EncodingType keyEncoding, String iv, EncodingType ivEncoding, Mode mode, Padding padding) {
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
  public static String decryptHex(String encryptedData, String key, String iv, Mode mode, Padding padding) {
    return decrypt(encryptedData, key, iv, mode, padding, EncodingType.HEX);
  }
}
