package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;

import jakarta.validation.Valid;


@RestController // notação que fala pro spring que essa é uma controladora de acesso ao método
@RequestMapping("/produtos") // como chegar nessa classe pelo Insomnia
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class ProdutoController {

	@Autowired // injetar dependências, instanciar a classe PostagemRepository
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping // verbo http que vai mostrar o conteudo no insomnia
	public ResponseEntity<List<Produto>> getAll(){
		// ResponseEntity - formatação da saída de dados
		return ResponseEntity.ok(produtoRepository.findAll());
   }
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		// findById = SELECT * FROM tb_postagens WHERE id = 1;
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
	
	@GetMapping("/nome/{nome}") //localhost:8080/postagens/titulo/Postagem 02
	// = SELECT * FROM tb_postagens WHERE titulo = "titulo";
	public ResponseEntity<List<Produto>> getByDescricao(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	
  }
	@PostMapping
	// = INSERTO INTO tb_postagens (titulo, texto, data) VALUES ("Titulo", "texto", "2024-12-31 15:02:45");
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRepository.save(produto));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		if(produtoRepository.existsById(produto.getId())) {
		
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		produtoRepository.deleteById(id);
		
	}
	
}
