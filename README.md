
# ATM Simulator Project
A Java-based application that simulates the basic behaviour of an ATM, in order to demonstrate a real-world application
of the Dynamic Programming (DP) algorithm

## Disclaimer
This is an old project that has recently undergone some minor updates. Consequently, some of the technologies used are
old and outdated.

## Technology Stack
* Java 11
* Spring Boot 2
* Angular JS 1
* Socks JS

## Build and Run the App
### Dockerised Version
#### Prerequisites
* Docker
#### Commands
The app can be built using Docker (no Java installation required). Execution time will, however be significantly 
slower than would be the case when using local installations.
* `make build-docker` (or run `./build_scripts/build.sh` from your root directory, if `Make` is not installed on your machine)

Run the app by using the following command
* `make run` (or run `docker build -t atm-cash-dispenser . && docker run -it -p9081:9081 atm-cash-dispenser` from your command line)
* to specify a different port, change `-p9081:9081` to the desired port number
### Local Java Version
#### Prerequisites
* Java 11
* Maven 3.x
#### Commands
Build the app by using your local Java 11, and Maven installations as follows:
* `mvn clean package -U`

Run the app by executing the following command:
* `mvn spring-boot:run`

## Accessing the User Interfaces
* A UI is provided to display the ATM simulation in real-time. It can be accessed at http://localhost:{port}.
The default port is currently set to 9081 (Eg. http://localhost:9081 )
* Swagger-UI can be accessed at the following  http://localhost:{port}/swagger-ui.html, and provides means to test the API

## Algorithms
The following algorithms are used to find the best combination of notes to service a withdrawal request:
* Dynamic programming - Refer to the following documentation for a complete guide on the implementation of this algorithm
  : 
* Exhaustive search (brute-force) - The Brute- Force algorithm will overwhelmingly be less performant than the 
  Dynamic Programming version. The Brute-Force version, however, has the advantage of more accurately prioritising 
  certain denominations, as it exhaustively finds every possible combination that satisfies the withdrawal request. 
  This means that it has the capability to select the combination that would have the least disruptive impact on the
  remaining cash in the ATM. For example, if the cash dispenser is running low on $10 notes, we want to dispense as 
  few of these as possible, so that we can continue to service the highest amount of withdrawal requests. 
  The Dynamic Programming algorithm can also prioritise certain denominations based on the way the input is sorted, 
  but is not as effective as the Brute Force algorithm in this regard.
  
## Design Patterns
* Strategy Pattern - 
  * Cash Dispensing Algorithms - The Strategy pattern is used to switch between the two different withdrawal algorithms:
    * When there is ample cash still available in the ATM, the main priority is performance, as we want to service the
    user's request within a reasonable time-frame. Thus, the Dynamic Programming algorithm is used
    * When the ATM starts running low on cash, performance becomes less of a priority, and all potential combinations can
    be evaluated within a reasonable time frame. Thus, when the total amount of notes left in the ATM reaches 15 
    (this amount is configurable - refer to the `bruteforce.cutoff` property in the `src/main/resources/application.yml` 
    file), the cash dispenser will switch to the brute-force algorithm to find the best possible combination of notes
    to return
  * Sorting algorithms - The strategy pattern is used to programmatically select the appropriate algorithm to sort the
  available-cash data model so that certain denominations can be prioritised.
* Factory Method Pattern - The factory method pattern is used to create instances of the cash dispenser, and sorting
  classes
* Observer Pattern - The Observer pattern is used, in combination with websockets, to notify all front-end
  instances of changes to the `available-cash` data model in the back-end API, in real-time.
      

## Features
* Initial cash available in ATM at application startup can be customised through minor configuration changes 
  (refer to `src/main/resources/application.yml`) 
* Cash preservation - Denominations with the largest amount of available notes will be prioritised, in order to maintain
  an even distribution of notes in the ATM
* Continuous monitoring and display of cash available in the ATM - Websockets are utilised to notify all front-end 
  instances of any changes to the remaining cash data model in the back-end API. This can be tested by executing a 
  withdrawal request via the Swagger interface (or a standard cUrl request, etc), and observing the impact on the 
  `Cash In ATM` section of the front-end
* * Gives the user the ability to enter a withdrawal amount, and issue 
    


## Known limitations and Future Improvements
* Prioritisation of certain denominations are calculated before individual withdrawal requests, as opposed to
  continuously monitoring the available amounts while processing requests. This has the potential side effect of 
  certain denomination getting depleted faster than others when large withdrawal amounts have been requested, as it would
  have been prioritised prior to the request being served. Balance is generally restored in subsequent requests,however, 
  as the lowest amount will be de-prioritised. 
* Currently, all denominations are treated equally when prioritising the distribution of notes to serve withdrawal requests.
  A more efficient approach would be to place a higher priority on preserving lower denominations, as they have the potential to
  satisfy a larger amount of withdrawal requests than higher denominations.
* When the requested withdrawal amount is not available in the ATM, and there is no smaller amount availabe 
  (for example: the ATM only has $200 and $100 bills left, and a withdrawal of $50 is requested), the user will 
  incorrectly be prompted as to whether they want to withdraw $0. This can easily be fixed by adding minor logic to 
  suggest the lowest available amount, when the requested withdrawal amount is less than the smallest available amount.
* Currently, during the DP matrix construction phase, construction continues even after a suitable combination for the
  requested amount has been found. A circuit-breaker can easily be added to halt construction as soon as a suitable 
  combination has been found

## API documentation
Swagger API documentation can be found at the following location: http://localhost:9081/swagger-ui.html

## Unit tests
### Back-end
Comprehensive Unit tests have been implemented for most of the functional parts of the back-end. Coverage reports can 
be found here: `target/jacoco-coverage-report`

Execute the tests by running `mvn clean test`

### Front-end
Front-end unit testing has been wired up via PhantomJS and Jasmine. Coverage is currently at about 0%, however.
* a test report can be found here: `target/jsamine/TEST-jasmine.xml`
* a coverage report can be found here: `target/angular-reports/total-report`

Execute front-end tests by running `mvn clean test`
