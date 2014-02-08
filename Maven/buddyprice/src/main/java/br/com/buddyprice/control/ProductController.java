package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Produto;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;
@Service
@Scope("prototype")
public class ProductController extends GenericControl<Produto> {
        public ProductController() {
                super(Produto.class);
        }
        

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
        
        public Return getProductId(Produto prod){
        	String sql = "from Produto where nome = '"+prod.getNome()+"'";
        	data.put("sql", sql);
        	
        	return searchByHQL();
        }
        
    	public Return searchProducts(){
    		Return ret = new Return(true);
    		String searchKey = (String) data.get("searchField");
    		if((searchKey == null || searchKey.isEmpty()) || !(searchKey.indexOf("%") != 0) || !(searchKey.indexOf("%") != searchKey.length() - 1)){
    			return ret;
    		}
    		String sql = "FROM Produto WHERE (nome like '%" + searchKey + "%') ";
    		
    		data.put("sql", sql);
    		return super.searchByHQL();
    	}
        
        public Return nullit() {
        	Return ret = new Return(false);
        	
        	return ret;
        }
        
        
}