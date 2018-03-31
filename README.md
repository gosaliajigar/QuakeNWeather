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

QuakeNWeather has total 10 screens (views/activities) ...

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
      - From this screen user can only go back to Latest Quakes (MainActivity)
      - From this screen user can go to ...
		  - Quake Details
		  - Settings

  6. **SettingsActivity (Settings)** :
      - Displays the following user settings
	      - QUAKE FILTERS
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
		  - TEMPERATURE FORMAT
		      - Temperature Units
			      - Celsius
			      - Fahrenheit
		  - DEBUGGING DATA
		      - Device Information
		      - Error Information
      - From this screen user can go to the screen from where settings was selected.
	  - Settings are available on following pages ...
	      - Latest Quakes
		  - View Quakes
		  - Quakes Statistics
		  - Search City

  7. **WeatherActivity (Weather)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Quakes (MainActivity)

  7. **WeatherDetailsActivity (Weather Details)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Quakes (MainActivity)

  7. **AboutUsActivity (About Authors and Application)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Quakes (MainActivity)


### Package Explorer ###

Here is how the code is organized in respective packages.

<img src="https://user-images.githubusercontent.com/5839686/37933944-a1595476-3101-11e8-8d6c-701230accbbf.png" width="250" height="500">


### Database ###

QuakeNWeather Android Application stores the information about when the application is installed on the mobile device and when did the user last viewed quake data in the application.

This information is displayed in About Us screen of the android application.

QuakeNWeather Android Application stores any error information in database so that it can be shared with developer when the user of android application wants to provide feedback.

This information is appended to the feedback email which any user of the android application can send to developer.

User of android application can decide whether he/she would like to share the error information by toggling permissions in Settings.

| Database | ScreenShot |
| ------------- | ------------- |
| Registration | <img src="https://user-images.githubusercontent.com/5839686/37933945-a186922e-3101-11e8-8530-1a4c3b478cd8.png" width="600" height="70" > |
| Error | <img src="https://user-images.githubusercontent.com/5839686/37933943-a1203cae-3101-11e8-9786-2290c3ddf5f2.png" width="600" height="90" > |


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
| Navigation Drawer | <img src="https://user-images.githubusercontent.com/5839686/37933920-968f6148-3101-11e8-95eb-40efa866a456.png" width="150"> |
| MainActivity | <img src="https://user-images.githubusercontent.com/5839686/37933932-9fc7a662-3101-11e8-8b40-c0d51e5f9280.png" width="150"> |
| QuakeMapActivity | <img src="https://user-images.githubusercontent.com/5839686/37933935-a00d82fe-3101-11e8-8b78-2f4a2ef413a1.png" width="150"> <img src="" width="150"> |
| QuakeStatisticsActivity | <img src="https://user-images.githubusercontent.com/5839686/37933937-a06417cc-3101-11e8-9258-06ca036b34a2.png" width="150"> |
| QuakeDetailsActivity | <img src="https://user-images.githubusercontent.com/5839686/37933936-a02ed774-3101-11e8-84cf-e4551e4b3954.png" width="150"> <img src="https://user-images.githubusercontent.com/5839686/37933933-9fddf0ac-3101-11e8-8764-7f5cc3b0b683.png" width="150" > |
| QuakeSearchActivity | <img src="https://user-images.githubusercontent.com/5839686/37933938-a0976d66-3101-11e8-8700-8c183b153115.png" width="150"> |
| USGS Details | <img src="https://user-images.githubusercontent.com/5839686/37933934-9ff62fb4-3101-11e8-9a21-9f940fa5137f.png" width="150"> |
| WeatherActivity | <img src="https://user-images.githubusercontent.com/5839686/37933939-a0d8eafc-3101-11e8-8679-50c7ed553aa4.png" width="150"> |
| WeatherDetailsActivity | <img src="https://user-images.githubusercontent.com/5839686/37933941-a0f13814-3101-11e8-8b39-c747fec9e64d.png" width="150"> |
| SettingsActivity | <img src="https://user-images.githubusercontent.com/5839686/37933942-a10a2892-3101-11e8-9050-9a5f6ef7f598.png" width="150"> |
| AboutUsActivity | <img src="https://user-images.githubusercontent.com/5839686/37933928-9f7825ce-3101-11e8-8b17-973988a5dc82.png" width="150"> |


## Credits ##

For Quake related screens, this course project has been influenced from https://play.google.com/store/apps/details?id=com.joshclemm.android.quake

It is not a replica or replacement of the above-mentioned Android Application.


## DISCLAIMER ##

This is just a course project and source of data is from the free APIs at https://earthquake.usgs.gov/ and https://openweathermap.org/.

Please do not use this for reference for quake alert or analysis, instead directly go to https://earthquake.usgs.gov/.
