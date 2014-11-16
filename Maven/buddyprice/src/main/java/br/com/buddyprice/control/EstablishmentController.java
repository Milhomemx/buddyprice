package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
/**
 * @author Natan
 * Controlador da abstração Estabelecimento. Gere todos os cenários relacionado à gestão do estabelecimento (cadastro de estabelecimentos, manutenção de estabelecimentos, etc).
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class EstablishmentController extends GenericControl<Estabelecimento> {
        public EstablishmentController() {
                super(Estabelecimento.class);
        }


		/**
		 * @return
		 * Insere um novo estabelecimento.
		 */
		public Return InsertEstablishment() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    regReturn.addMessage(new Message(null, "Estabelecimento cadastrado!"));
                    
                    
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
        
        /**
         * @param est
         * @return
         * Retorna o ID de um estabelecimento pela pesquisa de seu respectivo nome.
         */
        public Return getEstablishmentId(Estabelecimento est){
        	String sql = "from Estabelecimento where nome = '"+est.getNome()+"'";
        	data.put("sql", sql);
        	
        	return searchByHQL();
        }
        
    	/**
    	 * @return
    	 * Pesquisa por estabelecimentos. Ignora maiúsculas/minúsculas.
    	 * A palavra chave de pesquisa pode ser apenas um pedaço da String armazenada.
    	 */
    	public Return searchEstablishments(){
    		Return ret = new Return(true);
    		String searchKey = (String) data.get("searchField");
    		if((searchKey == null || searchKey.isEmpty()) || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
    			return ret;
    		}
    		String sql = "FROM Estabelecimento WHERE (upper(nome) like upper('%" + searchKey + "%')) ";
    		
    		data.put("sql", sql);
    		return super.searchByHQL();
    	}
        
        public Return deactivate() {
        	entity.setActive(false);
        	
        	return super.update();
        }
        public Return nullit() {
        	Return ret = new Return(false);
        	
        	return ret;
        }
        
}