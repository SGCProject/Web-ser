package service;

import database.Jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

@Path("/Crime")
public class Crime {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Crime.class);

    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @POST
    public String query() {
        try {            
            JSONArray records = new JSONArray();
Map<String, String> map;

            Connection connect = Jdbc.getConnect();
            Statement stmt = connect.createStatement();
            String sql = "SELECT * FROM crime";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                map = new LinkedHashMap<String, String>();
                map.put("pk", rs.getLong("pk") + "");
                map.put("crime_id", rs.getString("crime_id"));
                map.put("crime_date", rs.getString("crime_date"));
                map.put("crime_lat", rs.getString("crime_lat"));
                map.put("crime_lng", rs.getString("crime_lng"));
                map.put("crime_detail", rs.getString("crime_detail"));
                map.put("crime_level", rs.getString("crime_level"));
                map.put("area_pk", rs.getString("area_pk"));
                map.put("type_pk", rs.getString("type_pk"));
                map.put("place_pk", rs.getString("place_pk"));
                map.put("user_pk", rs.getString("user_pk"));
                map.put("time_pk", rs.getString("time_pk"));
                records.put(map);
            }
            rs.close();
            connect.close();
            
           JSONObject js = new JSONObject();
           js.put("Result", "OK");
           js.put("Records", records);
                       
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
