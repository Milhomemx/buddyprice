package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Comentario;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
/**
 * @author Natan
 * Controlador da abstração Comentário. Gere todos os cenários relacionado à ação de comentar.
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class CommentController extends GenericControl<Comentario> {
        public CommentController() {
                super(Comentario.class);
        }
        
        /**
         * @return
         * Insere um comentário.
         */
        public Return InsertComment() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    regReturn.addMessage(new Message(null, "Comentario feito!"));
                    
                    
            }
            return regReturn;
            
    }
        @Override
        public Return save() {
        return super.save();
        }
        
        public Return update() {

        return super.update();
        }
        
        public Return nullit() {
        	Return ret = new Return(false);
        	
        	return ret;
        }
        
}