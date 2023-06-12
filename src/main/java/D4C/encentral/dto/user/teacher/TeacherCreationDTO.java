package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.user.UserCreationDTO;

/**
 * DTO for creating a Teacher Object
 */
public class TeacherCreationDTO extends UserCreationDTO {

    public TeacherCreationDTO() {
    }

    public TeacherCreationDTO(String firstName, String lastName, String password, Long regNo) {
        super(firstName, lastName, password, regNo);
    }

    @Override
    public String toString() {
        return "TeacherCreationDTO{" +
                super.toString() +
                "}";
    }
}
