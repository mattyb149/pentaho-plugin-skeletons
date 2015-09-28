## pentaho-database-plugin

This project contains a skeleton for Pentaho Database plugin.
 
 Things to do when instantiating a new project:
 
 1. Rename sub-package "mydatabase" (or the whole package) to your package
 2. Rename the meta classes (MyDatabaseMeta, e.g.) to something reflective of your Database plugin
 3. Change the "type" and "typeDescription" parameters of the @DatabaseMetaPlugin annotation
 4. Set the SCHEME, default database port, etc. for your database.
 5. If you rename DelegateDriver, be sure to change the getDriverClass() method below.
 6. Update src/main/resources/pentaho-database-plugin-feature.xml and the POM as desired.
 7. For any class, add custom logic to workaround any limitations the real JDBC driver might have.
 