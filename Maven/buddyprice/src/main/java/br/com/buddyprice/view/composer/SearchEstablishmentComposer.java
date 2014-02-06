package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;

import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.util.Return;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchEstablishmentComposer extends EstablishmentComposer {

	private Grid resultList;

	private Label noResultMessage;

	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	@SuppressWarnings("unchecked")
	public Return searchEstablishments(){
		Return ret = getControl().doAction("searchEstablishments");
		if(ret.getList() != null && !ret.getList().isEmpty()){
			setListEntity((List<Estabelecimento>) ret.getList());
			loadBinder();
			resultList.setVisible(true);
			noResultMessage.setVisible(false);
		} else {
			resultList.setVisible(false);
			noResultMessage.setVisible(true);
		}
		
		return ret;
	}
	
	
}
