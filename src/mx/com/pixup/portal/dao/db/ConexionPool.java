/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author jorge
 */
public class ConexionPool {

    private static ConexionPool conexion;
    private DataSource ds;

    /**
     * Creates a new instance of Conexion
     */
    private ConexionPool() {
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            ds = (DataSource) envContext.lookup("jdbc/pixupweb");
        } catch (NamingException nex) {
            System.out.println("No se pudo abrir"
                    + " la base de datos" + nex.getMessage());
        }
    }

    public static ConexionPool getInstance() {
        if (conexion == null) {
            conexion = new ConexionPool();
        }
        return conexion;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    public void cierraFlujo(Object flujo) {
        if (flujo != null) {
            try {
                if (flujo instanceof Statement) {
                    ((Statement) flujo).close();
                } else if (flujo instanceof PreparedStatement) {
                    ((PreparedStatement) flujo).close();
                } else if (flujo instanceof CallableStatement) {
                    ((CallableStatement) flujo).close();
                } else if (flujo instanceof ResultSet) {
                    ((ResultSet) flujo).close();
                } else if (flujo instanceof Connection) {
                    ((Connection) flujo).close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

}
