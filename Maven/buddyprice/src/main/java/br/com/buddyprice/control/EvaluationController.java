package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Avaliacao;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
/**
 * @author Natan
 * Controlador da abstração Avaliação. Gere todos os cenários relacionado à ação de avaliar.
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class EvaluationController extends GenericControl<Avaliacao> {
        public EvaluationController() {
                super(Avaliacao.class);
        }
        
        /**
         * @return
         * Insere uma avaliação.
         */
        public Return InsertEvaluation() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    regReturn.addMessage(new Message(null, "Oferta avaliada!"));
                    
                    
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