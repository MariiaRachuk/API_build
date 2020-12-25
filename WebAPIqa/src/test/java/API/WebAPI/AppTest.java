package API.WebAPI;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.config.EncoderConfig.encoderConfig;
import io.restassured.RestAssured;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest{
	
	//your token from dropbox.com
	public static String my_token = "g4LWglKDFyUAAAAAAAAAAc08r2YVtLTO3Hcf2-O4GuNtS7CkMPFB2V_XEkbBcPow";

    @Test
    public void test1_upload(){
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        File photo = new File("src/test/1.png");
        String arg = "{\"mode\":\"add\",\"autorename\":true,\"mute\":false,\"path\":\"/1.png\"}";

        RestAssured
        .given().headers("Authorization","Bearer " + my_token,"Content-Type", "application/octet-stream","Dropbox-API-Arg", arg).body(photo)
        .when().post("https://content.dropboxapi.com/2/files/upload").then().statusCode(200);
    }

    @Test
    public void test2_get(){
        Map<String,String> path = new HashMap<>();
        path.put("path", "/1.png");

        RestAssured
        .given().headers("Authorization","Bearer " + my_token,"Content-Type", "application/json").body(path)
        .when().post("https://api.dropboxapi.com/2/files/get_metadata").then().statusCode(200);
    }

    @Test
    public void test3_delete(){
        Map<String,String> path = new HashMap<>();
        path.put("path", "/1.png");

        RestAssured
        .given().headers("Authorization","Bearer " + my_token,"Content-Type", "application/json").body(path)
        .when().post("https://api.dropboxapi.com/2/files/delete_v2").then().statusCode(200);
    }
}
