Platform-Commons-Task
Overview

This Android application is designed to fetch and display a paginated list of users from an API, allow users to add new users with offline support, and navigate to a movie listing screen. The movie list also supports pagination and navigation to a detailed movie screen.

--This project is built using Kotlin and follows the MVVM architecture. It leverages Dagger for dependency injection and Room Database for local storage. When the network is unavailable, user data is stored in RoomDB. A BroadcastReceiver detects when internet connectivity is restored, triggering WorkManager to filter unsynced data from RoomDB and submit it to the API. Once the data is successfully posted, the API response ID is updated in the local database. For pagination, the project uses Paging 3, ensuring efficient data loading from the API.--
