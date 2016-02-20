package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Idioma;

/**
 *
 * @author JAVA-07
 */
public interface IdiomaDao {
    
    Idioma insertIdioma(Idioma idioma);
    
    List<Idioma> findAllIdiomas();
    
    Idioma findById(Integer id);
    
    Idioma updateIdioma(Idioma idioma);
    
    void deleteIdioma(Idioma idioma);
    
    
}
