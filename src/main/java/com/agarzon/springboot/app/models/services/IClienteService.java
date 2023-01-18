package com.agarzon.springboot.app.models.services;

import java.util.List;

import com.agarzon.springboot.app.models.entities.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findById(Long id);
	public void delete(Long id);
	
}
