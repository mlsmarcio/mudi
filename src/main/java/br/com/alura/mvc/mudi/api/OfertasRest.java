package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {
	
    @Autowired
    private PedidoRepository pedidoRepository;
	
	@PostMapping
	public Oferta criaOferta(@RequestBody RequisicaoNovaOferta requisicao) {
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		if (!pedidoBuscado.isPresent()) {
			return null;
		}
		
		Pedido pedido = pedidoBuscado.get();  		// PEDIDO BUSCADO
		
		Oferta nova = requisicao.toOferta();		// OFERTA COM DADOS DA REQUISIÇÃO
		nova.setPedido(pedido);						// ESPECIFICA O PEDIDO
		pedido.getOfertas().add(nova);				// ADICIONA A OFERTA
		pedidoRepository.save(pedido);				// SALVA A OFERTA PARA O PEDIDO E ATUALIZA OS DADOS DO PEDIDO
		
		return nova;
	}
}
