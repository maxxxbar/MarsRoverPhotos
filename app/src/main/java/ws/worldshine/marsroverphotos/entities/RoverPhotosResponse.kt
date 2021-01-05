package ws.worldshine.marsroverphotos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RoverPhotosResponse(

    @field:SerializedName("photos")
    val photos: List<PhotosItem>
)

data class Camera(

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("rover_id")
    val roverId: Int
) {
    override fun toString(): String {
        return "$fullName,$name,$id,$roverId"
    }
}

@Entity(tableName = "photo_item")
data class PhotosItem(

    @PrimaryKey(autoGenerate = true)
    val uniqueID: Long,

    @field:SerializedName("sol")
    val sol: Int,

    @field:SerializedName("earth_date")
    val earthDate: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("camera")
    val camera: Camera,

    @field:SerializedName("rover")
    val rover: Rover,

    @field:SerializedName("img_src")
    val imgSrc: String
)

data class Rover(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("launch_date")
    val launchDate: String,

    @field:SerializedName("landing_date")
    val landingDate: String,

    @field:SerializedName("status")
    val status: String
) {
    override fun toString(): String {
        return "$name,$id,$launchDate,$landingDate,$status"
    }
}
