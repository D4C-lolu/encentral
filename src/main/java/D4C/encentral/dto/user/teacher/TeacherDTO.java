package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.user.UserDTO;

import java.time.LocalDateTime;

/**
 * A DTO for representing a teacher object
 */
public class TeacherDTO extends UserDTO {

    public TeacherDTO(){
        super();
    }

    public TeacherDTO(String firstName, String lastName, Long regNo, LocalDateTime regDate){
        super(firstName,lastName,regNo,regDate);
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                super.toString() +
                "}";
    }
}
