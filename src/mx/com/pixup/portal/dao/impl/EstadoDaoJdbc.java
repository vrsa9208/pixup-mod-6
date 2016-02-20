/*
 * To change this license header, choose License HeaderesultSet in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.com.pixup.portal.dao.interfaces.EstadoDao;
import mx.com.pixup.portal.dao.db.ConexionPool;
import mx.com.pixup.portal.model.Estado;

/**
 *
 * @author mflores
 */
public class EstadoDaoJdbc implements EstadoDao {

    @Override
    public Estado insertEstado(Estado estado) {
        ResultSet resultSet = null;
        String sql = "insert into estado(nombre) values (?)";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, estado.getNombre());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            estado.setId(resultSet.getInt(1));
            return estado;
        } catch (Exception e) {
            return null;
        }finally {
            if(resultSet != null) { try{ resultSet.close();} catch(Exception e){}}
        }
    }

    @Override
    public List<Estado> findAllEstados() {
        String sql = "select * from estado order by id";
        List<Estado> estados = new ArrayList<>();
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Estado estado = new Estado();
                estado.setId(resultSet.getInt(1));
                estado.setNombre(resultSet.getString(2));
                estados.add(estado);
            }
        } catch (Exception e) {
            return null;
        }
        return estados;
    }

    @Override
    public Estado findById(Integer id) {
        String sql = "SELECT * FROM estado WHERE id = ?";
        Estado estado = new Estado();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connectionection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connectionection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            estado.setId(resultSet.getInt("id"));
            estado.setNombre(resultSet.getString("nombre"));
            
        } catch (Exception e) {
            return null;
        }finally {
            if(resultSet != null) { try{ resultSet.close();} catch(Exception e){}}
        }
        return estado;
    }

    @Override
    public Estado updateEstado(Estado estado) {
        String sql = "update estado set nombre = ? where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, estado.getNombre());
            preparedStatement.setInt(2, estado.getId());
            preparedStatement.execute();
            return estado;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteEstado(Estado estado) {
        String sql = "delete from estado where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, estado.getId());
            preparedStatement.execute();
        } catch (Exception e) {
        }
    }    
}
