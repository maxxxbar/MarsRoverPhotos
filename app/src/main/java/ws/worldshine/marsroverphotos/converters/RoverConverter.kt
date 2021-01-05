package ws.worldshine.marsroverphotos.converters

import androidx.room.TypeConverter
import ws.worldshine.marsroverphotos.entities.Rover

class RoverConverter {
    @TypeConverter
    fun fromRover(rover: Rover): String {
        return rover.toString()
    }

    @TypeConverter
    fun toRover(rover: String): Rover {
        val str = rover.split(",").toTypedArray()
        return Rover(
            name = str[0],
            id = str[1].toInt(),
            launchDate = str[2],
            landingDate = str[3],
            status = str[4]
        )
    }
}