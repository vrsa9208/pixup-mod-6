
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.GeneroMusical;

/**
 *
 * @author JAVA-07
 */
public interface GeneroMusicalDao {
    
    GeneroMusical insertGeneroMusical(GeneroMusical generoMusical);
    
    List<GeneroMusical> findAllGenerosMusicales();
    
    GeneroMusical findById(Integer id);
    
    GeneroMusical updateGeneroMusical(GeneroMusical generoMusical);
    
    void deleteGeneroMusical(GeneroMusical generoMusical);
    
    
}
