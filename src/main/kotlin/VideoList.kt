import react.FC
import react.Props
import react.dom.html.ReactHTML.p
import react.key

external interface VideoListProps : Props {
    var videos: List<Video>

    // is nullable, a.k.a when a user first comes to the page there is no selected video
    var selectedVideo: Video?

    var onSelectVideo: (Video) -> Unit
}

val VideoList = FC<VideoListProps> { props ->
    for (video in props.videos) {
        p {
            key = video.id.toString()
            onClick = {
                props.onSelectVideo(video)
            }
            if (video == props.selectedVideo) {
                +"▶ "
            }
            +"${video.speaker}: ${video.title}"
        }
    }
}