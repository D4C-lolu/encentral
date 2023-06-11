package D4C.encentral.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Class representing an Admin object.
 * An admin only has the attributes of the base user class.
 * The admin can modify the details of other users including other admins
 */
@Entity
@Table(name = "admin")
public class Admin extends User {

    @Override
    public String toString() {
        return "Admin{" +
                super.toString()+
                "}";
    }
}
