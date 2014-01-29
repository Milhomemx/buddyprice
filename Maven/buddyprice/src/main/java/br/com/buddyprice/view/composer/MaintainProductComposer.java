package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.buddyprice.control.EstablishmentController;
import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;
import br.com.vexillum.view.CRUDComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class MaintainProductComposer extends
		CRUDComposer<Estabelecimento, EstablishmentController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	@Override
	public EstablishmentController getControl() {
		return SpringFactory.getController("establishmentController",
				EstablishmentController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	public Return insertEstablishment() {
		Return ret =null;
		ret = saveEntity();
		if(ret.isValid())
		{
			Executions.sendRedirect("../dashboard/index.zul");
		}else
			//TODO retornar o erro aqui
		component.detach();
		return ret;
		
	}

	public static void redirectToDash() {
		Executions.sendRedirect("../dashboard/index.zul");
	}

	@Override
	public Estabelecimento getEntityObject() {
		return new Estabelecimento();
	}

}
