package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import okhttp3.*;

import java.io.IOException;

public class BaseOkhttp {
    Gson gson = new Gson();

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    String token;
    public void login() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder()
                .username("mara@gmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body= RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response= client.newCall(request).execute();
        AuthResponseDto responseDto=gson.fromJson(response.body().string(), AuthResponseDto.class);
        token=responseDto.getToken();
    }

}
