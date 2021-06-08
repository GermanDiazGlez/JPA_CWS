package uo.ri.cws.application.service.spare.order.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;

public class GenerateOrders implements Command<List<OrderDto>> {

	private SparePartRepository spareRepo = Factory.repository.forSparePart();
	private OrderRepository orderRepo = Factory.repository.forOrder();

	@Override
	public List<OrderDto> execute() throws BusinessException {
		Map<Provider, Order> orders = new HashMap<>();

		List<SparePart> sparesUnderStock = spareRepo.findUnderStockNotPending();
		for(SparePart spare: sparesUnderStock) {
			Optional<Supply> os = spare.getBestSupply();
			if ( os.isEmpty() ) continue; // there is no supply, so ignore it

			Supply supply = os.get();
			if(supply.getProvider()!=null) {
				Order order = getOrCreateOrderForProvider( orders, supply.getProvider() );
				order.addSparePartFromSupply( supply );
			}
		}
		
		for(Order o: orders.values()) {
			orderRepo.add( o );
		}

		return DtoAssembler.toOrdersDtoList( List.copyOf( orders.values() ));
	}
	private Order getOrCreateOrderForProvider(Map<Provider, Order> orders,
			Provider provider) {
		Order order = orders.get( provider );
		if (order == null) {
			order = new Order( generateCode( provider ) );
			Associations.Deliver.link(provider, order);
			orders.put(provider, order);
		}
		return order;
	}

	private String generateCode(Provider provider) {
		return provider.getNif()
					+ "-"
					+ generateRandom();
	}
	
	public String generateRandom() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		       char c = chars[random.nextInt(chars.length)];
		       sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

}
