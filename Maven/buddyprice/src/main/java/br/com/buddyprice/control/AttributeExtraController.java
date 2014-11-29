package br.com.buddyprice.control;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.AtributoExtra;
import br.com.buddyprice.model.CommonEntityDated;
import br.com.vexillum.control.GenericControl;

@Service
@Scope("prototype")
public class AttributeExtraController extends
		GenericControl<AtributoExtra> {

	public AttributeExtraController() {
		super(AtributoExtra.class);
	}

	public List<AtributoExtra> getAtributosByEntity(CommonEntityDated entity) {
		String sql = "FROM AtributoExtra WHERE id = " + entity.getId();
		data.put("sql", sql);
		return (List<AtributoExtra>) searchByHQL();
	}
}