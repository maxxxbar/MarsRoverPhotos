package ws.worldshine.marsroverphotos.converters

import androidx.room.TypeConverter
import ws.worldshine.marsroverphotos.entities.Camera

class CameraConverter {

    @TypeConverter
    fun fromCamera(camera: Camera): String {
        return camera.toString()
    }

    @TypeConverter
    fun toCamera(camera: String): Camera {
        val result = camera.split(",").toTypedArray()
        return Camera(
            fullName = result[0],
            name = result[1],
            id = result[2].toInt(),
            roverId = result[3].toInt()
        )
    }

}