# Home Automation and Security

# Description

The project automates the all the electrical appliances in the house. A person can control the switches and all other electrical appliances from anywhere in the world. This also maintains a constant temperature in your house (terms applied). The project also provides a basic security such as reporting fire accidents, gas leakages and some basic security measures in home. This is hosted on firebase, has an Android and Web interfaces available. So that the person can handle the house from anywhere using the interfaces.



## Installation

This has a esp board that is to be configured in the home and connected to the main of the house, there must be a relay attached to the every switch that the user has to access it. The user should also login in the arduino board so that a unique access token generated is stored in the Arduino.

## Working

Whenever a user keeps a request to the Arduino board about a particular switch, the values will be updated to the database. After, for every 5 seconds there will be a request from esp device to the corresponding Arduino board. 

Along with the get operation it also get some attributes such as temperature, humidity, fire status etc ( security options ).

## Contributing

The project is created by 
1. G. Sai Sarath chandra (AM.EN.U4CSE16124) -- ESP configuration.
2. CK. Phani datta (AM.EN.U4CSE16119) -- ESP configuration
2. B. Akhil (AM.EN.U4CSE16117) -- Android app and firebase configuration.
3. P.Rammanoj (AM.EN.U4CSE16149) -- Website using firebase API

## Learning

Sarath-Had learnt about configuration of the esp8266 module and about various standard libraries like IR , DHT library (for determining temperature and humidity).
Phani-Had learnt interfacing of various sensors like smoke, fire and temperature and humidity sensors.
Manoj-learnt accessing firebase API
Akhil-had learnt material design and firebase linking and accessing firebase database.
