package D4C.encentral.dto.user.admin;

import D4C.encentral.dto.user.UserDTO;


public class AdminDTO extends UserDTO {
    public AdminDTO(){
        super();
    }

    public AdminDTO(String firstName, String lastName, Long regNo,String regDate){
        super(firstName,lastName,regNo,regDate);
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                super.toString() +
                "}";
    }
}
