package D4C.encentral.dto.user.teacher;

import D4C.encentral.dto.user.UserCreationDTO;

/**
 * DTO for creating a Teacher Object
 */
public class TeacherCreationDTO extends UserCreationDTO {
    @Override
    public String toString() {
        return "TeacherCreationDTO{" +
                super.toString() +
                "}";
    }
}
