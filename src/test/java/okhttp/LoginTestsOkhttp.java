package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;

import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp extends BaseOkhttp {

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        login();
       System.out.println(token);
//        Assert.assertTrue(response.isSuccessful());
//        Assert.assertEquals(response.code(),200);



    }

    @Test
    public void loginWrongEmail() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("maragmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request;
        request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response= client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDto=gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
        Assert.assertEquals(errorDto.getPath(),"/v1/user/login/usernamepassword");





    }
    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("mara@gmail.com")
                .password("Mm")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request;
        request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response= client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDto=gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
        Assert.assertEquals(errorDto.getPath(),"/v1/user/login/usernamepassword");




    }
    @Test
    public void loginUnregisteredUser() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("mara45987@gmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request;
        request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response= client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDto=gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
        Assert.assertEquals(errorDto.getPath(),"/v1/user/login/usernamepassword");
        Assert.assertEquals(errorDto.getError(),"Unauthorized");




    }

}