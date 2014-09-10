package tmp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/callservice")
public interface CdgDltMobileService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerUser")
	public String registerUser(@FormParam("idNo") String idNo, @FormParam("password") String password) throws Exception;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerUser2")
	public String registerUser2(@QueryParam("idNo") String idNo, @QueryParam("password") String password) throws Exception;
	
}
