package D4C.encentral.dto.user;


import java.util.Objects;

/**
 * Base DTO class for creating a user
 */
abstract public class UserCreationDTO {
    private  String firstName;
    private String lastName;
    private String password;
    private Long regNo;

    public UserCreationDTO(){}

    public UserCreationDTO(String firstName, String lastName, String password, Long regNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.regNo = regNo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRegNo() {
        return regNo;
    }

    public void setRegNo(Long regNo) {
        this.regNo = regNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreationDTO)) return false;
        UserCreationDTO that = (UserCreationDTO) o;
        return Objects.equals(getRegNo(), that.getRegNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegNo());
    }

    @Override
    public String toString() {
        return " "+
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", regNo=" + regNo +
                " ";
    }
}
