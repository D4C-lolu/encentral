package D4C.encentral.dto.user;

import java.time.LocalDateTime;
import java.util.Objects;

import static D4C.util.FormatDate.formatDate;

/**
 * DTO object representing a user
 */
public class UserDTO {

    private String firstName;
    
    private String lastName;
    
    private Long regNo;

    private LocalDateTime regDate;
    
    public UserDTO(){}

    public UserDTO(String firstName, String lastName, Long regNo, LocalDateTime regDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.regNo = regNo;
        this.regDate=regDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getRegNo() {
        return regNo;
    }

    public void setRegNo(Long regNo) {
        this.regNo = regNo;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getRegNo(), userDTO.getRegNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegNo());
    }

    @Override
    public String toString() {
        return " " +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", regNo=" + regNo +
                ", regDate="+ formatDate(regDate)+
                " ";
    }
}
