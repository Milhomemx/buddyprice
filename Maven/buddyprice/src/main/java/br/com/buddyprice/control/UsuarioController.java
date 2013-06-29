package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
@Service
@Scope("prototype")
public class UsuarioController extends GenericControl<Usuario> {
        public UsuarioController() {
                super();
                classEntity = Usuario.class;
        }
        
        public Return registerUser() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    //TODO email?
                    regReturn.addMessage(new Message(null, "Voce esta cadastrado! Seja bem vindo!"));
                    
                    
            }
            return regReturn;
            
    }public Return loginUser() {
        Return regReturn = new Return(true);
        regReturn.concat(doAction("save"));
        if (regReturn.isValid()){
                //TODO email?
                regReturn.addMessage(new Message(null, "Voce esta cadastrado! Seja bem vindo!"));
                
                
        }
        return regReturn;
        
}
}