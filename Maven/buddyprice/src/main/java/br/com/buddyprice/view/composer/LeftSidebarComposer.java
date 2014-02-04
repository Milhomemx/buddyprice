package br.com.buddyprice.view.composer;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import br.com.vexillum.view.GenericComposer;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings({ "serial", "rawtypes" })
public class LeftSidebarComposer extends
		GenericComposer {

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}


	public static void redirectToConfig() {
		Executions.sendRedirect("../configuration/");
	}

	
	public static void redirectToUser() {
		Executions.sendRedirect("/pages/user/edit.zul");
	}
	
	public static void redirectToDash() {
		Executions.sendRedirect("/pages/dashboard/");
	}

	public static void redirectToEstablishment() {
		Executions.sendRedirect("/pages/establishments/");
	}
	
	public static void redirectToProduct() {
		Executions.sendRedirect("/pages/products/");
	}	
	
	public static void redirectToOffer() {
		Executions.sendRedirect("/pages/offers/");
	}	
		
	public static void redirectToFriend() {
		Executions.sendRedirect("/pages/friends/");
	}	

	

}
