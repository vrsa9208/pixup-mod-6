/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.Disquera;

/**
 *
 * @author JAVA-13
 */
public interface DisqueraDao {
    
    Disquera insertDisquera(Disquera disquera);
    Disquera updateDisquera(Disquera disquera);
    void deleteDisquera(Disquera disquera);
    List<Disquera> findAllDisqueras();
    Disquera findById(Integer idDisquera);
    
}
