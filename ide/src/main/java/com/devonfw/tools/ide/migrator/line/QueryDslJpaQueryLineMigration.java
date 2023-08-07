package com.devonfw.tools.ide.migrator.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.devonfw.tools.ide.migrator.file.FileFilterAll;

/**
 * Implementation of {@link LineMigration} for simple string replacement.
 */
public class QueryDslJpaQueryLineMigration extends AbstractLineMigration {

  private static final Pattern ENTITY_NAME_PATTERN = Pattern.compile(".*<([a-zA-Z0-9]*Entity)>.*");

  private String entityName;

  private String replacement;

  /**
   * The constructor.
   */
  public QueryDslJpaQueryLineMigration() {

    super(FileFilterAll.INSTANCE);
  }

  @Override
  protected String migrate(String line) {

    if (this.entityName == null) {
      // kind of hackish but should do
      Matcher matcher = ENTITY_NAME_PATTERN.matcher(line);
      if (matcher.matches()) {
        this.entityName = matcher.group(1);
        this.replacement = "JPAQuery<" + this.entityName + ">";
      }
      return line;
    } else {
      return line.replace("JPAQuery", this.replacement);
    }
  }

  @Override
  public void clear() {

    this.entityName = null;
    this.replacement = null;
  }

}
