package mx.com.pixup.portal.model;

import java.util.List;
//vrsa9208 was here
/**
 *
 * @author rodvazqu
 */
public class Artista {
    private Integer id;
    private String nombreArtistico;
    private String descripcion;
    private List<Disco> discos;

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
     * @return the nombreArtistico
     */
    public String getNombreArtistico() {
        return nombreArtistico;
    }

    /**
     * @param nombreArtistico the nombreArtistico to set
     */
    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the discos
     */
    public List<Disco> getDiscos() {
        return discos;
    }

    /**
     * @param discos the discos to set
     */
    public void setDiscos(List<Disco> discos) {
        this.discos = discos;
    }
    
}
