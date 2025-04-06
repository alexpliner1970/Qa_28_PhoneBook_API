package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDto authReg = AuthRequestDto.builder()
                .username("mara"+i+"@gmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(authReg),JSON);

        Request request=new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        AuthResponseDto authResponseDto =gson.fromJson(response.body().string(), AuthResponseDto.class);
        System.out.println(authResponseDto.getToken());
    }

    @Test
    public void registrationTestWrongEmail() throws IOException {

        AuthRequestDto authReg = AuthRequestDto.builder()
                .username("maragmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(authReg),JSON);

        Request request=new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDTO errorDTO =gson.fromJson(response.body().string(),ErrorDTO.class);
        System.out.println(errorDTO.getMessage());//{username=must be a well-formed email address}
        System.out.println(errorDTO.getError());//Bad Request
    }
    @Test
    public void registrationTestRegisteredUser() throws IOException {
        AuthRequestDto authReg= AuthRequestDto.builder()
                .username("mara@gmail.com")
                .password("Mmar123456$")
                .build();
        RequestBody body=RequestBody.create(gson.toJson(authReg),JSON);
        Request request =new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),409);
        ErrorDTO errorDTO=gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getError());//Conflict
        Assert.assertEquals(errorDTO.getMessage(),"User already exists");

    }
}
