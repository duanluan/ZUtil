package top.zhogjianhao.constant;

import java.util.regex.Pattern;

/**
 * 公共正则匹配器
 */
public class CommonPattern {

  /**
   * 左大括号
   */
  public static final Pattern LEFT_CURLY_BRACE = Pattern.compile("\\{");
  /**
   * 右大括号
   */
  public static final Pattern RIGHT_CURLY_BRACE = Pattern.compile("}");
  /**
   * 双引号
   */
  public static final Pattern DOUBLE_QUOTATION_MARK = Pattern.compile("\"");
  /**
   * 冒号
   */
  public static final Pattern COLON = Pattern.compile(":");
  /**
   * 左方括号
   */
  public static final Pattern LEFT_SQUARE_BRACKET = Pattern.compile("\\[");
  /**
   * 右方括号
   */
  public static final Pattern RIGHT_SQUARE_BRACKET = Pattern.compile("]");
  /**
   * 逗号
   */
  public static final Pattern COMMA = Pattern.compile(",");
  /**
   * 斜杠
   */
  public static final Pattern SLASH = Pattern.compile("/");
  /**
   * 反斜杠
   */
  public static final Pattern BACKSLASH = Pattern.compile("\\\\");
}
