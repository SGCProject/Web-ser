package webservice;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import service.*;


public class WebserviceApplication extends Application {
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Type.class);
        classes.add(Crime.class);
//        classes.add(ServiceType.class);
//        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
//        classes.add(CdgDltMobileServiceWS.class);
        return classes;
    }

}
