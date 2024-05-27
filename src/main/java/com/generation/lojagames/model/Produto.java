package com.generation.lojagames.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // essa classe vai se tornar uma entidade/tabela do banco de dados
@Table(name="tb_produtos")
public class Produto {
	
	@Id //tornar o campo uma chave primária no banco
	@GeneratedValue(strategy=GenerationType.IDENTITY) // tornando a chave primaria como auto inremento
	private Long id;
	
	@NotBlank(message="O atributo NOME é obrigatório!") // validation - validar o atributo NN e not empty
	@Size(min = 5, max = 100, message="O atributo NOME deve ter no mínimo 5 caracteres e no máximo 100 carcteres.")
	private String nome;
	
	@NotBlank(message="O atributo TEXTO é obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo TEXTO deve ter no mínimo 10 caracteres e no máximo 1000 caracteres.")
	private String texto;
	
	@UpdateTimestamp // pega a hora no banco de dados e preenche a coluna
	private LocalDateTime data;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

}
