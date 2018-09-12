# BookingGO Assessment

### Build
./gradlew build

### Test
./gradlew test

# Part 1
## Console application to print the search results for Dave's Taxis
`./gradlew run --args 'pickup dropoff'`

e.g.
`./gradlew run --args '53.483959,-2.244644 3.4774,2.2309'`

## Console application to filter by number of passengers
`./gradlew run --args 'pickup dropoff #Passengers'`

e.g.
`./gradlew run --args '53.483959,-2.244644 3.4774,2.2309 16'`

# Part 2
To start the server pass no arguments. 
`./gradlew run`

1 route is defined.

* **URL**

    /rides

* **Method:**
  
    `GET`
  
*  **URL Params**

    **Required:**
 
    `pickup=[latitude,longitude]`
    `dropoff=[latitude,longitude]`
    `passengers=[number_of_passengers]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```json
        [
            { 
                "car_type": "CAR",
                "price": 100,
                "supplier": "Dave"
            },
            { 
                "car_type": "CAR2",
                "price": 1001,
                "supplier": "Jeff"
            },
        ]
    ```
 
* **Error Response:**

    * **Code:** 400 BAD REQUEST ERROR <br />
        **Content:** `{ message : "Invalid parameter value." }`

* **Sample Call:**

    ```javascript
        $.ajax({
        url: "/rides?pickup=53.483959,-2.244644&dropoff=3.4774,2.2309&passengers=16",
        dataType: "json",
        type : "GET",
        success : function(r) {
            console.log(r);
        }
        });
    ```
