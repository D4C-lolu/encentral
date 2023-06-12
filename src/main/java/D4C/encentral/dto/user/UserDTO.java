package D4C.encentral.dto.user;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * DTO object representing a user
 */
abstract public class UserDTO {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("reg_no")
    private Long regNo;
    @JsonProperty("reg_date")
    private String regDate;
    
    public UserDTO(){}

    public UserDTO(String firstName, String lastName, Long regNo, String regDate) {
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

    public String getName(){
        return getFirstName()+" "+getLastName();
    }

    public Long getRegNo() {
        return regNo;
    }

    public void setRegNo(Long regNo) {
        this.regNo = regNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
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
                ", regDate="+ regDate +
                " ";
    }

}
