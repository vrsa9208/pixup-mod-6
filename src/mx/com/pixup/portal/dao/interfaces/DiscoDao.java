/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Disco;

/**
 *
 * @author daniel
 */
public interface DiscoDao {
    Disco insertDisco(Disco disco);
    Disco updateDisco(Disco disco);
    void deleteDisco(Disco disco);
    List<Disco> findAllDiscos();
    Disco findById(Integer idDisco);
}
