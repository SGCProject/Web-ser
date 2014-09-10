package service;

public class UserSession {
    UserSession us;
    
    private UserSession() {
        
    }
    
    public UserSession getInstance() {
        if (us == null) {
            us = new UserSession();
        }
        return us;
    }
    
}
