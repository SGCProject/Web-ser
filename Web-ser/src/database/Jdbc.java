package database;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.JdbcRowSet;
import org.slf4j.LoggerFactory;

public class Jdbc {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Jdbc.class);
    private static JdbcRowSet jrs = null;
    private static Connection con;

    private Jdbc() {
    }

    public static JdbcRowSet getJrs() {

        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/MySQL");
            con = ds.getConnection();

            jrs = new JdbcRowSetImpl(con);

            return jrs;
        } catch (NamingException ex) {
            log.error(ex.toString());
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            log.error(ex.toString());
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
