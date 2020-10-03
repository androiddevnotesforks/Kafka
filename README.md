# Kafka

Currently in process of content curation in order to build a CMS. It will host more than 5 million audio content (about 20% is popular) available in more than 100 languages, all free.



</br></br>

<img src="https://user-images.githubusercontent.com/6247940/94991059-b1179e80-0580-11eb-87c5-9ecfaa8da75e.png">

<img src="https://user-images.githubusercontent.com/6247940/94991424-65b2bf80-0583-11eb-8f20-8909301aab11.png">




## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by Chris Banes' [Tivi app](https://github.com/chrisbanes/tivi).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) (for thread switching; and streams with Flow)
* Jetpack compose (the app is almost complete with compose in the branch [jetpack-compose](https://github.com/vipulyaara/Kafka/tree/develop-compose). But I had to move away from it in order to complete some features as the framework is not as mature (dev-14 as of now) 
* [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* Retrofit2
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ExoPlayer](https://github.com/google/ExoPlayer) - For audio playback


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
