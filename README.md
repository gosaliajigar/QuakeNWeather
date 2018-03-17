## About ##

As part of SWE 690 Capstone Project course, QuakeNWeather Android Application was developed for final term project.

## Introduction ##

QuakeNWeather Android Application retrieves quake information from the free APIs available at https://earthquake.usgs.gov/ and displays it in user-readable formats like list view, map view and statistics view.

QuakeNWeather Android Application retrieves weather information from the free APIs available at https://openweathermap.org/api and displays it in user-readable format.

## How to Use? ##
  1. Clone the repository and import the project in Android Studio
  2. Get your google api key by following steps on https://developers.google.com/maps/documentation/android-api/signup
  		* Save your YOUR-GOOGLE-API-KEY in AndroidManifest.xml under <br>
		    	**meta-data <br>
				android:name="com.google.android.geo.API_KEY" <br>
				android:value="YOUR-GOOGLE-API-KEY"** <br>
  		* Save your YOUR_API_KEY in gradle.properties as GOOGLE_MAPS_API_KEY=YOUR_API_KEY
  3. Get your Open Weather API Key by signing up on https://openweathermap.org/api
  		* Save your YOUR-WEATHER-API-KEY in AndroidManifest.xml under <br>
		    	**meta-data <br>
				android:name="org.openweathermap.APP_ID" <br>
				android:value="YOUR-WEATHER-API-KEY"** <br>






TBD : More to come ....
