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
public class EstablishmentComposer extends
		CRUDComposer<Estabelecimento, EstablishmentController> {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if(session.getAttribute("estabelecimento")!=null)
			setEntity((Estabelecimento) session.getAttribute("estabelecimento"));
		loadBinder();
	}

	@Override
	public EstablishmentController getControl() {
		return SpringFactory.getController("establishmentController",
				EstablishmentController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Return saveEntity() {
		Estabelecimento estabelecimento = entity;
		Return ret = super.saveEntity();
		if(ret.isValid()){
			ret.concat(getControl().getEstablishmentId(estabelecimento));
			if(ret.isValid() && !ret.getList().isEmpty())
			estabelecimento = (Estabelecimento) ret.getList().get(0);
			session.setAttribute("estabelecimento", estabelecimento);
			Executions.sendRedirect("view.zul?sucess=true");
			
		}
			
		return ret;
	}
	
	public void editEstablishment(){
		entity = (Estabelecimento) session.getAttribute("estabelecimento");
		callModalWindow("/pages/forms/modalEstablishment.zul");
		
	}
	

	@Override
	public Estabelecimento getEntityObject() {
		return new Estabelecimento();
	}

}
