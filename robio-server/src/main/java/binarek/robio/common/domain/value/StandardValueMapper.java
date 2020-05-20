package binarek.robio.common.domain.value;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Mapper that is responsible for mapping value objects to primitives and vice versa
 */
@Component
public class StandardValueMapper {

    private static final LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.M;
    private static final WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.G;

    public Notes toNotes(String notes) {
        return Notes.of(notes);
    }

    public String toValue(Notes notes) {
        return notes.getValue();
    }

    public Length toLength(BigDecimal length) {
        return Length.of(length, DEFAULT_LENGTH_UNIT);
    }

    public BigDecimal toValue(Length length) {
        return length.convertUnit(DEFAULT_LENGTH_UNIT).getValue();
    }

    public Weight toWeight(BigDecimal weight) {
        return Weight.of(weight, DEFAULT_WEIGHT_UNIT);
    }

    public BigDecimal toValue(Weight weight) {
        return weight.convertUnit(DEFAULT_WEIGHT_UNIT).getValue();
    }

    public PersonFirstName toPersonFirstName(String firstName) {
        return PersonFirstName.of(firstName);
    }

    public String toValue(PersonFirstName firstName) {
        return firstName.getValue();
    }

    public PersonLastName toPersonLastName(String lastName) {
        return PersonLastName.of(lastName);
    }

    public String toValue(PersonLastName lastName) {
        return lastName.getValue();
    }
}
