# Kafka

Kafka uses www.archive.org to serve millions of audiobooks and pdf books available in more than 100 languages, all free.

### [Video](https://vimeo.com/user68598793/review/472788300/8256f4487c)

</br>


<img src="https://user-images.githubusercontent.com/6247940/218861002-ff07951f-e9a5-428a-ad60-05b5e1393ccd.png">
<img src="https://user-images.githubusercontent.com/6247940/218860894-99674641-3a46-46bc-83b5-fb4c9e83c805.png">


The audio player is separated into another library https://github.com/vipulyaara/Sarahang

## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by [DatMusic](https://github.com/alashow/datmusic-android).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) (for thread switching; and streams with Flow)
* [Jetpack compose](https://developer.android.com/jetpack/compose) 
the app is built with both compose and epoxy in 2 completely separate UI flows. The traditional flow is built using xmls and recyclerViews and works well. Compose still has some limitations but almost all the screens are built in compose too. Switch the flows using MainActivity and ComposeMainActivity.
* [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* Retrofit2
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ExoPlayer](https://github.com/google/ExoPlayer) - For audio playback


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
