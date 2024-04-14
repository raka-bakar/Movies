# A simple Movies app that uses MVVM and Clean architecture
the projects has two modules :
1. data module -> it provides the access to data source, either from asset file or local database
2. app -> it contains the app itself where there are all UIs and resources
The project has 3 layers, the UI layer, the Domain layer and the Data layer.
the data layer is inside the data module. While UI and Domain layer are in app module

By using this app, a user can bookmark their favorite movie and search a movie based on the title
the app has 3 screens :
1. The home screen where it contains the list of favorite movies of the user and a list of
Staff choices of movies
2. The detail screen where it contains a detail information of a movie
3. The search screen where it contains a searching functionality based on the title of the movie


techstack : 
1. Jetpack Compose
2. Jetpack Navigation
3. Kotlin Coroutine
4. Kotlin Flow
5. Gson
6. Coil
7. Dagger Hilt and Dagger 2
8. Room database
9. Mockito
10. Timber

Screenshots : 
![alt text](https://github.com/raka-bakar/Movies/blob/main/Screenshot_20240220-055822.png)
![alt text](https://github.com/raka-bakar/Movies/blob/main/Screenshot_20240220-055829.png)
![alt text](https://github.com/raka-bakar/Movies/blob/main/Screenshot_20240220-055840.png)
