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
package org.pentaho.community.di.plugins.database.mydatabase;

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
 * 1) Rename sub-package "mydatabase" (or the whole package) to your package
 * 2) Rename this class (MyDatabaseMeta) to something reflective of your Database plugin
 * 3) Change the "type" and "typeDescription" parameters of the @DatabaseMetaPlugin annotation
 * 4) Set the SCHEME, default database port, etc. for your database.
 * 5) If you rename DelegateDriver, be sure to change the getDriverClass() method below.
 * 6) Update src/main/resources/pentaho-database-plugin-feature.xml and the POM as desired.
 * 7) For any class, add custom logic to workaround any limitations the real JDBC driver might have.
 */
@DatabaseMetaPlugin( type = "myDbType", typeDescription = "MyDB" )
public class MyDatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {

  public static final String SCHEME = "mydb";



  @Override
  public int[] getAccessTypeList() {
    return new int[]{ DatabaseMeta.TYPE_ACCESS_NATIVE, DatabaseMeta.TYPE_ACCESS_JNDI };
  }

  @Override
  public int getDefaultDatabasePort() {
    return -1;
  }

  @Override
  public String getDriverClass() {
    return "org.pentaho.di.plugins.database.DelegateDriver";

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
