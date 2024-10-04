package top.csaf.crypto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import top.csaf.charset.StandardCharsets;
import top.csaf.crypto.enums.BlockCipherType;
import top.csaf.crypto.enums.EncodingType;
import top.csaf.crypto.enums.Mode;
import top.csaf.crypto.enums.Padding;
import top.csaf.lang.NumberUtil;
import top.csaf.lang.StrUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

/**
 * 分组加解密工具类
 */
@Slf4j
@Setter
@Getter
public class BlockCipher {

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  /**
   * 加解密算法
   */
  private BlockCipherType type;
  /**
   * 密钥长度，一般为加解密算法的分组块大小
   */
  private Integer keyLength;
  /**
   * 初始化向量长度，一般为加解密算法的分组块大小
   */
  private Integer ivLength;
  /**
   * 密钥编码，为 null 时不做编码转换
   */
  private EncodingType keyEncoding;
  /**
   * 初始化向量编码，为 null 时不做编码转换
   */
  private EncodingType ivEncoding;

  public BlockCipher(BlockCipherType type, Integer keyLength, Integer ivLength, EncodingType keyEncoding, EncodingType ivEncoding) {
    if (StrUtil.isBlank(type)) {
      throw new IllegalArgumentException("type must not be blank");
    }
    if (!NumberUtil.gtZero(keyLength)) {
      // 不同加解密算法有默认的密钥长度
      switch (type) {
        case DES:
          this.keyLength = 8;
          break;
        default:
          throw new IllegalArgumentException("Unsupported type");
      }
    } else {
      this.keyLength = keyLength;
    }
    if (!NumberUtil.gtZero(ivLength)) {
      // 不同加解密算法有默认的 iv 长度
      switch (type) {
        case DES:
          this.ivLength = 8;
          break;
        default:
          throw new IllegalArgumentException("Unsupported type");
      }
    } else {
      this.ivLength = ivLength;
    }
    this.type = type;
    this.keyEncoding = keyEncoding;
    this.ivEncoding = ivEncoding;
  }

  /**
   * 建造者模式
   */
  public static class Builder {
    private final BlockCipherType type;
    private Integer keyLength;
    private Integer ivLength;
    private EncodingType keyEncoding;
    private EncodingType ivEncoding;

    public Builder(BlockCipherType type) {
      this.type = type;
    }

    public Builder keyLength(Integer keyLength) {
      this.keyLength = keyLength;
      return this;
    }

    public Builder ivLength(Integer ivLength) {
      this.ivLength = ivLength;
      return this;
    }

    public Builder keyEncoding(EncodingType keyEncoding) {
      this.keyEncoding = keyEncoding;
      return this;
    }

    public Builder ivEncoding(EncodingType ivEncoding) {
      this.ivEncoding = ivEncoding;
      return this;
    }

    public BlockCipher build() {
      return new BlockCipher(type, keyLength, ivLength, keyEncoding, ivEncoding);
    }
  }

  public static Builder builder(@NonNull final BlockCipherType type) {
    return new Builder(type);
  }

  private void check(Object plainOrCipherText, Padding padding) {
    if (!(plainOrCipherText instanceof String || plainOrCipherText instanceof byte[])) {
      throw new IllegalArgumentException("keyOrIv must be a String or byte[]");
    }
    byte[] plainOrCipherTextBytes = plainOrCipherText instanceof String ? ((String) plainOrCipherText).getBytes() : (byte[]) plainOrCipherText;
    if (Padding.NO.equals(padding) && (plainOrCipherTextBytes.length % this.keyLength != 0)) {
      throw new IllegalArgumentException("Data not of proper length for NoPadding mode, length must be multiple of " + this.keyLength);
    }
  }

  /**
   * 加密
   *
   * @param plaintext 明文
   * @param key       密钥
   * @param iv        初始化向量
   * @param mode      加密模式
   * @param padding   填充方式
   * @param encoding  密文编码
   * @return 密文
   */
  protected String encrypt(Object plaintext, Object key, Object iv, Mode mode, Padding padding, EncodingType encoding) {
    check(plaintext, padding);
    byte[] plaintextBytes = plaintext instanceof String ? ((String) plaintext).getBytes() : (byte[]) plaintext;
    return encode(encryptOrDecrypt(plaintextBytes, key, iv, mode, padding, Cipher.ENCRYPT_MODE), encoding);
  }

  /**
   * 解密
   *
   * @param ciphertext 密文
   * @param key        密钥
   * @param iv         初始化向量
   * @param mode       加密模式
   * @param padding    填充方式
   * @param encoding   密文编码
   * @return 明文
   */
  protected String decrypt(Object ciphertext, Object key, Object iv, Mode mode, Padding padding, EncodingType encoding) {
    check(ciphertext, padding);
    return new String(encryptOrDecrypt(ciphertext instanceof String ? decode((String) ciphertext, encoding) : (byte[]) ciphertext, key, iv, mode, padding, Cipher.DECRYPT_MODE));
  }

