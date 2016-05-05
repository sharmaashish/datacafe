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
package edu.emory.bmi.datacafe.client.drill;

import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.core.kernel.CoreUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Retrieve data lake meta data
 */
public class DataLakeRetriever {
    private static Logger logger = LogManager.getLogger(DataLakeRetriever.class.getName());

    public static final String QUERY_RETRIEVE_DATABASES = "SHOW DATABASES";
    public static final String SHOW_TABLES = "SHOW TABLES";

    public static String retrieveDataLake() {
        return CoreUtil.constructPageFromList(DrillConnector.getQueryExecutionResponse(QUERY_RETRIEVE_DATABASES, 1));
    }

    public static String getTables(String databaseName) {
        String out = String.format("USE %s", databaseName);
//        String out = String.format("USE %s; %s", databaseName, SHOW_TABLES);
        DrillConnector.getQueryExecutionResponse(out, 1);

        out = String.format("SHOW TABLES");
        return CoreUtil.constructPageFromList(DrillConnector.getQueryExecutionResponse(out, 1));

    }

    public static void main(String[] args) {
        ClientExecutorEngine.init();

        String out = getTables("mysql.clinical");

        logger.info(out);
    }
}
