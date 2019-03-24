#Welcome to the IDnow example project

##Setup

You need:
* Java 8 installed
* A checkout of this project (obviously)

To run the web frontend:
* go this folder in a shell
* Linux / Mac: run "./activator run"
* Windows: run "./activator.bat run"
* After the startup (on the first run, this can take some minutes) you can access the web frontend at http://localhost:9000

To run the unit tests:
* go this folder in a shell
* Linux / Mac: run "./activator test"
* Windows: run "./activator.bat test"

##Your tasks

You are provided with the JSON representation of 2 objects:
- An identification which is a request of a user to be identified by IDnow
- A company, which is a customer of IDnow

The identification has the following properties:
- Id: The unique ID of the identification
- Name: Name of the user
- Time: The time when this identification request was started by the user (Unix format)
- Waiting_time: The current waiting time of the identification in seconds (since the user started)
- Companyid: The ID of the company to which this identification belongs

The company has the following properties:
- Id: The unique ID of the company
- Name: The name of the company
- SLA_time: The SLA (Service Level Agreement) time of this company in seconds
- SLA_percentage: The SLA (Service Level Agreement) percentage of this company as float
- Current_SLA_percentage: The current SLA percentage of this company in this month (e.g. 0.95 would mean that IDnow achieved an SLA_percentage of 95% for this company for this month. If this is lower than SLA_percentage at the end of the month, we would not have reached to agreed SLA)

The SLA is calculated as follows:
- <SLA_percentage>% of the identifications should be accepted in less than <SLA_time> seconds
- Example1: SLA_time=60, SLA_percentage=0.9: 90% of the idents should be accepted in less than 60 seconds
- Example2: SLA_time=120, SLA_percentage=0.8: 80% of the idents should be accepted in less than 120 seconds

Your task:
- Implement a REST interface with 2 routes
	- POST /api/v1/startIdentification: Here you can POST a identification object which is then added to the current list of open identifications
	- GET /api/v1/pendingIdentifications: Here you can get a list of identifications. The identifications should be ordered in the optimal order regarding the SLA of the company, the waiting time of the ident and the current SLA percentage of that company. More urgent idents come first.
- Implement a unit test which tests a list of identifications to check wether the correct order is returned

Example 1:
- One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
- Identification 1: Waiting_time=30
- Identification 2: Waiting_time=45
Expected order: Identification 2, Identification 1 (since Ident 2 has waited longer)

Example 2:
- Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
- Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
- Identification 1 belonging to Company1: Waiting_time=30
- Identification 2 belonging to Company2: Waiting_time=30
Expected order: Identification 2, Identification 1 (since Company 2 already has a lower current SLA percentage in this month, so its identifications have higher prio)

Example 3:
- Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
- Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.95
- Identification 1 belonging to Company1: Waiting_time=30
- Identification 2 belonging to Company2: Waiting_time=30
Expected order: Identification 1, Identification 2 (since company 1 has a lower, more urgent SLA)

Example 4:
- Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
- Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.80
- Identification 1 belonging to Company1: Waiting_time=45
- Identification 2 belonging to Company2: Waiting_time=30
What is the expected order here?

##What do we check?

How do we check the results:
- First we check if the tasks have been finished and if they work correctly
- We expect good code quality
- We expect the code to be easily readable and understandable
- Good unit test coverage
- We check if the order of the identifications is correct regarding the SLAs of the companies

Bonus task:
- Create a web interface to add new identifications to the REST interface

##Where to start

- Check the test/RestController.Test.java for an example test
- Check the conf/routes for the REST routes
- Check the app/controllers/RestController.java for the stubs for the REST interface

##How to turn in the assignment

- Clone this Github repository
- Check in your code
- Create a pull request against this Github repository