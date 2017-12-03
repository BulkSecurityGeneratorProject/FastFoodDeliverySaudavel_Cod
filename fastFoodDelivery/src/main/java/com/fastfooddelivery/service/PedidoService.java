package com.fastfooddelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfooddelivery.domain.Pedido;
import com.fastfooddelivery.domain.Pessoa;
import com.fastfooddelivery.domain.User;
import com.fastfooddelivery.repository.PedidoRepository;
import com.fastfooddelivery.repository.PessoaRepository;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PessoaRepository pessoaRepository;
    
    private final PedidoRepository pedidoRepository;

    public PedidoService(PessoaRepository pessoaRepository, PedidoRepository pedidoRepository) {
        
    	this.pedidoRepository = pedidoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public Pedido getPedido(Long id) {
    	return pedidoRepository.findOne(id);
    }
    
    @Transactional(readOnly = true)
    public void deletePedido(Long id){
    	pedidoRepository.delete(id);
    }
    
    @Transactional(readOnly = true)
    public Page<Pedido> getAllPedidos(Pageable pageable){
    	return pedidoRepository.findAll(pageable);
    	
    }
    
    @Transactional(readOnly = true)
    public Pedido salvarPedido(Pedido pedido){
    	return pedidoRepository.save(pedido);
    	
    }
    
    public List<Pedido> consultarPedidosPorIdUsuario(User usuario) {
    	
    	Optional<Pessoa> pessoa = Optional.empty();
    	List<Pedido> pedidos = new ArrayList<Pedido>();
    	
		pessoa = pessoaRepository.findOneByUser(usuario);
		
		if(pessoa.isPresent()) {
			
			pedidos = pedidoRepository.findAllByPessoa(pessoa.get());
		}
		
		log.debug("Consulta pedidos do usuario logado: {}", pedidos);
		
		return pedidos;
    }
    
}
