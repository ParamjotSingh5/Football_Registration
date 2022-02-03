# Football Registration

![GitHub top language](https://img.shields.io/github/languages/top/ParamjotSingh5/Football_Registration)
![Form](https://img.shields.io/badge/Form📝-brightgreen)
![Under Construction](https://img.shields.io/badge/Under🚧construction-red)  

![alt text](https://github.com/ParamjotSingh5/Football_Registration/blob/footballRegistration/Football_Registration_banner.png?raw=true)

Register yourself for an opportunity to play from your dream team. 

## Set up  

* Right now, because we have a working UI of our form, to clone and run this application, you'll need [Git](https://git-scm.com) and your ❤️ web browser installed on your computer. From your command line:

```bash

# Clone this repository
$ git clone https://github.com/ParamjotSingh5/Football_Registration

# Go into the repository
$ cd Football_Registration

# Run the app
$ WebRoot\registration.html

```

* that's it 😎, system set up done.

## How it Works

### 🚀 Page Load

* We are 🔗 binding the `focusout`, `change` and `keypress` events to the `input`, [`checkbox`, `radio`, `select`] and `input[inputmode="numeric"]` form fields respectively.
* Fetching the countries and their corrosponding dail codes, add that data into the select lists.

### 📤 focusout from input

* Whenever we focusout of any input text feild, a method named `isSanitizedValue(val)` will be called, checks value is does not have a character mentioned in this regex `[<>();{}=\\s]+`. It also makes sure that length of string should be less then 10,000 characters.
* On the basis of return bit we apply or remove validatioon css classes to feedback element.

### 👇 keypress in input[inputmode="numeric"]

* Right now, user is not allowed type other then numeric characters for `inputmode="numeric"` input fields . We have mapped the `keypress` event with `numericAllowedOnly()` method.
* By calling `preventDefault()` method on event, we are manipulating the default behaviour of the user-agent.

### 🎰 change on select

* Changing selected option from select list, checks its value if other then 0.
* Special case: checks whether if its name is `countryselect`, if ✔️ `true`, calls a method `fecthStatesSelectList(selectedOptionText)` to bind data with state list, and kind of same procedure is followed when state list is changed for city select list.
* ⚠️ Super special case: While working with form, we may encounter a stage, where a country does not have states in it 😕 (try **Anguilla**). In that situation, we by-pass state select list selection, and directly calls `fetchCitySelectListForStates(selectedOptionText)` to bind data to city sleect list 🧐.

### change on checkbox/ radio

## How to Use

## 👽 Integrated Resource

* We are dependent on <a href="https://countriesnow.space/">Countries now</a> API for data provided.
