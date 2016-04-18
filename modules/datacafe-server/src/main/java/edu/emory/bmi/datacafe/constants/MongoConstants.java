/*
 * Copyright (c) 2015 Pradeeban Kathiravelu and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package edu.emory.bmi.datacafe.constants;

/**
 * Constants for Mongo Integration
 */
public class MongoConstants {

    public static final String ID_ATTRIBUTE = "_id";

    /*AWS deployment*/
    public static final String CLIENT_HOST =  DatacafeConstants.IS_REMOTE_MONGO_SERVER ?
            "ec2-54-161-152-13.compute-1.amazonaws.com" : "localhost";
    public static final int CLIENT_PORT = 27017;

    public static final String DATABASE_KEY_ENTRY = "database";
    public static final String COLLECTION_KEY_ENTRY = "collection";
}
