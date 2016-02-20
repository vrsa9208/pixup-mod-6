/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author JAVA-07
 */
public class Disco {
    private Integer id;
    private String titulo;
    private Date fechaLanzamiento;
    private Float precio;
    private Integer cantidadDisponible;
    private Idioma idioma;
    private Pais pais;
    private Disquera disquera;
    private GeneroMusical generoMusical;
    private Promocion promocion;
    private Iva iva;
    private String rutaImagen;
    private List<Artista> artistas;
    private List<Cancion> canciones;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the fechaLanzamiento
     */
    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    /**
     * @param fechaLanzamiento the fechaLanzamiento to set
     */
    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    /**
     * @return the precio
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * @return the cantidadDisponible
     */
    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    /**
     * @param cantidadDisponible the cantidadDisponible to set
     */
    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * @return the idioma
     */
    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * @return the disquera
     */
    public Disquera getDisquera() {
        return disquera;
    }

    /**
     * @param disquera the disquera to set
     */
    public void setDisquera(Disquera disquera) {
        this.disquera = disquera;
    }

    /**
     * @return the generoMusical
     */
    public GeneroMusical getGeneroMusical() {
        return generoMusical;
    }

    /**
     * @param generoMusical the generoMusical to set
     */
    public void setGeneroMusical(GeneroMusical generoMusical) {
        this.generoMusical = generoMusical;
    }

    /**
     * @return the promocion
     */
    public Promocion getPromocion() {
        return promocion;
    }

    /**
     * @param promocion the promocion to set
     */
    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    /**
     * @return the iva
     */
    public Iva getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(Iva iva) {
        this.iva = iva;
    }

    /**
     * @return the rutaImagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * @param rutaImagen the rutaImagen to set
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * @return the artistas
     */
    public List<Artista> getArtistas() {
        return artistas;
    }

    /**
     * @param artistas the artistas to set
     */
    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

    /**
     * @return the canciones
     */
    public List<Cancion> getCanciones() {
        return canciones;
    }

    /**
     * @param canciones the canciones to set
     */
    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    
}
