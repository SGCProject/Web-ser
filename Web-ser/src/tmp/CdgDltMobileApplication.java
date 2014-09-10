package tmp;

import java.util.*;

import javax.ws.rs.core.Application;

public class CdgDltMobileApplication extends Application {
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
        return classes;
    }

}
