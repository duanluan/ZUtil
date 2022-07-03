package top.zhogjianhao.constant;

import java.util.regex.Pattern;

/**
 * 公共正则匹配器
 */
public class CommonPattern {

  /**
   * 左括号
   */
  public static final Pattern LEFT_ROUND_BRACES = Pattern.compile("\\(");
  /**
   * 右括号
   */
  public static final Pattern RIGHT_ROUND_BRACES = Pattern.compile("\\)");
  /**
   * 左括号
   */
  public static final Pattern LEFT_SQUARE_BRACES = Pattern.compile("\\[");
  /**
   * 右括号
   */
  public static final Pattern RIGHT_SQUARE_BRACES = Pattern.compile("]");
  /**
   * 左大括号
   */
  public static final Pattern LEFT_CURLY_BRACES = Pattern.compile("\\{");
  /**
   * 右大括号
   */
  public static final Pattern RIGHT_CURLY_BRACES = Pattern.compile("}");
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
   * 点
   */
  public static final Pattern DOT = Pattern.compile("\\.");
  /**
   * 问号
   */
  public static final Pattern QUESTION_MARK = Pattern.compile("\\?");
  /**
   * 加号
   */
  public static final Pattern PLUS_SIGN = Pattern.compile("\\+");
  /**
   * 星号
   */
  public static final Pattern ASTERISK = Pattern.compile("\\*");
  /**
   * 竖线
   */
  public static final Pattern VERTICAL_BAR = Pattern.compile("\\|");
  /**
   * 抑扬符
   */
  public static final Pattern CIRCUMFLEX = Pattern.compile("\\^");
  /**
   * 美元符号
   */
  public static final Pattern DOLLAR_SIGN = Pattern.compile("\\$");
  /**
   * 斜杠
   */
  public static final Pattern SLASH = Pattern.compile("/");
  /**
   * 反斜杠
   */
  public static final Pattern BACKSLASH = Pattern.compile("\\\\");
  /**
   * 单个汉字
   */
  public static final Pattern SINGLE_CHINESE_CHAR = Pattern.compile("[\u4e00-\u9fa5]");
  /**
   * 一个以上汉字
   */
  public static final Pattern MULTIPLE_CHINESE_CHAR = Pattern.compile("[\u4e00-\u9fa5]+");
}
