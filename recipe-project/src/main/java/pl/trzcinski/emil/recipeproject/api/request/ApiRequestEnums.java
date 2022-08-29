package pl.trzcinski.emil.recipeproject.api.request;

public enum ApiRequestEnums {
    HEADER_HOST_NAME("x-rapidapi-host"),
    HEADER_HOST_VALUE("tasty.p.rapidapi.com"),
    HEADER_KEY_NAME("x-rapidapi-key"),
    HEADER_KEY_VALUE("add here your X-RapidAPI-Key");

    private final String value;

    ApiRequestEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
