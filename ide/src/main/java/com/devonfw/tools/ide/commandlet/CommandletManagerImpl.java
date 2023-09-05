package com.devonfw.tools.ide.commandlet;

import java.util.HashMap;
import java.util.Map;

import com.devonfw.tools.ide.commandlet.gh.Gh;
import com.devonfw.tools.ide.commandlet.java.Java;
import com.devonfw.tools.ide.context.IdeContext;

/**
 * Implementation of {@link CommandletManager}.
 */
public final class CommandletManagerImpl implements CommandletManager {

  private final Map<Class<? extends Commandlet>, Commandlet> commandletMap;

  /**
   * The constructor.
   *
   * @param context the {@link IdeContext}.
   */
  private CommandletManagerImpl(IdeContext context) {

    super();
    this.commandletMap = new HashMap<>();
    add(new Gh(), context);
    add(new Java(), context);
    add(new EnvironmentCommand(), context);
  }

  private void add(Commandlet commandlet, IdeContext context) {

    commandlet.initContext(context);
    this.commandletMap.put(commandlet.getClass(), commandlet);
  }

  @Override
  public <C extends Commandlet> C getCommandlet(Class<C> commandletType) {

    Commandlet commandlet = this.commandletMap.get(commandletType);
    if (commandlet == null) {
      throw new IllegalStateException("Commandlet " + commandletType + " not registered!");
    }
    return commandletType.cast(commandlet);
  }

  /**
   * @param context the {@link IdeContext}.
   * @return the {@link CommandletManager}.
   */
  public static CommandletManager of(IdeContext context) {

    return new CommandletManagerImpl(context);
  }

}
