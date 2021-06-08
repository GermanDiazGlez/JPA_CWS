package uo.ri.ui.manager.spares.provider;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.spares.provider.action.AddAction;
import uo.ri.ui.manager.spares.provider.action.DeleteAction;
import uo.ri.ui.manager.spares.provider.action.ListByNameAction;
import uo.ri.ui.manager.spares.provider.action.ListBySparePartsSuppliedAction;
import uo.ri.ui.manager.spares.provider.action.UpdateAction;
import uo.ri.ui.manager.spares.provider.action.ViewDetailAction;

public class ProvidersMenu extends BaseMenu {

	public ProvidersMenu() {
		menuOptions = new Object[][] {
			{"Manager > Parts management > Providers", null},

			{ "Add", 		AddAction.class },
			{ "Update", 	UpdateAction.class },
			{ "Remove", 	DeleteAction.class },
			{ "View provider detail", 	ViewDetailAction.class },
			{ "List by name", 			ListByNameAction.class },
			{ "List by spare part code", ListBySparePartsSuppliedAction.class },
		};
	}

}
