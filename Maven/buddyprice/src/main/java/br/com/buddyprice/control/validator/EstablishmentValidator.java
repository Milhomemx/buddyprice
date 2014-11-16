package br.com.buddyprice.control.validator;

import java.util.HashMap;
import java.util.Map;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.control.validator.Validator;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Validador da abstração Estabelecimento. Valida todas as limitações determinadas para os atributos do modelo,
 * bem como as validações de negócio (Existência de um estabelecimento com o mesmo nome).
 * Extende do Validator.
 */
public class EstablishmentValidator extends Validator {

	public EstablishmentValidator(Map<String, Object> mapData) {

		super(mapData);
	}

	public Return validateSave() {
		Return ret = super.validateSave();
		ret.concat(existsNome());
		return ret;
	}

	/**
	 * @return
	 * Valida a existência do nome informado para o estabelecimento.
	 */
	private Return existsNome() {
		Return ret = new Return(true);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("sql", "FROM Estabelecimento e WHERE e.nome = '"
				+ ((Estabelecimento) entity).getNome() + "'");

		EstablishmentController controller = getEstablishmentController(data);
		if (!controller.searchByHQL().getList().isEmpty()) {
			ret.concat(creatReturn("nome",
					getValidationMessage("nome", "exists", true)));
		}
		return ret;
	}

	/**
	 * @param data
	 * @return
	 * Pega o EstablishmentController que será validado.
	 */
	private EstablishmentController getEstablishmentController(
			HashMap<String, Object> data) {
		EstablishmentController controller = SpringFactory.getController(
				"establishmentController", EstablishmentController.class, data);
		return controller;
	}

}
