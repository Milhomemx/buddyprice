package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.HibernateUtils;

/**
 * Controla a tela requisições pendentes e em aberto de amizade.
 * @author Fernando
 *
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class RequestsComposer extends FriendshipComposer {

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setPendents(getListPendents()); 
		setOpenRequests(getListOpenRequests());
		initLists();
		loadBinder();
	}
	
	@Override
	protected void initLists() {
		List<Friendship> pendents = getPendents();
		if(pendents != null){
			HibernateUtils.initialize(pendents);
		}
		
		List<Friendship> openRequests = getOpenRequests();
		if(openRequests != null){
			HibernateUtils.initialize(openRequests);
		}
	}
	
//	public void redirectToProfile(Component comp) {
//		Row row = (Row) super.getParent(comp, "row");
//		Friendship fri = row.getValue();
//		redirectToProfile(fri.getFriend().getId());
//	}
	
}
