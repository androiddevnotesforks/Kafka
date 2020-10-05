# Kafka

Kafka uses www.archive.org to serve millions of audiobooks and pdf books available in more than 100 languages, all free.



</br></br>

<img src="https://user-images.githubusercontent.com/6247940/95137771-1c23c980-0769-11eb-868e-60bbd20fd1e9.png">

<img src="https://user-images.githubusercontent.com/6247940/95137781-2219aa80-0769-11eb-8e71-c662e440cd85.png">




## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by Chris Banes' [Tivi app](https://github.com/chrisbanes/tivi).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) (for thread switching; and streams with Flow)
* [Jetpack compose](https://developer.android.com/jetpack/compose) 
the app is built with both compose and epoxy in 2 completely separate UI flows. The traditional flow is built using xmls and recyclerViews and works well. Compose still has some limitations but almost all the screens are built in compose too. I switch the flows using MainActivity and ComposeMainActivity.
* [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* Retrofit2
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ExoPlayer](https://github.com/google/ExoPlayer) - For audio playback


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
