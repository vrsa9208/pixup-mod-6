/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Municipio;

/**
 *
 * @author mflores
 */
public interface MunicipioDao {
    Municipio insertMunicipio(Municipio municipio);
    List<Municipio> findAllMunicipios();
    Municipio findById(Integer id);
    Municipio updateMunicipio(Municipio municipio);
    void deleteMunicipio(Municipio municipio);
    
}
