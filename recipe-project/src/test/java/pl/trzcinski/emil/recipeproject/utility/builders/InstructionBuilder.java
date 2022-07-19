package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Instruction;
import pl.trzcinski.emil.recipeproject.model.Recipe;

public class InstructionBuilder {

    private Long instruction_id;

    private String displayText;

    private Recipe recipe;

    public InstructionBuilder withDefaultInstruction() {
        this.instruction_id = withDefaultInstruction_id().instruction_id;
        this.displayText = withDefaultDisplayText().displayText;
        return this;
    }


    public InstructionBuilder withDefaultInstruction_id() {
        this.instruction_id = 1L;
        return this;
    }

    public InstructionBuilder withInstruction_id(Long instruction_id) {
        this.instruction_id = instruction_id;
        return this;
    }

    public InstructionBuilder withDefaultDisplayText() {
        this.displayText = "default instruction";
        return this;
    }

    public InstructionBuilder withDisplayText(String displayText) {
        this.displayText = displayText;
        return this;
    }

    public InstructionBuilder withRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Instruction build() {
        return new Instruction(instruction_id, displayText, recipe);
    }
}
