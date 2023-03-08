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
* Spring security
* Spring web
* Spring Data JPA
* Lombok
* Spring Postgres Driver
* Java 17