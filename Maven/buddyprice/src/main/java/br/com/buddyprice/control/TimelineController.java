package br.com.buddyprice.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.buddyprice.model.Timeline;
import br.com.vexillum.control.GenericControl;
import br.com.vexillum.util.Return;

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
		getData().put("sql", "from Timeline");
		Return ret = searchByHQL();
		if(ret.getList() == null){
			return new ArrayList<>();
		}
		
		return (List<Timeline<?>>) ret.getList();
	}
	
}