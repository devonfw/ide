package com.devonfw.tools.ide.commandlet;

/**
 * Interface to {@link #getCommandlet(Class) get} a {@link Commandlet} instance that is properly initialized.
 */
public interface CommandletManager {

  /**
   * @param <C> type of the {@link Commandlet}.
   * @param commandletType the {@link Class} reflecting the requested {@link Commandlet}.
   * @return an instance of the requested {@link Commandlet} that is properly initialized.
   */
  <C extends Commandlet> C getCommandlet(Class<C> commandletType);

}
