package hr.javafx.energycms.entities;

import java.math.BigDecimal;

public sealed interface EnergyTaxable permits Device{
    BigDecimal calculateEnvironmentalTax();
}
