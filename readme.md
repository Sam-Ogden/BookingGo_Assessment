# BookingGO Assessment

### Build
./gradlew build

### Run
./gradlew run --args 'arg1 arg2 arg3'

### Test
./gradlew clean test

# Part 1
## Design
The Lib package has been desiged to allow re-use in different applications by providing methods to customise available suppliers and car types.

#### Supplier Network Class
Maintains a list of available Suppliers and handles the responses from them to return the best offers. 

#### Supplier Class
Handles individual supplier details and sending requests to their APIs. Also maintains the list of available car types.

# Part 2
## Implementation
The API was implemented using spark. 

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