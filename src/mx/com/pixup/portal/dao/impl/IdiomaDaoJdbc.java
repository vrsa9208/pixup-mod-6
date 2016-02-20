/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.IdiomaDao;
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
import mx.com.pixup.portal.model.Idioma;

/**
 *
 * @author JAVA-07
 */
public class IdiomaDaoJdbc implements IdiomaDao {


    public IdiomaDaoJdbc(){

    }
    
    @Override
    public Idioma insertIdioma(Idioma idioma) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into idioma ("
                + "descripcion"
                + ") "
                + "values (?)";

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, idioma.getDescripcion());
            //preparedStatement.setNull(1, java.sql.Types.NULL);

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            idioma.setId(resultSet.getInt(1));
            
            return idioma;
        } catch (SQLException ex) {
            Logger.getLogger(IdiomaDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Idioma> findAllIdiomas() {

        String sql = "select * from idioma";
        List<Idioma> idiomas = new ArrayList<>();
        
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                Idioma temp = new Idioma();
                temp.setId(resultSet.getInt("id"));
                temp.setDescripcion(resultSet.getString("descripcion"));
                idiomas.add(temp);
            }

            
        } catch (SQLException e) {
            return null;
        }
        return idiomas;

    }

    @Override
    public Idioma findById(Integer id) {
        String sql = "SELECT * FROM idioma"
                + " WHERE id = ?";
        Idioma idiomas = new Idioma();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            idiomas.setId(resultSet.getInt("id"));
            idiomas.setDescripcion(resultSet.getString("descripcion"));
            
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
        return idiomas;
    }

    @Override
    public Idioma updateIdioma(Idioma idioma){
        String sql = "update idioma set descripcion = ? where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(2, idioma.getId());
            preparedStatement.setString(1, idioma.getDescripcion());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return idioma;

    }

    @Override
    public void deleteIdioma(Idioma idioma) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "delete from idioma where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idioma.getId());

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
