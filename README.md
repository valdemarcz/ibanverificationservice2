# ibanverificationservice2

2nd service from task, which verifies multiple IBAN numbers from file, validates it and checks for which bank this IBAN belongs.


 ### service endpoints:
 
 - ibanfile/v1/process                 **HTTTP POST request with file parameter required to upload and process text file**
 - ibanfile/v1/files                   **HTTP GET request to receive list of files with URLS to download files**
 - ibanfile/v1/files/{filename:.+}     **HTTP GET request to receive content of {filename:.+} file in body of response**
 

Service is configured to run on 8082 port by default.

to run the service on Your computer use command in CMD or BASH: 

**./gradlew bootRun** from ibanverificationservice2 folder after source code was cloned or downloaded and extracted.

Example requests after service is running:

    
  - **How to make POST request with file**
  
    *** http://localhost:8082/ibanfile/v1/process ***
  
  
    ![example process request screenshot](https://github.com/valdemarcz/ibanverificationservice2/blob/master/RequestWithFile.png?raw=true)
  
  - **How to make GET request with result URLs to files**

    *** http://localhost:8082/ibanfile/v1/files/ ***

    ![example files request screenshot](https://github.com/valdemarcz/ibanverificationservice2/blob/master/ResponseExample.png?raw=true)


***Request can be made with the help of Postman***

***To download any generated file for tests copy received url in browser, file will be downloaded automatically***

### As a result of banfile/v1/process POST request service generates 2 files:
  
  - <originalname>_valid.csv  (this file contains information about IBAN validation in format: IBAN;valid/invalid
  - <originalname>_bank.csv  (this file contains information about IBAN validation in format: IBAN;bank
  
**At the moment service:

  - validates IBAN as per ISO/IEC 7064, IBAN lengts and it's country code.
  - recognizes most bank codes of Lithuanian, Estonian and Latvian banks.
    - which banks are recognized can be checked in banknameslit.txt file.

***IBANs in second file are not validated, only bank name is specified if it exists in IBAN code***
