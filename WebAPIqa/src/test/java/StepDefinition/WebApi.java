package StepDefinition;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class WebApi {
	static String token = "sl.AoG7GJkLB_foXN18BubJxJpLStQ3eNhZYyz7lzgyvlZw2OolEIxmLemgB9Uhw95IWZZ81Kr1Cxvgy-9y95zHikO-6hFwgbL4c4bnQJ4MkRqv92z44kDVzk-_4vjBUepMVk_PYSk";
	
	@Order(1)
    @Test
    public void uploaFile() {
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        File file = new File("src/test/resources/scream.jpg");
        String apiArgs = "{\"mode\":\"add\",\"autorename\":true,\"mute\":false,\"path\":\"/scream.jpg\"}";

        given()
        .headers("Authorization","Bearer " + token,
        		"Content-Type", "application/octet-stream", 
        		"Dropbox-API-Arg", apiArgs).body(file)
        .when().post("https://content.dropboxapi.com/2/files/upload")
        .then().statusCode(200);
    }
	
	@Order(2)
    @Test
    public void getMetadata() {
        Map<String,String> path = new HashMap<>();
        path.put("path", "/scream.jpg");

        given()
        .headers("Authorization","Bearer " + token, 
        		"Content-Type", "application/json").body(path)
        .when().post("https://api.dropboxapi.com/2/files/get_metadata")
        .then().statusCode(200);
    }
	
	@Order(3)
    @Test
    public void deleteFile() {
        Map<String,String> path = new HashMap<>();
        path.put("path", "/scream.jpg");

        given()
        .headers("Authorization","Bearer " + token, 
        		"Content-Type", "application/json").body(path)
        .when().post("https://api.dropboxapi.com/2/files/delete_v2")
        .then().statusCode(200);
    }
}