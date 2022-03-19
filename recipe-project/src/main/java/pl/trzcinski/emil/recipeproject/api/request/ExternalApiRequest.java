package pl.trzcinski.emil.recipeproject.api.request;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
public class ExternalApiRequest {

    //dodać do enum lub proporities
    private final String urlTasty = "https://tasty.p.rapidapi.com/recipes/";
    private final String headerHostName = "x-rapidapi-host";
    private final String headerHostValue = "tasty.p.rapidapi.com";
    private final String headerKeyName = "x-rapidapi-key";
    private final String headerKeyValue = "599499f508msh901b6a991084120p1b3173jsn2ea6a449da3e";

    private Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlTasty + url)
                .get()
                .addHeader(headerHostName, headerHostValue)
                .addHeader(headerKeyName, headerKeyValue)
                .build();
        return client.newCall(request).execute();
    }

    public String getResponseBodyFromResponse() throws IOException {
        Response response = getResponse("list?from=0&size=40");
        return response.body().string();
    }

}
