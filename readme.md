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