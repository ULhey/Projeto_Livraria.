package Entidade;

public class Compra {
	private int cod;
	private Edicao edicao;
	private int quantidade;
	private double valorTotal;
	private Cliente cliente;
	private String codCliente;
	private int codEdicao;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public Edicao getEdicao() {
		return edicao;
	}
	public void setEdicao(Edicao edicao) {
		this.edicao = edicao;
		this.codEdicao = edicao.getCod();
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.codCliente = cliente.getCPF();
	}
	public String getCodCliente() {
		return codCliente;
	}
	public int getCodEdicao() {
		return codEdicao;
	}
}