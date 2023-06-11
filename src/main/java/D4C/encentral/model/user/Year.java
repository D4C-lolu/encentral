package D4C.encentral.model.user;

/**
 * An enum representing the class of a user
 */
public enum Year {
    JS1(1),
    JS2(2),
    JS3(3),
    SS1(4),
    SS2(5),
    SS3(6);

    final int val;

    Year(int val) {
        this.val = val;
    }
}