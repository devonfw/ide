package com.devonfw.ide.configurator.resolve;

/**
 * Interface for a resolver of variables. It is capable of replacing one or multiple occurrences of variables with their
 * actual value.
 *
 * @see #resolve(String)
 *
 * @since 3.0.0
 */
public interface VariableResolver {

  /**
   * @param text the {@link String} to resolve (e.g. value of {@link java.util.Properties#get(Object) property} or from
   *        XML attribute or element).
   * @return the resolved {@link String}. If the given {@link String} contained variables (e.g. "${«variable.name»}"),
   *         these are resolved and replaced with their actual value.
   */
  String resolve(String text);

  /**
   * Inverse operation of {@link #resolve(String)}.
   *
   * @param text the {@link String} to generalize (substitute values back to variable expressions).
   * @return the generalized {@link String}. If the given {@link String} contained values of the variables (e.g. an
   *         absolute path), these are substituted back to variable expressions.
   */
  String inverseResolve(String text);

}
