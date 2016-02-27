/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.impl;

import mx.com.pixup.portal.dao.interfaces.DiscoDao;
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
import mx.com.pixup.portal.model.Artista;
import mx.com.pixup.portal.model.Cancion;
import mx.com.pixup.portal.model.Disco;
import mx.com.pixup.portal.model.Disquera;
import mx.com.pixup.portal.model.GeneroMusical;
import mx.com.pixup.portal.model.Idioma;
import mx.com.pixup.portal.model.Iva;
import mx.com.pixup.portal.model.Pais;
import mx.com.pixup.portal.model.Promocion;

/**
 *
 * @author JAVA-07
 */
public class DiscoDaoJdbc implements DiscoDao {

    public DiscoDaoJdbc(){
    }
    
    @Override
    public Disco insertDisco(Disco disco) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String sql = "insert into disco ("
                + "titulo,"
                + "fecha_lanzamiento,"
                + "precio,"
                + "cantidad_disponible,"
                + "id_idioma,"
                + "id_pais,"
                + "id_disquera,"
                + "id_genero_musical,"
                + "id_promocion,"
                + "id_iva,"
                + "ruta_imagen"
                + ") "
                + "values (?,?,?,?,?,?,?,?,?,?,?)";
        
        String sqlArtistas = "INSERT INTO disco_artista ("
                + "id_disco,"
                + "id_artista"
                + ") "
                + "values (?,?)";
        
        String sqlCanciones = "INSERT INTO disco_cancion ("
                + "id_disco,"
                + "id_cancion"
                + ") "
                + "values (?,?)";
        

        try {
            connection = ConexionPool.getInstance().getConnection();

            connection.setAutoCommit(false);
            
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, disco.getTitulo());
            preparedStatement.setDate(2, disco.getFechaLanzamiento());
            preparedStatement.setFloat(3, disco.getPrecio());
            preparedStatement.setInt(4, disco.getCantidadDisponible());
            preparedStatement.setInt(5, disco.getIdioma().getId());
            preparedStatement.setInt(6, disco.getPais().getId());
            preparedStatement.setInt(7, disco.getDisquera().getId());
            preparedStatement.setInt(8, disco.getGeneroMusical().getId());
            if(disco.getPromocion() != null && disco.getPromocion().getId() > 0){
                preparedStatement.setInt(9, disco.getPromocion().getId());
            } else {
                preparedStatement.setNull(9, java.sql.Types.NULL);
            }
            preparedStatement.setInt(10, disco.getIva().getId());
            //preparedStatement.setNull(1, java.sql.Types.NULL);
            if(disco.getRutaImagen()!= null && disco.getRutaImagen().length() > 0){
                preparedStatement.setString(11, disco.getRutaImagen());
            } else {
                preparedStatement.setNull(11, java.sql.Types.NULL);
            }
            
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            
            resultSet.next();
            disco.setId(resultSet.getInt(1));
            
            //Añadiendo relacion de artistas a la tabla.
            List<Artista> artistas = disco.getArtistas();
            for(Artista artista : artistas){
                preparedStatement = connection.prepareStatement(sqlArtistas);
                preparedStatement.setInt(1, disco.getId());
                preparedStatement.setInt(2, artista.getId());
                preparedStatement.execute();
            }
            
            List<Cancion> canciones = disco.getCanciones();
            for(Cancion cancion : canciones){
                preparedStatement = connection.prepareStatement(sqlCanciones);
                preparedStatement.setInt(1, disco.getId());
                preparedStatement.setInt(2, cancion.getId());
                preparedStatement.execute();
            }
            connection.commit();
            
