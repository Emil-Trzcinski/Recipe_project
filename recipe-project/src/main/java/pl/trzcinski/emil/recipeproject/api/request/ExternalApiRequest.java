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
 * ExternalApiRequest pobiera z zew. api liste przepisow na podsttawie przekazanych danych
 */
@Slf4j
@Controller
public class ExternalApiRequest {

    /**
     * tworzy link zawierajacy oczekiwane tagi posiłków
     *
     * @param mealTag              nazwa oczekiwanego tagu
     * @param requestStartingPoint punkt startowy listy w zew. api
     * @return przygotowany link
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
     * wysyła zapytanie do api i zwraca otrzymana odpowiedz, lub null w przypadku braku odpowiedzi,
     * <p>
     * oczekuje listy 40 posiłków na podstawie przygotowanego linku
     *
     * @param mealTag
     * @param requestStartingPoint
     * @return zwraca ciało odpowiedzi z api
     * @throws NullPointerException nie otrzymuje odpowiedzi z zew api
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
            //todo refactor !!!???
            log.error(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "ExternalApi is Offline, please try again latter");

        }
        return response.body();
    }
}
