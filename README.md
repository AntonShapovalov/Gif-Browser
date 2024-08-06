# Giphy Browser

#### Android application concept to display Giphy trending images with paging loading and offline work support.

Project demonstrates how to use AndroidX, Kotlin, Navigation Component, Paging Component, MVVM, LiveData, Dagger, RxJava and Retrofit to get remote data and save it to local cache.
For simplification, shared preferences are used like local cache. For real scenario Db storage (Room, GreenDao) is preferable.

#### Structure:

1. Grid screen - shows grid of trending gifs
2. Full screen - displays full screen image

#### How to install

Please use [app-release.apk](./app/release/) file.

#### Used language and libraries
 * [Kotlin](https://kotlinlang.org/docs/tutorials/kotlin-android.html) - primary project language
 * [Navigation component](https://developer.android.com/guide/navigation) - handles all navigation aspects, allows to avoid boilerplate code with fragments transaction, backstack etc.
 * [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) - the core of [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern
 * [Paging library](https://developer.android.com/topic/libraries/architecture/paging) - painless paging loading
 * [RxJava](https://github.com/ReactiveX/RxJava) - simple way to manage data chains
 * [Dagger](https://google.github.io/dagger/) - dependency injection framework
 * [Retrofit](http://square.github.io/retrofit/) - to perform API call
 * [Glide](https://bumptech.github.io/glide/) - images loading library with cache possibility
