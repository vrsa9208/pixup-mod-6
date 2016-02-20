package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Pais;

/**
 *
 * @author JAVA-07
 */
public interface PaisDao {
    
    Pais insertPais(Pais pais);
    
    List<Pais> findAllPaises();
    
    Pais findById(Integer id);
    
    Pais updatePais(Pais pais);
    
    void deletePais(Pais pais);
    
    
}
