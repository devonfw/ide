package com.devonfw.tools.ide.property;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * {@link PathProperty} for a file.
 */
public class FileProperty extends PathProperty {

  /**
   * The constructor.
   *
   * @param name the {@link #getName() property name}.
   * @param required the {@link #isRequired() required flag}.
   * @param alias the {@link #getAlias() property alias}.
   */
  public FileProperty(String name, boolean required, String alias) {

    this(name, required, alias, null);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() property name}.
   * @param required the {@link #isRequired() required flag}.
   * @param alias the {@link #getAlias() property alias}.
   * @param validator the {@link Consumer} used to {@link #validate() validate} the {@link #getValue() value}.
   */
  public FileProperty(String name, boolean required, String alias, Consumer<Path> validator) {

    super(name, required, alias, validator);
  }

}
