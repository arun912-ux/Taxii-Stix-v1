# Taxii-Stix-Web-v1

### Till now 
1. Fetching the data from HailATaxii server from 2017 to 2018 on guest.Abuse.ch Collection. Because there is so much data and taking time.

------------------


### TODO
1. There is so much data to fetch. Taking time. 
2. Understand the Stix Format to extract required data.
3. How to search specific Domain or IP from data.
4. Store data in any of the database (MapDB.memory | Postgres).

- After understanding the Stix format, implementing a Search feature in HTML page, print it's respective taxii data.

--------------------


### To Run
1. Build the Project with Maven Build tool
2. War file is generated in "target/" directory. Run it
   <p><code>java -jar "filename.war"</code> </p>
####
3. END POINT :
   - /json : to fetch json converted data
   - /xml : to fetch raw xml data


--------------------
