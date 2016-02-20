package mx.com.pixup.portal.dao.interfaces;

import java.util.List;
import mx.com.pixup.portal.model.FormaPago;

/**
 *
 * @author JAVA-07
 */
public interface FormaPagoDao {
    
    FormaPago insertFormaPago(FormaPago formaPago);
    
    List<FormaPago> findAllFormaPagos();
    
    FormaPago findById(Integer id);
    
    FormaPago updateFormaPago(FormaPago formaPago);
    
    void deleteFormaPago(FormaPago formaPago);
    
    
}
