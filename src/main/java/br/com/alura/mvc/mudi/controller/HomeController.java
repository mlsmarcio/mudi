package br.com.alura.mvc.mudi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired	// pedi para o spring a instância
	private PedidoRepository pedidoRepository;
	
	@GetMapping
	public String home(Model model, Principal principal, 
			@PageableDefault(sort = "dataDaEntrega", direction = Direction.DESC, page = 0, size = 5) Pageable paginacao) {
		
//		Sort sort = Sort.by("dataDaEntrega").descending(); 
//		PageRequest paginacao = PageRequest.of(0, 2, sort);
		Page<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.ENTREGUE, paginacao);
		
		
		int totalPages = (int) Math.ceil(pedidos.getSize() / (double) paginacao.getPageSize());
		
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("currentPage", paginacao.getPageNumber());
		model.addAttribute("totalPages", totalPages);
		
		return "home";
	}
}
