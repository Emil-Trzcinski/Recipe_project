package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.*;

import java.util.*;

public class RecipeBuilder {

    private Long recipeId;
    private String name;
    private Nutrition nutrition;
    private List<Instruction> instructions = new ArrayList<>();
    private Integer totalTimeMinutes;
    private Integer prepTimeMinutes;
    private Integer cookTimeMinutes;
    private List<Section> sections = new ArrayList<>();
    private String thumbnailUrl;
    private Integer numServings;
    private List<Tag> tags = new ArrayList<>();
    private Set<Meals> meals = new HashSet<>();

    public RecipeBuilder withDefaultRecipe() {
        this.recipeId = 1L;
        this.name = "Default Recipe Name";
        this.nutrition = new NutritionBuilder().withDefaultNutrition().build();

        Instruction instruction = new InstructionBuilder().withDefaultInstruction().build();
        Instruction secInstruction = new InstructionBuilder().withInstruction_id(5L).withDisplayText("Some instruction").build();
        Instruction thirdInstruction = new InstructionBuilder().withInstruction_id(10L).withDisplayText("Another instruction").build();
        this.instructions = new ArrayList<>(Arrays.asList(instruction, secInstruction, thirdInstruction));

        this.totalTimeMinutes = 21;
        this.prepTimeMinutes = 5;
        this.cookTimeMinutes = 13;

        Section section = new SectionBuilder().withDefaultSection().build();
        Section secSection = new SectionBuilder().withDefaultSection().build();
        Section thirdSection = new SectionBuilder().withDefaultSection().build();
        this.sections = new ArrayList<>(Arrays.asList(section, secSection, thirdSection));

        this.thumbnailUrl = "https://someURL";
        this.numServings = 3;

        Tag tag = new TagBuilder().withDefaultTag().build();
        Tag secTag = new TagBuilder().withIdTag(2L).withName("Lunch").build();
        this.tags = new ArrayList<>(Arrays.asList(tag, secTag));

        return this;
    }

    public RecipeBuilder withDefaultName() {
        this.name = "default";
        return this;
    }

    public RecipeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RecipeBuilder withNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
        return this;
    }

    public RecipeBuilder withInstruction(List<Instruction> instructions) {
        this.instructions = instructions;
        return this;
    }

    public RecipeBuilder withDefaultTotalTimeMinutes() {
        this.totalTimeMinutes = 30;
        return this;
    }

    public RecipeBuilder withTotalTimeMinutes(Integer totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
        return this;
    }

    public RecipeBuilder withDefaultPrepTimeMinutes() {
        this.prepTimeMinutes = 31;
        return this;
    }

    public RecipeBuilder withCookTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
        return this;
    }

    public RecipeBuilder withDefaultCookTimeMinutes() {
        this.cookTimeMinutes = 32;
        return this;
    }

    public RecipeBuilder withPrepTimeMinutes(Integer cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
        return this;
    }

    public RecipeBuilder withSection(List<Section> sections) {
        this.sections = sections;
        return this;
    }

    public RecipeBuilder withDefaultUrl() {
        this.thumbnailUrl = "https://img.buzzfeed.com/thumbnailer-prod-us-east-1/" +
                "791939e64cd749898945a4e217faf531/BFV84574_Tasty_3SouthAsianWarmDrinks_ADB_101321_Final_1x1_OO.jpg";
        return this;
    }

    public RecipeBuilder withUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public RecipeBuilder withDefaultNumberServings() {
        this.numServings = 1;
        return this;
    }

    public RecipeBuilder withNumberServings(Integer numServings) {
        this.numServings = numServings;
        return this;
    }

    public RecipeBuilder withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public RecipeBuilder withMeals(Set<Meals> meals) {
        this.meals = meals;
        return this;
    }

    public Recipe build() { //meals nie jest generowany
        Recipe recipe = new Recipe(recipeId, name, nutrition, instructions, totalTimeMinutes, prepTimeMinutes, cookTimeMinutes, sections, thumbnailUrl, numServings, tags, meals);
        return recipe;

    }
}
