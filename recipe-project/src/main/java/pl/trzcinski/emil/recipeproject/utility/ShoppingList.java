package pl.trzcinski.emil.recipeproject.utility;

import lombok.extern.slf4j.Slf4j;
import pl.trzcinski.emil.recipeproject.model.*;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class ShoppingList {

    private ShoppingList() {
        //defensive move to block creating instance of this class
    }

    public static String shoppingList(Meals meals) {
        final Map<String, String> shoppingList;

        shoppingList = meals.getRecipeSet()
                .stream()
                .flatMap(recipe -> recipe.getSections().stream())
                        .flatMap(section -> section.getComponents().stream())
                                .collect(Collectors.toMap(Component::getRawText, Component::getRawText));

        log.info(String.valueOf(shoppingList));
        return null;
    }



//    public static String shoppingList(Meals meals) {
//        final Map<String, String> shoppingMap;
//
//        final List<Recipe> recipeList;
//        final List<Section> sectionList;
//        final List<Component> componentList;
//        final List<Measurement> measurementList;
//        final List<Unit> list;
//
//        recipeList = meals.getRecipeSet().stream().toList();
//        //log.info(String.valueOf(recipeList));
//
//        sectionList = recipeList.stream().flatMap(recipe -> recipe.getSections().stream()).toList();
//
//        componentList = sectionList.stream().flatMap(section -> section.getComponents().stream()).toList();
//
//        shoppingMap = componentList.stream().collect(Collectors.toMap(Component::getRawText, Component::getRawText));
//
//        measurementList = componentList.stream().flatMap(component -> component.getMeasurements().stream()).toList();
//        log.info(String.valueOf(shoppingMap));
//
//
//        return null;
//    }
}



















