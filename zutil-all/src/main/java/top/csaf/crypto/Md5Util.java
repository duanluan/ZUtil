package top.csaf.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * MD5 工具类
 */
public class Md5Util {

  /**
   * MD5 加密
   *
   * @param in          输入
   * @param isUpperCase 是否转大写
   * @return 加密后的字符串
   */
  public static String to(String in, boolean isUpperCase) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5", "BC");
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      throw new RuntimeException(e);
    }
    byte[] messageDigest = md.digest(in.getBytes());
    StringBuilder hexString = new StringBuilder();

    for (byte b : messageDigest) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    // 是否转大写
    if (isUpperCase) {
      return hexString.toString().toUpperCase();
    } else {
      return hexString.toString();
    }
  }


  /**
   * MD5 加密后转大写
   *
   * @param in 输入
   * @return 加密后的字符串
   */
  public static String toUpperCase(String in) {
    return to(in, true);
  }

  /**
   * MD5 加密（小写）
   *
   * @param in 输入
   * @return 加密后的字符串
   */
  public static String toLowerCase(String in) {
    return to(in, false);
  }

  /**
   * MD5 加密后截取中间 16 位并转大写
   *
   * @param in 输入
   * @return 加密后的字符串
   */
  public static String toUpperCaseShort(String in) {
    return to(in, true).substring(8, 24);
  }

  /**
   * MD5 加密后截取中间 16 位（小写）
   *
   * @param in 输入
   * @return 加密后的字符串
   */
  public static String toLowerCaseShort(String in) {
    return to(in, false).substring(8, 24);
  }
}
