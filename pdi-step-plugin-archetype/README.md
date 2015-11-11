##pdi-step-plugin-archetype

Install this into your local maven repository
```
mvn install
```

To use this to create a new PDI step plugin project you could then do something like this:

```
mvn archetype:generate \
 -DarchetypeCatalog=local \
 -DarchetypeGroupId=org.pentaho \
 -DarchetypeArtifactId=pdi-step-plugin-archetype \
 -DgroupId=com.my.company \
 -DartifactId=my-step-plugin \
 -Dversion=1.0-SNAPSHOT \
 -Dplugin_class_name=MyStep \
 -Dplugin_name="My Step" \
 -Dplugin_category=Transform \
 -Dplugin_description="This is what my step does."

```