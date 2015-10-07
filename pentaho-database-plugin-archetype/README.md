##pentaho-database-plugin-archetype

Install this into your local maven repository
```
mvn install
```

To use this to create a new database plugin project you could then do something like this:

```
mvn archetype:generate \
 -DarchetypeCatalog=local \
 -DarchetypeGroupId=org.pentaho \
 -DarchetypeArtifactId=pentaho-database-plugin-archetype \
 -DgroupId=com.my.company \
 -DartifactId=my-db-plugin \
 -Dversion=1.0-SNAPSHOT \
 -Ddriver_groupId=org.postgresql \
 -Ddriver_artifactId=postgresql \
 -Ddriver_version=9.3-1100-jdbc41 \
 -Djdbc_default_port=5432 \
 -Djdbc_url_scheme=psql \
 -Ddatabase_meta_class_name=PsqlMeta \
 -Dfully_qualified_class_for_real_driver=org.postgresql.Driver

```