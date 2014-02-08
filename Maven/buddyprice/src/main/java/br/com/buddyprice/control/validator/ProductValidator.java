package br.com.buddyprice.control.validator;

import java.util.HashMap;
import java.util.Map;

import br.com.buddyprice.control.ProductController;
import br.com.buddyprice.model.Produto;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

public class ProductValidator extends Validator {

	public ProductValidator(Map<String, Object> mapData) {
		
		super(mapData);
	}

	

	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(existsNome());
		return ret;
	}

	private Return existsNome() {
		Return ret = new Return(true);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("sql", "FROM Produto e WHERE e.nome = '"
				+ ((Produto) entity).getNome() + "'");

		ProductController controller = getProductController(data);
		if (!controller.searchByHQL().getList().isEmpty()) {
			ret.concat(creatReturn("nome",
					getValidationMessage("nome", "exists", true)));
		}
		return ret;
	}

	private ProductController getProductController(
			HashMap<String, Object> data) {
		ProductController controller = SpringFactory.getController(
				"productController", ProductController.class, data);
		return controller;
	}
}
