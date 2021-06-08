package uo.ri.ui.manager.spares.sparepart;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.spares.sparepart.action.AddAction;
import uo.ri.ui.manager.spares.sparepart.action.DeleteAction;
import uo.ri.ui.manager.spares.sparepart.action.ListByDescriptionAction;
import uo.ri.ui.manager.spares.sparepart.action.ListUnderStockAction;
import uo.ri.ui.manager.spares.sparepart.action.UpdateAction;
import uo.ri.ui.manager.spares.sparepart.action.ViewDetailAction;

public class SparePartsMenu extends BaseMenu {

	public SparePartsMenu() {
		menuOptions = new Object[][] {
			{"Manager > Parts management > Spare parts", null},

			{ "Add", 		AddAction.class },
			{ "Update", 	UpdateAction.class },
			{ "Remove", 	DeleteAction.class },
			{ "View detail", 			ViewDetailAction.class },
			{ "List by description", 	ListByDescriptionAction.class },
			{ "List under stock", 		ListUnderStockAction.class },
		};
	}

}
