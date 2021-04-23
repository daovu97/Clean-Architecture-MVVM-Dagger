# EmployeeManager

## The application use Clean Architecture

Clean Architecture combines a group of practices that produce systems with the following characteristics:

- Testable
- UI-independent (the UI can easily be changed without changing the system)
- Independent of databases, frameworks, external agencies, and libraries

In app, the layers into three modules:

- ## app layer 
    will include normal Activities , Fragments and ViewModels which will only handle rendering views and will follow **MVVM pattern**.

- ## domain layer 
    With the Use Cases that  will include all business logic and interact between Data and Presentation layer by means of interface and     interactors. The objective is to make the domain layer independent of anything, so the business logic can be tested without 
    any dependency to external components.

- ##  data layer 
    With the Repositories, which includes Databases and Rest clients



## The software architecture scheme
- The UI can only communicate with the ViewModel
- The ViewModel can only communicate with the UseCase
- The UseCase can only communicate with the Repository
- And the Repository can only communicate with the DataSource

## [Android architecture components](https://developer.android.com/topic/libraries/architecture) 
- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) ‒ Provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) ‒ Designed to store and manage UI-related data in a life cycle-conscious way
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) ‒ An observable data holder class that, unlike a regular observable, is life cycle-aware, meaning it respects the lifecycle of other app components such as activities, fragments, and services
- [Navigation component](https://developer.android.com/guide/navigation) will manage for the [bottom navigation](https://material.io/components/bottom-navigation/)

## [Android Studio Coding Style](https://github.com/raywenderlich/kotlin-style-guide) 
- It is possible to get Android Studio to adhere to these style guidelines, via a rather complex sequence of menus. 
To make it easier, we've provided a coding style that can be imported into Android Studio.
- The file can be found - path: .**./stylecoding/rwstyle.xml**
- To install the file, open Android Studio Settings and go to **Editor > Code Style > Kotlin**, then click the gear menu and choose **Import Scheme....**

## Used libraries 
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Dagger2](https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2)
- [Retrofit2](https://github.com/square/retrofit)
- [Gson](https://github.com/google/gson)
- [Okhttp](https://github.com/square/okhttp)
- [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
