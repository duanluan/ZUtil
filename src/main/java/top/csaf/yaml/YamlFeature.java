package top.csaf.yaml;

import lombok.extern.slf4j.Slf4j;

/**
 * YAML 特性，决定着 YAML 工具类的处理方式，“_ALWAYS”结尾的总是生效
 * <p>
 * 默认值：<br>
 * 值中的 ${xxx} 需要转义但是找不到时的替换值：null（保持不变）<br>
 * 值中的 ${xxx} 需要转义但是找不到时是否抛出异常：false<br>
 */
@Slf4j
public class YamlFeature {

  /**
   * 值中的 ${xxx} 需要转义但是找不到时的替换值
   */
  private static final ThreadLocal<Object> ESCAPE_NOT_FOUND_REPLACEMENT = new ThreadLocal<>();

  /**
   * 持久的设置值中的 ${xxx} 需要转义但是找不到时的替换值
   */
  private static volatile Object ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS = null;

  /**
   * 设置值中的 ${xxx} 需要转义但是找不到时的替换值
   *
   * @param escapeNotFoundReplacement 值中的 ${xxx} 需要转义但是找不到时的替换值
   */
  public static void setEscapeNotFoundReplacement(final Object escapeNotFoundReplacement) {
    ESCAPE_NOT_FOUND_REPLACEMENT.set(escapeNotFoundReplacement);
  }

  /**
   * 设置持久的设置值中的 ${xxx} 需要转义但是找不到时的替换值
   *
   * @param escapeNotFoundReplacement 值中的 ${xxx} 需要转义但是找不到时的替换值
   */
  public static void setEscapeNotFoundReplacementAlways(final Object escapeNotFoundReplacement) {
    ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS = escapeNotFoundReplacement;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时的替换值
   *
   * @param escapeNotFoundReplacement 值中的 ${xxx} 需要转义但是找不到时的替换值
   * @return 值中的 ${xxx} 需要转义但是找不到时的替换值，默认为 null
   */
  public static Object getEscapeNotFoundReplacement(final Object escapeNotFoundReplacement) {
    if (escapeNotFoundReplacement != null) {
      return escapeNotFoundReplacement;
    } else if (ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_REPLACEMENT.get() != null) {
      Object escapeNotFoundReplacement1 = ESCAPE_NOT_FOUND_REPLACEMENT.get();
      ESCAPE_NOT_FOUND_REPLACEMENT.remove();
      return escapeNotFoundReplacement1;
    }
    return null;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时的替换值
   *
   * @param escapeNotFoundReplacement 值中的 ${xxx} 需要转义但是找不到时的替换值
   * @return 值中的 ${xxx} 需要转义但是找不到时的替换值，默认是形参
   */
  public static Object getEscapeNotFoundReplacementLazy(final Object escapeNotFoundReplacement) {
    if (escapeNotFoundReplacement != null) {
      return escapeNotFoundReplacement;
    } else if (ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_REPLACEMENT.get() != null) {
      Object escapeNotFoundReplacement1 = ESCAPE_NOT_FOUND_REPLACEMENT.get();
      ESCAPE_NOT_FOUND_REPLACEMENT.remove();
      return escapeNotFoundReplacement1;
    }
    return escapeNotFoundReplacement;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时的替换值
   *
   * @return 值中的 ${xxx} 需要转义但是找不到时的替换值，默认为 null
   */
  public static Object getEscapeNotFoundReplacement() {
    if (ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_REPLACEMENT_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_REPLACEMENT.get() != null) {
      Object escapeNotFoundReplacement1 = ESCAPE_NOT_FOUND_REPLACEMENT.get();
      ESCAPE_NOT_FOUND_REPLACEMENT.remove();
      return escapeNotFoundReplacement1;
    }
    return null;
  }

  /**
   * 值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   */
  private static final ThreadLocal<Boolean> ESCAPE_NOT_FOUND_THROW_EXCEPTION = new ThreadLocal<>();

  /**
   * 持久的设置值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   */
  private static volatile Boolean ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS = null;

  /**
   * 设置值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   *
   * @param escapeNotFoundThrowException 值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   */
  public static void setEscapeNotFoundThrowException(final Boolean escapeNotFoundThrowException) {
    ESCAPE_NOT_FOUND_THROW_EXCEPTION.set(escapeNotFoundThrowException);
  }

  /**
   * 设置持久的设置值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   *
   * @param escapeNotFoundThrowException 值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   */
  public static void setEscapeNotFoundThrowExceptionAlways(final Boolean escapeNotFoundThrowException) {
    ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS = escapeNotFoundThrowException;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   *
   * @param escapeNotFoundThrowException 值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   * @return 值中的 ${xxx} 需要转义但是找不到时是否抛出异常，默认为 false
   */
  public static Boolean getEscapeNotFoundThrowException(final Boolean escapeNotFoundThrowException) {
    if (escapeNotFoundThrowException != null) {
      return escapeNotFoundThrowException;
    } else if (ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_THROW_EXCEPTION.get() != null) {
      Boolean escapeNotFoundThrowException1 = ESCAPE_NOT_FOUND_THROW_EXCEPTION.get();
      ESCAPE_NOT_FOUND_THROW_EXCEPTION.remove();
      return escapeNotFoundThrowException1;
    }
    return false;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   *
   * @param escapeNotFoundThrowException 值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   * @return 值中的 ${xxx} 需要转义但是找不到时是否抛出异常，默认是形参
   */
  public static Boolean getEscapeNotFoundThrowExceptionLazy(final Boolean escapeNotFoundThrowException) {
    if (escapeNotFoundThrowException != null) {
      return escapeNotFoundThrowException;
    } else if (ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_THROW_EXCEPTION.get() != null) {
      Boolean escapeNotFoundThrowException1 = ESCAPE_NOT_FOUND_THROW_EXCEPTION.get();
      ESCAPE_NOT_FOUND_THROW_EXCEPTION.remove();
      return escapeNotFoundThrowException1;
    }
    return escapeNotFoundThrowException;
  }

  /**
   * 获取值中的 ${xxx} 需要转义但是找不到时是否抛出异常
   *
   * @return 值中的 ${xxx} 需要转义但是找不到时是否抛出异常，默认为 false
   */
  public static Boolean getEscapeNotFoundThrowException() {
    if (ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS != null) {
      return ESCAPE_NOT_FOUND_THROW_EXCEPTION_ALWAYS;
    } else if (ESCAPE_NOT_FOUND_THROW_EXCEPTION.get() != null) {
      Boolean escapeNotFoundThrowException1 = ESCAPE_NOT_FOUND_THROW_EXCEPTION.get();
      ESCAPE_NOT_FOUND_THROW_EXCEPTION.remove();
      return escapeNotFoundThrowException1;
    }
    return false;
  }
}
