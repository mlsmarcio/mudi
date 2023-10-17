package br.com.alura.mvc.mudi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.OfertaRepository;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/oferta")
public class OfertaController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@GetMapping
	public String getFormularioParaOfertas() {
		return "oferta/home";
	}
	
	@GetMapping("/ver")
	public String ver(@RequestParam(name = "id", defaultValue = "0") Long id, Model model) {
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
		Pedido pedido = pedidoBuscado.get();
		model.addAttribute("pedido", pedido);
		return "oferta/verOferta";
	}
	
	@GetMapping("/aprova")
	public String aprova(@RequestParam(name = "id", defaultValue = "0") Long id, Model model) {
		Optional<Oferta> ofertaBuscada = ofertaRepository.findById(id);
		Oferta oferta = ofertaBuscada.get();
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(oferta.getPedido().getId());
		Pedido pedido = pedidoBuscado.get();
		pedido.setDataDaEntrega(oferta.getDataDaEntrega());
		pedido.setValorNegociado(oferta.getValor());
		pedido.setStatus(StatusPedido.APROVADO);
		pedidoRepository.save(pedido);
		
		return "usuario/home";	///usuario/pedido/aprovado}";
	}
	
}
