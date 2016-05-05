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
package edu.emory.bmi.datacafe.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import edu.emory.bmi.datacafe.constants.MongoConstants;
import edu.emory.bmi.datacafe.core.DataCafeUtil;
import edu.emory.bmi.datacafe.core.kernel.AbstractDataSourceConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Connects to the Mongo database
 */
public class MongoConnector extends AbstractDataSourceConnector {
    private static Logger logger = LogManager.getLogger(MongoConnector.class.getName());

    /**
     * Gets the list of IDs. Default id, _id is used.
     *
     * @param iterable the collection iterable
     */
    public List<Object> getAllIDs(FindIterable<Document> iterable) {
        return getAllIDs(iterable, MongoConstants.ID_ATTRIBUTE);
    }

    /**
     * Gets the list of IDs
     *
     * @param iterable    the collection iterable
     * @param idAttribute The attribute key that is used as the ID.
     * @return the list of IDs.
     */
    public List<Object> getAllIDs(FindIterable<Document> iterable, String idAttribute) {
        List<Object> idList = new ArrayList<Object>();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                idList.add(document.get(idAttribute));
            }
        });
        if (logger.isDebugEnabled()) {
            for (Object anIdList : idList) {
                logger.debug(anIdList);
            }
        }
        return idList;
    }


    @Override
    public List getAllIDs(String database, String collection, String idAttribute) {
        MongoCollection mongoCollection = new MongoCollection(database, collection);
        return getAllIDs(mongoCollection.iterateCollection(), idAttribute);
    }


    /**
     * Gets the list of IDs
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param document   the Ids
     */
    public List<Object> getIDs(String database, String collection, Document document) {
        MongoCollection mongoCollection = new MongoCollection(database, collection);
        return getAllIDs(mongoCollection.getCollection(document));
    }


    /**
     * Get only the values for a chosen sub set of attributes. Default MongoID, _id is assumed.
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param preferredAttributes the attributes to be added.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids,
                                           String[] preferredAttributes) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes, null);
    }


    @Override
    public List<String> getAttributesWithHeader(String database, String collection, List ids, String idAttribute,
                                                String[] preferredAttributes) {
        List<String> attributes = new ArrayList<>();
        attributes.add(getChosenAttributeNames(preferredAttributes));

        attributes.addAll(getAttributeValues(database, collection, ids, idAttribute, preferredAttributes,
                new String[]{idAttribute}).stream().map(str -> str).collect(Collectors.toList()));
        return attributes;
    }


    /**
     * Get all the values except the default MongoID attribute.
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param ids        the list of ids.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValuesExceptAutoGenMongoId(String database, String collection, List ids,
                                                               String[] preferredAttributes) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, preferredAttributes,
                new String[]{MongoConstants.ID_ATTRIBUTE}, true);
    }


    /**
     * Get all the values except the default MongoID attribute.
     *
     * @param database   the data base
     * @param collection the collection in the data base
     * @param ids        the list of ids.
     * @return the list of DBCursor.
     */
    public List<String> getAllAttributeValuesExceptAutoGenMongoId(String database, String collection, List ids) {
        return getAttributeValues(database, collection, ids, MongoConstants.ID_ATTRIBUTE, null,
                new String[]{MongoConstants.ID_ATTRIBUTE}, true);
    }


    @Override
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes) {

        return getAttributeValues(database, collection, ids, idAttribute, preferredAttributes, null);
    }


    /**
     * Get only the values for a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param idAttribute         The attribute key that is used as the ID.
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes, String[] removedAttributes) {
        return getAttributeValues(database, collection, ids, idAttribute,
                preferredAttributes, removedAttributes, false);
    }


    /**
     * Get only the values for a chosen sub set of attributes
     *
     * @param database            the data base
     * @param collection          the collection in the data base
     * @param ids                 the list of ids.
     * @param idAttribute         The attribute key that is used as the ID.
     * @param preferredAttributes the attributes to be added.
     * @param removedAttributes   the attributes to be removed.
     * @param addHeader           should the headers be added.
     * @return the list of DBCursor.
     */
    public List<String> getAttributeValues(String database, String collection, List ids, String idAttribute,
                                           String[] preferredAttributes,
                                           String[] removedAttributes, boolean addHeader) {
        MongoCollection mongoCollection = new MongoCollection(database, collection);

        DBCollection collection1 = mongoCollection.getCollection();
        List<String> dbCursors = new ArrayList<>();
        for (Object id : ids) {
            DBCursor results = collection1.find(new BasicDBObject(idAttribute, id),
                    MongoUtil.getDBObjFromAttributes(preferredAttributes, removedAttributes));

            String cursorValue;
            if (addHeader) {
                cursorValue = getCursorValues(results, true);
                addHeader = false;
            } else {
                cursorValue = getCursorValues(results);
            }
            dbCursors.add(cursorValue.trim());
        }
        return dbCursors;
    }


    /**
     * Prints the cursor
     *
     * @param results   the DBCursor
     * @param addHeader Should a header with attributes be added.
     */
    public String getCursorValues(DBCursor results, boolean addHeader) {
        String outValue = "";

        while (results.hasNext()) {

            DBObject resultElement = results.next();
            Map resultElementMap = resultElement.toMap();
            Collection resultValues = resultElementMap.values();

            if (addHeader) {
                if (outValue.trim().equals("")) {
                    Collection resultNames = resultElementMap.keySet();
                    outValue += DataCafeUtil.constructStringFromCollection(resultNames);
                    outValue += "\n";
                }
            }

            String temp = DataCafeUtil.constructStringFromCollection(resultValues);

            outValue += temp;
            if (logger.isDebugEnabled()) {
                logger.debug(outValue);
            }
        }
        return outValue;
    }


    /**
     * Prints the cursor
     *
     * @param results the DBCursor
     */
    public String getCursorValues(DBCursor results) {
        return getCursorValues(results, false);
    }


    @Override
    public void closeConnections() {
        if (logger.isDebugEnabled()) {
            logger.debug("Successfully closed the Mongo connection.");
        }
        MongoCollection.close();
    }
}
