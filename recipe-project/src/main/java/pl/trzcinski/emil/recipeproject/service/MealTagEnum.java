package pl.trzcinski.emil.recipeproject.service;

public enum MealTagEnum {

    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private final String meal;

    MealTagEnum(String meal) {
        this.meal = meal;
    }

    public String getMeal() {
        return meal;
    }
}
