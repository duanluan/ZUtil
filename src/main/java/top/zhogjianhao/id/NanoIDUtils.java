package top.zhogjianhao.id;

import java.security.SecureRandom;
import java.util.Random;

/**
 * NanoID 工具类
 * @author yeliulee
 * @create 2022/10/29 22:05
 */
public class NanoIDUtils {

    // 默认生成器
    public static final SecureRandom DEFAULT_ID_GENERATOR = new SecureRandom();

    // 默认字典
    public static final String DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 默认长度
    public static final int DEFAULT_SIZE = 21;

    /**
     * 生成随机ID
     * @return NanoID
     */
    public static String randomNanoId() {
        return randomNanoId(DEFAULT_SIZE, DEFAULT_ALPHABET, DEFAULT_ID_GENERATOR);
    }

    /**
     * 生成NanoID
     * @param size 长度
     * @return NanoID
     */
    public static String randomNanoId(int size) {
        return randomNanoId(size, DEFAULT_ALPHABET, DEFAULT_ID_GENERATOR);
    }

    /**
     * 生成随机NanoID
     * @param size 长度
     * @param alphabet 字典
     * @return NanoID
     */
    public static String randomNanoId(int size, String alphabet) {
        return randomNanoId(size, alphabet, DEFAULT_ID_GENERATOR);
    }

    /**
     * 生成 NanoID
     * @param size 长度
     * @param alphabet 字典
     * @param generator 随机数生成器
     * @return NanoID
     */
    public static String randomNanoId(int size, String alphabet, Random generator) {
        if(alphabet.isEmpty() || alphabet.length() > 256) {
            throw new IllegalArgumentException("Alphabet must contain between 1 and 256 characters.");
        }
        if(size < 1) {
            throw new IllegalArgumentException("Size must be at least 1.");
        }
        int mask = (2 << (int) Math.floor(Math.log(alphabet.length() - 1) / Math.log(2))) - 1;
        int step = (int) Math.ceil(1.6 * mask * size / alphabet.length());
        StringBuilder idBuilder = new StringBuilder(size);
        while (true) {
            byte[] bytes = new byte[step];
            generator.nextBytes(bytes);
            for (int i = 0; i < step; i++) {
                int alphabetIndex = bytes[i] & mask;
                if(alphabetIndex < alphabet.length()) {
                    idBuilder.append(alphabet.charAt(alphabetIndex));
                    if(idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }
            }
        }
    }
}
