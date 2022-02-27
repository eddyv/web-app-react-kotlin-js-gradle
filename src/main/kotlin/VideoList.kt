import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.p
import react.key
import react.useState

external interface VideoListProps : Props {
    var videos: List<Video>
}

val VideoList = FC<VideoListProps> { props ->
    // state is nullable, a.k.a when a user first comes to the page there is no selected video
    // useState acts as a delegated property b/c of the `by` keyword.
    var selectedVideo: Video? by useState(null)
    for (video in props.videos) {
        p {
            key = video.id.toString()
            onClick = {
                selectedVideo = if (selectedVideo == video) null else video
            }
            if (video == selectedVideo) {
                +"▶ "
            }
            +"${video.speaker}: ${video.title}"
        }
    }
}