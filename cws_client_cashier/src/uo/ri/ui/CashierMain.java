package uo.ri.ui;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.cws.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.cws.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.ui.cashier.action.FindWorOrdersByPlateAction;
import uo.ri.ui.cashier.action.FindWorkOrdersByClientAction;
import uo.ri.ui.cashier.action.InvoiceWorkorderAction;
import uo.ri.ui.cashier.action.SettleInvoiceAction;

public class CashierMain {

    private static class MainMenu extends BaseMenu {
	{
	    menuOptions = new Object[][] { { "Cash", null },
		    { "Find work orders by client", 	FindWorkOrdersByClientAction.class },
		    { "Find work orders by plate", 	FindWorOrdersByPlateAction.class },
		    { "Invoice work orders", 		InvoiceWorkorderAction.class },
		    { "Settle invoice", 		SettleInvoiceAction.class }, };
	}
    }

    public static void main(String[] args) {
	new CashierMain().config().run().close();
    }

    private CashierMain config() {
	Factory.service = new BusinessFactory();
	Factory.repository = new JpaRepositoryFactory();
	Factory.executor = new JpaExecutorFactory();

	return this;
    }

    public CashierMain run() {
	try {
	    new MainMenu().execute();

	} catch (RuntimeException rte) {
	    Printer.printRuntimeException(rte);
	}
	return this;
    }

    private void close() {
	Jpa.close();
    }

}
