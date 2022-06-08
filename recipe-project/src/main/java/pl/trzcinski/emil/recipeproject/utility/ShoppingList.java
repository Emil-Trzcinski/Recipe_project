package pl.trzcinski.emil.recipeproject.utility;

import lombok.extern.slf4j.Slf4j;
import pl.trzcinski.emil.recipeproject.model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class ShoppingList {

    private ShoppingList() {
        //defensive move to block creating instance of this class
    }

    public static Map<String, String> shoppingList(Meals meals) {
        final Map<String, String> shoppingList;

        shoppingList = meals.getRecipeSet()
                .stream()
                .flatMap(recipe -> recipe.getSections().stream())
                .flatMap(section -> section.getComponents().stream())
                .collect(Collectors.toMap(component -> component.getIngredient().getName(), ShoppingList::getValue,
                        (firstValue, secValue) -> String.valueOf(Double.parseDouble(firstValue) + Double.parseDouble(secValue))));

        log.info(String.valueOf(shoppingList));
        return shoppingList;
    }

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

        listOfQuantity = component.getMeasurements().stream().map(measurement -> fractionConverter(measurement.getQuantity())).toList();

        double number = Double.parseDouble(listOfQuantity.get(0));
        if (number == 0) {
            return "1";
        }

        return listOfQuantity.get(0);

    }

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


    private static String fractionConverter(String number) {
        StringBuilder builder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();
        StringBuilder builderTemp = new StringBuilder();

        Pattern pattern = Pattern.compile("[¼½¾]");
        Matcher matcher = pattern.matcher(number);

        numberBuilder.append(numberConverter(number));

        if (numberBuilder.isEmpty()) {
            numberBuilder.append("0");
        }

        if (matcher.find()) {
            builderTemp.append(matcher.group());

            switch (builderTemp.toString()) {
                case "¼":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.25"));
                    break;
                case "½":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.5"));
                    break;
                case "¾":
                    builder.append(Double.parseDouble(numberBuilder.toString()) + Double.parseDouble("0.75"));
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



















