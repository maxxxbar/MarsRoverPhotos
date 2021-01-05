package ws.worldshine.marsroverphotos.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ws.worldshine.marsroverphotos.entities.RoverManifest
import ws.worldshine.marsroverphotos.entities.RoverPhotosResponse

interface NasaApi {
    @GET("rovers/curiosity/photos")
    suspend fun getPhotos(
        @Query("sol") sol: Int = 2970,
        @Query("page") page: Int = 1
    ): Response<RoverPhotosResponse>


    @GET("manifests/{rover_name}")
    suspend fun getRoverManifest(
        @Path("rover_name") roverName: String = "Curiosity"
    ): Response<RoverManifest>
}