            return disco;
        } catch (SQLException ex) {
            Logger.getLogger(DiscoDaoJdbc.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Disco> findAllDiscos() {
        
        String sql = "select * from disco"
                + " JOIN idioma ON idioma.id = disco.id_idioma"
                + " JOIN pais ON pais.id = disco.id_pais"
                + " JOIN disquera ON disquera.id = disco.id_disquera"
                + " JOIN genero_musical ON genero_musical.id = disco.id_genero_musical"
                + " LEFT JOIN promocion ON promocion.id = disco.id_promocion"
                + " JOIN iva ON iva.id = disco.id_iva"
                + "";
        List<Disco> discos = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Disco temp = new Disco();
                temp.setId(resultSet.getInt("id"));
                temp.setTitulo(resultSet.getString("titulo"));
                temp.setFechaLanzamiento(resultSet.getDate("fecha_lanzamiento"));
                temp.setPrecio(resultSet.getFloat("precio"));
                temp.setCantidadDisponible(resultSet.getInt("cantidad_disponible"));
                Idioma idioma = new Idioma();
                idioma.setId(resultSet.getInt("id_idioma"));
                idioma.setDescripcion(resultSet.getString("idioma.descripcion"));
                temp.setIdioma(idioma);
                Pais pais = new Pais();
                pais.setId(resultSet.getInt("id_pais"));
                pais.setNombre(resultSet.getString("pais.nombre"));
                temp.setPais(pais);
                Disquera disquera = new Disquera();
                disquera.setId(resultSet.getInt("id_disquera"));
                disquera.setNombre(resultSet.getString("disquera.nombre"));
                temp.setDisquera(disquera);
                GeneroMusical generoMusical = new GeneroMusical();
                generoMusical.setId(resultSet.getInt("id_genero_musical"));
                generoMusical.setNombre(resultSet.getString("genero_musical.nombre"));
                temp.setGeneroMusical(generoMusical);
                Promocion promocion = new Promocion();
                promocion.setId(resultSet.getInt("id_promocion"));
                promocion.setFechaInicio(resultSet.getTimestamp("promocion.fecha_inicio"));
                promocion.setFechaFin(resultSet.getTimestamp("promocion.fecha_fin"));
                promocion.setNombre(resultSet.getString("promocion.nombre"));
                promocion.setPorcentajeDescuento(resultSet.getFloat("promocion.porcentaje_descuento"));
                promocion.setVigente(resultSet.getBoolean("promocion.vigente"));
                
                temp.setPromocion(promocion);
                Iva iva = new Iva();
                iva.setId(resultSet.getInt("id_iva"));
                iva.setPorcentaje(resultSet.getFloat("iva.porcentaje"));
                iva.setFechaInicio(resultSet.getDate("iva.fecha_inicio"));
                iva.setVigente(resultSet.getBoolean("iva.vigente"));
                
                temp.setIva(iva);
                temp.setRutaImagen(resultSet.getString("ruta_imagen"));
                
                String sqlArtista = "SELECT artista.* from artista"
                        + " JOIN disco_artista ON disco_artista.id_artista = artista.id"
                        + " WHERE disco_artista.id_disco = ?";
                preparedStatement = connection.prepareStatement(sqlArtista);
                preparedStatement.setInt(1, temp.getId());
                rs = preparedStatement.executeQuery();
                List<Artista> artistas = new ArrayList<>();
                while (rs.next()) {
                    Artista artista = new Artista();
                    artista.setId(rs.getInt("id"));
                    artista.setNombreArtistico(rs.getString("nombre_artistico"));
                    artista.setDescripcion(rs.getString("descripcion"));
                    artistas.add(artista);
                }
                temp.setArtistas(artistas);
                
                String sqlCancion = "SELECT cancion.* from cancion"
                        + " JOIN disco_cancion ON disco_cancion.id_cancion = cancion.id"
                        + " WHERE disco_cancion.id_disco = ?";
                preparedStatement = connection.prepareStatement(sqlCancion);
                preparedStatement.setInt(1, temp.getId());
                rs = preparedStatement.executeQuery();
                List<Cancion> canciones = new ArrayList<>();
                while (rs.next()) {
                    Cancion cancion = new Cancion();
                    cancion.setId(rs.getInt("id"));
                    cancion.setNombre(rs.getString("nombre"));
                    cancion.setDuracion(rs.getTime("duracion"));
                    canciones.add(cancion);
                }
                temp.setCanciones(canciones);
                discos.add(temp);
            }

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            if (rs != null){try{rs.close();}catch(SQLException sqle){}}
            if (resultSet != null){try{resultSet.close();}catch(SQLException sqle){}}
            if (preparedStatement != null){try{preparedStatement.close();}catch(SQLException sqle){}}
        }
        return discos;

    }

    @Override
    public Disco findById(Integer id) {
        String sql = "SELECT * FROM disco"
                + " JOIN idioma ON idioma.id = disco.id_idioma"
                + " JOIN pais ON pais.id = disco.id_pais"
                + " JOIN disquera ON disquera.id = disco.id_disquera"
                + " JOIN genero_musical ON genero_musical.id = disco.id_genero_musical"
                + " LEFT JOIN promocion ON promocion.id = disco.id_promocion"
                + " JOIN iva ON iva.id = disco.id_iva"
                + " WHERE disco.id = ?";
        Disco disco = new Disco();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet rs = null;
        try (Connection connection = ConexionPool.getInstance().getConnection();) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            disco.setId(resultSet.getInt("id"));
            disco.setTitulo(resultSet.getString("titulo"));
            disco.setFechaLanzamiento(resultSet.getDate("fecha_lanzamiento"));
            disco.setPrecio(resultSet.getFloat("precio"));
            disco.setCantidadDisponible(resultSet.getInt("cantidad_disponible"));
            Idioma idioma = new Idioma();
            idioma.setId(resultSet.getInt("id_idioma"));
            idioma.setDescripcion(resultSet.getString("idioma.descripcion"));
            disco.setIdioma(idioma);
            Pais pais = new Pais();
            pais.setId(resultSet.getInt("id_pais"));
            pais.setNombre(resultSet.getString("pais.nombre"));
            disco.setPais(pais);
            Disquera disquera = new Disquera();
            disquera.setId(resultSet.getInt("id_disquera"));
            disquera.setNombre(resultSet.getString("disquera.nombre"));
            disco.setDisquera(disquera);
            GeneroMusical generoMusical = new GeneroMusical();
            generoMusical.setId(resultSet.getInt("id_genero_musical"));
            generoMusical.setNombre(resultSet.getString("genero_musical.nombre"));
            disco.setGeneroMusical(generoMusical);
            Promocion promocion = new Promocion();
            promocion.setId(resultSet.getInt("id_promocion"));
            promocion.setFechaInicio(resultSet.getTimestamp("promocion.fecha_inicio"));
            promocion.setFechaFin(resultSet.getTimestamp("promocion.fecha_fin"));
            promocion.setNombre(resultSet.getString("promocion.nombre"));
            promocion.setPorcentajeDescuento(resultSet.getFloat("promocion.porcentaje_descuento"));
            promocion.setVigente(resultSet.getBoolean("promocion.vigente"));
            disco.setPromocion(promocion);
            Iva iva = new Iva();
            iva.setId(resultSet.getInt("id_iva"));
            iva.setPorcentaje(resultSet.getFloat("iva.porcentaje"));
            iva.setFechaInicio(resultSet.getDate("iva.fecha_inicio"));
            iva.setVigente(resultSet.getBoolean("iva.vigente"));
            disco.setIva(iva);
            disco.setRutaImagen(resultSet.getString("ruta_imagen"));
            String sqlArtista = "SELECT artista.* from artista"
                    + " JOIN disco_artista ON disco_artista.id_artista = artista.id"
                    + " WHERE disco_artista.id_disco = ?";
            preparedStatement = connection.prepareStatement(sqlArtista);
            preparedStatement.setInt(1, disco.getId());
            rs = preparedStatement.executeQuery();
            List<Artista> artistas = new ArrayList<>();
            while (rs.next()) {
                Artista artista = new Artista();
                artista.setId(rs.getInt("id"));
                artista.setNombreArtistico(rs.getString("nombre_artistico"));
                artista.setDescripcion(rs.getString("descripcion"));
                artistas.add(artista);
            }
            disco.setArtistas(artistas);

            String sqlCancion = "SELECT cancion.* from cancion"
                    + " JOIN disco_cancion ON disco_cancion.id_cancion = cancion.id"
                    + " WHERE disco_cancion.id_disco = ?";
            preparedStatement = connection.prepareStatement(sqlCancion);
            preparedStatement.setInt(1, disco.getId());
            rs = preparedStatement.executeQuery();
            List<Cancion> canciones = new ArrayList<>();
            while (rs.next()) {
                Cancion cancion = new Cancion();
                cancion.setId(rs.getInt("id"));
                cancion.setNombre(rs.getString("nombre"));
                cancion.setDuracion(rs.getTime("duracion"));
                canciones.add(cancion);
            }
            disco.setCanciones(canciones);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
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
        return disco;
    }

    @Override
    public Disco updateDisco(Disco disco){
        String sql = "update disco set "
                + "titulo = ?, "
                + "fecha_lanzamiento = ?, "
                + "cantidad_disponible = ?, "
                + "id_idioma = ?, "
                + "id_pais = ?, "
                + "id_disquera = ?, "
                + "id_genero_musical = ?, "
                + "id_promocion = ?, "
                + "id_iva = ?, "
                + "ruta_imagen = ? "
                + "where id = ?";
        try (Connection connection = ConexionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, disco.getTitulo());
            preparedStatement.setDate(2, disco.getFechaLanzamiento());
            preparedStatement.setInt(3, disco.getCantidadDisponible());
            preparedStatement.setInt(4, disco.getIdioma().getId());
            preparedStatement.setInt(5, disco.getPais().getId());
            preparedStatement.setInt(6, disco.getDisquera().getId());
            preparedStatement.setInt(7, disco.getGeneroMusical().getId());
            if(disco.getPromocion() != null && disco.getPromocion().getId() > 0){
                preparedStatement.setInt(8, disco.getPromocion().getId());
            } else {
                preparedStatement.setNull(8, java.sql.Types.NULL);
            }
            preparedStatement.setInt(9, disco.getIva().getId());
            if(disco.getRutaImagen()!= null && disco.getRutaImagen().length() > 0){
                preparedStatement.setString(10, disco.getRutaImagen());
            } else {
                preparedStatement.setNull(10, java.sql.Types.NULL);
            }
            preparedStatement.setInt(11, disco.getId());
            
            preparedStatement.execute();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return disco;

    }

    @Override
    public void deleteDisco(Disco disco) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        String sql = "delete from disco where id = ?";

        try {
            connection = ConexionPool.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, disco.getId());
            
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    
    public static void main(String[] args) {
        DiscoDaoJdbc discoDao = new DiscoDaoJdbc();
        List<Disco> discos = discoDao.findAllDiscos();
        for(Disco disco: discos){
            System.out.println("id: "+disco.getId()+", Titulo: "+disco.getTitulo());
            System.out.println("artistas: ");
            for (Artista artista : disco.getArtistas()){
                System.out.println(artista.getNombreArtistico());
            }
        }
    }

}
