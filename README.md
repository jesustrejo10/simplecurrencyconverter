# simplecurrencyconverter
Through this app, I'm going to show you a simple example about how to connect with an Dummy API to make a login and also another API to receive a currency parameter and generate the equivalent values for other currencies.

It's a single activity app.

This app is composed by 4 different modules.
Core: this module contains all clases that could be used by the other modules. it's a simple way to connect all the other modules
App: Contains the main Activity. also manages all the transaction between the screens in the app.
Login: Contains the logic to login into the app, creates de connection with reqres API.
Converter: Contains the fragments and view models than genreates all currency exchange process. creates the connection with the cryptocurrency API and generates the QR code.

Things to take into consideration:
1) The data sent to the reqres API, it's always the same, that's because the API only return the token for some users. 
2) The reqres API, only returns values for BTC and Etherium, other currencies values are hardcoded.
3) There are no unit test developed for this project.
