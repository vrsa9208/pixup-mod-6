package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.GeneroMusicalDao;
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
import mx.com.pixup.portal.model.GeneroMusical;

/**
 *
 * @author JAVA-07
 */
public class GeneroMusicalDaoJdbc implements GeneroMusicalDao {

    
    public GeneroMusicalDaoJdbc(){
    
    }
    
    @Override
    public GeneroMusical insertGeneroMusical(GeneroMusical generoMusical) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into genero_musical ("
                + "nombre"
                + ") "
                + "values (?)";

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, generoMusical.getNombre());
            //preparedStatement.setNull(1, java.sql.Types.NULL);

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            generoMusical.setId(resultSet.getInt(1));
            
            return generoMusical;
        } catch (SQLException ex) {
            Logger.getLogger(GeneroMusicalDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<GeneroMusical> findAllGenerosMusicales() {

        String sql = "select * from genero_musical";
        List<GeneroMusical> generosMusicales = new ArrayList<>();
        
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                GeneroMusical temp = new GeneroMusical();
                temp.setId(resultSet.getInt("id"));
                temp.setNombre(resultSet.getString("nombre"));
                generosMusicales.add(temp);
            }

            
        } catch (SQLException e) {
            return null;
        }
        return generosMusicales;

    }

    @Override
    public GeneroMusical findById(Integer id) {
        String sql = "SELECT * FROM genero_musical"
                + " WHERE id = ?";
        GeneroMusical generoMusical = new GeneroMusical();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            generoMusical.setId(resultSet.getInt("id"));
            generoMusical.setNombre(resultSet.getString("nombre"));
            
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
        return generoMusical;
    }

    @Override
    public GeneroMusical updateGeneroMusical(GeneroMusical generoMusical){
        String sql = "update genero_musical set nombre = ? where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(2, generoMusical.getId());
            preparedStatement.setString(1, generoMusical.getNombre());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return generoMusical;

    }

    @Override
    public void deleteGeneroMusical(GeneroMusical generoMusical) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from genero_musical where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, generoMusical.getId());

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
