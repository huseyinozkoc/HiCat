


import com.beykocbtcapp.hicat.model.Cat
import retrofit2.Call
import retrofit2.http.GET

interface CatService {
    @GET("images/search")
     fun getRandomCat(): Call<List<Cat?>>
}