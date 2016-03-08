# Company Registry REST API
REST api for creating companies and beneficiaries, as well as adding or removing beneficiary from company. This project uses SparkJava has its JAX-RS framework and PostgreSQL as the preferred RDBMS


Usage
-------------

<br>

##SETUP LOCAL BUILD
```
git clone git@github.com:dokuboyejo/companyregistry.git
cd companyregistry
mvn clean package jetty:run
NB: you should be able to access the resources @http://localhost:8080/companyregistry/companies and @http://localhost:8080/companyregistry/beneficiaries
```

##LIVE ACCESS

**NB** 
In each of the curl request, the following are true<br>
[1] $ represent terminal prompt<br>
[2] {id} represent a placeholder for either company or beneficiary id<br>
[3] {c_id} represent a placeholder for company id<br>
[3] {b_id} represent a placeholder for beneficiary id<br>

##COMPANY Resource

###GET request
- list all previously registered companies<br>
`$ curl -X GET http://host:port/companyregistry/companies`<br>
e.g.<br>
`$ curl -X GET https://companyregistry.herokuapp.com/companies`

- retrieve details of a particular company<br>
`$ curl -X GET http://host:port/companyregistry/companies/{id}`<br>
e.g.<br>
`$ curl -X GET https://companyregistry.herokuapp.com/companies/1`

- list all previously registered beneficiaries for a particular company<br>
`$ curl -X GET http://host:port/companyregistry/companies/{id}/beneficiaries`<br>
e.g.<br>
`$ curl -X GET https://companyregistry.herokuapp.com/companies/1/beneficiaries`


###POST request
- create a new company with new beneficiary<br>
`$ curl -X POST -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/companies`<br>
e.g.<br>
`$ curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"AB Enterprise\",\"address\":\"40 Hollanda Street, Lynnwood\",\"city\":\"Pretoria\",\"country\":\"South Africa\",\"email\":\"ab@enterprise.co.za\",\"phoneNumber\":\"123-22-4567\",\"beneficiaries\":[{\"firstName\":\"Damilola\",\"lastName\":\"Okuboyejo\"},{\"firstName\":\"Gbolahan\",\"lastName\":\"Okuboyejo\"},{\"firstName\":\"Adebola\",\"lastName\":\"Okuboyejo\"}]}" https://companyregistry.herokuapp.com/companies`
- create a new company with existing beneficiary<br>
`$ curl -X POST -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/companies`<br>
e.g.<br>
`$ curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"AB Enterprise\",\"address\":\"40 Hollanda Street, Lynnwood\",\"city\":\"Pretoria\",\"country\":\"South Africa\",\"email\":\"ab@enterprise.co.za\",\"phoneNumber\":\"123-22-4567\",\"beneficiaries\":[{\"id\":2,\"firstName\":\"\",\"lastName\":\"\"},{\"id\":5,\"firstName\":\"\",\"lastName\":\"\"}]}" https://companyregistry.herokuapp.com/companies`

- add new beneficiaries to a particular existing company<br>
`curl -X POST -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/companies/{id}/beneficiaries`<br>
e.g.<br>
`curl -X POST -H "Content-Type: application/json" -d "[{\"firstName\":\"Ade\",\"lastName\":\"Ronald\"},{\"firstName\":\"Stephen\",\"lastName\":\"Welsh\"}]}" https://companyregistry.herokuapp.com/companies/1/beneficiaries`

### PUT request
- update an existing company<br>
`$ curl -X PUT -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/companies`<br>
e.g.<br>
`$ curl -X PUT -H "Content-Type: application/json" -d "{\"id\":1,\"name\":\"AB Enterprise\",\"address\":\"40 Hollanda Street, Lynnwood\",\"city\":\"New York\",\"country\":\"United State\",\"email\":\"ab@enterprise.co.za\",\"phoneNumber\":\"+1-555-22-4567\",\"beneficiaries\":[{\"id\":1,\"firstName\":\"Damilola\",\"lastName\":\"Okuboyejo\"},{\"id\":2,\"firstName\":\"Gbolahan\",\"lastName\":\"Okuboyejo\"},{\"id\":3,\"firstName\":\"Adebola\",\"lastName\":\"Okuboyejo\"}]}" https://companyregistry.herokuapp.com/companies`

