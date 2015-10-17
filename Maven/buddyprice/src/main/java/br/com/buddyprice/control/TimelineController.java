package br.com.buddyprice.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Comentario;
import br.com.buddyprice.model.Oferta;
import br.com.buddyprice.model.Timeline;
import br.com.buddyprice.model.Usuario;
import br.com.vexillum.control.FriendshipController;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.model.Friendship;
import br.com.vexillum.util.HibernateUtils;
import br.com.vexillum.util.Return;
import br.com.vexillum.util.SpringFactory;

/**
 * @author Natan
 * Controlador da abstração Oferta. Gere todos os cenários relacionado a ação de ofertar.
 * Extende do controle genérico.
 */
@SuppressWarnings("rawtypes")
@Service
@Scope("prototype")
public class TimelineController extends GenericControl<Timeline> {
	
	public TimelineController() {
		super(Timeline.class);
	}

	@SuppressWarnings("unchecked")
	public List<Timeline<?>> getTimelineItens(){
		String sql = "from Timeline t";
		if(!getData().get("userTimeline").equals(getData().get("userLogged"))){
			sql += " where t.usuarioDono <> " + ((Usuario) getData().get("userLogged")).getId() + " and t.usuarioDono.id = " + ((Usuario) getData().get("userTimeline")).getId();
			getData().put("sql", sql);
		} else {
			List<Usuario> listFriendsUsuario = getListFriendsUsuario();
			if(listFriendsUsuario == null || listFriendsUsuario.isEmpty()) return new ArrayList<>();
			Query query = getPersistence().getSession().createQuery(sql + " where t.usuarioDono <> " + ((Usuario) getData().get("userLogged")).getId() + " and t.usuarioDono in (:listFriends)")
																	.setParameterList("listFriends", listFriendsUsuario);
			getData().put("sql", query);
		}  
		Return ret = searchByHQL();
		if(ret.getList() == null){
			return new ArrayList<>();
		}
		List<Timeline<?>> timleineList = (List<Timeline<?>>) ret.getList();
		Collections.sort(timleineList);
		return timleineList;
	}
	
	private List<Usuario> getListFriendsUsuario(){
		Usuario userLogged = (Usuario) getData().get("userLogged");
		
		getData().put("user", userLogged);
		FriendshipController controller = SpringFactory.getController("friendshipController",
				FriendshipController.class, getData());
		
		List<Usuario> list = new ArrayList<>();
		Return ret = controller.searchAllFriends();
		if(ret.getList() != null){
			for (Object o : ret.getList()) {
				Friendship f = (Friendship) o;
				if(f.getOwner().equals(userLogged)){
					list.add((Usuario) HibernateUtils.materializeProxy(f.getFriend()));
				} else {
					list.add((Usuario) HibernateUtils.materializeProxy(f.getOwner()));
				}
			}
		}
		return list;
	}
	
	public List<Comentario> getCommentsFromOffer(Oferta oferta){
		OfferController controller = getOfferController();
		return controller.getCommentsFromOffer(oferta);
	}

	public Return evaluateOfferPositive(){
		Oferta oferta = (Oferta) getData().get("selectedOffer");
		return getOfferController().evaluateOfferPositive(oferta);
	}
	
	public Return evaluateOfferNegative(){
		Oferta oferta = (Oferta) getData().get("selectedOffer");
		return getOfferController().evaluateOfferNegative(oferta);
	}
	
	public Usuario getUserById(Long id){
		UsuarioController controller = SpringFactory.getController("usuarioController",
				UsuarioController.class, getData());
		return controller.searchById(id);
	}
	
	private OfferController getOfferController() {
		return SpringFactory.getController("offerController",
				OfferController.class, getData());
	}
	
}