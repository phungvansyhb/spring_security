package lg.frontend.spring_security_section1.utils;

import org.mindrot.jbcrypt.BCrypt;

public class StringUtil {
    public static String stringToSearchLike(String text){
        if(text == null) return "%%";
        return  "%".concat(text.concat("%"));
    }
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

}
