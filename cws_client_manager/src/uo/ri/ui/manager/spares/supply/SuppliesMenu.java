package uo.ri.ui.manager.spares.supply;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.spares.supply.action.AddAction;
import uo.ri.ui.manager.spares.supply.action.DeleteAction;
import uo.ri.ui.manager.spares.supply.action.ListByProviderAction;
import uo.ri.ui.manager.spares.supply.action.ListBySparePartAction;
import uo.ri.ui.manager.spares.supply.action.UpdateAction;

public class SuppliesMenu extends BaseMenu {

	public SuppliesMenu() {
		menuOptions = new Object[][] {
			{"Manager > Parts management > Supplies", null},

			{ "Add", 		AddAction.class },
			{ "Update", 	UpdateAction.class },
			{ "Remove", 	DeleteAction.class },
			{ "List by provider", 	ListByProviderAction.class },
			{ "List by spare part", ListBySparePartAction.class },
		};
	}

}
