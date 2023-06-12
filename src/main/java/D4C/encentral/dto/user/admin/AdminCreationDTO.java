package D4C.encentral.dto.user.admin;

import D4C.encentral.dto.user.UserCreationDTO;

public class AdminCreationDTO extends UserCreationDTO {

    public AdminCreationDTO() {
    }

    public AdminCreationDTO(String firstName, String lastName, String password, Long regNo) {
        super(firstName, lastName, password, regNo);
    }

    @Override
    public String toString() {
        return "AdminCreationDTO{" +
                super.toString() +
                "}";
    }
}
