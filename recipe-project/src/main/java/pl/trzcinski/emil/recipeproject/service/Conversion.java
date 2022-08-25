package pl.trzcinski.emil.recipeproject.service;

/**
 * Converter specifies the minimum range in which meals will be returned.
 * <p>
 * if expect meal of 1000 Kcal, you will get the lower range of 1000 * CONVERTER
 * <p>
 * example: 1000 * 0,7 = 700
 */
public class Conversion {

    private Conversion() {
        //defensive move to block create instance of this class
    }

    public static final double CONVERTER = 0.7;
}
