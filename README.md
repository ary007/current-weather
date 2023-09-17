
# Current Weather API
The application provides current weather of a city.

## There are two api endpoints secured with API key
#### 1. [GET] /api/weather/current\?cityName={CITY_NAME}&countryCode={COUNTRY_CODE} 

Returns the current weather in the description field in JSON format. 
 
For the supported list of city name and country code you can refer [here](http://bulk.openweathermap.org/sample/)

Sample:
```
Request:

http://localhost:8080/api/weather/current?cityName=Sydney&countryCode=AU

Response:  
{
    "description": "light rain"
}
```    

#### 2. [GET] /api/weather/records

Returns the list of current weather requested in JSON format

Sample:
```
Request:

http://localhost:8080/api/weather/records?X-API-KEY=weather

Response:  
[
    {
        "id": 1,
        "cityName": "Perth",
        "countryCode": "AU",
        "description": "broken clouds",
        "createdDateTime": "2023-09-14T13:19:21.178509"
    },
    {
        "id": 2,
        "cityName": "Sydney",
        "countryCode": "AU",
        "description": "light rain",
        "createdDateTime": "2023-09-14T13:19:28.110941"
    },
    {
        "id": 3,
        "cityName": "Melbourne",
        "countryCode": "AU",
        "description": "clear sky",
        "createdDateTime": "2023-09-14T13:19:33.972013"
    }
]
``` 
# Prerequisites
1. JVM >=18
2. Gradle >=7.5.1

# How to run this
1. git clone git@github.com:ary007/current-weather.git
2. cd current-weather 
3. gradle bootRun 

It will start running in port 8080

# How to test
gradle test

# How to hit the API

## Test the app is up and running
  ``` 
  curl localhost:8080/api/isAlive   
  ```

## Get current weather 
This requires API key which needs to be provided in the header under header key `X-API-KEY` and value `weather`
  ``` 
  curl -v --header "X-API-KEY: weather" http://localhost:8080/api/weather/current\?cityName=Sydney\&countryCode=AU
  ```
## Get weather records
This also requires API key which needs to provided as above.
  ``` 
   curl -v --header "X-API-KEY: weather" http://localhost:8080/api/weather/records    ```
## Secured api with wrong api key
 ```
 ### Note:
The API key has a rate limiter of only 5 request in an hour

## Implementation
1. application is build using Spring-boot-3.1.3
2. The API calls OpenWeatherMap to fetch the weather description 
3. An authentication filter is implemented using Spring-Security-6.1.3 for validating API key 
4. Bucket4j is used to limit the rate of requests 
5. The records gets stored in H2 database

## TODO
1. Rate limit for 5 API keys
2. Increase unit test coverage