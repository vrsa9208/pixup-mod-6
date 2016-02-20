package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Artista;

/**
 *
 * @author JAVA-07
 */
public interface ArtistaDao {
    
    Artista insertArtista(Artista artista);
    
    List<Artista> findAllArtistas();
    
    Artista findById(Integer id);
    
    Artista updateArtista(Artista artista);
    
    void deleteArtista(Artista artista);
    
    
}
