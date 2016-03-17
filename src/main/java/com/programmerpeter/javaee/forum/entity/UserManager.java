package com.programmerpeter.javaee.forum.entity;

import com.programmerpeter.javaee.forum.utils.Database;
import com.programmerpeter.javaee.forum.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class UserManager {
    
    private UserManager() {}
    
    public static void register(String nick, String email, String password) {
        EntityManager entityManager = Database.getEntityManager();
        UserEntity user = new UserEntity();
        user.setNick(nick);
        user.setEmail(email);
        user.setPassword(password);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }
    
    public static UserEntity login(String nickOrEmail, String password) {
        UserEntity user;
        if (fieldExists("nick", nickOrEmail))
            user = getByField("nick", nickOrEmail).get(0);
        else if (fieldExists("email", nickOrEmail))
            user = getByField("email", nickOrEmail).get(0);
        else
            return null;
        
        String currentHash = user.getPasswordHash();
        user.setPassword(password);
        if (user.getPasswordHash().equals(currentHash))
            return user;
        else
            return null;
    }
    
    public static boolean fieldExists(String field, String value) {
        return !getByField(field, value).isEmpty();
    }
    
    private static List<UserEntity> getByField(String field, String value) {
        ArrayList<String> allowedFields = new ArrayList<>();
        allowedFields.add("nick");
        allowedFields.add("email");
        if (!allowedFields.contains(field))
            throw(new IllegalArgumentException("Unknown field!"));
        return Database.getEntityManager().createNamedQuery("User.findBy" + StringUtils.capitalize(field))
                .setParameter(field, value).getResultList();
    }
    
}
