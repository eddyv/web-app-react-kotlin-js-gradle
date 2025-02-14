import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.useEffectOnce
import react.useState

const val video_api_url = "https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos"
suspend fun fetchVideo(id: Int): Video {
    val response = window.fetch("$video_api_url/$id").await().text().await()
    return Json.decodeFromString(response)
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    // 25 async tasks (one per requests) and wait for them all to complete.
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

val mainScope = MainScope()

val App = FC<Props> {
    // state is nullable, a.k.a. when a user first comes to the page there is no selected video
    // useState acts as a delegated property b/c of the `by` keyword.
    var currentVideo: Video? by useState(null)
    var unwatchedVideos: List<Video> by useState(emptyList())
    var watchedVideos: List<Video> by useState(emptyList())

    useEffectOnce { mainScope.launch { unwatchedVideos = fetchVideos() } }
    h1 {
        +"Hello, React+Kotlin/JS!"
    }
    div {
        h3 {
            +"Videos to watch"
        }
        VideoList {
            videos = unwatchedVideos
            selectedVideo = currentVideo
            onSelectVideo = { video ->
                currentVideo = if (currentVideo == video) null else video
            }
        }

        h3 {
            +"Videos watched"
        }
        VideoList {
            videos = watchedVideos
            selectedVideo = currentVideo
            onSelectVideo = { video ->
                currentVideo = if (currentVideo == video) null else video
            }
        }
    }
    // the let scope ensures that the video player component is only added when state.currentVideo is not null
    currentVideo?.let { curr ->
        VideoPlayer {
            video = curr
            unwatchedVideo = curr in unwatchedVideos
            onWatchedButtonPressed = {
                if (video in unwatchedVideos) {
                    unwatchedVideos = unwatchedVideos - video
                    watchedVideos = watchedVideos + video
                } else {
                    watchedVideos = watchedVideos - video
                    unwatchedVideos = unwatchedVideos + video
                }
            }
        }
    }
}