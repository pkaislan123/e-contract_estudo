

package controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import model.Pagamento;
import model.Cliente;
import model.Contrato;
import model.Veiculo;




public class GerenciaPagamento {

	private ArrayList<Pagamento> listPagamento;
	private Scanner num, str;
	private Contrato contrato;
	Locale ptBr = new Locale("pt", "BR");
	
	public GerenciaPagamento( Contrato contrato) {
		this.listPagamento = contrato.getListPagamentos();
		this.contrato = contrato;
		str = new Scanner(System.in);
		num = new Scanner(System.in);

	}
	
public void incluir() {
        
		double valorPagamento;
		int id_veiculo;
		LocalDate data_pag = null;
        System.out.println("\n --==[ Cadastro de Pagamento ]==--");
     
        System.out.print(" Valor: ");
        valorPagamento = num.nextDouble();
       boolean data_valida = false;
    	do {
        System.out.print(" Data do Pagamento: Ex: 19/06/2021\n");
        try {
         data_pag = LocalDate.parse(str.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        data_valida = true;
    	}catch(Exception e) {
    		System.out.println("Data Invalida!");
    	}
        
    	}while(!data_valida);
       
    	listPagamento.add(new Pagamento(listPagamento.size() + 1, valorPagamento, data_pag));
    	contrato.setListPagamentos(listPagamento);
        System.out.println(" Pagamento cadastrado com sucesso!\n");
    }
	
	
	

public void relatorio() {
	if (listPagamento.isEmpty()) {
		System.out.println("\n N�o h� Pagamentos cadastradas!\n");
		return;
	}
	double valorTotalPagamentos = 0;

	System.out.println("\n --==[ Relat�rio de Pagamentos ]==--");
	for (int i = 0; i < listPagamento.size(); i++) {
		valorTotalPagamentos += listPagamento.get(i).getValor();
		imprimir(i);
	}
	
	
	double valorTotalPagar = contrato.getValorPorUnidade() * contrato.getQuantidadeContratada();
	System.out.println("\nValor Total a Pagar: "  + NumberFormat.getCurrencyInstance(ptBr).format(valorTotalPagar) );
	System.out.println("Valor Total Pago: "  + NumberFormat.getCurrencyInstance(ptBr).format(valorTotalPagamentos) );
	double diferenca = valorTotalPagar - valorTotalPagamentos;

	if (diferenca == 0) {
		System.out.println("Pagamento Conclu�do");
	} else if (diferenca < 0) {
		System.out.println("Excedeu em " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

	} else if (diferenca > 0) {
		System.out.println("Incompleto, falta " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

	}


}

public void listarPagamentos() {
	for(Pagamento ca : listPagamento) {
		System.out.println("ID: " + ca.getId() + " Valor: " +ca.getValor() + " Data Pagamento: " + formatarData ( ca.getDataPagamento()));
	}
}

public void imprimir(int posConsulta) {

	System.out.println("\n --== Consulta posi��o: " + posConsulta + " ==--");
	System.out.println(" Valor: " + NumberFormat.getCurrencyInstance(ptBr).format(listPagamento.get(posConsulta).getValor())  );
	System.out.println(" Data Pagamento: " + formatarData( listPagamento.get(posConsulta).getDataPagamento() ));

}

public void consultar() {
	listarPagamentos();
	int posConsulta = 0;

	if (listPagamento.isEmpty()) {
		System.out.println("\n N�o h� Pagamentos cadastradas!\n");
		return;
	}

	System.out.println("\n --==[ Pesquisa produtos ]==--");
	System.out.print(" Informe a posi��o da consulta: ");
	posConsulta = num.nextInt();
	posConsulta -= 1;
	if (posConsulta >= listPagamento.size() || posConsulta < 0) {
		System.out.println("\n Posi��o inv�lida!\n");
		return;
	}
	imprimir(posConsulta);
	
}

public void excluir() {
	int posConsulta, op;
	listarPagamentos();

	if (listPagamento.isEmpty()) {
		System.out.println("\n N�o h� Pagamentos cadastrados!\n");
		return;
	}
	System.out.println("\n --==[ Exclus�o de Pagamento ]==--");
	System.out.print(" Informe a posi��o do Pagamento que deseja EXCLUIR: ");
	posConsulta = num.nextInt() - 1;

	if (posConsulta >= listPagamento.size() || posConsulta < 0) {
		System.out.println("\n Posi��o inv�lida!\n");
		return;
	}
	imprimir(posConsulta);

	System.out.println("\n Deseja EXCLUIR o produto listado? 1.Sim | 2.N�o");
	op = num.nextInt();
	if (op == 1) {
		listPagamento.remove(posConsulta);
		System.out.println("\n Pagamento exclu�do com sucesso!\n");
	} else {
		System.out.println(" Exclus�o cancelada");
	}
}

	
public String formatarData(LocalDate data) {
	return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}

	
}
