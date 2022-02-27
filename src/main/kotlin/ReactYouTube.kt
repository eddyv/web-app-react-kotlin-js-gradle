@file:JsModule("react-youtube-lite") @file:JsNonModule

import react.ComponentClass
import react.Props

@JsName("ReactYouTubeLite")
external val ReactPlayer: ComponentClass<ReactYouTubeProps>

external interface ReactYouTubeProps : Props {
    var url: String
}