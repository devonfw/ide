package com.devonfw.ide.configurator.resolve;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of {@link VariableResolver}.
 *
 * @since 3.0.0
 */
public class VariableResolverImpl implements VariableResolver {

  private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

  private static final String VARIABLE_PREFIX = "${";

  private static final String VARIABLE_SUFFIX = "}";

  private Properties variables;

  /**
   * The constructor.
   *
   * @param variables the {@link Properties} with the variables to resolve.
   */
  public VariableResolverImpl(Properties variables) {

    this.variables = variables;
  }

  @Override
  public String resolve(String text) {

    Matcher m = VARIABLE_PATTERN.matcher(text);
    String result = text;
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String match = m.group(1);
      String replacement = resolveVariable(match);
      if (replacement != null) {
        m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
      }
    }
    m.appendTail(sb);
    result = sb.toString();
    return result;
  }

  /**
   * @param name the name of the variable to resolve.
   * @return the value of the variable with the given {@code name}.
   */
  protected String resolveVariable(String name) {

    String result = null;
    Object value = this.variables.get(name);
    if (value != null) {
      result = value.toString();
    } else {
      result = System.getProperty(name);
      if (result == null) {
        result = System.getenv(name);
      }
    }
    return result;
  }

  @Override
  public String inverseResolve(String text) {

    String result = text;
    for (Map.Entry<Object, Object> entry : this.variables.entrySet()) {
      result = result.replace(entry.getValue().toString(),
          VARIABLE_PREFIX + entry.getKey().toString() + VARIABLE_SUFFIX);
    }
    return result;
  }

}
