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

    public static Year fromInteger(int x){
        switch(x){
            case 1:
                return JS1;
            case 2:
                return JS2;
            case 3:
                return JS3;
            case 4:
                return SS1;
            case 5:
                return SS2;
            case 6:
                return SS3;
            default:
                return null;
        }
    }
}