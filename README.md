# Taxii-Stix-Web-v1

### Till now 
1. Fetching the data from HailATaxii server. Logging the number of IP, Domain, URL count received from server.
2. Organizing the data and storing them in MapDB.
3. From the webpage, we can separately request for URL, DOMAIN, IP. These are processed from saved MapDB.
4. To search for a specific IP, DOMAIN, URL. Use the search box.

------------------


[comment]: <> (### TODO)

[comment]: <> (- [x] " ")

[comment]: <> (--------------------)


### To Run
1. Build the Project with Maven Build tool.
2. War file is generated in <code>target/</code> directory. Run it
   <p><code>java -jar "filename.war"</code> </p>
3. Goto <code>localhost:8080/</code> in a browser.
4. End Points :
   - /ips : to fetch ips
   - /domains : to fetch domains
   - /urls : to fetch urls


--------------------
##Homepage

- IP : to fetch IPs
- DOMAIN : to fetch Domains
- URL : to fetch URL
- Search box : to search specific content

<img src="homepage.png" alt="homepage.png" />

