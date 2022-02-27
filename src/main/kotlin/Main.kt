import kotlinx.browser.document
import react.create
import react.dom.render

data class Video(val id: Int, val title: String, val speaker: String, val videoUrl: String)

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    render(App.create(), container)
}