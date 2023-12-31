package D4C.encentral.dto.user.student;

import D4C.encentral.dto.user.UserDTO;
import D4C.encentral.dto.user.teacher.TeacherDTO;
import D4C.encentral.model.user.Year;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A DTO for representing a student object
 */
public class StudentDTO extends UserDTO {
    @JsonProperty("year")
    private Year year;

    @JsonProperty("teacher")
    private TeacherDTO teacher;

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public StudentDTO() {
        super();
    }

    public StudentDTO(String firstName, String lastName, Long regNo, Year year, String regDate) {
        super(firstName, lastName, regNo,regDate);
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                super.toString() +
                "year=" + year +
                ", teacher=" + teacher +
                '}';
    }
}
