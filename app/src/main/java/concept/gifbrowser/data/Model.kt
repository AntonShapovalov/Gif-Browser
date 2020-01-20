package concept.gifbrowser.data

/**
 * Response model for [ApiService.getImages]
 */
data class GifResponse(val data: List<GifEntry>)

data class GifEntry(val id: String, val images: GifImages)

data class GifImages(val preview_gif: GifImage, val original: GifImage)

data class GifImage(val url: String? = null)

/**
 * Data item to display in UI or save to local cache
 */
data class ImageItem(
    val id: String,
    val previewUrl: String? = null,
    val originalUrl: String? = null
)
