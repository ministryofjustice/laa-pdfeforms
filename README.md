## PDS eForms Architecture, Design and Development Document


**Overview**
	Objective of this implementation is to create a progressive web application in order to avoid hand filing hard copies of PDS documents and redundant data entry from the hard copies
	to the Visual Files system which holds these records. The new system is expected to allow the users to enter the data once and be rest assured it gets persisted and exported to
	the Visual Files system in the form of a Comma Separated Values files.

**Purpose of this document**
	Purpose of this document is to capture all relevant information that may correspond to this implementation both non- technical/business and technical requirements so that this
	acts a single place of reference for anyone to understand the need, objective of this implementation and to know how it's being perceived at the time of this write up, implementation
	 nuances.

**Architecture**
![architecture](https://github.com/ministryofjustice/laa-pdfeforms/blob/master/PdfeFormsArchitecture.png)

**Front End**
	The html is generated at the server side and there is no javascript dependency. Mustache templates are used to generate
	the html pages using spring mvc. Spring Web and Spring Mustache are the two dependencies included in the gradle file.
	To know how a spring boot mvc application can be developed using mustache templates, please visit the mustache prototype
	[link](https://github.com/ministryofjustice/SpringBoot-Mustach-GovukStyle)

**Back End**
	The back end will developed as a set of MVC and REST endpoints catering to the CRUD operations of the entities that get persisted into the database after applying various business rules.
	Integration with the upstream/downstream systems will be catered too with appropriate integration mechanism as the case may be say for e.g. REST service based or direct DB updates etc.
	The back end is proposed to developed using the following technology stack subject to the agreement of relevant tech team members
- Java 8
- Spring Boot
- Postgres Database
- JPA
- liquibase

**Cross cutting / Common Elements**
	The entire implementation will adhere to the following common elements
- Git
- Docker
- AWS Cloud
- Facilitate Progress Web Application
- Adhere to GDS standards in terms of
- SAML-Single Sign On based Security
- Images, CSS, UI
- Deployment Strategy
- Role based authentication and authorisation as defined by the business requirement

**Packaging and Deployment Strategy**
All the application source code and configurations etc are source controlled in Git repositories.
Two docker images one containing the front end and the other containing the back end application components will be maintained
The Portal application will be the access point where PDS Forms application will be rendered as a service adhering to the SAML security expectations
Two repos will be maintained in Git one for source code and the other for the configuration elements as .yaml files
Continuous Integration flow would be similar to Git -> build and generate package -> Stick to a Docker Container -> Store in ECR -> Deploy in AWS


**Functional Inputs**
	Stakeholders/actors
	Solicitors are the key actors of the system. They use the system/form to capture details of their clients who are the people in need of the LAA services.

**Current System and Practice**
	The existing practice is that the solicitors capture all the required information from their clients in the form of a paper forms and they key them back into a system called Visual
	Files through a remote desktop access. Unique File Number is the key that uniquely identifies a client.

**New System**
The new eForms to be equivalent to the paper forms that are currently being used by the solicitor. The eForms will be progressive in nature meaning the data captured are eventually
stored in the persistent database irrespective of slow or no- network, as soon as network connectivity is restored.
The system should be capable of exporting the data stored in the database in the form of a predefined CVS (Comma Separated Version) template to the desired destination.
This CVS will then be used by a downstream system to import the content and thereby ensure the Visual Files system is able to operate on them as if it was keyed in by the solicitors
in the current system.A system built this way will greatly reduce the redundant data entry and ensure the data gets persisted into the database eventually.

**Pending Work/Information**
- The state machine of the over all eForms system is not yet defined.
- The logic to generate UFN has not been arrived at as yet.
- Search based on UFN or any other data/filter criteria to be defined.
- What data will be allowed to edit, what record will be allowed to delete etc are not yet clearly defined.
- Drop downs, checkboxes etc are dependent on static data which would be in a way hard coded in the html template.
- Validation rules, regular expression requirements etc on html form fields etc are not yet clearly defined. It's better to define a matrix which clearly states what kind of validations
 are required at field level.
- Package the application in the required aws deployment pattern followed within MOJ (dockerized) in coordination with the AWS team.
- Packaging and deploying of the application adhering to the AWS pipeline.


**To run and test the application locally**
- Please make yourself aware of spring boot mustache application using government style spec through this prototype [link](https://github.com/ministryofjustice/SpringBoot-Mustach-GovukStyle)
- install and configure postgress DB (jdbc:postgresql://localhost/pdfeforms is the DB URL configured in application.yml)
- Clone this repository
- gradle bootRun or running it inside IDE as in the case of any spring boot application.
- Use postman and import the 01-PDS.postman_collection.
    * 01-client has the PDS2 related CRUD
    * 02-attendanceNote has the PDS3 related CRUD
    * 03-attendance has the PDS4 related CRUD

- Create client, attendance note and attendance entries in the database using the REST end points.
- http://localhost:8080 should bring up the UI where you can search for an existing client or his/her attendance note or attendance entries.



