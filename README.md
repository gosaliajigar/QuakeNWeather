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


## Summary ##

### Activities ###

QuakeNWeather has total 9 screens (views/activities) ...

  1. **MainActivity (Quakes)** :
      - Displays the latest quakes information as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar
	  - Depending on the magnitude of the quake, the quake information is color-coded
	  - User can click on individual quake entry to get detailed information about the quake
      - From this screen user can go to ...
	      - About Us
		  - Settings
		  - View Quakes
		  - Quake Statistics
		  - Quake Details
		  - Search

  2. **QuakeMapActivity (View Quakes)** :
      - Plots the quakes on google map as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar
	  - Depending on the magnitude of the quake, the quake marker is color-coded
	  - User can click on individual quake marker to get high-level information about the quake
	  - User can click on individual quake info window to get detailed information about the quake
      - From this screen user can go to ...
		  - View Quakes
		  - Settings

  3. **QuakeStatisticsActivity (Quake Statistics)** :
      - Displays the no. of quakes today, this week and this month as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar
      - From this screen user can go to ...
		  - View Quakes
		  - Settings

  4. **QuakeDetailsActivity (Quake Details)** :
      - Displays the detailed information about the quake on View Quake screen
	  - Provides a link to USGS website for more information about the selected quake
      - From this screen user can only go back to View Quakes (MainActivity)

  5. **QuakeSearchActivity (Search)** :
	  - Gives capability to search city (uses Trie for recommendations) with quake
	  - User can also refresh the data by click on refresh button in tool bar
	  - Depending on the magnitude of the quake, the quake marker is color-coded
	  - User can click on individual quake info window to get detailed information about the quake
      - From this screen user can only go back to Latest Earthquakes (MainActivity)
      - From this screen user can go to ...
		  - Earthquake Details
		  - Settings

  6. **SettingsActivity (Settings)** :
      - Displays the following user settings
	      - EARTHQUAKE FILTERS
		      - Default Magnitude Filter
			      - 1.0+
				  - 2.0+
				  - 3.0+
				  - 4.0+
				  - 4.5+
				  - 5.0+
				  - 5.5+
				  - 6.0+
				  - 6.5+
				  - 7.0+
				  - 7.5+
              - Default Date Filter
			      - Last Hour
				  - Today
				  - Last 24 Hr
				  - This Week
		  - DATA CUSTOMIZATION
		      - Distance Units
			      - Miles
				  - Kilometers
      - From this screen user can go to the screen from where settings was selected.
	  - Settings are available on following pages ...
	      - Latest Earthquakes
		  - View Earthquakes
		  - Earthquake Statistics
		  - Search City

  7. **WeatherActivity (Weather)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Earthquakes (MainActivity)

  7. **WeatherDetailsActivity (Weather Details)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Earthquakes (MainActivity)

  7. **AboutUsActivity (About Authors and Application)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Earthquakes (MainActivity)


### Package Explorer ###

Here is how the code is organized in respective packages.


### Database ###

QuakeNWeather Android Application stores the information about when the application is installed on the mobile device and when did the user last viewed quake data in the application.

This information is displayed in About Us screen of the android application.

QuakeNWeather Android Application stores any error information in database so that it can be shared with developer when the user of android application wants to provide feedback.

This information is appended to the feedback email which any user of the android application can send to developer.

User of android application can decide whether he/she would like to share the error information by toggling permissions in Settings.

| Database | ScreenShot |
| ------------- | ------------- |
| Registration | <img src="" width="150"> |
| Error | <img src="" width="150"> |


**NOTE**: Installed date is captured when the app is opened for the first time and not actual installation date.


### Color Codes ###

Depending on the magnitude of the quake, data in MainActivity and google markers in QuakeMapActivity are color coded as follows.

| Sr. No. | Magnitude | MainActivity Text Color | QuakeMapActivity Google Marker |
| ------- | --------- | ----------------------- | ------------------------- |
| 1. | 0.0 – 3.5 | #00FF00 | HUE_GREEN |
| 2. | 3.5 – 5.5 | #FF6347 | HUE_ORANGE |
| 3. | 5.5 – Above | #FF0000 | HUE_RED |


### Refresh Icon ###

All main screens i.e. MainActivity, QuakeMapActivity, QuakeStatisticsActivity and QuakeSearchActivity have refresh icon in the tool bar, which refreshes data on the screen.


### Smart Footer ###

MainActivity, QuakeStatisticsActivity and QuakeSearchActivity have smart footer, which displays the time when data was refreshed and filters set in Settings.


### Weather Icons ###

Weather icons are fetched from Open Weather API documented on https://openweathermap.org/weather-conditions as per weather conditions.


## Screenshots ##

| Screen | ScreenShot |
| ------------- | ------------- |
| Navigation Drawer | <img src="" width="150"> |
| MainActivity | <img src="" width="150"> |
| QuakeMapActivity | <img src="" width="150"> <img src="" width="150"> <img src="" width="150"> |
| QuakeStatisticsActivity | <img src="" width="150"> <img src="" width="150"> |
| QuakeDetailsActivity | <img src="" width="150"> <img src="" width="150"> |
| QuakeSearchActivity | <img src="" width="150"> <img src="" width="150"> |
| WeatherActivity | <img src="" width="150"> <img src="" width="150"> |
| WeatherDetailsActivity | <img src="" width="150"> <img src="" width="150"> |
| SettingsActivity | <img src="" width="150"> <img src="" width="150"> |
| AboutUsActivity | <img src="" width="150"> |


## Credits ##

For Quake related screens, this course project has been influenced from https://play.google.com/store/apps/details?id=com.joshclemm.android.quake

It is not a replica or replacement of the above-mentioned Android Application.


## DISCLAIMER ##

This is just a course project and source of data is from the free APIs at https://earthquake.usgs.gov/ and https://openweathermap.org/.

Please do not use this for reference for quake alert or analysis, instead directly go to https://earthquake.usgs.gov/.