package com.devonfw.tools.ide.migrator;

import com.devonfw.tools.ide.migrator.builder.MigrationBuilder;
import com.devonfw.tools.ide.migrator.file.FileFilterPattern;
import com.devonfw.tools.ide.migrator.line.QueryDslJpaQueryLineMigration;
import com.devonfw.tools.ide.migrator.version.VersionIdentifier;

/**
 * The "configuration" with the business logic for the migration as fluent API (DSL).
 */
public class Migrations {

  /**
   * @return the {@link MigrationImpl} for devon4j.
   */
  public static MigrationImpl devon4j() {

    return new MigrationBuilder(VersionIdentifier.ofOasp4j("2.6.0")) //

        .to(VersionIdentifier.ofOasp4j("2.6.1")) //
        .pom().replaceProperty("oasp4j.version", "2.6.1").and().next() //

        .to(VersionIdentifier.ofOasp4j("3.0.0")) //
        .pom().replaceProperty("oasp4j.version", "3.0.0") //
        .replaceProperty("spring.boot.version", "2.0.4.RELEASE") //
        .replaceProperty("flyway.version", "5.0.7") //
        .replaceDependency(new VersionIdentifier("org.hibernate", "hibernate-validator", null),
            new VersionIdentifier("org.hibernate.validator", "hibernate-validator", null))
        .addDependency(new VersionIdentifier("*-core", null),
            new VersionIdentifier(VersionIdentifier.GROUP_ID_OASP4J_MODULES, "oasp4j-jpa", null))
        .and().java() //
        .replace("org.hibernate.Query", "org.hibernate.query.Query")
        .replace("com.mysema.query.alias.Alias", "com.querydsl.core.alias.Alias")
        .replace("com.mysema.query.jpa.impl.JPAQuery", "com.querydsl.jpa.impl.JPAQuery")
        .replace("com.mysema.query.types.path.EntityPathBase", "com.querydsl.core.types.dsl.EntityPathBase")
        .replace("org.springframework.boot.web.support.SpringBootServletInitializer",
            "org.springframework.boot.web.servlet.support.SpringBootServletInitializer")
        .replace("org.springframework.boot.context.embedded.LocalServerPort",
            "org.springframework.boot.web.server.LocalServerPort")
        .replace("org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration")
        .replace("org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration",
            "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
        .replace("org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration",
            "org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration")
        .replace("query.clone().count()", "query.clone().fetchCount()").replace("query.count()", "query.fetchCount()")
        .replace("query.list(expr)", "query.select(expr).fetch()").replace("query.list()", "query.fetch()")
        .replace("query.firstResult(", "query.fetchFirst(").replace("query.uniqueResult(", "query.fetchUnique(")
        .replace("query.listResults(", "query.fetchResults(").add(new QueryDslJpaQueryLineMigration()).and() //
        .applicationProperties().replace("flyway.", "spring.flyway.") //
        .replace("server.context-path", "server.servlet.context-path").and().next() //

        .to(VersionIdentifier.ofDevon4j("3.0.0")) //
        .pom().replaceProperty("oasp4j.version", "3.0.0", "devon4j.version") //
        .replaceRegex("\\s*\\$\\{oasp4j\\.version\\}\\s*", "\\$\\{devon4j.version\\}") //
        .replaceProperty("oasp.test.excluded.groups", null, "devonfw.test.excluded.groups") //
        .replaceRegex("\\s*\\$\\{oasp\\.test\\.excluded\\.groups\\}\\s*", "\\$\\{devonfw.test.excluded.groups\\}") //
        .replaceRegex("io\\.oasp\\.module\\.test\\.", "com.devonfw.module.test.") //
        .replaceDependency(new VersionIdentifier(VersionIdentifier.GROUP_ID_OASP4J, "oasp4j-bom", null),
            new VersionIdentifier(VersionIdentifier.GROUP_ID_DEVON4J_BOMS, "devon4j-bom", null))
        .replaceDependency(new VersionIdentifier(VersionIdentifier.GROUP_ID_OASP4J + "*", "oasp4j*", null),
            new VersionIdentifier(VersionIdentifier.GROUP_ID_DEVON4J + "*", "devon4j*", null))
        .addDependency(new VersionIdentifier(null, "*-api", null),
            new VersionIdentifier(VersionIdentifier.GROUP_ID_OASP4J_MODULES, "oasp4j-jpa", "3.0.0"))
        .and().java() //
        .replace("io.oasp.module.rest.service.impl.json.ObjectMapperFactory",
            "com.devonfw.module.json.common.base.ObjectMapperFactory")
        .replace("io.oasp.module.jpa.dataaccess.api.MutablePersistenceEntity",
            "com.devonfw.module.basic.common.api.entity.RevisionedPersistenceEntity")
        .replace("ApplicationPersistenceEntity implements ApplicationEntity, MutablePersistenceEntity<Long>",
            "ApplicationPersistenceEntity implements ApplicationEntity, RevisionedPersistenceEntity<Long>")
        .replace("ApplicationDao<ENTITY extends MutablePersistenceEntity<Long>>",
            "ApplicationDao<ENTITY extends com.devonfw.module.basic.common.api.entity.PersistenceEntity<Long>>")
        .replace("ApplicationRevisionedDao<ENTITY extends MutablePersistenceEntity<Long>>",
            "ApplicationRevisionedDao<ENTITY extends RevisionedPersistenceEntity<Long>>")
        .replace("DaoImpl<ENTITY extends MutablePersistenceEntity<Long>>",
            "DaoImpl<ENTITY extends RevisionedPersistenceEntity<Long>>")
        .replace("MutablePersistenceEntity", "RevisionedPersistenceEntity")
        .replace("io.oasp.module.jpa.common.api.to.", "hack.oasp.module.jpa.common.api.to.")
        .replace("io.oasp.module.", "com.devonfw.module.")
        .replace("hack.oasp.module.jpa.common.api.to.", "io.oasp.module.jpa.common.api.to.")
        .replace("net.sf.mmm.util.entity.api.GenericEntity", "com.devonfw.module.basic.common.api.entity.GenericEntity")
        .replace("net.sf.mmm.util.entity.api.RevisionedEntity",
            "com.devonfw.module.basic.common.api.entity.RevisionedEntity")
        .replace("net.sf.mmm.util.entity.api.MutableRevisionedEntity",
            "com.devonfw.module.basic.common.api.entity.RevisionedEntity")
        .replace("net.sf.mmm.util.entity.api.PersistenceEntity",
            "com.devonfw.module.basic.common.api.entity.PersistenceEntity")
        .replace("net.sf.mmm.util.entity.api.MutableGenericEntity",
            "com.devonfw.module.basic.common.api.entity.GenericEntity")
        .replace("net.sf.mmm.util.transferobject.api.CompositeTo", "com.devonfw.module.basic.common.api.to.AbstractCto")
        .replace("net.sf.mmm.util.transferobject.api.AbstractTransferObject",
            "com.devonfw.module.basic.common.api.to.AbstractTo")
        .replace("net.sf.mmm.util.transferobject.api.TransferObject",
            "com.devonfw.module.basic.common.api.to.AbstractTo")
        .replace("TransferObject", "AbstractTo")
        .replace("AbstractCto extends CompositeTo",
            "AbstractCto extends com.devonfw.module.basic.common.api.to.AbstractCto")
        .replace("CompositeTo", "AbstractCto")
        .replace("AbstractEto extends EntityTo<Long>",
            "AbstractEto extends com.devonfw.module.basic.common.api.to.AbstractEto")
        .replace("EntityTo<Long>", "AbstractEto").replace("MutableGenericEntity<", "GenericEntity<")
        .replace("net.sf.mmm.util.transferobject.api.EntityTo", "com.devonfw.module.basic.common.api.to.AbstractEto")
        .replace("/io/oasp/module/security/access-control-schema.xsd",
            "/com/devonfw/module/security/access-control-schema.xsd") //
        .replace(".OaspPackage", ".Devon4jPackage").replace("OaspPackage ", "Devon4jPackage ")
        .replace("OaspPackage.", "Devon4jPackage.")
        .replace("import com.devonfw.module.basic.common.api.to.AbstractEto;", "",
            FileFilterPattern.accept("AbstractEto\\.java"))
        .replace("import com.devonfw.module.basic.common.api.to.AbstractCto;", "",
            FileFilterPattern.accept("AbstractCto\\.java"))
        .replaceRegex("implements ([a-zA-Z0-9_]*)Dao",
            "implements $1Dao, io.oasp.module.jpa.common.base.LegacyDaoQuerySupport<$1Entity>",
            FileFilterPattern.reject("Application(MasterData)?DaoImpl\\.java")) //
        .and() //
        .next().to(VersionIdentifier.ofDevon4j("3.0.1")) //
        .pom().replaceProperty("devon4j.version", "3.0.1").and() //
        .next().to(VersionIdentifier.ofDevon4j("3.0.2")) //
        .pom().replaceProperty("devon4j.version", "3.0.2").and() //
        .next().to(VersionIdentifier.ofDevon4j("3.1.0")) //
        .pom().replaceProperty("devon4j.version", "3.1.0") //
        .replaceProperty("spring.boot.version", "2.1.6.RELEASE") //
        // only for oasp4j legacy project (flyway.version not present in projects created from devon4j)
        .replaceProperty("flyway.version", "5.2.4") //
        .replaceDependency(new VersionIdentifier("org.hibernate.javax.persistence", "hibernate-jpa-2.1-api", null),
            new VersionIdentifier("javax.persistence", "javax.persistence-api", null))
        .and().java() //
        .replace("org.dozer.DozerBeanMapper", "com.github.dozermapper.core.DozerBeanMapper")
        .replace("import org.dozer.Mapper;",
            "import com.github.dozermapper.core.DozerBeanMapperBuilder;\n import com.github.dozermapper.core.Mapper;")
        .replace("return new DozerBeanMapper(beanMappings);",
            "Mapper mapper = DozerBeanMapperBuilder.create().withMappingFiles(beanMappings).build();\n return mapper;")//
        .replace("org.dozer.loader.api.BeanMappingBuilder", "com.github.dozermapper.core.loader.api.BeanMappingBuilder") //
        .replace("org.dozer.CustomConverter", "com.github.dozermapper.core.CustomConverter") //
        .replace("org.dozer.MappingException", "com.github.dozermapper.core.MappingException") //
        .replace("org.dozer.loader.api.FieldsMappingOptions",
            "com.github.dozermapper.core.loader.api.FieldsMappingOptions") //
        .and().xml("dozer-mapping.xml")
        .replaceNamespace("http://dozer.sourceforge.net", "http://dozermapper.github.io/schema/bean-mapping")
        .replaceNamespace("http://dozer.sourceforge.net http://dozer.sourceforge.net/schema/beanmapping.xsd",
            "http://dozermapper.github.io/schema/bean-mapping http://dozermapper.github.io/schema/bean-mapping.xsd")
        .replaceCommentNode(
            "<!DOCTYPE mappings PUBLIC \"-//DOZER//DTD MAPPINGS//EN\" \"http://dozer.sourceforge.net/dtd/dozerbeanmapping.dtd\">",
            "<!DOCTYPE mappings PUBLIC \"-//DOZER//DTD MAPPINGS//EN\" \"https://github.com/DozerMapper/DozerMapper.github.io/blob/master/dtd/bean-mapping.dtd\">")
        .and() //
        .next().to(VersionIdentifier.ofDevon4j("3.1.1")) //
        .pom().replaceProperty("devon4j.version", "3.1.1") //
        .replaceProperty("jackson.version", "2.9.9.20190727") //
        .and() //
        .next().to(VersionIdentifier.ofDevon4j("3.2.0")) //
        .pom().replaceProperty("devon4j.version", "3.2.0") //
        .and().java()
        .replace("com.devonfw.module.jpa.dataaccess.api.RevisionMetadata",
            "com.devonfw.module.basic.common.api.RevisionMetadata")
        .replace("com.devonfw.module.jpa.dataaccess.api.RevisionMetadataType",
            "com.devonfw.module.basic.common.api.RevisionMetadataType")
        .and() //
        .next().build();
  }

}
