package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TWORKORDERS", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"VEHICLE_ID","DATE"
		})
})
public class WorkOrder extends BaseEntity{
	public enum WorkOrderStatus {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	
	private LocalDateTime date;
	private String description;
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	// accidental attributes
	@ManyToOne private Vehicle vehicle;
	@ManyToOne private Mechanic mechanic;
	@ManyToOne private Invoice invoice;
	@OneToMany (mappedBy="workOrder") private Set<Intervention> interventions = new HashSet<>();
	
	WorkOrder() {}

	public WorkOrder(Vehicle vehicle) { //Si paso el atributo de otra clase en el constructor, tengo que hacer el vinculo
		super();
		this.date = LocalDateTime.now();
		ArgumentChecks.isNotNull(vehicle);
		Associations.Fix.link(vehicle, this);
	}
	
	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle);
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		this(vehicle);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isNotNull(date);
		this.date = date;
		this.description = description;
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(WorkOrderStatus.FINISHED.equals(status) , "Finished to invoiced only");
		StateChecks.isTrue(this.getInvoice()!=null, "This workOrder doesnt have an invoice");
		this.status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		StateChecks.isTrue(WorkOrderStatus.ASSIGNED.equals(status) , "WorkOrder is not assigned");
		Associations.Assign.link(mechanic, this);
		this.status = WorkOrderStatus.FINISHED;
		computeAmount();
	}
	
	public void computeAmount(){
		amount = 0L;
		for (Intervention i : interventions) {
			amount += i.getAmount();
		}
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(WorkOrderStatus.INVOICED.equals(status) , "Invoiced to finished only");
		this.status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN status, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		StateChecks.isTrue(WorkOrderStatus.OPEN.equals(status) , "Open to assigned only");
		StateChecks.isTrue(this.getMechanic()==null , "This workOrder has a mechanic");
		Associations.Assign.link(mechanic, this);
		this.status = WorkOrderStatus.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its status back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED status
	 */
	public void desassign() {
		StateChecks.isTrue(WorkOrderStatus.ASSIGNED.equals(status), "This WorkOrder is not assigned");
		Associations.Assign.unlink(mechanic, this);
		this.status = WorkOrderStatus.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED status
	 */
	public void reopen() {
		ArgumentChecks.isTrue(WorkOrderStatus.FINISHED.equals(status) , "This workOrder is not Finished");
		this.status = WorkOrderStatus.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}
	
	public boolean isInvoiced() {
		return getInvoice() == null ? false : true;
	}
	
	public boolean isFinished() {
		return getStatus() != WorkOrderStatus.FINISHED ? false : true;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", status=" + status
				+ ", vehicle=" + vehicle + "]";
	}

	
}
