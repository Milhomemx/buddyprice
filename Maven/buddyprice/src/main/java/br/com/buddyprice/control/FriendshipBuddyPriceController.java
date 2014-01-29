package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Relacionamento;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
@Service
@Scope("prototype")
public class FriendshipBuddyPriceController extends GenericControl<Relacionamento> {
        public FriendshipBuddyPriceController() {
                super(Relacionamento.class);
        }
        
        public Return InsertFriendship() {
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