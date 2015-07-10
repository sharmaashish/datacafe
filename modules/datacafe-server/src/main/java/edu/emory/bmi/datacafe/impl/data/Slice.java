/*
 * Title:        S²DN 
 * Description:  Orchestration Middleware for Incremental
 *               Development of Software-Defined Cloud Networks.
 * Licence:      Eclipse Public License - v 1.0 - https://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright (c) 2015, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */
package edu.emory.bmi.datacafe.impl.data;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Class to represent a Slice from the pathology data.
 * BCR Slide UID in pathology.csv is the _id.
 * BCR Patient ID is the foreign key.
 *
 * 19:39:25.619 [main] INFO  edu.emory.bmi.datacafe.impl.main.DatacafeEngine - {
 * "BCR_Patient_UID_From_Pathology" : "2272DFF7-A509-496F-B500-467C1BCE2F79" ,
 * "_id" : "17F9BDA2-9B25-45DA-9A55-EDE15C14195F" , "Sample_Barcode" : "TCGA-WY-A85E-01A" ,
 * "Slide_Barcode" : "TCGA-WY-A85E-01A-01-TS1" ,
 * "Image_File_Name" : "TCGA-WY-A85E-01A-01-TS1.17F9BDA2-9B25-45DA-9A55-EDE15C14195F.svs" ,
 * "Section_Location" : "TOP" , "Tumor_Cells_Percentage" : 50 , "Necrosis_Percentage" : 0 ,
 * "Stromal_Cells_Percentage" : 40 , "Normal_Cells_Percentage" : 10 , "Tumor_Nuclei_Percentage" : 85}
 */
public class Slice {
    @MongoId // auto
    @MongoObjectId
    private String _id;

    @MongoObjectId
    private String BCR_Patient_UID_From_Pathology;

    @MongoObjectId
    private String Sample_Barcode;

    @MongoObjectId
    private String Slide_Barcode;

    @MongoObjectId
    private String Image_File_Name;

    @MongoObjectId
    private String Section_Location;

    @MongoObjectId
    private String Tumor_Cells_Percentage;

    @MongoObjectId
    private String Necrosis_Percentage;

    @MongoObjectId
    private String Stromal_Cells_Percentage;

    @MongoObjectId
    private String Normal_Cells_Percentage;

    @MongoObjectId
    private String Tumor_Nuclei_Percentage;

    public String getKey() {
        return _id;
    }

    public String getBCR_Patient_UID_From_Pathology() {
        return BCR_Patient_UID_From_Pathology;
    }

    public String getSample_Barcode() {
        return Sample_Barcode;
    }

    public String getSlide_Barcode() {
        return Slide_Barcode;
    }

    public String getImage_File_Name() {
        return Image_File_Name;
    }

    public String getSection_Location() {
        return Section_Location;
    }

    public String getTumor_Cells_Percentage() {
        return Tumor_Cells_Percentage;
    }

    public String getNecrosis_Percentage() {
        return Necrosis_Percentage;
    }

    public String getStromal_Cells_Percentage() {
        return Stromal_Cells_Percentage;
    }

    public String getNormal_Cells_Percentage() {
        return Normal_Cells_Percentage;
    }

    public String getTumor_Nuclei_Percentage() {
        return Tumor_Nuclei_Percentage;
    }
}