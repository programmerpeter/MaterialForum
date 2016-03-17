package net.materialforum.util;

import net.materialforum.sql.UserManager;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;

public class Validator {

    private Validator() {}
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    public static class ValidationException extends Exception { }

    public static void message(HttpServletResponse response) {
        try {
            response.getWriter().write("Tajna wiadomość dla hakierów lub no-scriptów!");
        } catch (IOException ex) { }
    }
    
    public static void empty(String s) throws ValidationException {
        if (s == null || s.isEmpty())
            throw new ValidationException();
    }

    public static void length(String s, int min, int max) throws ValidationException {
        if (s.length() < min || s.length() > max)
            throw new ValidationException();
    }

    public static void email(String s) throws ValidationException {
        if(!EMAIL_PATTERN.matcher(s).matches())
            throw new ValidationException();  
    }
    
    public static void lengthOrEmpty(String s, int min, int max) throws ValidationException {
        empty(s);
        length(s, min, max);
    }
    
    public static class User {

        private User() {}
        
        public static void nickExists(String nick) throws ValidationException {
            if (UserManager.fieldExists("nick", nick))
                throw new ValidationException();
        }

        public static void emailExists(String email) throws ValidationException {
            if (UserManager.fieldExists("email", email))
                throw new ValidationException();
        }
    }

}