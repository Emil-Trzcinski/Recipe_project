package pl.trzcinski.emil.recipeproject.service;

/**
 * konwernter okresla minmalny zakres w jakim beda zwracane posilki.
 * <p>
 * W przypadku oczkiwania posilku o 1000Kcal otrzymamy dolny zakres stanowiacy 1000 * CONVERTER;
 * <p>
 * np. 1000 * 0,7 = 700
 */
public class Conversion {

    private Conversion() {
        //defensive move to block create instance of this class
    }

    public static final double CONVERTER = 0.7;
}
