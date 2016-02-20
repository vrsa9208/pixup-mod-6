/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Cancion;

/**
 *
 * @author mflores
 */
public interface CancionDao {
    Cancion insertCancion(Cancion cancion);
    List<Cancion> findAllCanciones();
    Cancion findById(Integer id);
    Cancion updateCancion(Cancion cancion);
    void deleteCancion(Cancion cancion);
    
}
