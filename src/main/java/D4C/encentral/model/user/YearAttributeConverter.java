package D4C.encentral.model.user;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


/**
 * Utility class to convert enum to a form that can be stored in the DB
 */
@Converter
public class YearAttributeConverter implements AttributeConverter<Year, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Year year) {
        if (year == null)
            return null;

        switch (year) {
            case JS1:
                return 1;

            case JS2:
                return 2;

            case JS3:
                return 3;

            case SS1:
                return 4;

            case SS2:
                return 5;

            case SS3:
                return 6;

            default:
                throw new IllegalArgumentException(year + " not supported.");
        }
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return Year.JS1;

            case 2:
                return Year.JS2;

            case 3:
                return Year.JS3;

            case 4:
                return Year.SS1;

            case 5:
                return Year.SS2;

            case 6:
                return Year.SS3;

            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }

}