#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ${package};

import ${package}.delegate.DelegateDriver;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

/**
 * Skeleton for PDI Database plugin.
 *
 * Things to do when instantiating a new project:
 *
 * 1) If you rename DelegateDriver, be sure to change the getDriverClass() method below.
 * 2) Update src/main/resources/${artifactId}-feature.xml and the POM as desired.
 * 3) For any class, add custom logic to workaround any limitations the real JDBC driver might have.
 */
@DatabaseMetaPlugin( type = "${database_meta_class_name}", typeDescription = "${database_meta_class_name}" )
public class ${database_meta_class_name} extends BaseDatabaseMeta implements DatabaseInterface {

  public static final String SCHEME = "${jdbc_url_scheme}";

  public static final int DEFAULT_PORT = ${jdbc_default_port};

  @Override
  public int[] getAccessTypeList() {
    return new int[]{ DatabaseMeta.TYPE_ACCESS_NATIVE, DatabaseMeta.TYPE_ACCESS_JNDI };
  }

  @Override
  public int getDefaultDatabasePort() {
    return DEFAULT_PORT;
  }

  @Override
  public String getDriverClass() {
    return DelegateDriver.class.getCanonicalName();
  }

  @Override
  public String getURL( String hostname, String port, String databaseName ) {
    return new StringBuilder( "jdbc:" )
      .append( SCHEME )
      .append( "://" )
      .append( hostname )
      .append( ":" )
      .append( port )
      .append( "/" )
      .append( databaseName ).toString();

  }

  @Override
  public String getAddColumnStatement( String s, ValueMetaInterface valueMetaInterface, String s1, boolean b, String s2, boolean b1 ) {
    // TODO
    return null;
  }

  @Override
  public String getModifyColumnStatement( String s, ValueMetaInterface valueMetaInterface, String s1, boolean b, String s2, boolean b1 ) {
    // TODO
    return null;
  }

  @Override
  public String[] getUsedLibraries() {
    return new String[0];
  }

  @Override
  public String getFieldDefinition( ValueMetaInterface v, String tk, String pk, boolean use_autoinc,
                                    boolean add_fieldname, boolean add_cr ) {
    // TODO override for your types
    String retval = "";

    String fieldname = v.getName();
    int length = v.getLength();
    int precision = v.getPrecision();

    if ( add_fieldname ) {
      retval += fieldname + " ";
    }

    int type = v.getType();
    switch ( type ) {
      case ValueMetaInterface.TYPE_DATE:
        retval += "TIMESTAMP";
        break;
      case ValueMetaInterface.TYPE_BOOLEAN:
        if ( supportsBooleanDataType() ) {
          retval += "BOOLEAN";
        } else {
          retval += "CHAR(1)";
        }
        break;
      case ValueMetaInterface.TYPE_NUMBER:
      case ValueMetaInterface.TYPE_INTEGER:
      case ValueMetaInterface.TYPE_BIGNUMBER:
        if ( fieldname.equalsIgnoreCase( tk ) || // Technical key
          fieldname.equalsIgnoreCase( pk ) // Primary key
          ) {
          retval += "BIGSERIAL";
        } else {
          if ( length > 0 ) {
            if ( precision > 0 || length > 18 ) {
              // Numeric(Precision, Scale): Precision = total length; Scale = decimal places
              retval += "NUMERIC(" + ( length + precision ) + ", " + precision + ")";
            } else {
              if ( length > 9 ) {
                retval += "BIGINT";
              } else {
                if ( length < 5 ) {
                  retval += "SMALLINT";
                } else {
                  retval += "INTEGER";
                }
              }
            }

          } else {
            retval += "DOUBLE PRECISION";
          }
        }
        break;
      case ValueMetaInterface.TYPE_STRING:
        if ( length < 1 || length >= DatabaseMeta.CLOB_LENGTH ) {
          retval += "TEXT";
        } else {
          retval += "VARCHAR(" + length + ")";
        }
        break;
      default:
        retval += " UNKNOWN";
        break;
    }

    if ( add_cr ) {
      retval += Const.CR;
    }

    return retval;
  }

}
