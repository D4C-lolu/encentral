package D4C.encentral.model.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.Objects;

import static D4C.util.FormatDate.formatDate;

/**
 * A class representing a user.
 * User's registration number (regNo) serves as a natural id and is unique for a user
 * regNo is used for comparison's and hashing
 */
@MappedSuperclass
abstract public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name",nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name",nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name="reg_no",unique = true,nullable = false)
    @NaturalId
    private Long regNo;

    @Column(name="reg_date")
    private LocalDateTime regDate = LocalDateTime.now();

    public User(){

    }

    public User(String firstName, String lastName, String password,Long regNo){
        this.firstName=firstName;
        this.lastName=lastName;
        this.password = password;
        this.regNo = regNo;
    }

    public Long getId() {
        return id;
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
        return firstName+" "+lastName;
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

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getRegNo(), user.getRegNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegNo());
    }

    @Override
    public String toString() {
        return " id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", regNo=" + regNo +
                ", regDate=" + formatDate(regDate) +
                " ";
    }
}
