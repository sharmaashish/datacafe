********************
Sample Drill Queries
********************

Here we list a few sample Drill queries used in the preliminary evaluations.

 SELECT * FROM hdfs.root.`patients.csv` WHERE Gender='MALE'


-------------------------------------------------------


 SELECT * FROM hdfs.`/user/pradeeban/patients.csv` WHERE Gender='MALE'


-------------------------------------------------------

 SELECT * FROM hdfs.root.`clinical_clinicalData.csv` WHERE columns[1]='MALE'


-------------------------------------------------------


 SELECT `slices.csv`.sliceID, `slices.csv`.slideBarCode, `patients.csv`.patientID, `patients.csv`.gender, `patients.csv`.laterality
 FROM hdfs.root.`slices.csv`, hdfs.root.`patients.csv`
 WHERE CAST(`patients.csv`.patientID AS VARCHAR) = CAST(`slices.csv`.patientID AS VARCHAR)


-------------------------------------------------------


 SELECT `pathology_pathologyData.csv`.columns[0], `pathology_pathologyData.csv`.columns[2], `clinical_clinicalData.csv`.columns[0], `clinical_clinicalData.csv`.columns[1], `clinical_clinicalData.csv`.columns[2]
 FROM hdfs.root.`pathology_pathologyData.csv`, hdfs.root.`clinical_clinicalData.csv`
 WHERE CAST(`clinical_clinicalData.csv`.columns[0] AS VARCHAR) = CAST(`pathology_pathologyData.csv`.columns[1] AS VARCHAR) AND `clinical_clinicalData.csv`.columns[1]='MALE'




---------------------------------------

PhysioNet MIMIC

SELECT * FROM hdfs.`/user/hdfs/input/PATIENTS_DATA_TABLE.csv` WHERE EXPIRE_FLAG='0'



--------------------------------------------

SELECT SUBJECT_ID, DOB, GENDER
FROM hdfs.`/user/hdfs/input/PATIENTS_DATA_TABLE.csv`
WHERE EXPIRE_FLAG='0'

--------------------------------------------

SELECT SUBJECT_ID, DOB, GENDER
FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv`
WHERE EXPIRE_FLAG='0'

--------------------------------------------

SELECT `PATIENTS_DATA_TABLE.csv`.SUBJECT_ID, `PATIENTS_DATA_TABLE.csv`.DOB, `ADMISSIONS_DATA_TABLE.csv`.DEATHTIME
FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv`, hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv`
WHERE CAST(`PATIENTS_DATA_TABLE.csv`.SUBJECT_ID AS VARCHAR) = CAST(`ADMISSIONS_DATA_TABLE.csv`.SUBJECT_ID AS VARCHAR)

------------------------------------------------------------------------------

Joining Patients and Admissions:

SELECT t1.SUBJECT_ID, t1.DOB, t2.DEATHTIME
FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv` t1
JOIN hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv` t2
ON t1.SUBJECT_ID = t2.SUBJECT_ID

--------------------------------------------

Joining Patients, Admissions, Callout, and ICU Stays:

SELECT t1.SUBJECT_ID, t1.DOB, t2.DEATHTIME, t3.CALLOUT_OUTCOME, t4.LOS, t4.FIRST_WARDID
FROM hdfs.root.`input/PATIENTS_DATA_TABLE.csv` t1, hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv` t2, hdfs.root.`input/CALLOUT_DATA_TABLE.csv` t3, hdfs.root.`input/ICUSTAYS_DATA_TABLE.csv` t4
WHERE t1.SUBJECT_ID = t2.SUBJECT_ID AND t1.SUBJECT_ID = t3.SUBJECT_ID AND t1.SUBJECT_ID = t4.SUBJECT_ID

--------------------------------------------

SELECT DEATHTIME
FROM hdfs.root.`input/ADMISSIONS_DATA_TABLE.csv`


------------------------------------------------------------

 SELECT * FROM hdfs.root.`clinical_clinical.csv` WHERE CAST(columns[1] AS INTEGER)>70
