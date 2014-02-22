package br.com.buddyprice.control;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Oferta;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Message;
import br.com.vexillum.util.Return;

@Service
@Scope("prototype")
public class OfferController extends GenericControl<Oferta> {
	public OfferController() {
		super(Oferta.class);
	}

	public Return InsertOffert() {
		Return regReturn = new Return(true);
		regReturn.concat(doAction("save"));
		if (regReturn.isValid()) {
			regReturn.addMessage(new Message(null, "Oferta criada!"));

		}
		return regReturn;

	}

	@Override
	public Return save() {
		return super.save();
	}

	public Return searchOffers() {
		Return ret = new Return(true);
		String searchKey = (String) data.get("searchField");
		if ((searchKey == null || searchKey.isEmpty())
				|| !(searchKey.indexOf("%") != 0)
				|| !(searchKey.indexOf("%") != searchKey.length() - 1)) {
			return ret;
		}
		String sql = "From Oferta where id in (select o.id from Oferta o, Estabelecimento e, Produto p where o.estabelecimento = e.id and o.produto = p.id  and (upper(p.nome) like  upper('%"
				+ searchKey
				+ "%') or upper(e.nome) like upper('%"
				+ searchKey
				+ "%')))";

		data.put("sql", sql);
		return super.searchByHQL();
	}

	public Return update() {

		return super.update();
	}

	public Return nullit() {
		Return ret = new Return(false);

		return ret;
	}

}