package uo.ri.ui.manager;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.mechanic.MechanicsMenu;
import uo.ri.ui.manager.spares.SparePartsManagementMenu;
import uo.ri.ui.manager.vehicletype.VehicleTypesMenu;

public class MainMenu extends BaseMenu {{
		menuOptions = new Object[][] {
			{ "Manager", null },

			{ "Mechanics management", 		MechanicsMenu.class },
			{ "Spareparts management", 		SparePartsManagementMenu.class },
			{ "Vehicle types management", 	VehicleTypesMenu.class },
		};
}}