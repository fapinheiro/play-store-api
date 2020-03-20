package com.nelioalves.cursomc.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> list = service.findAll()
			.stream()
			.map(cat -> new CategoriaDTO(cat))
			.collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value ="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria cat = service.findById(id);
		return ResponseEntity.ok().body(cat);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> add(@RequestBody Categoria cat) {
		cat = service.add(cat);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(cat.getId())
			.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value ="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Categoria cat) {
		cat.setId(id);
		cat = service.update(cat);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value ="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value ="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page", defaultValue = "0") Integer page,
		@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
		@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = list.map( cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(listDto);
	}
}
