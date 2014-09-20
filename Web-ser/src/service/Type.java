package service;

import database.Jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JdbcRowSet;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

@Path("/Type")
public class Type {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Type.class);
    private String sql;

    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @GET
    public String test() {
        return "ทดสอบ";
    }

    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @POST
    public String query() {
        JSONObject jo = new JSONObject();
        try {
            JSONArray records = new JSONArray();
            Map<String, String> map;

            JdbcRowSet jrs = Jdbc.getJrs();
            jrs.setCommand("SELECT * FROM type");
            jrs.execute();
            while (jrs.next()) {
                map = new LinkedHashMap<String, String>();
                map.put("pk", jrs.getLong("pk") + "");
                map.put("name", jrs.getString("name") + "");
                records.put(map);
            }

            jrs.close();

            jo.put("Result", "OK");
            jo.put("Records", records);

            return jo.toString();
        } catch (SQLException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        } catch (JSONException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @POST
    public String insert(@FormParam("name") String name) {
        try {
            JdbcRowSet jrs = Jdbc.getJrs();
            jrs.setCommand("SELECT * FROM type WHERE 1 = 2");
            jrs.execute();
            jrs.moveToInsertRow();
            jrs.updateString("name", name);
            jrs.insertRow();
            jrs.next();

            JSONObject jo = new JSONObject();
            JSONObject row = new JSONObject();

            row.put("pk", jrs.getLong("pk"));
            row.put("name", jrs.getString("name"));

            jo.put("Result", "OK");
            jo.put("Record", row);

            jrs.close();
            System.out.println(jo);
            return jo.toString();
        } catch (SQLException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return "false";
        } catch (JSONException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return "false";
        }
    }

    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @POST
    public String update(@FormParam("pk") Long pk, @FormParam("name") String name) {
        sql = "update type set name = ? where pk = ?;";
        try {
            Connection connect = Jdbc.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setLong(2, pk);
            pstmt.executeUpdate();
            pstmt.close();
            connect.close();
            return "{\"Result\":\"OK\"}";
        } catch (SQLException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return "{\"Result\":\"ERROR\"}";
        }

    }

    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @POST
    public String delete(@FormParam("pk") Long pk) {
        try {
            JdbcRowSet jrs = Jdbc.getJrs();
            jrs.setCommand("SELECT * FROM type WHERE pk = " + pk);
            jrs.execute();
            jrs.last();
            jrs.deleteRow();
            jrs.close();

            return query();
        } catch (SQLException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
            return "false";
        }
    }

}
