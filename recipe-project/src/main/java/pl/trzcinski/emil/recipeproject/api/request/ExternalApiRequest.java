package pl.trzcinski.emil.recipeproject.api.request;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import static pl.trzcinski.emil.recipeproject.api.request.ApiRequestEnums.*;

/**
 * ExternalApiRequest retrieves a recipe list from an external api based on the provided data
 */
@Slf4j
@Controller
public class ExternalApiRequest {

    /**
     * creates a link with the expected meal tags
     *
     * @param mealTag name of the expected tag
     * @param requestStartingPoint list starting point in external api
     * @return prepared link
     */
    public String createUrl(String mealTag, int requestStartingPoint) {

        StringBuilder urlStringBuilder = new StringBuilder();

        urlStringBuilder.append("https://tasty.p.rapidapi.com/recipes/")
                .append("list?from=")
                .append(requestStartingPoint)
                .append("&size=40")
                .append("&tags=")
                .append(mealTag);

        return urlStringBuilder.toString();
    }

    /**
     * sends a query to api and returns the received response
     * <p>
     * expects a list of 40 meals based on the prepared link
     *
     * @param mealTag tag name
     * @param requestStartingPoint starting point
     * @return returns the response body from external api
     * @throws NullPointerException does not receive a reply from external api
     */
    public ResponseBody getResponse(String mealTag, int requestStartingPoint) throws NullPointerException {

        Response response;

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(createUrl(mealTag, requestStartingPoint))
                    .get()
                    .addHeader(HEADER_HOST_NAME.getValue(), HEADER_HOST_VALUE.getValue())
                    .addHeader(HEADER_KEY_NAME.getValue(), HEADER_KEY_VALUE.getValue())
                    .build();

            response = client.newCall(request).execute();
            log.info(response.toString());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "ExternalApi is Offline, please try again latter");

        }
        return response.body();
    }
}
