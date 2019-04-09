package ca.ulaval.ima.mecenapp.models.Service;

import ca.ulaval.ima.mecenapp.models.Login;
import ca.ulaval.ima.mecenapp.models.Users;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @POST("login")
    Call<Users> login(@Body Login login);
    @GET("secretnfos")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);

}
