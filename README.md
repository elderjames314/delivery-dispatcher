# delivery-dispatcher
App to manage delivery dispatchers
* The implementation process:

* register company staff:
    - name,
    - email,
    - password

* login:
  - email
  - password


* add: location
    - origin
    - destination
    - total_distance


* delete: location obj
* update location obj


* take delivery:
    - at least three locations must exist before delivery can take place
    - select location
    - total_cost_of_delivery == $1 * total_distance (of origin and destination)

## Dependencies
* Springboot 3.04 : latest
* Spring 6
* Spring security
* Spring web
* Spring Data JPA
* Lombok
* Spring Postgres Driver
* Java 17
* Maven

## Controller
* AuthenticationController
* LocationController
* DeliveryController

## Services
* AuthenticationService
* LocationService
* DeliveryService


## Test
* AuthenticationControllerIntegrationTest
* LocationControllerIntegrationTest
* DeliveryControllerIntegrationTest

## Postman collection for the APIS
  download and upload it to your postman collection
* /klash-delivery-dispatcher.postman_collection.json

## Apis documentation:
view it here: https://documenter.getpostman.com/view/1702774/2s93JtQj7D