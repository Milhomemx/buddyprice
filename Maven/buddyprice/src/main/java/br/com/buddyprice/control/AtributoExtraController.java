package br.com.buddyprice.control;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.AtributoExtra;

@Service
@Scope("prototype")
public class AtributoExtraController extends BaseController<AtributoExtra> {

	public AtributoExtraController() {
		super(AtributoExtra.class);
	}

	@SuppressWarnings("unchecked")
	public AtributoExtra setAtributoExtraNome(AtributoExtra atributo, String atributoExtra) {
		atributo.setNome(atributoExtra);
		List<AtributoExtra> list = (List<AtributoExtra>) searchByHQL(atributo).getList();
		if(list != null && !list.isEmpty()){
			atributo = list.iterator().next();
		}
		return atributo;
	}

}
