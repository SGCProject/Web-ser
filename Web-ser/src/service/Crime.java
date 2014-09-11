package service;

import database.Jdbc;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JdbcRowSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

@Path("/Crime")
public class Crime {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Crime.class);

    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String query() {
        try {
            JSONArray js = new JSONArray();

            JdbcRowSet jrs = Jdbc.getJrs();
            jrs.setCommand("SELECT * FROM crime");
            jrs.execute();
            while (jrs.next()) {
                JSONObject jo = new JSONObject();
                jo.append("pk", jrs.getLong("pk"));
                jo.append("name", jrs.getString("name"));
                js.put(jo);
            }
            jrs.close();

            log.debug(js.toString());
            return js.toString();
        } catch (SQLException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        } catch (JSONException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String insert() {
        return "";
    }

    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String update() {
        return "";
    }

    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String delete(Long pk) {
        return "";
    }

}
