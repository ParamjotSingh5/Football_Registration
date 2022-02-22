# Football Registration

![GitHub top language](https://img.shields.io/github/languages/top/ParamjotSingh5/Football_Registration)
![Form](https://img.shields.io/badge/Form📝-brightgreen)
![Under Construction](https://img.shields.io/badge/Under🚧construction-red)  

![alt text](https://github.com/ParamjotSingh5/Football_Registration/blob/footballRegistration/Football_Registration_banner.png?raw=true)

Register yourself for an opportunity to play from your dream team.

## Prerequisites

* [Git](https://git-scm.com) (to clone the project on the local system, otherwise you can download the project directly.)
* [Maven](https://maven.apache.org/download.cgi) (to build and run web app through CLI.) [Guid for Maven installation](https://mkyong.com/maven/how-to-install-maven-in-windows/)
* [Microsoft SQL Server](https://www.microsoft.com/en-in/sql-server/sql-server-downloads)

## Set up  

```bash

# Clone this repository
$ git clone https://github.com/ParamjotSingh5/Football_Registration

# Go into the repository
$ cd Football_Registration\Register

# Clean and pack the app
$ mvn clean package

# Go to the build batch file
$ cd target\bin

# Run the web app
$ webapp.bat

```

* If everything goes well, URL localhost:8080/ will be occupied with a runing webapp.

## How it Works

### 🚀 Page Load

* We are 🔗 binding the `focusout`, `click`, `change` and `keypress` events to the `input`, [`checkbox`, `radio`], `select` and `input[inputmode="numeric"]` form fields respectively.
* Fetching the countries and their corresponding dial codes from static resource, add that data into the select lists.

### 📤 focusout from input

* Whenever we focusout of any input text field, a method named `isSanitizedValue(val)` will be called, checks if value does not have a character mentioned in this regex `[<>();{}=\\s]+`. In this case we are further filtering the form felids on the basis of the `inputMode` attribute. `inputMode == "text"` case will lead to calling of `isAlphabatesOnly()` method.
* On the basis of return bit we apply or remove validation css classes to feedback element.

### 👇 keypress in input[inputmode="numeric"]

* Right now, user is not allowed type other then numeric characters for `inputmode="numeric"` input fields . We have mapped the `keypress` event with `numericAllowedOnly()` method.
* By calling `preventDefault()` method on event, we are manipulating the default behavior of the user-agent.

### 🎰 change on select

* Changing selected option from select list, checks its value if other then 0.
* Special case: checks whether if its name is `countryselect`, if ✔️ `true`, calls a method `fecthStatesSelectList(selectedOptionText)` to bind data with state list, and kind of same procedure is followed when state list is changed for city select list.

### 📌 click on checkbox/ radio

* To make sure that at least one of checkbox/ radio is checked from a group, we make call to `isAtleastCheckInputChecked(selector)` method. On the basis of the returned bit, we add a class to on each element of the group.

### Validating form

* All of the above mentioned form fields are validated according to their characteristics and their validation responses are recorded into a global variable. This global variable will passed on to `RespondValdationFailure()` to represent validation failures on to their particular fields.
* Similar validation processes happens on backend for "New User" and "Update User" request. If there is a validation failure on backend, a JSON object is sent back as response which we can send to `RespondValdationFailure()` to raise validation errors.
* If there is no any validation failures within the form, submit/update button will be enabled for submitting the form.

## Backend

* We have used Maven as dependency manager and build Tool, JAVA, Embedded Apache Tomcat server.
* Project Division:
  * **/Domain**  contains the domain object repersenting the database tables. They also comprises the logic for database operations.
  * **/DTO** (Data transfer objects) holds the object responsible for mapping data received to or send by server.
  * **/launch** `Main` Class entry point to our web application.
  * **/servlet** holds endpoint handlers.
  * **/Utilities** holds classes for extra logic that application needs to work efficiently like `Extensions.java` contains a method `hasColumn()` as follows.

  ![Extension.HasColumn() method definition image](https://github.com/ParamjotSingh5/Football_Registration/blob/footballRegistration/Extension.hasColumn.png?raw=true)

## API endpoints

* *[Postman Collection link](https://www.getpostman.com/collections/ebd019ff546b855ca65b)*
* Response of every request comprises these generic parameters.

`"status"` will represents while processing the request, if an exception has occurred or not.

`"success"` indicates the request specific result.

### 1. Check if username exists "/username-taken?username=param"

**Method Type : `GET`**

#### Sample Responses

* Provided username exists

```json
    {
        "status": true, 
        "success": true,
        "message": "username already exists."
    }
```

* Provided username does not exists

```json
    {
        "status": true,
        "success": false,
        "message": "No user found with provided username"
    }
```

* Provided a bad value such that `/user?username=Navjot or 1=1 --`

```json
    {
        "status": false,
        "success": false,
        "message": "Invalid Request"
    }
```

`"success"` indicates if provided username already exists on to the server.

### 2. Fetch user data "/user?username=param"

**Method Type : `GET`**

#### Sample Responses

* Provided username is valid

```json
    {
        "status": true,
        "success": true,
        "message": "username already exists.",
        "data": {
            "firstname": "NavjotShiv",
            "lastname": "singhNavjot",
            "countrydailcodeselect": "+91",
            "phonenumber": "9656256581",
            "email": "singhNavjot@example.com",
            "agegroup": 3,
            "desiredteamradios": 1,
            "desiredpositionchecks": [
                1,
                2
            ],
            "address": "",
            "pincode": "987654",
            "countryselect": "IN",
            "stateselect": "PB",
            "cityselect": "Nabha"
        }
    }
```

* Provided username does not exists

```json
    {
        "status": true,
        "success": false,
        "message": "No user found with provided username"
    }
```

`"success"` indicates if a user exists with provided username.

### 3. Register new user "/register"

**Method Type : `POST`**

#### Sample Payload

```json
    { 
        "username":"Yadwinder",
        "firstname": "Yadwinder",
        "lastname":"singh",
        "countrydailcodeselect":"+91",
        "phonenumber":"9876541320",
        "email":"yadwinder@example.com",
        "agegroup":"1",
        "desiredteamradios":"1",
        "desiredpositionchecks":["1","2","3"],
        "address":"house number 245",
        "pincode":"112234",
        "countryselect":"IN",
        "stateselect":"03",
        "cityselect":"Drammen"
    }
```

#### Sample Responses

* Missing firstname field inside request payload.

```json
    {
        "status": true,
        "success": false,
        "data": [
            {
                "feildname": "firstname",
                "isValid": false,
                "feedbackMesssage": "Field required."
            }
        ]
    }
```

* Provided an invalid value for cityselect field.

```json
    {
        "status": false,
        "success": false,
        "message": "An error occurred while registering a new user.",
        "data": []
    }
```

* everything seems good.

```json
    {
        "status": true,
        "success": true,
        "message": "User registered successfully.",
        "data": []
    }
```

`"success"` indicates if a new user is registered successfully ot not.

### 4. Update user "/register"

**Method Type : `PATCH`**

By default `HttpServlet/1.1` was not having any PATCH method that we can override to handle a HTTP Patch request. So, we did overridden the `service` method of `HttpServlet` class as follows.

![Override HttpServlet service method image](https://github.com/ParamjotSingh5/Football_Registration/blob/footballRegistration/override_service.png?raw=true)

*Supports request payload to include only changed fields.*

#### Sample Payload

```json
    { 
        "username":"Navjot",
        "lastname":"singhNavjot",
        "countrydailcodeselect":"+91",
        "email":"singhNavjot@example.com",
        "desiredpositionchecks": [1, 2] 
    }
```

#### Sample Responses

* Missing username field inside request payload.

```json
    {
        "status": true,
        "success": false,
        "data": [
            {
                "feildname": "username",
                "isValid": false,
                "feedbackMesssage": "Field required."
            }
        ]
    }
```

* Provided an invalid value for username i.e. `"username":"Navjo2a"`.

```json
    {
        "status": true,
        "success": false,
        "data": [
            {
                "feildname": "username",
                "isValid": false,
                "feedbackMesssage": "Only alphabetic characters are allowed."
            }
        ]
    }
```

* Provide invalid values for multiple fields i.e. `"username":"Navjo2a", "lastname":"singhNavj1ot"`

```json
    {
        "status": true,
        "success": false,
        "data": [
            {
                "feildname": "username",
                "isValid": false,
                "feedbackMesssage": "Only alphabetic characters are allowed."
            },
            {
                "feildname": "lastname",
                "isValid": false,
                "feedbackMesssage": "Only alphabetic characters are allowed."
            }
        ]
    }
```

* everything seems good.

```json
    {
        "status": true,
        "success": true,
        "message": "User data updated successfully",
        "data": []
    }
```

`"success"` indicates if a user updated successfully.
