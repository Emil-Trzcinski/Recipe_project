package pl.trzcinski.emil.recipeproject.api.request;

public enum ApiRequestEnums {

    HEADER_HOST_NAME("x-rapidapi-host"),
    HEADER_HOST_VALUE("tasty.p.rapidapi.com"),
    HEADER_KEY_NAME("x-rapidapi-key"),
//    HEADER_KEY_VALUE("599499f508msh901b6a991084120p1b3173jsn2ea6a449da3e");
    HEADER_KEY_VALUE("259a8d7c8fmsheed54b685a759a3p1eb7d2jsn46270f6f5903");
//    HEADER_KEY_VALUE("17dfa65fc1mshca0f2d8db0df438p1dd862jsnd54469dafe81");

    private final String value;

    ApiRequestEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
