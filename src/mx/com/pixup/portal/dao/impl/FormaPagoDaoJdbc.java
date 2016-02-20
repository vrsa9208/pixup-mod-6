package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.FormaPagoDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.com.pixup.portal.dao.db.ConexionPool;
import mx.com.pixup.portal.model.FormaPago;

/**
 *
 * @author JAVA-07
 */
public class FormaPagoDaoJdbc implements FormaPagoDao {

    
    public FormaPagoDaoJdbc(){
        
    }
    
    @Override
    public FormaPago insertFormaPago(FormaPago formaPago) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into forma_pago (descripcion) values (?)";

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, formaPago.getDescripcion());

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            formaPago.setId(resultSet.getInt(1));
            
            return formaPago;
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
        return null;
    }

    @Override
    public List<FormaPago> findAllFormaPagos() {

        String sql = "select * from forma_pago";
        List<FormaPago> formaPagos = new ArrayList<FormaPago>();
        
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                FormaPago temp = new FormaPago();
                temp.setId(resultSet.getInt("id"));
                temp.setDescripcion(resultSet.getString("descripcion"));
                formaPagos.add(temp);
            }

            
        } catch (SQLException e) {
            return null;
        }
        return formaPagos;

    }

    @Override
    public FormaPago findById(Integer id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FormaPago updateFormaPago(FormaPago formaPago){
        String sql = "update forma_pago set descripcion = ? where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(2, formaPago.getId());
            preparedStatement.setString(1, formaPago.getDescripcion());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return formaPago;

    }

    @Override
    public void deleteFormaPago(FormaPago formaPago) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from forma_pago where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, formaPago.getId());

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
