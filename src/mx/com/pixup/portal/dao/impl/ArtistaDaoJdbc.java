/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.ArtistaDao;
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
//import mx.com.pixup.portal.db.DBConecta;
import mx.com.pixup.portal.model.Artista;

/**
 *
 * @author JAVA-07
 */
public class ArtistaDaoJdbc implements ArtistaDao {

    public ArtistaDaoJdbc(){
        
    }
    
    @Override
    public Artista insertArtista(Artista artista) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder sql = new StringBuilder("insert into artista (");
        sql.append("nombre_artistico,");
        sql.append("descripcion) ");
        sql.append("values (?,?)");

        try {
            //connection = DBConecta.getConnection();
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, artista.getNombreArtistico());
            if(artista.getDescripcion() != null && artista.getDescripcion().length()>0){
                preparedStatement.setString(2, artista.getDescripcion());
            }else{
                preparedStatement.setNull(2, java.sql.Types.NULL);
            }

            preparedStatement.execute();

            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            artista.setId(resultSet.getInt(1));
            
            return artista;
        } catch (SQLException ex) {
            Logger.getLogger(ArtistaDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionPool.getInstance().cierraFlujo(resultSet);
            ConexionPool.getInstance().cierraFlujo(preparedStatement);
            ConexionPool.getInstance().cierraFlujo(connection);
        }
        return null;
    }

    @Override
    public List<Artista> findAllArtistas() {

        String sql = "select * from artista";
        List<Artista> artistas = new ArrayList<>();
        
        //try (Connection connection = DBConecta.getConnection();
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                
            while (resultSet.next()) {
                Artista temp = new Artista();
                temp.setId(resultSet.getInt("id"));
                temp.setNombreArtistico(resultSet.getString("nombre_artistico"));
                temp.setDescripcion(resultSet.getString("descripcion"));
                artistas.add(temp);
            }
        } catch (SQLException e) {
            return null;
        }
        return artistas;

    }

    @Override
    public Artista findById(Integer id) {
        String sql = "SELECT * FROM artista"
                + " WHERE id = ?";
        Artista artista = new Artista();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            artista.setId(resultSet.getInt("id"));
            artista.setNombreArtistico(resultSet.getString("nombre_artistico"));
            artista.setDescripcion(resultSet.getString("descripcion"));
            
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
        return artista;
    }

    @Override
    public Artista updateArtista(Artista artista){
        String sql = "update artista set "
                + "nombre_artistico = ?"
                + ",descripcion = ?"
                + " where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, artista.getNombreArtistico());
            if(artista.getDescripcion()!=null && artista.getDescripcion().length() > 0){
                preparedStatement.setString(2, artista.getDescripcion());
            }else{
                preparedStatement.setNull(2, java.sql.Types.NULL);
            }
            
            preparedStatement.setInt(3, artista.getId());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            return null;
        }
        return artista;

    }

    @Override
    public void deleteArtista(Artista artista) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from artista where id = ?";

        try {
            //connection = DBConecta.getConnection();
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, artista.getId());

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
