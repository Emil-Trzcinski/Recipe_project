package pl.trzcinski.emil.recipeproject.api.request;

public class UrlBuilder {
    private String urlTasty;
    private String urlParameters;
    private int requestStartingPoint;
    private String tag;

    public void setUrlTasty(String urlTasty) {
        this.urlTasty = urlTasty;
    }

    public void setUrlParameters(String urlParameters) {
        this.urlParameters = urlParameters;
    }

    public void setRequestStartingPoint(int requestStartingPoint) {
        this.requestStartingPoint = requestStartingPoint;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

//    public String build() {
//        return urlTasty + "list?from=" + requestStartingPoint + urlParameters;
//    }

    public String build() {
        return urlTasty + "list?from=" + requestStartingPoint + urlParameters + "&tags=" + tag;
    }
}
