package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;

import br.com.buddyprice.model.Estabelecimento;
import br.com.vexillum.util.Return;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchEstablishmentComposer extends ViewEstablishmentComposer {

	private Grid resultList;

	private String searchField;

	
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
		} else {
			resultList.setVisible(false);
		}
		
		return ret;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public static void redirectToView() {
		Executions.sendRedirect("view.zul");
	}
	
	public void redirectToUpdate(){
		Return ret = validateSelectedEntity();
		if(ret.isValid()){
			Executions.sendRedirect("include.zul?id=" + getSelectedEntity().getId());
		}
		treatReturn(ret);
	}
	
	
	
}
