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

import static pl.trzcinski.emil.recipeproject.api.request.ApiRequestEnums.*;

@Slf4j
@Controller
public class ExternalApiRequest {
    public int requestStartingPoint = 0; //- to trzeba zabezpieczyć

    public String createUrl(String meal) {
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setUrlTasty("https://tasty.p.rapidapi.com/recipes/");
        urlBuilder.setRequestStartingPoint(requestStartingPoint);
        urlBuilder.setUrlParameters("&size=40");
        urlBuilder.setTag(meal);

        return urlBuilder.build();
    }

    public Response getResponse(String meal) throws IOException, NullPointerException , StreamReadException,
            DatabindException, JsonProcessingException, JsonMappingException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(createUrl(meal))
                .get()
                .addHeader(HEADER_HOST_NAME.getValue(), HEADER_HOST_VALUE.getValue())
                .addHeader(HEADER_KEY_NAME.getValue(), HEADER_KEY_VALUE.getValue())
                .build();

        return client.newCall(request).execute();
    }

    public void setRequestStartingPoint(int requestStartingPoint) {
        this.requestStartingPoint = requestStartingPoint;
    }
}
