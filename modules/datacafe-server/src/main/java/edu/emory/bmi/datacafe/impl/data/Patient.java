/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Class to represent a Patient from the clinical data.
 * BCR Patient UID (From Clinical) in clinical.csv is the _id.
 * <p/>
 * 19:13:04.177 [main] INFO  edu.emory.bmi.datacafe.impl.main.DatacafeEngine - { "Race" : "WHITE" , "Gender" : "FEMALE" ,
 * "Supratentorial_Localization" : "[Not Available]" , "Tumor_Site" : "Supratentorial, Frontal Lobe" ,
 * "Laterality" : "Left" , "Histologic_Diagnosis" : "Oligoastrocytoma" , "Age_at_Initial_Diagnosis" : 48 ,
 * "Karnofsky_Score" : "[Not Available]" , "Patient_Barcode" : "TCGA-WY-A85E" , "Cancer_Type" : "LGG" ,
 * "_id" : "2272DFF7-A509-496F-B500-467C1BCE2F79"}
 */
public class Patient {
    private static Logger logger = LogManager.getLogger(Patient.class.getName());

    @MongoId // auto
    @MongoObjectId
    private String _id;

    @MongoObjectId
    private String Race;

    @MongoObjectId
    private String Gender;

    @MongoObjectId
    private String Supratentorial_Localization;

    @MongoObjectId
    private String Tumor_Site;

    @MongoObjectId
    private String Laterality;

    @MongoObjectId
    private String Histologic_Diagnosis;

    @MongoObjectId
    private String Age_at_Initial_Diagnosis;

    @MongoObjectId
    private String Karnofsky_Score;

    @MongoObjectId
    private String Patient_Barcode;

    @MongoObjectId
    private String Cancer_Type;

    public String getKey() {
        return _id;
    }

    public String getRace() {
        return Race;
    }

    public String getGender() {
        return Gender;
    }

    public String getSupratentorial_Localization() {
        return Supratentorial_Localization;
    }

    public String getTumor_Site() {
        return Tumor_Site;
    }

    public String getLaterality() {
        return Laterality;
    }

    public String getHistologic_Diagnosis() {
        return Histologic_Diagnosis;
    }

    public int getAge_at_Initial_Diagnosis() {
        int age = -1;
        if (!Age_at_Initial_Diagnosis.trim().equals("")) {
            try {
                age = Integer.parseInt(Age_at_Initial_Diagnosis);
            } catch (NumberFormatException e) {
                logger.error("Error in parsing the age", e);
            }
        }
        return age;
    }

    public String getKarnofsky_Score() {
        return Karnofsky_Score;
    }

    public String getPatient_Barcode() {
        return Patient_Barcode;
    }

    public String getCancer_Type() {
        return Cancer_Type;
    }
}