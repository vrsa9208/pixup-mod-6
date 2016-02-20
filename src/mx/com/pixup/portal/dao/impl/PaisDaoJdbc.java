/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.PaisDao;
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
import mx.com.pixup.portal.model.Pais;

/**
 *
 * @author JAVA-07
 */
public class PaisDaoJdbc implements PaisDao {


    public PaisDaoJdbc(){

    }
    
    @Override
    public Pais insertPais(Pais pais) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into pais ("
                + "nombre"
                + ") "
                + "values (?)";

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pais.getNombre());
            //preparedStatement.setNull(1, java.sql.Types.NULL);

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            pais.setId(resultSet.getInt(1));
            
            return pais;
        } catch (SQLException ex) {
            Logger.getLogger(PaisDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Pais> findAllPaises() {

        String sql = "select * from pais";
        List<Pais> paises = new ArrayList<>();
        
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                Pais temp = new Pais();
                temp.setId(resultSet.getInt("id"));
                temp.setNombre(resultSet.getString("nombre"));
                paises.add(temp);
            }

            
        } catch (SQLException e) {
            return null;
        }
        return paises;

    }

    @Override
    public Pais findById(Integer id) {
        String sql = "SELECT * FROM pais"
                + " WHERE id = ?";
        Pais pais = new Pais();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            pais.setId(resultSet.getInt("id"));
            pais.setNombre(resultSet.getString("nombre"));
            
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
        return pais;
    }

    @Override
    public Pais updatePais(Pais pais){
        String sql = "update pais set nombre = ? where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(2, pais.getId());
            preparedStatement.setString(1, pais.getNombre());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return pais;

    }

    @Override
    public void deletePais(Pais pais) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from pais where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pais.getId());

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
