package D4C.encentral.dao.user;

import D4C.encentral.dto.user.UserCreationDTO;
import D4C.encentral.dto.user.UserDTO;
import D4C.encentral.dto.user.student.StudentDTO;


import java.util.*;

/**
 * Interface for User Data Access Object
 */
public interface UserDAO<T extends UserDTO, S extends UserCreationDTO> {

    /**
     * Get a User DTO Object by its registration number.
     * Checks that given password matches password field on user
     * @param regNo - User's registration number
     * @param password - User's password
     * @return UserDTO
     */
    Optional<T> get(long regNo, String password);

    /**
     * Gets a User DTO object by its registration number
     * @param regNo - User's registration number
     * @return userDTO
     */
    Optional<T> get(long regNo);

    /**
     * Get a List of all Users
     * @return a list of all User DTOs
     */
    List<T> getAll();

    /**
     * Adds User DTO to DB
     * @param s - Creation DTO for user
     */
    void save(S s);

    /**
     * Updates the name fields of a User
     * @param regNo - User regNo
     * @param firstname
     * @param lastname
     */
    void updateName(Long regNo, String firstname, String lastname);

    /**
     * Updates the password field of a user
     * @param regNo - User regNo
     * @param password
     */
    void updatePassword(Long regNo, String password);

    /**
     * Delete User Object
     * @param regNo - User regNo
     */
    void delete(Long regNo);

}
