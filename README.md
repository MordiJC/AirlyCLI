# AirlyCLI
AirlyCLI is console client for Airly. Supported operations are: getting sensor data by its id and getting data of sensor nearest to specified location.

### Important
Your commandline have to support UTF-8 encoding. If you are using windows console change code page to 65001 using `chcp 65001` comand.

## Options
Option | Description
------------ | -------------
-k, --apiKey=\<apiKey\> | Airly REST service API token.
--latitude=\<latitude\> | Nearest sensor searching latitude. (Longitude must be provided as well)
--longitude=\<longitude\> | Nearest sensor searching longitude. (Latitude must be provided as well)
-s, --sensor=\<sensorId\> | Get sensor data by id.
-h, --help | Display help and exit.

Application needs REST API token to be able to get data from Airly. It can be provided as environment varialbe `API_KEY`
or as program option `-k, --apiKey`. If program will not find token in this two places then it will ask for it.