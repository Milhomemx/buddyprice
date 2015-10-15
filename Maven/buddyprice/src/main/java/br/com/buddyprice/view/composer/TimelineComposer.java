
package br.com.buddyprice.view.composer;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;

import br.com.buddyprice.control.TimelineController;
import br.com.buddyprice.model.Timeline;
import br.com.vexillum.util.ReflectionUtils;
import br.com.vexillum.util.SpringFactory;

@org.springframework.stereotype.Component
@Scope("prototype")
@SuppressWarnings({ "serial", "rawtypes" })
public class TimelineComposer extends BaseComposer<Timeline, TimelineController> {

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		loadBinder();
	}
	
	public List<Timeline<?>> getTimelineItens(){
		return getControl().getTimelineItens();
	}
	
	@Override
	public TimelineController getControl() {
		 return SpringFactory.getController("timelineController",
				TimelineController.class,
				ReflectionUtils.prepareDataForPersistence(this));
	}

	@Override
	public Timeline getEntityObject() {
		return null;
	}

}
