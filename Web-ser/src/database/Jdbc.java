package database;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
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

    private static void createConnetion() {
        try {
            Context envContext = new InitialContext();
            Context initContext = (Context) envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) initContext.lookup("jdbc/MySQL");
            con = ds.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JdbcRowSet getJrs() {
        try {
            createConnetion();
            jrs = new JdbcRowSetImpl(con);

            return jrs;
        } catch (SQLException ex) {
            log.error(ex.toString());
            Logger.getLogger(Jdbc.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static Connection getConnect() {
        createConnetion();
        return con;
    }
}
