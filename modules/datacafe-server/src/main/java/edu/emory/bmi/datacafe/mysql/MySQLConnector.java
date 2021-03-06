/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.mysql;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.SqlConstants;
import edu.emory.bmi.datacafe.core.kernel.AbstractDataSourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Connects to the MySQL data server.
 */
public class MySQLConnector extends AbstractDataSourceConnector {
    private static Logger logger = LogManager.getLogger(MySQLConnector.class.getName());
    private static Map<String, Connection> databaseConnectionMap = new HashMap<>();

    @Override
    public List getAllIDs(String database, String table, String idAttribute) {
        return getIDs(database, table, idAttribute);
    }

    /**
     * Gets all the values from the given table for any attribute. Used to get the Ids.
     *
     * @param database        the database name
     * @param table           the table in the given database.
     * @param idAttribute     the attribute key.
     * @param limitingClauses WHERE<..>
     * @return idList the list of ids.
     */
    public List<String> getIDs(String database, String table, String idAttribute, String... limitingClauses) {
        Connection con;
        List<String> idList = new ArrayList<>();
        try {
            con = getConnection(database);

            Statement st = con.createStatement();
            String sql = ("SELECT " + idAttribute + " FROM " + table);
            if (limitingClauses.length == 1) {
                sql += " " + limitingClauses[0];
            }
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(idAttribute);
                idList.add(id);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in initiating the connection", e);
        }
        return idList;
    }

    public Connection getConnection(String database) throws SQLException {
        getMySQLDriver();
        if (databaseConnectionMap.get(database) != null) {
            return databaseConnectionMap.get(database);
        } else {
            Connection con = DriverManager.getConnection(SqlConstants.MYSQL_URL_PREFIX +
                            ConfigReader.getCompleteMySQLUrl() + "/" +
                            database + ConfigReader.getAdditionalMySQLConf(),
                    ConfigReader.getMySQLUserName(), ConfigReader.getMySQLPassword()
            );
            databaseConnectionMap.put(database, con);
            return con;
        }
    }

    /**
     * Close the SQL Connection
     *
     * @param con the sql connection
     */
    public void closeConnection(Connection con) {
        try {
            assert con != null;
            con.close();
            if (logger.isDebugEnabled()) {
                logger.debug("SQL Connection Closed..");
            }
        } catch (SQLException e) {
            logger.error("Exception in closing the connection");
        }
    }

    public void getMySQLDriver() {
        try {
            Class.forName(SqlConstants.MYSQL_DRIVER).newInstance();
        } catch (InstantiationException e) {
            logger.error("Exception initiating the instance", e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal Access Exception", e);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found Exception", e);
        }
    }

    @Override
    public List<String> getAttributeValues(String database, String table, List ids, String idAttribute,
                                           String[] preferredAttributes) {
        Connection con;
        List<String> idList = new ArrayList<>();
        try {
            con = getConnection(database);

            Statement st = con.createStatement();

            for (Object id : ids) {
                String allAttributes;

                allAttributes = getChosenAttributeNames(preferredAttributes);
                String sql = ("SELECT " + allAttributes + " FROM " + table + " WHERE " + idAttribute + " = " +
                        "\"" + id + "\"");

                ResultSet rs = st.executeQuery(sql);
                String outcomeOfEachEntry = "";

                if (rs.next()) {
                    for (int key = 0; key < preferredAttributes.length; key++) {
                        if (key > 0) {
                            outcomeOfEachEntry += ",";
                        }
                        outcomeOfEachEntry += rs.getString(preferredAttributes[key]);
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(outcomeOfEachEntry);
                }
                idList.add(outcomeOfEachEntry);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception in initiating the connection", e);
        }
        return idList;
    }

    @Override
    public void closeConnections() {
        for (Connection con : databaseConnectionMap.values()) {
            try {
                con.close();
                if (logger.isDebugEnabled()) {
                    logger.debug("Successfully closed the MySQL server connection.");
                }
            } catch (SQLException e) {
                logger.error("Error in closing the MySQL connection", e);
            }
        }
    }
}
