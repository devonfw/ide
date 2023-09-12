package com.devonfw.tools.ide.commandlet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.devonfw.tools.ide.context.IdeContext;
import com.devonfw.tools.ide.property.Property;
import com.devonfw.tools.ide.tool.gh.Gh;
import com.devonfw.tools.ide.tool.java.Java;
import com.devonfw.tools.ide.tool.mvn.Mvn;

/**
 * Implementation of {@link CommandletManager}.
 */
public final class CommandletManagerImpl implements CommandletManager {

  private static CommandletManagerImpl INSTANCE;

  private final Map<Class<? extends Commandlet>, Commandlet> commandletTypeMap;

  private final Map<String, Commandlet> commandletNameMap;

  private final Collection<Commandlet> commandlets;

  /**
   * The constructor.
   *
   * @param context the {@link IdeContext}.
   */
  private CommandletManagerImpl(IdeContext context) {

    super();
    this.commandletTypeMap = new HashMap<>();
    this.commandletNameMap = new HashMap<>();
    this.commandlets = Collections.unmodifiableCollection(this.commandletTypeMap.values());
    add(new EnvironmentCommandlet(context));
    add(new InstallCommandlet(context));
    add(new VersionSetCommandlet(context));
    add(new VersionGetCommandlet(context));
    add(new VersionListCommandlet(context));
    add(new Gh(context));
    add(new Java(context));
    add(new Mvn(context));
  }

  private void add(Commandlet commandlet) {

    boolean hasRequiredProperty = false;
    for (Property<?> property : commandlet.getProperties()) {
      if (property.isRequired()) {
        hasRequiredProperty = true;
        break;
      }
    }
    if (!hasRequiredProperty) {
      throw new IllegalStateException("Commandlet " + commandlet + " must have at least one mandatory property!");
    }
    this.commandletTypeMap.put(commandlet.getClass(), commandlet);
    Commandlet duplicate = this.commandletNameMap.put(commandlet.getName(), commandlet);
    if (duplicate != null) {
      throw new IllegalStateException("Commandlet " + commandlet + " has the same name as " + duplicate);
    }
  }

  @Override
  public Collection<Commandlet> getCommandlets() {

    return this.commandlets;
  }

  @Override
  public <C extends Commandlet> C getCommandlet(Class<C> commandletType) {

    Commandlet commandlet = this.commandletTypeMap.get(commandletType);
    if (commandlet == null) {
      throw new IllegalStateException("Commandlet for type " + commandletType + " is not registered!");
    }
    return commandletType.cast(commandlet);
  }

  @Override
  public Commandlet getCommandlet(String name) {

    Commandlet commandlet = this.commandletNameMap.get(name);
    if (commandlet == null) {
      throw new IllegalStateException("Commandlet for name " + name + " is not registered!");
    }
    return commandlet;
  }

  /**
   * @param context the {@link IdeContext}.
   * @return the {@link CommandletManager}.
   */
  public static CommandletManager of(IdeContext context) {

    if (context == null) {
      if (INSTANCE == null) {
        throw new IllegalStateException("Not initialized!");
      }
    } else {
      if (INSTANCE != null) {
        System.out.println("Multiple initializations!");
      }
      INSTANCE = new CommandletManagerImpl(context);
    }
    return INSTANCE;
  }

}
