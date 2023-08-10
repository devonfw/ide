package com.devonfw.tools.ide.env.var;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.env.var.def.IdeVariables;
import com.devonfw.tools.ide.env.var.def.VariableDefinition;

/**
 * Implementation of {@link EnvironmentVariables} that resolves variables recursively.
 */
class EnvironmentVariablesResolved extends AbstractEnvironmentVariables {

  // Variable surrounded with "${" and "}" such as "${JAVA_HOME}" 1......2........
  private static final Pattern VARIABLE_SYNTAX = Pattern.compile("(\\$\\{([^}]+)})");

  private static final Map<String, VariableDefinition<?>> VARIABLE_MAP;

  private static final int MAX_RECURSION = 9;

  static {
    VARIABLE_MAP = new HashMap<>();
    for (VariableDefinition<?> var : IdeVariables.VARIABLES) {
      register(var, VARIABLE_MAP, false);
      register(var, VARIABLE_MAP, true);
    }
  }

  /**
   * The constructor.
   *
   * @param parent the parent {@link EnvironmentVariables} to inherit from.
   */
  EnvironmentVariablesResolved(AbstractEnvironmentVariables parent) {

    super(parent, "Resolved", parent.logger);
  }

  private static void register(VariableDefinition<?> var, Map<String, VariableDefinition<?>> map, boolean legacy) {

    String key;
    if (legacy) {
      key = var.getLegacyName();
      if (key == null) {
        return;
      }
    } else {
      key = var.getName();
    }
    VariableDefinition<?> duplicate = map.put(key, var);
    if (duplicate != null) {
      throw new IllegalStateException("Duplicate variables for key '" + key + "': " + var + " and " + duplicate);
    }
  }

  @Override
  public String getFlat(String name) {

    return null;
  }

  @Override
  public String get(String name) {

    String value = getValue(name);
    if (value != null) {
      value = resolve(value, name, 0, name, value);
    }
    return value;
  }

  private String getValue(String name) {

    // this is an intended hack but only allowed here...
    IdeContext context = (IdeContext) this.logger;
    VariableDefinition<?> var = VARIABLE_MAP.get(name);
    String value;
    if ((var != null) && var.isForceDefaultValue()) {
      value = var.getDefaultValueAsString(context);
    }
    value = this.parent.get(name);
    if ((value == null) && (var != null)) {
      String key = var.getName();
      if (!name.equals(key)) {
        value = this.parent.get(key);
      }
      if (value != null) {
        value = var.getDefaultValueAsString(context);
      }
    }
    if ((value != null) && (value.startsWith("~/"))) {
      value = context.env().getUserHome() + value.substring(1);
    }
    return value;
  }

  String resolve(String value, String name, int recursion, String rootName, String rootValue) {

    if (value == null) {
      return null;
    }
    if (recursion > MAX_RECURSION) {
      throw new IllegalStateException("Reached maximum recursion resolving " + value + " for root valiable " + rootName
          + " with value '" + rootValue + "'.");
    }
    recursion++;
    Matcher matcher = VARIABLE_SYNTAX.matcher(value);
    if (!matcher.find()) {
      return value;
    }
    StringBuilder sb = new StringBuilder(value.length() + 8);
    do {
      String variableName = matcher.group(2);
      String variableValue = getValue(variableName);
      if (variableValue == null) {
        this.logger.warning("Undefined variable {} in '{}={}' for root '{}={}'", variableName, name, value, rootName,
            rootValue);
      } else {
        String replacement = resolve(variableValue, variableName, recursion, rootName, rootValue);
        matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
      }
    } while (matcher.find());
    matcher.appendTail(sb);
    String resolved = sb.toString();
    return resolved;
  }

  @Override
  public EnvironmentVariables resolved() {

    return this;
  }

  @Override
  public EnvironmentVariablesResolved extend(Path propertiesPath) {

    throw new UnsupportedOperationException();
  }

}
