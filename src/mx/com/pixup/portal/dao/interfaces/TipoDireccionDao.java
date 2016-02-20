/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.TipoDireccion;

/**
 *
 * @author mflores
 */
public interface TipoDireccionDao {
    TipoDireccion insertTipoDireccion(TipoDireccion tipo);
    List<TipoDireccion> findAllTipoDireccion();
    TipoDireccion findById(Integer id);
    TipoDireccion updateTipoDireccion(TipoDireccion tipo);
    void deleteTipoDireccion(TipoDireccion tipo);
    
}
