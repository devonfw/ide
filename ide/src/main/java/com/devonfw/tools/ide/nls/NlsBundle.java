package com.devonfw.tools.ide.nls;

import java.util.Locale;
import java.util.ResourceBundle;

import com.devonfw.tools.ide.context.IdeContext;

/**
 * Wrapper for {@link ResourceBundle} to avoid {@link java.util.MissingResourceException}.
 */
public class NlsBundle {

  private final IdeContext context;

  private final String fqn;

  private final ResourceBundle bundle;

  /**
   * The constructor.
   *
   * @param context the {@link IdeContext}.
   * @param name the simple name of {@link ResourceBundle} (e.g. "Cli").
   */
  public NlsBundle(IdeContext context, String name) {

    this(context, name, Locale.getDefault());
  }

  /**
   * The constructor.
   *
   * @param context the {@link IdeContext}.
   * @param name the simple name of {@link ResourceBundle} (e.g. "Cli").
   * @param locale the explicit {@link Locale} to use.
   */
  public NlsBundle(IdeContext context, String name, Locale locale) {

    super();
    this.context = context;
    this.fqn = "nls." + name;
    this.bundle = ResourceBundle.getBundle(this.fqn, locale);
  }

  /**
   * @param key the NLS key.
   * @return the localized message (translated to the users language).
   */
  public String get(String key) {

    if (!this.bundle.containsKey(key)) {
      this.context.warning("Cound not find key '{}' in ResourceBundle {}.properties", key, this.fqn);
      return "?" + key;
    }
    return this.bundle.getString(key);
  }

  /**
   * @param commandlet the {@link com.devonfw.tools.ide.commandlet.Commandlet#getName() name} of the
   *        {@link com.devonfw.tools.ide.commandlet.Commandlet}.
   * @return the localized message (translated to the users language).
   */
  public String getCommand(String commandlet) {

    return get("cmd-" + commandlet);
  }

  /**
   * @param commandlet the {@link com.devonfw.tools.ide.commandlet.Commandlet#getName() name} of the
   *        {@link com.devonfw.tools.ide.commandlet.Commandlet}.
   * @param option the {@link com.devonfw.tools.ide.property.Property#getName() name} of the
   *        {@link com.devonfw.tools.ide.property.Property#isOption() option}.
   * @return the localized message (translated to the users language).
   */
  public String getOption(String commandlet, String option) {

    return get("opt" + option);
  }

  /**
   * @param commandlet the {@link com.devonfw.tools.ide.commandlet.Commandlet#getName() name} of the
   *        {@link com.devonfw.tools.ide.commandlet.Commandlet}.
   * @param value the {@link com.devonfw.tools.ide.property.Property#getNameOrAlias() name or alias} of the
   *        {@link com.devonfw.tools.ide.property.Property#isValue() value}.
   * @return the localized message (translated to the users language).
   */
  public String getValue(String commandlet, String value) {

    return get("val-" + value);
  }

  /**
   * @param context the {@link IdeContext}.
   * @return the {@link NlsBundle} for "Cli".
   */
  public static NlsBundle of(IdeContext context) {

    return new NlsBundle(context, "Cli");
  }

}
