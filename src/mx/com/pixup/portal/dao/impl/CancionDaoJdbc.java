/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.CancionDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.com.pixup.portal.dao.db.ConexionPool;
import mx.com.pixup.portal.model.Cancion;

/**
 *
 * @author JAVA-07
 */
public class CancionDaoJdbc implements CancionDao {

    public CancionDaoJdbc(){
        
    }
    
    @Override
    public Cancion insertCancion(Cancion cancion) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into cancion ("
                + "nombre,"
                + "duracion"
                + ") "
                + "values (?,?)";

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cancion.getNombre());
            preparedStatement.setTime(2, cancion.getDuracion());
            
            //preparedStatement.setNull(1, java.sql.Types.NULL);

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            cancion.setId(resultSet.getInt(1));
            
            return cancion;
        } catch (SQLException ex) {
            Logger.getLogger(CancionDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }

        }
        return null;
    }

    @Override
    public List<Cancion> findAllCanciones() {

        String sql = "select * from cancion";
        List<Cancion> canciones = new ArrayList<>();
        
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                Cancion temp = new Cancion();
                temp.setId(resultSet.getInt("id"));
                temp.setNombre(resultSet.getString("nombre"));
                temp.setDuracion(resultSet.getTime("duracion"));
                canciones.add(temp);
            }
        } catch (SQLException e) {
            return null;
        }
        return canciones;

    }

    @Override
    public Cancion findById(Integer id) {
        String sql = "SELECT * FROM cancion"
                + " WHERE id = ?";
        Cancion cancion = new Cancion();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            cancion.setId(resultSet.getInt("id"));
            cancion.setNombre(resultSet.getString("nombre"));
            cancion.setDuracion(resultSet.getTime("duracion"));
            
        } catch (SQLException e) {
            return null;
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            
        }
        return cancion;
    }

    @Override
    public Cancion updateCancion(Cancion cancion){
        String sql = "update cancion set "
                + "nombre = ?"
                + ",duracion = ?"
                + " where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, cancion.getNombre());
            preparedStatement.setTime(2, cancion.getDuracion());
            
            preparedStatement.setInt(3, cancion.getId());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return cancion;

    }

    @Override
    public void deleteCancion(Cancion cancion) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from cancion where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cancion.getId());

            preparedStatement.execute();

        } catch (SQLException e) {

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }

        }

    }

}
