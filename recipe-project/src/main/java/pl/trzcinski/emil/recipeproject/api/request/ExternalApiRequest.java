package pl.trzcinski.emil.recipeproject.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
public class ExternalApiRequest {

    public String createUrl() {
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setUrlTasty("https://tasty.p.rapidapi.com/recipes/");
        urlBuilder.setRequestStartingPoint(40);
        urlBuilder.setUrlParameters("&size=40");
        urlBuilder.setTag("dinner");

        return urlBuilder.build();
    }

    public Response getResponse() throws IOException, NullPointerException , StreamReadException,
            DatabindException, JsonProcessingException, JsonMappingException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(createUrl())
                .get()
                .addHeader(ApiRequestEnums.HEADER_HOST_NAME.getValue(), ApiRequestEnums.HEADER_HOST_VALUE.getValue())
                .addHeader(ApiRequestEnums.HEADER_KEY_NAME.getValue(), ApiRequestEnums.HEADER_KEY_VALUE.getValue())
                .build();

        return client.newCall(request).execute();
    }

}
