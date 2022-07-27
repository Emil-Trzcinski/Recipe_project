package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Component;
import pl.trzcinski.emil.recipeproject.model.Measurement;
import pl.trzcinski.emil.recipeproject.model.Unit;

public class MeasurementBuilder {

    private Long measurementId;

    private String quantity;

    private Component component;

    private Unit unit;

    public MeasurementBuilder withDefaultMeasurement() {
        this.measurementId = withDefaultId().measurementId;
        this.quantity = withDefaultQuantity().quantity;
//        this.unit = withUnit(new UnitBuilder().withDefaultUnit().build()).unit;

        return this;
    }

    public MeasurementBuilder withDefaultId() {
        this.measurementId = 1L;
        return this;
    }

    public MeasurementBuilder withMeasurementId(Long measurementId) {
        this.measurementId = measurementId;
        return this;
    }

    public MeasurementBuilder withDefaultQuantity() {
        this.quantity = "default";
        return this;
    }

    public MeasurementBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public MeasurementBuilder withComponent(Component component) {
        this.component = component;
        return this;
    }

    public MeasurementBuilder withUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public Measurement build() {
        return new Measurement(measurementId, component, quantity, unit);
    }
}
