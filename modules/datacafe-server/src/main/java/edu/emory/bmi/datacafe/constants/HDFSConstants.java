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
package edu.emory.bmi.datacafe.constants;

import edu.emory.bmi.datacafe.conf.ConfigReader;

/**
 * Constants for HDFS Integration.
 */
public final class HDFSConstants {

    /**
     * Suppress instantiation.
     */
    private HDFSConstants() {
    }

    /**
     * Hadoop configuration files.
     */
    public static final String CORE_SITE_XML = "core-site.xml";
    public static final String HDFS_SITE_XML = "hdfs-site.xml";

    /**
     * Hive connection uri.
     */
    public static final String HIVE_CONNECTION_URI = ConfigReader.getHivePrefix() + ConfigReader.getHiveServer() + ":" +
            ConfigReader.getHivePort() + ConfigReader.getHivePostfix();
}
