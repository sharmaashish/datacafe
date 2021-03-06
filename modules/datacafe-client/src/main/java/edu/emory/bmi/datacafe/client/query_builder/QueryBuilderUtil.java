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
package edu.emory.bmi.datacafe.client.query_builder;

import edu.emory.bmi.datacafe.client.core.HzClient;

/**
 * Utility methods for building the query.
 */
public final class QueryBuilderUtil {
    private String datalakeID;

    public QueryBuilderUtil(String datalakeID) {
        this.datalakeID = datalakeID;
    }

    /**
     * Prints the tables that has the any given attribute.
     *
     * @param attribute the attribute to be probed.
     */
    public void displayTablesWithAttribute(String attribute) {
        HzClient.printValuesFromMultiMap(datalakeID, attribute);
    }

    /**
     * Prints the tables that has the any given attribute.
     *
     * @param attributes the attributes to be probed as an array.
     */
    public void displayTablesWithAttribute(String[] attributes) {
        for (String attribute : attributes) {
            HzClient.printValuesFromMultiMap(datalakeID, attribute);
        }
    }
}
