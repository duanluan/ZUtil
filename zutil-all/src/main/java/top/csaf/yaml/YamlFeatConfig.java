package top.csaf.yaml;

/**
 * YAML 特性配置，方便链式配置 {@link YamlFeat}
 */
public class YamlFeatConfig {

  private static final YamlFeatConfig INSTANCE = new YamlFeatConfig();

  private Object escapeNotFoundReplacement;
  private Object escapeNotFoundReplacementAlways;
  private Boolean escapeNotFoundThrowException;
  private Boolean escapeNotFoundThrowExceptionAlways;

  private YamlFeatConfig() {
  }

  public static YamlFeatConfig setEscapeNotFoundReplacement(Object escapeNotFoundReplacement) {
    INSTANCE.escapeNotFoundReplacement = escapeNotFoundReplacement;
    return INSTANCE;
  }

  public static YamlFeatConfig setEscapeNotFoundReplacementAlways(Object escapeNotFoundReplacement) {
    INSTANCE.escapeNotFoundReplacementAlways = escapeNotFoundReplacement;
    return INSTANCE;
  }

  public static YamlFeatConfig setEscapeNotFoundThrowException(Boolean escapeNotFoundThrowException) {
    INSTANCE.escapeNotFoundThrowException = escapeNotFoundThrowException;
    return INSTANCE;
  }

  public static YamlFeatConfig setEscapeNotFoundThrowExceptionAlways(Boolean escapeNotFoundThrowException) {
    INSTANCE.escapeNotFoundThrowExceptionAlways = escapeNotFoundThrowException;
    return INSTANCE;
  }

  public void apply() {
    if (escapeNotFoundReplacement != null) {
      YamlFeat.setEscapeNotFoundReplacement(escapeNotFoundReplacement);
    }
    if (escapeNotFoundReplacementAlways != null) {
      YamlFeat.setEscapeNotFoundReplacementAlways(escapeNotFoundReplacementAlways);
    }
    if (escapeNotFoundThrowException != null) {
      YamlFeat.setEscapeNotFoundThrowException(escapeNotFoundThrowException);
    }
    if (escapeNotFoundThrowExceptionAlways != null) {
      YamlFeat.setEscapeNotFoundThrowExceptionAlways(escapeNotFoundThrowExceptionAlways);
    }
    INSTANCE.clear();
  }

  private void clear() {
    INSTANCE.escapeNotFoundReplacement = null;
    INSTANCE.escapeNotFoundReplacementAlways = null;
    INSTANCE.escapeNotFoundThrowException = null;
    INSTANCE.escapeNotFoundThrowExceptionAlways = null;
  }
}
