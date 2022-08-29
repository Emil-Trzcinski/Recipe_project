package pl.trzcinski.emil.recipeproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.trzcinski.emil.recipeproject.model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * ShoppingListService generates a shopping list for recipes
 */
@Slf4j
@Service
public class ShoppingListService {

    public ShoppingListService() {
    }

    /**
     * creates a shopping list (map - key -> ingredient name: value -> ingredient quantity) for given meals
     * <p>
     * if the components are repeated, they are added together
     * @param meals meals
     * @return shopping list
     */
    public Map<String, String> createShoppingList(Meals meals) {
        final Map<String, String> shoppingList;

        shoppingList = meals.getRecipeSet()
                .stream()
                .flatMap(recipe -> recipe.getSections().stream())
                .flatMap(section -> section.getComponents().stream())
                .collect(Collectors.toMap(component -> component.getIngredient().getName(), ShoppingListService::getValue,
                        (firstValue, secValue) -> String.valueOf(Double.parseDouble(firstValue) + Double.parseDouble(secValue))));

        log.info(String.valueOf(shoppingList));
        return shoppingList;
    }

    /**
     * gets the quantity of a specific component
     * <p>
     * if the range of quantities is from, to returns the initial quantity
     * <p>
     * if it is a fraction converts it to a decimal number
     * <p>
     * if the value of the ingredient is "zero", it returns one pcs
     * @param component component
     * @return component value
     */
    private static String getValue(Component component) {
        List<String> listOfQuantity;
        List<String> listTemp = new ArrayList<>();

        if (component.getMeasurements().size() > 1) {
            listOfQuantity = (component.getMeasurements()
                    .stream()
                    .map(measurement -> fractionConverter(measurement.getQuantity()))
                    .filter(s -> s.length() > 0))
                    .toList();

            if (listOfQuantity.size() > 1) {
                double number = Math.max(Double.parseDouble(listOfQuantity.get(0)), Double.parseDouble(listOfQuantity.get(1)));
                listTemp.add(String.valueOf(number));

                return listTemp.get(0);
            }

            return listOfQuantity.get(0);
        }

        listOfQuantity = component.getMeasurements()
                .stream()
                .map(measurement -> fractionConverter(measurement.getQuantity()))
                .toList();

        double number = Double.parseDouble(listOfQuantity.get(0));
        if (number == 0) {
            return "1";
        }

        return listOfQuantity.get(0);
    }

    /**
     * gets the initial number from the passed string
     * @param numbers string
     * @return numbers in the form of a string
     */
    private static String numberConverter(String numbers) {
        StringBuilder builder = new StringBuilder();

        Pattern pattern = Pattern.compile("(^\\d\\W+\\d)|\\W");
        Matcher matcher = pattern.matcher(numbers);

        if (matcher.find()) {
            builder.append(matcher.replaceAll(""));
        } else {
            builder.append(numbers);
        }

        return builder.toString().trim();
    }

    /**
     * converts fractions to decimals in a given string
     * @param number string
     * @return double number
     */
    private static String fractionConverter(String number) {
        StringBuilder builder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();
        StringBuilder builderTemp = new StringBuilder();

        Pattern pattern = Pattern.compile("[⅛⅜⅝⅞⅕¼⅓⅖½⅗⅔¾⅘]");
        Matcher matcher = pattern.matcher(number);

        numberBuilder.append(numberConverter(number));

        if (numberBuilder.isEmpty()) {
            numberBuilder.append("0");
        }

        if (matcher.find()) {
            builderTemp.append(matcher.group());

            switch (builderTemp.toString()) {

                case "⅛":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.125"));
                    break;
                case "⅕":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.2"));
                    break;
                case "¼":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.25"));
                    break;
                case "⅓":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.33"));
                    break;
                case "⅜":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.375"));
                    break;
                case "⅖":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.4"));
                    break;
                case "½":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.5"));
                    break;
                case "⅗":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.6"));
                    break;
                case "⅝":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.625"));
                    break;
                case "⅔":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.66"));
                    break;
                case "¾":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.75"));
                    break;
                case "⅘":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.8"));
                    break;
                case "⅞":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.875"));
                    break;
                default:
                    //nothing to do here
            }

        } else {
            builder.append(number);
        }

        return builder.toString().trim();
    }
}



