  /**
   * 根据填充方式和加密模式加解密
   *
   * @param data       明文或密文字节数组
   * @param key        密钥
   * @param iv         初始化向量
   * @param mode       加密模式
   * @param padding    填充方式
   * @param cipherMode Cipher 模式
   * @return 加解密结果的字节数组
   */
  private byte[] encryptOrDecrypt(byte[] data, Object key, Object iv, Mode mode, Padding padding, int cipherMode) {
    try {
      SecretKeySpec keySpec = new SecretKeySpec(decodeAndPad(key, this.keyEncoding, this.keyLength), this.type.getValue());
      IvParameterSpec ivSpec = "ECB".equals(mode.getValue()) ? null : new IvParameterSpec(decodeAndPad(iv, this.ivEncoding, this.ivLength));
      String transformation = this.type + "/" + mode + "/" + padding.getValue();
      Cipher cipher = Cipher.getInstance(transformation, "BC");
      cipher.init(cipherMode, keySpec, ivSpec);
      return cipher.doFinal(data);
    } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
      log.error(e.getMessage(), e);
      return new byte[0];
    }
  }

  /**
   * 将内容按照编码转换后，填充 0 到指定长度
   *
   * @param keyOrIv  字符串，此处为密钥或 iv
   * @param encoding 编码
   * @param length   长度
   * @return 字节数组
   */
  private byte[] decodeAndPad(Object keyOrIv, EncodingType encoding, int length) {
    if (!(keyOrIv instanceof String || keyOrIv instanceof byte[])) {
      throw new IllegalArgumentException("keyOrIv must be a String or byte[]");
    }
    byte[] dataBytes = keyOrIv instanceof String ? decode((String) keyOrIv, encoding) : (byte[]) keyOrIv;
    if (dataBytes.length < length) {
      byte[] paddedData = new byte[length];
      Arrays.fill(paddedData, (byte) 0);
      System.arraycopy(dataBytes, 0, paddedData, 0, dataBytes.length);
      return paddedData;
    }
    return dataBytes;
  }

  /**
   * 根据内容编码将字节数组转为字符串
   *
   * @param data     字节数组，此处为密文字节数组
   * @param encoding 编码
   * @return 字符串
   */
  private String encode(byte[] data, EncodingType encoding) {
    if (encoding == null) {
      return new String(data);
    }
    switch (encoding) {
      case UTF_8:
        return new String(data, StandardCharsets.UTF_8);
      case BASE_64:
        return Base64.getEncoder().encodeToString(data);
      case HEX:
        return Hex.toHexString(data);
      default:
        throw new IllegalArgumentException("Unsupported encoding: " + encoding);
    }
  }

  /**
   * 根据内容编码将字符串转为字节数组
   *
   * @param keyOrIvOrCiphertext 字符串，此处为密钥或 iv 或密文
   * @param encoding            编码
   * @return 字节数组
   */
  private byte[] decode(String keyOrIvOrCiphertext, EncodingType encoding) {
    if (StrUtil.isBlank(keyOrIvOrCiphertext)) {
      return new byte[0];
    }
    if (encoding == null) {
      return keyOrIvOrCiphertext.getBytes();
    }
    switch (encoding) {
      case UTF_8:
        return keyOrIvOrCiphertext.getBytes(StandardCharsets.UTF_8);
      case BASE_64:
        return Base64.getDecoder().decode(keyOrIvOrCiphertext);
      case HEX:
        return Hex.decode(keyOrIvOrCiphertext);
      default:
        throw new IllegalArgumentException("Unsupported encoding: " + encoding);
    }
  }

  // public static void main(String[] args) throws Exception {
  //   String originalData = "1";
  //   String key = "123"; // Your key as UTF-8, Hex or Base64
  //   String iv = "123"; // Your IV as UTF-8, Hex or Base64
  //   Mode mode = Mode.CBC; // ECB, CBC, CTR, CFB, OFB
  //   Padding padding = Padding.ZERO; // PKCS7Padding, NoPadding, ZeroBytePadding etc.
  //   EncodingType encoding = EncodingType.HEX; // Hex or Base64
  //   EncodingType keyEncoding = EncodingType.UTF_8; // utf-8, hex, base64
  //   EncodingType ivEncoding = EncodingType.UTF_8; // utf-8, hex, base64
  //
  //   BlockCipher blockCipher = BlockCipher.builder(BlockCipherType.DES).keyEncoding(keyEncoding).ivEncoding(ivEncoding).build();
  //
  //   // Encrypt
  //   String encryptedData = blockCipher.encrypt(originalData, key, iv, mode, padding, encoding);
  //   System.out.println("Encrypted (in " + encoding + "): " + encryptedData);
  //
  //   // Decrypt
  //   String decryptedData = blockCipher.decrypt(encryptedData, key, iv, mode, padding, encoding);
  //   System.out.println("Decrypted: " + decryptedData);
  // }
}
