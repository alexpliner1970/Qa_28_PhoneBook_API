package okhttp;

import com.google.gson.Gson;
import dto.*;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp extends BaseOkhttp {
    Gson gson = new Gson();

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    String id;

    @BeforeMethod
    public void preCondition() throws IOException {
        login();
        System.out.println(token);
        System.out.println("============");
        int i = new Random().nextInt(1000) + 1000;
        ContactDto contact = ContactDto.builder()
                .name("Maya")
                .lastName("Dow")
                .email("maya" + i + "@gmail.com")
                .phone("12345565" + i)
                .address("TA")
                .description("Friend")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);
        //System.out.println(messageDto.getMessage());
        String[] message = messageDto.getMessage().split(": ");
        id = message[1];
        System.out.println(id);

    }


    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);
        System.out.println(messageDto.getMessage());
        Assert.assertEquals(messageDto.getMessage(), "Contact was deleted!");

    }

    @Test
    public void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/6e6260c1-c0f2-437f-967e-d3e0aea86457")
                .delete()
                .addHeader("Authorization", "rytyfhg")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");

    }

    @Test
    public void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/18bf1a49")
                .delete()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO=gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getError());
    }
}

//a8cb6927-d2c8-4d02-950e-3e0bd3465e70
//wow1351@gmail.com
//==================
//18bf1a49-d80d-4961-9fc3-792b3b0971c8
//tanya@maol.com
//==================
//ea91ad86-f9a7-4416-9591-bb432733bb03
//vera@vera.ru
//==================
//6fbbc54d-8158-49de-843c-fa70e9230e0b
//olsana@com.com
//==================
//a4a33d33-b00e-4049-8570-dfd07aace9c7
//eva@gmail.com
//==================
//6af2eb35-e9f1-4b1a-bbc4-d7add29bf9c2
//maya1158@gmail.com
//==================
//6b45a98d-6b80-47ad-a424-bf48b30b54d1
//maya1044@gmail.com
//==================
//d8ac6fb6-63b0-476a-a0f5-2aacef51fe2b
//maya1948@gmail.com
//==================
//67dd843f-89f5-4413-bc06-44bacc1cbe2a
//maya1726@gmail.com
//==================
//c0e40610-3532-46b0-895a-a49a7d1dd616
//maya1227@gmail.com
//==================
//58f9813e-ae2d-4ef6-bfc3-87a501686561
//maya1768@gmail.com
//==================
//aadcdf06-901c-4a42-9ed7-5534a18e2c86
//maya1084@gmail.com
//==================
//b469978d-1861-4c9f-94e9-662651f7f0a9
//maya1286@gmail.com
//==================
//294544f0-9bda-40b6-9595-f8f6d80c4cbe
//maya1067@gmail.com
//==================
//3ba2c6f4-8061-4bc0-a566-eedbb454f2c2
//maya1696@gmail.com
//==================
//6e6260c1-c0f2-437f-967e-d3e0aea86457
//maya1285@gmail.com