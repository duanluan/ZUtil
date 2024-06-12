package top.csaf.regex.enums;

import java.util.regex.Pattern;

/**
 * 正则匹配模式枚举
 */
public enum FlagsEnum {
  /**
   * {@link java.util.regex.Pattern}
   */
  UNIX_LINES(Pattern.UNIX_LINES),
  /**
   * 不区分大小写
   * <p>
   * {@link java.util.regex.Pattern}
   */
  CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE),
  /**
   * 忽略空白字符，可以多行书写，并使用 # 进行注释说明
   * <p>
   * {@link java.util.regex.Pattern}
   */
  COMMENTS(Pattern.COMMENTS),
  /**
   * 多行模式，此模式下 ^ 和 $ 可以分别匹配行首和行尾
   * <p>
   * {@link java.util.regex.Pattern}
   */
  MULTILINE(Pattern.MULTILINE),
  /**
   * {@link java.util.regex.Pattern}
   */
  LITERAL(Pattern.LITERAL),
  /**
   * {@link java.util.regex.Pattern}
   */
  DOTALL(Pattern.DOTALL),
  /**
   * {@link java.util.regex.Pattern}
   */
  UNICODE_CASE(Pattern.UNICODE_CASE),
  /**
   * {@link java.util.regex.Pattern}
   */
  CANON_EQ(Pattern.CANON_EQ),
  /**
   * {@link java.util.regex.Pattern}
   */
  UNICODE_CHARACTER_CLASS(Pattern.UNICODE_CHARACTER_CLASS),
  ;

  private int value;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  FlagsEnum(int value) {
    this.value = value;
  }
}
