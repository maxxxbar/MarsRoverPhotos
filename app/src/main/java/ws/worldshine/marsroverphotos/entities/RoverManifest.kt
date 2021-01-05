package ws.worldshine.marsroverphotos.entities

import com.google.gson.annotations.SerializedName

data class RoverManifest(

    @field:SerializedName("photo_manifest")
    val photoManifest: PhotoManifest
)

data class PhotoManifest(
    @field:SerializedName("max_sol")
    val maxSol: Int,

    @field:SerializedName("max_date")
    val maxDate: String,

    @field:SerializedName("total_photos")
    val totalPhotos: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("launch_date")
    val launchDate: String,

    @field:SerializedName("photos")
    val photos: List<ManifestPhotosItem>,

    @field:SerializedName("landing_date")
    val landingDate: String,

    @field:SerializedName("status")
    val status: String
)

data class ManifestPhotosItem(

    @field:SerializedName("cameras")
    val cameras: List<String>,

    @field:SerializedName("sol")
    val sol: Int,

    @field:SerializedName("earth_date")
    val earthDate: String,

    @field:SerializedName("total_photos")
    val totalPhotos: Int
)

