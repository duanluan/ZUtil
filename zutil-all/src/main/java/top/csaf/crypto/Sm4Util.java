package top.csaf.crypto;

import top.csaf.crypto.enums.BlockCipherType;
import top.csaf.crypto.enums.EncodingType;
import top.csaf.crypto.enums.Mode;
import top.csaf.crypto.enums.Padding;

/**
 * SM4 加解密工具类
 * <p>
 * SM4 是中国国家密码管理局发布的一种分组密码算法，也被称为国密 SM4 算法。它是一种对称加密算法，用于替代 DES 和 AES 等传统的对称加密算法。 其中 “SM” 代表 “商密”，即用于商用的、不涉及国家秘密的密码技术。
 * SM4 算法具有以下特点：
 * <ul>
 *   <li>安全性高：SM4 算法采用了 32 轮的 Feistel 结构和非线性 S 盒，结合了代换、置换和异或等操作，以保证其安全性和抗攻击能力。</li>
 *   <li>高效性能：SM4 算法在硬件和软件实现上都具有较高的效率，能够在不同平台上快速进行加密和解密操作。</li>
 *   <li>国密标准：SM4 算法是中国国家密码管理局发布的密码算法标准，被广泛应用于中国的信息安全领域。</li>
 *   <li>密钥长度灵活：SM4 算法支持 128 位密钥长度，可以提供足够的安全性，并且可以根据实际需求进行调整。</li>
 *   <li>兼容性好：SM4 算法的分组长度为 128 位，与现有的密码学协议和系统进行兼容，可以与其他对称加密算法进行互操作。</li>
 * </ul>
 */
public class Sm4Util {

  private static final BlockCipher.Builder BUILDER = BlockCipher.builder(BlockCipherType.SM4);
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
