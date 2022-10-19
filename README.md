# Photo Explorer

Photo Explorer project shows modern Android development, following Android design and development best practices. It's inspired by [NowInAndroid](https://github.com/android/nowinandroid) project and [Guide to app architecture](https://developer.android.com/topic/architecture).

Application use data from [Unsplash API](https://unsplash.com/documentation)

## _To Do improvements_

- Work on UI in landscape mode
- Add instrumented test on UI
- Put `com.lyh.photoexplorer.feature.photos.util` classes in a new module `feature-core-test`
- Create a custom Gradle Plugin for `java-library` module, and use it instead of `com.android.library` in modules don't have Android dependencies as `data-remote`, `data`, ...
- Create a custom Gradle Plugin for `compose` module
- Manage [large screen](https://developer.android.com/guide/topics/large-screens/get-started-with-large-screens)
- Use Flow.combine to have a single state per screens
- Manage conversion from Json to Kotlin for date with kotlinx serialization or retrofit (kotlinx-datetime)
- Add security:
  - certificate pinning
  - Hide access-key in C source code
  - Check app is not running on rooted device
  - Check source of installation
  - Add obfuscation


## Setup

in `local.properties` file (in root of project) add Unsplash access key :

```
accessKey="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
```

## Architecture

Application uses:

- **Clean Architecture** for separate business logic **[domain]**, services (storage, API, ...) **[data]** and **[presentation]** layers

![Clean Architecture](doc/clean-archi.png)

- **MVVM** (Model-View-ViewModel) design pattern for presentation layer

Clean Archi and MVVM help:

- to avoid or reduce dependencies between layers (separation of concerns)
- to test easily
- to have uniform way to develop
- ...

Clean Architecture stack uses Flow to stream data, from data layer to presentation layer.

UI use Jetpack Compose.

## Modularization

- app **[clean-archi/presentation]**: Single-Activity and Application classes
    - feature-core: common classes and resources (styles, strings, drawable) for feature modules
    - feature-photos: feature showing photos and details, using MVVM pattern
- build-logic : for gradle plugins, to share common configuration
- data **[clean-archi/data]**: repositories
    - data-remote: for API calls
- domain **[clean-archi/domain]**: for business logic, use-case

## Tests

- data: unit tests on repository and helper
- domain: unit tests on use-case
- app
    - feature-photos : unit tests on view-model

## Build system

- Project uses Gradle build system with Kotlin DSL (kts).
- Catalog (with versions) of libraries and plugins is in `gradle/libs.versions.toml` see this [article](https://proandroiddev.com/gradle-version-catalogs-for-an-awesome-dependency-management-f2ba700ff894)
- Common gradle configuration is shared in custom gradle plugins in build-logic

## Libraries

List of libraries used in project:

- JetPack Navigation
- Coil to load image from URL
- Koin for dependency injection
- Kotlinx-serialization for Json de/serializer
- Material3 for UI components and styles
- OkHttp for HTTP calls
- Retrofit for API calls
- Room to persist local data in SQLite database
- Timber for logging
- For testing:
    - JUnit5 for unit tests
    - Mockk for mocking
    - Turbine for Flow test
  
## ScreenShots

- Loading screen:

![Loading screen](doc/Screenshot_loading.png)

- Error screen:

![Error screen](doc/Screenshot_error.png)

- List screen:

![List screen](doc/Screenshot_list.png)

- Details screen:

![Details screen](doc/Screenshot_details.png)
