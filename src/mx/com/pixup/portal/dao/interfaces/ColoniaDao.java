/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Colonia;

/**
 *
 * @author mflores
 */
public interface ColoniaDao {
    Colonia insertColonia(Colonia colonia);
    List<Colonia> findAllColonias();
    Colonia findById(Integer id);
    Colonia updateColonia(Colonia colonia);
    void deleteColonia(Colonia colonia);
    
}
