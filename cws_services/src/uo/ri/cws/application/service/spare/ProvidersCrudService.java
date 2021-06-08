package uo.ri.cws.application.service.spare;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

public interface ProvidersCrudService {

	/**
	 * Adds a new provider to the system
	 * @param dto
	 * @return the ID for the new provided registered in the system
	 * @throws BusinessException in case of:
	 * 	- there already exist another provider with the same nif
	 * 	- there already exist another provider with different nif but same
	 * 		name, email and phone
	 * 	- any of the four fields are null or empty
	 * 	- the email field does not, at least, contains an @ sign
	 */
	String add(ProviderDto dto) throws BusinessException;

	/**
	 * Removes the provider indicated by its nif from the system. It can only
	 * be removed if the provider has no supplies or orders attached to it.
	 * @param nif
	 * @throws BusinessException in case of:
	 * 	- there is no provider with that nif
	 * 	- the provider has supplies
	 * 	- the provider has orders
	 */
	void delete(String nif) throws BusinessException;

	/**
	 * Updates all the field for the provider except the id, nif and version.
	 * Id and nif will signal the provider to update and version will be
	 * internally updated after committing.
	 * All the fields must pass the same validation rules as for the add()
	 * operation.
	 * @param dto
	 * @throws BusinessException in case of:
	 * 	- it does not exist any provider with the nif
	 * 	- there already exist another provider with different nif but same
	 * 		name, email and phone once the provided be updated
	 * 	- any of the four fields are null or empty
	 * 	- the email field does not, at least, contains an @ sign
	 * 	- the version field of the incoming dto does not match the version of
	 * 		the persistent entity
	 */
	void update(ProviderDto dto) throws BusinessException;

	/**
	 * @param provider nif
	 * @return the provider identified by the nif or Optional.empty() if
	 * 		does not exist
	 * @throws BusinessException DOES NOT
	 */
	Optional<ProviderDto> findByNif(String nif) throws BusinessException;

	/**
	 * Remember there could be several providers with different nif but
	 * 		the same name
	 * @param name
	 * @return a list with providers or empty if there is no one with this name
	 * @throws BusinessException DOES NOT
	 */
	List<ProviderDto> findByName(String name) throws BusinessException;

	/**
	 * @param code
	 * @return a list with providers or empty if there is no one serving
	 * 		the spare part identified
	 * @throws BusinessException DOES NOT
	 */
	List<ProviderDto> findBySparePartCode(String code) throws BusinessException;

	public static class ProviderDto {
		public String id;
		public long version;

		public String nif;
		public String name;
		public String email;
		public String phone;
	}

}
