package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.TipoAtributoExtra;
import br.com.vexillum.control.GenericControl;

@Service
@Scope("prototype")
public class TypeAttributeExtraController extends
		GenericControl<TipoAtributoExtra> {

	public TypeAttributeExtraController() {
		super(TipoAtributoExtra.class);
	}
}