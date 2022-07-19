package pl.trzcinski.emil.recipeproject.utility.builders;

import pl.trzcinski.emil.recipeproject.model.Measurement;
import pl.trzcinski.emil.recipeproject.model.Unit;

public class UnitBuilder {

    private Long unitId;

    private String name;

    private String system;

    private Measurement measurement;

    public UnitBuilder withDefaultUnit() {
        this.unitId = withDefaultId().unitId;
        this.name = withDefaultName().name;
        this.system = withDefaultSystem().system;
//        this.measurement = withMeasurement(new MeasurementBuilder().withDefaultMeasurement().build()).measurement;

        return this;
    }

    public UnitBuilder withDefaultId() {
        this.unitId = 1L;
        return this;
    }

    public UnitBuilder withUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public UnitBuilder withDefaultName() {
        this.name = "default";
        return this;
    }

    public UnitBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UnitBuilder withDefaultSystem() {
        this.system = "default";
        return this;
    }

    public UnitBuilder withSystem(String system) {
        this.system = system;
        return this;
    }

    public UnitBuilder withMeasurement(Measurement measurement) {
        this.measurement = measurement;
        return this;
    }

    public Unit build() {
        return new Unit(unitId, measurement, name, system);
    }
}
