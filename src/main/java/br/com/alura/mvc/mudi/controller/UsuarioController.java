package br.com.alura.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired	
	private PedidoRepository pedidoRepository;
	
	@GetMapping("pedido")
	public String home(Model model, Principal principal,
			@PageableDefault(sort = "dataDaEntrega", direction = Direction.DESC, page = 0, size = 1) Pageable paginacao) {
		
		Page<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName(), paginacao);
		
		int totalPages = pedidos.getTotalPages();
		
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("currentPage", paginacao.getPageNumber());
		model.addAttribute("totalPages", totalPages);
		
//		System.out.println("currentPage" + paginacao.getPageNumber());
//		System.out.println("totalPages" + totalPages);

		return "usuario/home";
	}

	@GetMapping("pedido/{status}")
	public String porStatus(@PathVariable("status") String status, Model model, Principal principal,
			@PageableDefault(sort = "dataDaEntrega", direction = Direction.DESC, page = 0, size = 1) Pageable paginacao) {
		
		Page<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.valueOf(status.toUpperCase()), principal.getName(),
				paginacao);
		
//		for (Pedido p : pedidos) {
//			System.out.println("ofertas: " + p.getOfertas().size());
//			System.out.println("possui ofertas: " + p.possuiOfertas());
//			System.out.println(p.getStatus().name());
//		}
		int totalPages = pedidos.getTotalPages();
		
		model.addAttribute("currentPage", paginacao.getPageNumber());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", status);
		return "usuario/home";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}
}
