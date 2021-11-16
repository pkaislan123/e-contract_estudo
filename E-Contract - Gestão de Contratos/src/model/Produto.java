package model;

public class Produto {

	private int id;
	private String nome, descricao, transgenia;

	public Produto(int id, String nome, String descricao, String transgenia) {

		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.transgenia = transgenia;
	}

	public Produto(String nome, String descricao, String transgenia) {

		this.nome = nome;
		this.descricao = descricao;
		this.transgenia = transgenia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTransgenia() {
		return transgenia;
	}

	public void setTransgenia(String transgenia) {
		this.transgenia = transgenia;
	}

}
