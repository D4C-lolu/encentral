package D4C.encentral.dto.user.student;

import D4C.encentral.dto.user.UserCreationDTO;
import D4C.encentral.model.user.Year;

/**
 * DTO for creating a student object
 */
public class StudentCreationDTO extends UserCreationDTO {
    Year year;

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public StudentCreationDTO() {
        super();
    }

    public StudentCreationDTO(String firstName, String lastName, String password, Long regNo, Year year) {
        super(firstName, lastName, password, regNo);
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentCreationDTO{" +
                super.toString() +
                "year=" + year +
                "}";
    }
}