- add existing beneficiaries to a particular company<br>
`$ curl -X PUT -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/companies/{id}/beneficiaries`<br>
e.g.<br>
`$ curl -X PUT -H "Content-Type: application/json" -d "[{\"id\":\"5\"},{\"id\":\"8\"}]" https://companyregistry.herokuapp.com/companies/1/beneficiaries`

### DELETE request
- delete a particular company<br>
`$ curl -X DELETE http://host:port/companyregistry/companies/{id}`<br>
e.g.<br>
`$ curl -X DELETE https://companyregistry.herokuapp.com/companies/2`

- remove a beneficiary from a particular company<br>
`$ curl -X DELETE http://host:port/companyregistry/companies/{c_id}/beneficiaries/{b_id}`<br>
e.g.<br>
`$ curl -X DELETE https://companyregistry.herokuapp.com/companies/1/beneficiaries/3`
*NB*: beneficiary can only be removed from a company if such company would have a minimum of one beneficiary should the operation be successful

<br>

##BENEFICIARY Resource

###GET request
- list all previously registered beneficiaries<br>
`curl -X GET http://host:port/companyregistry/beneficiaries`<br>
e.g.<br>
`curl -X GET https://companyregistry.herokuapp.com/beneficiaries`

- retrieve details of a particular beneficiary<br>
`$ curl -X GET http://host:port/companyregistry/beneficiaries/{id}`<br>
e.g.<br>
`$ curl -X GET https://companyregistry.herokuapp.com/beneficiaries/2`


###POST request
- create a new beneficiary<br>
`$ curl -X POST -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/beneficiaries`<br>
e.g.<br>
`$ curl -X POST -H "Content-Type: application/json" -d "{\"firstName\":\"Phillip\",\"lastName\":\"Nel\"}" https://companyregistry.herokuapp.com/beneficiaries`

### PUT request
- update an existing beneficiary<br>
`$ curl -X POST -H "Content-Type: application/json" -d "data" http://host:port/companyregistry/beneficiaries`<br>
e.g.<br>
`$ curl -X POST -H "Content-Type: application/json" -d "{\"id\":\"2\",\"firstName\":\"Gbolahan\",\"lastName\":\"George\"}" https://companyregistry.herokuapp.com/beneficiaries`

### DELETE request
- delete a particular beneficiary<br>
`$ curl -X DELETE http://host:port/companyregistry/beneficiaries/{id}`<br>
e.g.<br>
`$ curl -X DELETE https://companyregistry.herokuapp.com/beneficiaries/1`<br>
*NB*: beneficiary can only be deleted if not enlisted in any company

<br><br>

Strategies for Authentication
-----------------------------
- Add filter to each (or required) HTTP request to ensure clients are authenticated before accessing protected resources
- Use established authentication framework (such as spark-pac4j) to do heavy lifting
- Apply the following authentication strategy based on the access need<br>
	[1] HTTP (Form and Basic Auth)<br>
	    * Internal non-LDAP users<br>
	    * Web or Network dependency<br>
	[2] LDAP<br>
	    * Internal LDAP users<br>
	    * Single-Sign-On (SSO)<br>
	[3] OAuth2 / OpenID / Open ID Connect<br>
	    * Internal LDAP and non-LDAP users<br>
	    * External users<br>
	[4] Database (RDBMS / NoSQL)<br>
	    * Internal LDAP and non-LDAP users<br>
	    * External users<br>
	    * Desktop / standalone / ubiquitous  users<br>
	    * Intranet<br>
    
    
<br>

Making the REST service redundant
---------------------------------
Couple of strategies could be implemented for this, depending on the nature of organization business and accepted trade-offs

- The service application can be horizontally scaled to ensure request been served from multiple nodes.
- A load balancer can be designed to act as API gateway for handling request of the horizontally scaled service applications. Request can be configured to be served based on pre-computed criteria, round robin or simply randomly.
- The responsibility of the load balancer might include: Metering, Throttling (global & per-service method), Caching, Monitoring, Load balancing, Discovery, Versioning, Tracing (request correlation, logs), Service composition, Routing, Authentication
- The load balancer can equally be further scaled to increase reliability of the service, thus yielding near zero downtime
- To ensure each node server to be independent of one another, a shared-nothing architecture can be implemented. This would essentially help prevent single point of failure and contention across the system.
