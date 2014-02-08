package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;

import br.com.buddyprice.model.Produto;
import br.com.vexillum.util.Return;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings("serial")
public class SearchProductComposer extends ViewProductComposer {

	private Grid resultList;

	private String searchField;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}

	@SuppressWarnings("unchecked")
	public Return searchProducts() {
		Return ret = getControl().doAction("searchProducts");
		if (ret.getList() != null && !ret.getList().isEmpty()) {
			setListEntity((List<Produto>) ret.getList());
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

	public void redirectToView(Produto produto) {

		session.setAttribute("produto", produto);
		Executions.sendRedirect("view.zul");
	}

	public void redirectToUpdate(Produto produto) {
		session.setAttribute("produto", produto);
		Executions.sendRedirect("include.zul");

	}

}
