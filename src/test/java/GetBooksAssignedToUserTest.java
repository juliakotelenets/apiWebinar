import dto.response.UserNotFoundResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.GenericRestUtils;
import utils.GenericRestVerify;
import utils.HttpMethod;
import utils.RestClient;

import static java.lang.String.format;

public class GetBooksAssignedToUserTest extends BaseTest{

    @DataProvider(name = "test-data")
    public Object[][] dataProvider(){
        return new Object[][]{
                {"TEST0011", 401},
                {"TEST0012", 400},
                {"User1", 200}
        };
    }


    @Test(description = "Request data for invalid ID", dataProvider = "test-data")
    public void TC1(String user, int status){
        Response response = RestClient.getInstance().sendRequest(HttpMethod.GET,
                format("Account/v1/User/%s", user), false);
        GenericRestVerify.checkResponseCodeIs(response, status);

        UserNotFoundResponse userNotFound = GenericRestUtils.getResponseAsJsonObject(response, UserNotFoundResponse.class);
        Assertions.assertThat(userNotFound.getMessage()).as("User is found!").isEqualTo("User not found!");
    }

}
