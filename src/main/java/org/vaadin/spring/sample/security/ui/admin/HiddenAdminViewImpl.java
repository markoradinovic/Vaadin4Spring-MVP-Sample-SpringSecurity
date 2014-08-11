package org.vaadin.spring.sample.security.ui.admin;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;
import org.vaadin.spring.sample.security.ui.admin.HiddenAdminPresenter.HiddenAdminView;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class HiddenAdminViewImpl extends AbstractMvpView implements HiddenAdminView {

	
	private CssLayout layout;		
	
	@Override
	public void postConstruct() {	
		super.postConstruct();
		setSizeFull();
		layout = new CssLayout();
		layout.setSizeFull();
		setCompositionRoot(layout);
		
		
		final Label label = new Label("This is admin only view. Navbar link is was hidden.");
		label.addStyleName(ValoTheme.LABEL_H2);
		layout.addComponent(label);		
		
	}
	
	@Override
	public void initView() {
				
	}

}
