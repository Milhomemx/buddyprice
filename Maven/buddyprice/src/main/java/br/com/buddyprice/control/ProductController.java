package br.com.buddyprice.control;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Produto;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
/**
 * @author Natan
 * Controlador da abstração Produto. Gere todos os cenários relacionado à gestão do produto (cadastro de produtos, manutenção de produtos, etc).
 * Extende do controle genérico.
 */
@Service
@Scope("prototype")
public class ProductController extends BaseController<Produto> {
        public ProductController() {
                super(Produto.class);
        }
        

		/**
		 * @return
		 * Insere o produto.
		 */
		public Return InsertProduct() {
            Return regReturn = new Return(true);
            regReturn.concat(doAction("save"));
            if (regReturn.isValid()){
                    regReturn.addMessage(new Message(null, "Produto Cadastrado!"));
                    
                    
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
         * @param prod
         * @return
         * Retorna o ID de um produto pela pesquisa de seu respectivo nome. 
         */
        public Return getProductId(Serializable id){
        	String sql = "from Produto where id = '"+id+"'";
        	data.put("sql", sql);
        	
        	return searchByHQL();
        }
        
        /**
    	 * @return
    	 * Pesquisa por produtos. Ignora maiúsculas/minúsculas.
    	 * A palavra chave de pesquisa pode ser apenas um pedaço da String armazenada.
    	 */
    	public Return searchProducts(){
    		Return ret = new Return(true);
    		String searchKey = (String) data.get("searchField");
    		if((searchKey == null || searchKey.isEmpty()) || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
    			return ret;
    		}
    		String sql = "FROM Produto WHERE (upper(nome) like upper('%" + searchKey + "%')) ";
    		
    		data.put("sql", sql);
    		return super.searchByHQL();
    	}
        
        public Return nullit() {
        	Return ret = new Return(false);
        	
        	return ret;
        }
        
        
}