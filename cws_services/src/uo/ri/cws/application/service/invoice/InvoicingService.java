package uo.ri.cws.application.service.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface InvoicingService {

	/**
	 * Creates an invoice for the work orders indicated by its id. All the
	 * 		work orders must exist and be in FINISHED status.
	 * @param workOrderIds, the ids of the work orders to be included
	 * @return the dto of the newly generated invoice
	 * @throws BusinessException if
	 * 	- Any of the indicated work orders are not in FINISHED status
	 * 	- There not exist any of the ids indicated
	 */
	InvoiceDto createInvoiceFor(List<String> workOrderIds)
			throws BusinessException;

	/**
	 * Returns a list with info of all the work orders of all the client's vehicles
	 * @param dni of the client
	 * @return a list of InvoicingWorkOrderDto or empty list if there is none
	 * @throws BusinessException DOES NOT
	 */
	List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni)
			throws BusinessException;

	/**
	 * Returns a list with info of all the work orders of a vehicle
	 * @param plate, the plate number of the vehicle
	 * @return a list of InvoicingWorkOrderDto or empty list if there is none
	 * @throws BusinessException DOES NOT
	 */
	List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException;

	/**
	 * @param number, of the invoice
	 * @return the InvoiceDto with the data of the invoice
	 * @throws BusinessException DOES NOT
	 */
	Optional<InvoiceDto> findInvoiceByNumber(Long number) throws BusinessException;

	/**
	 * @param dni of the client
	 * @return the list of the PaymentMeanDto of a client represented by the dni
	 * 		or an empty list if none
	 * @throws BusinessException DOES NOT
	 */
	List<PaymentMeanDto> findPayMeansByClientDni(String dni)
			throws BusinessException;

	/**
	 * Creates the charges against the indicated payment means (with the amount
	 * indicated) and then pass the invoice to the PAID status.
	 *
	 * @param invoiceId, the id of the invoice to be paid
	 * @param charges, a Map<String, Double>
	 * 	whose:
	 * 	- Key (String) represents the payment mean id, and
	 * 	- Value (Double) represents the amount to be charged to the payment mean
	 *
	 * @throws BusinessException if
	 * 	- there is no invoice for the invoiceId provided
	 *  - the indicated invoice is already in PAID status
	 * 	- does not exist any of the payment means indicated by the id
	 *  - the addition of all amounts charged to each payment mean does not
	 *  	equals the amount of the invoice with a precision of two cents
	 *  	( Math.abs( total - amount) <= 0.01 )
	 * 	- any of the payment means cannot be used to pay the specified amount:
	 * 		- For a CreditCard, if the current date is after the validThough date
	 * 		- For a Voucher, if it has not enough available for the amount
	 * 		- For Cash there is no constraint, cash can be used always
	 *
	 * Note:
	 * 		the domain model does not have the proper design to do it
	 * 		polymorphically,
	 *
	 * 		THUS
	 *
	 * 		add a
	 * 			public abstract boolean canPay( amount );
	 * 		method to PaymentMean class and the proper specialization on the
	 * 		child classes
	 */
	void settleInvoice(String invoiceId, Map<String, Double> charges)
			throws BusinessException;

	public static class InvoiceDto {

		public String id;		// the surrogate id (UUID)
		public long version;

		public double total;	// total amount (money) vat included
		public double vat;		// amount of vat (money)
		public long number;		// the invoice identity, a sequential number
		public LocalDate date;	// of the invoice
		public String status;	// the status as in FacturaStatus

	}

	public static class InvoicingWorkOrderDto {
		public String id;
		public String description;
		public LocalDateTime date;
		public String status;
		public double total;
	}

	public static abstract class PaymentMeanDto {
		public String id;
		public long version;

		public String clientId;
		public Double accumulated;
	}

	public static class CashDto extends PaymentMeanDto {

	}

	public static class CardDto extends PaymentMeanDto {
		public String cardNumber;
		public LocalDate cardExpiration;
		public String cardType;

	}

	public static class VoucherDto extends PaymentMeanDto {

		public String code;
		public String description;
		public Double available;

	}
}
