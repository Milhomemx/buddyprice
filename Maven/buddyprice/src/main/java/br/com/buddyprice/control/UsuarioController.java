package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.EncryptUtils;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
@Service
@Scope("prototype")
public class UsuarioController extends GenericControl<Usuario> {
        public UsuarioController() {
                super(Usuario.class);
        }
        
        public Return registerUser() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    //TODO email?
                    regReturn.addMessage(new Message(null, "Voce esta cadastrado! Seja bem vindo!"));
                    
                    
            }
            return regReturn;
            
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
        
        public Return loginUser() {
        Return regReturn = new Return(true);
        regReturn.concat(doAction("save"));
        if (regReturn.isValid()){
                //TODO email?
                regReturn.addMessage(new Message(null, "Voce esta logado! Seja bem vindo!"));
                
                
        }
        return regReturn;
        
}
}