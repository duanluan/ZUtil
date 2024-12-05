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
 * 对称加密-分组密码
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
    // 如果未指定密钥长度，则使用默认值
    if (!NumberUtil.gtZero(keyLength)) {
      switch (type) {
        // DES：8字节 64位
        case DES:
          this.keyLength = 8;
          break;
        case AES:
          // AES：16字节 128位
          this.keyLength = 16;
          break;
        // SM4：16字节 128位
        case SM4:
          this.keyLength = 16;
          break;
        default:
          throw new IllegalArgumentException("keyLength must be greater than 0");
      }
    } else {
      this.keyLength = keyLength;
    }
    // 如果未指定 iv 长度，则使用默认值
    if (!NumberUtil.gtZero(ivLength)) {
      switch (type) {
        case DES:
          this.ivLength = 8;
          break;
        case AES:
          this.ivLength = 16;
          break;
        case SM4:
          this.ivLength = 16;
          break;
        default:
          throw new IllegalArgumentException("ivLength must be greater than 0");
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
    private BlockCipherType type;
    private Integer keyLength;
    private Integer ivLength;
    private EncodingType keyEncoding;
    private EncodingType ivEncoding;

    private Builder() {
    }

    private Builder(BlockCipherType type) {
      this.type = type;
    }

    protected Builder type(BlockCipherType type) {
      this.type = type;
      return this;
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

  protected static Builder builder() {
    return new Builder();
  }

  /**
   * 检查明文或密文
   * <p>
   * <ul>
   *   <li>明文或密文类型只能是字符串或字节数组</li>
   *   <li>NoPadding 模式下，数据长度必须是密钥长度的整数倍</li>
   * </ul>
   *
   * @param plainOrCipherText 明文或密文
   * @param padding           填充方式
   */
  private void check(Object plainOrCipherText, @NonNull Padding padding) {
    // 明文或密文类型只能是字符串或字节数组
    if (!(plainOrCipherText instanceof String || plainOrCipherText instanceof byte[])) {
      throw new IllegalArgumentException("plaintext and ciphertext must be a String or byte[]");
    }
    // NoPadding 模式下，数据长度必须是密钥长度的整数倍
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
  protected String encrypt(@NonNull Object plaintext, @NonNull Object key, Object iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
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
  protected String decrypt(@NonNull Object ciphertext, @NonNull Object key, Object iv, @NonNull Mode mode, @NonNull Padding padding, EncodingType encoding) {
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
   * @param cipherMode Cipher 模式，ENCRYPT_MODE 或 DECRYPT_MODE
   * @return 加解密结果的字节数组
   */
  private byte[] encryptOrDecrypt(byte @NonNull [] data, @NonNull Object key, Object iv, @NonNull Mode mode, @NonNull Padding padding, int cipherMode) {
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
