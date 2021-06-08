package uo.ri.ui.cashier.action;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {{
	menuOptions = new Object[][] {
		{ "Cash", null },
		{ "Find work orders by client", 	FindWorkOrdersByClientAction.class },
		{ "Find work orders by plate", 		FindWorOrdersByPlateAction.class },
		{ "Invoice work orders", 			InvoiceWorkorderAction.class },
		{ "Settle invoice", 				SettleInvoiceAction.class },
	};
}}