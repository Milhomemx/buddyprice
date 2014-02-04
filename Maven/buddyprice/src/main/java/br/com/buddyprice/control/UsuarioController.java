package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Return;
@Service
@Scope("prototype")
public class UsuarioController extends GenericControl<Usuario> {
        public UsuarioController() {
                super(Usuario.class);
        }
        
        @Override
        public Return save() {
        	entity.setPassword(EncryptUtils.encryptOnSHA512(entity.getPassword()));
        return super.save();
        }
        
        public Return update() {
        	
        return super.update();
        }
        
        
        
        public Return deactivate() {
        	entity.setActive(false);
        	
        	return super.update();
        }
        public Return nullit() {
        	Return ret = new Return(false);
        	
        	return ret;
        }
        
        public Return changePasswordUser() {
        	Return ret = new Return(true);
        	entity.setPassword(EncryptUtils.encryptOnSHA512((String) data.get("newPassword")));
        	ret.concat(update());
        	return ret;
        	}
        
        public Return loginUser() {
        Return regReturn = new Return(true);
        regReturn.concat(doAction("save"));
        if (regReturn.isValid()){
        	return regReturn;
                
                
        }
        return regReturn;
        
}
}