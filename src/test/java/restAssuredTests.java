import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.PostAndGetResponseData;
import model.PutAndPatchResponseData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import parser.JsonParser;


import java.util.List;

import static io.restassured.RestAssured.given;

public class restAssuredTests {
    JsonParser parser = new JsonParser();

    @BeforeClass
    public static void setupURL() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    public void testResponseHeaderDataShouldBeCorrect() {
        given().when()
                .get("/api/users?page=1")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);

        System.out.println("testResponseHeaderDataShouldBeCorrect status:");
        System.out.println(given().when().get("/api/users?page=1").then().log().status());
    }

    @Test
    public void testResponseHeaderDataIncorrect() {
        given().when()
                .get("/api/users/23")
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .contentType(ContentType.JSON);
        System.out.println("testResponseHeaderDataIncorrect status:");
        System.out.println(given().when().get("/api/users/23").then().log().status());
    }

    @Test
    public void testCreatePost() {
        PostAndGetResponseData post = new PostAndGetResponseData();
        post.setName("morpheus");
        post.setJob("leader");
        post.setId(796);
        Response response = given().when().contentType("application/json").body(post).post("/api/users");
        PostAndGetResponseData receivedPost = parser.convertResponseToObject(response.body().asString(), PostAndGetResponseData.class);
        System.out.println("testCreatePost body:" );
        System.out.println(given().when().contentType("application/json").body(post).post("/api/users").then().log().body());

        Assert.assertEquals(post.getName(), receivedPost.getName());
        Assert.assertEquals(post.getJob(), receivedPost.getJob());
        Assert.assertEquals(post.getId(), receivedPost.getId());
    }

    @Test
    public void testUpdatePut() {
        PutAndPatchResponseData put = new PutAndPatchResponseData();
        put.setName("morpheus");
        put.setJob("zion resident");
        Response response = given().when().contentType("application/json").body(put).put("/api/users/2");
        PutAndPatchResponseData receivedPut = parser.convertResponseToObject(response.body().asString(), PutAndPatchResponseData.class);
        System.out.println("testUpdatePut body:");
        System.out.println(given().when().contentType("application/json").body(put).put("/api/users/2").then().log().body());

        Assert.assertEquals(put.getName(), receivedPut.getName());
        Assert.assertEquals(put.getJob(), receivedPut.getJob());
    }

    @Test
    public void testUpdatePatch() {
        PutAndPatchResponseData patch = new PutAndPatchResponseData();
        patch.setName("morpheus");
        patch.setJob("zion resident");
        Response response = given().when().contentType("application/json").body(patch).patch("/api/users/2");
        PutAndPatchResponseData receivedPut = parser.convertResponseToObject(response.body().asString(), PutAndPatchResponseData.class);
        System.out.println("testUpdatePatch body:");
        System.out.println(given().when().contentType("application/json").body(patch).patch("/api/users/2").then().log().body());

        Assert.assertEquals(patch.getName(), receivedPut.getName());
        Assert.assertEquals(patch.getJob(), receivedPut.getJob());
    }

    @Test
    public void testLoginSuccessfulPost() {
        PostAndGetResponseData post = new PostAndGetResponseData();
        post.setEmail("peter@klaven");
        post.setPassword("cityslicka");
        post.setToken("QpwL5tke4Pnpja7X");
        Response response = given().when().contentType("application/json").body(post).post("/api/login");
        PostAndGetResponseData receivedPost = parser.convertResponseToObject(response.body().asString(), PostAndGetResponseData.class);
        System.out.println("testLoginSuccessfulPost body:");
        System.out.println(given().when().contentType("application/json").body(post).post("/api/login").then().log().body());

        Assert.assertEquals(post.getToken(), receivedPost.getToken());
    }

    @Test
    public void testDelete() {
        given().when()
                .delete("/api/users/4")
                .then()
                .assertThat()
                .statusCode(204);
        System.out.println("testDelete status:");
        System.out.println(given().when().delete("/api/users/4").then().log().status());
    }


    @Test
    public void testGetPostById() {
        Response response = given().when().get("https://jsonplaceholder.typicode.com/posts/1");
        PostAndGetResponseData post = parser.convertResponseToObject(response.body().asString(), PostAndGetResponseData.class);
        System.out.println("testGetPostById body:");
        System.out.println(given().when().get("https://jsonplaceholder.typicode.com/posts/1").then().log().body());

        Assert.assertTrue(post.getTitle().contains("sunt aut facere"));
    }

    @Test
    public void testGetAllPosts() {
        Response response = given().when().get("https://jsonplaceholder.typicode.com/posts");
        List<PostAndGetResponseData> posts = parser.convertResponseToListOfObjects(response.body().asString(), PostAndGetResponseData.class);
        System.out.println("testGetAllPosts body:");
        System.out.println(given().when().get("https://jsonplaceholder.typicode.com/posts").then().log().body());

        Assert.assertEquals(posts.get(0).getTitle(), "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

    @Test
    public void testUpdatePost() {
        PostAndGetResponseData post = new PostAndGetResponseData();
        post.setUserId(1);
        post.setId(21);
        post.setTitle("magnam facilis autem");
        post.setBody("dolore placeat quibusdam ea quo vitae nmagni quis enim qui quis");

        Response response = given().when().contentType("application/json").body(post).put("https://jsonplaceholder.typicode.com/posts/21");
        PostAndGetResponseData receivedPost = parser.convertResponseToObject(response.body().asString(), PostAndGetResponseData.class);
        System.out.println("testUpdatePost body:");
        System.out.println(given().when().contentType("application/json").body(post).put("https://jsonplaceholder.typicode.com/posts/21").then().log().body());

        Assert.assertEquals(post.getUserId(), receivedPost.getUserId());
        Assert.assertEquals(post.getId(), receivedPost.getId());
        Assert.assertEquals(post.getTitle(), receivedPost.getTitle());
        Assert.assertEquals(post.getBody(), receivedPost.getBody());
    }
}
