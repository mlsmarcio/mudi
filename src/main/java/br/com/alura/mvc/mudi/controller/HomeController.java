package br.com.alura.mvc.mudi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.alura.mvc.mudi.model.Pedido;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String home(Model model) {
		
		Pedido pedido = new Pedido();
		pedido.setNomeProduto("Smartphone Samsung Galaxy A13 ");
		pedido.setUrlImagem("https://a-static.mlcdn.com.br/800x560/smartphone-samsung-galaxy-m13-128gb-verde-4g-octa-core-4gb-ram-66-cam-tripla-selfie-8mp/magazineluiza/235392700/21f35089ba29e513c714d74625794ce1.jpg");
		pedido.setUrlProduto("https://www.amazon.com.br/dp/B0B61W6MVF?ref=dlx_deals_gd_dcl_img_25_609dd41f_dt_sl16_bc");
		pedido.setDescricao("Preto 128GB 4GB RAM bateria 5000mAh Câmera Quádrupla Traseira de 50MP + 5MP + 2MP + 2MP Octa-Core tela infinita");
		
		List<Pedido> pedidos = Arrays.asList(pedido);
		model.addAttribute("pedidos", pedidos);
		
		return "home";
	}
}
