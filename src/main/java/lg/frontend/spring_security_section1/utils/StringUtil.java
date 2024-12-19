package lg.frontend.spring_security_section1.utils;

public class StringUtil {
    public static String stringToSearchLike(String text){
        if(text == null) return "%%";
        return  "%".concat(text.concat("%"));
    }
}
