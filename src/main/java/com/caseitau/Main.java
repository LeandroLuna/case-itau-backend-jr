package com.caseitau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Cliente {
  String nome = "";
  String cpf = "";
  String password = "";
  String token = "";
  Endereco endereco;
  
  public Cliente (String nome, String cpf, String password, String token, Endereco endereco) {
    this.nome = nome;
    this.cpf = cpf;
    this.password = password;
    this.token = token;
    this.endereco = endereco;
    this.validate();
  }
  
  private boolean validateNome(String name){
    if(nome == null || nome.isEmpty()) {
      System.out.println("Nome é obrigatório");
      return false;
    }
    return true; 
  }
  
  private boolean validateCpf(String cpf) {
    String validateCpf = cpf.replaceAll("[^0-9]", "");
    return validateCpf.length() == 11;
  }
  
  private boolean validatePassword(String password) {
    return password.equals(this.password);
  }
  
  private boolean validateToken(String token) {
    return token.equals(this.token);
  }
  
  void validate() {
    if(validateNome(nome) && validateCpf(cpf) && validatePassword(password) && validateToken(token) && endereco.validarEndereco(endereco))  {
      System.out.println("Campos válidados,");
    } else {
      System.out.println("Parâmetro Invalido");
    }
  }
}

// Método para válidar as últimas 3 compras
class Compras {
    private List<Map<String, Object>> ultimasCompras = new ArrayList<>();

    public Compras() {
        Map<String, Object> compra1 = new HashMap<>();
        compra1.put("valor", 900);
        compra1.put("moeda", "R$");
        compra1.put("local", "Loja de Eletronicos de São Gonçalo");
        compra1.put("data", "12/12/1234");
        ultimasCompras.add(compra1);

        Map<String, Object> compra2 = new HashMap<>();
        compra2.put("valor", 10);
        compra2.put("moeda", "R$");
        compra2.put("local", "Rei do Mate");
        compra2.put("data", "12/12/1234");
        ultimasCompras.add(compra2);

        Map<String, Object> compra3 = new HashMap<>();
        compra3.put("valor", 20);
        compra3.put("moeda", "R$");
        compra3.put("local", "Restaurante de Jaú");
        compra3.put("data", "12/12/1234");
        ultimasCompras.add(compra3);
    }

    public boolean validaUltimasCompras() {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 0; i < 3; i++) {
                Map<String, Object> compra = ultimasCompras.get(i);
                char resposta;

                System.out.printf("Você reconhece a compra realizada no dia %s no local %s no valor de %s%s?\nDigite 'S' para sim e 'N' para não.\nR: ",
                        compra.get("data"), compra.get("local"), compra.get("moeda"), compra.get("valor"));

                while (true) {
                    if (scanner.hasNext()) {
                        resposta = scanner.next().charAt(0);

                        if (resposta == 'S' || resposta == 'N') {
                            break;
                        } else {
                            System.out.println("Resposta inválida, tente novamente!");
                        }
                    } else {
                        System.out.println("Erro de entrada. Tente novamente.");
                        return false;
                    }
                }

                if (resposta == 'N') {
                    scanner.close();
                    return false;
                }
            }
            scanner.close();
        }
        return true;
    }
}

// Metódo para confirmar o endereço de entrega
class Endereco {
    private String rua;
    private String cidade;
    private String estado;
    private int cep;
    
    public Endereco(String rua, String cidade, String estado, int cep){
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
    
    public String getRua(){
        return rua;
    }
    
    public String getCidade(){
        return cidade;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public int getCep(){
        return cep;
    }
    
    public void setRua(String rua){
        this.rua = rua;
    }
    
    public void setCidade(String cidade){
        this.cidade = cidade;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void setCep(int cep){
        this.cep = cep;
    }

    public String getEndereco(){
        return rua + ", " + cidade + " - " + estado + " - " + cep;
    }
  
    public boolean validarEndereco(Endereco endereco){
        if(endereco.getRua() == null || endereco.getRua().isEmpty()) {
        System.out.println("Rua é obrigatória");
        return false;
        }
        
        if(endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
        System.out.println("Cidade é obrigatória");
        return false;
        }
        
        if(endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
        System.out.println("Estado é obrigatório");
        return false;
        }
        
        if(endereco.getCep() < 8) {
        System.out.println("CEP inválido");
        return false;
        }
        
        return true;
    }
}

// Objeto com informações do cartão (nome, endereço de entrega, se tem ou não NFC)
class Cartao {
    private String nome;
    private String endereco;
    private boolean NFC;
    private char resposta;
  
    public Cartao(Cliente cliente) {
        this.nome = cliente.nome;
        mudarEndereco(cliente);
        validarNFC();
        cancelarAnterior();
    }
    
    private void mudarEndereco(Cliente cliente) {
        System.out.println("Seu endereço continua: " + cliente.endereco.getEndereco());
        System.out.print("Digite 'S' para sim e 'N' para não.\nR: ");
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if(scanner.hasNext()) {
                    resposta = scanner.next().charAt(0);
    
                    if (resposta == 'S' || resposta == 'N') {
                        break;
                    } else {
                        System.out.println("Resposta inválida, tente novamente!");
                    }
                }
            }

            if (resposta == 'N') {
                System.out.println("Digite o novo endereço");
                System.out.println("Digite a Rua: ");
                String rua = scanner.nextLine();

                System.out.println("Digite a Cidade: ");
                String cidade = scanner.nextLine();

                System.out.println("Digite o Estado: ");
                String estado = scanner.nextLine();

                System.out.println("Digite o CEP: ");
                int cep = scanner.nextInt();

                cliente.endereco.setRua(rua);
                cliente.endereco.setCidade(cidade);
                cliente.endereco.setEstado(estado);
                cliente.endereco.setCep(cep);
            }

            scanner.close();
        }
    }
  
  private void validarNFC() {
    System.out.println("Gostaria que seu cartão tivesse NFC? Digite 'S' para sim e 'N' para nao\nR: ");

    try (Scanner scanner = new Scanner(System.in)) {
        resposta = scanner.next().charAt(0); 
        
        while (resposta != 'S' && resposta != 'N') {
          System.out.println("Resposta inválida, tente novamente.");
          resposta = scanner.next().charAt(0); 
        }
    }

    if (resposta == 'S' || resposta == 'N') {
      NFC = resposta == 'S';
    } else {
        System.out.println("Resposta inválida, tente novamente!");
    }
  }
  
  private boolean cancelarAnterior() {
    System.out.println("Gostaria de cancelar o cartão anterior? Digite 'S' para sim e 'N' para nao\nR: ");
    try (Scanner scanner = new Scanner(System.in)) {
        resposta = scanner.next().charAt(0); 
        
        while (resposta != 'S' && resposta != 'N') {
          System.out.println("Resposta inválida, tente novamente.");
          resposta = scanner.next().charAt(0); 
        }
    }
    
    return resposta == 'S';
  }
  
  public boolean confirmarCriacao() {
    System.out.printf("Você confirma a criação do cartão no nome de %s, entrega no endereço %s %s NFC?\nDigite 'S' para sim e 'N' para não.\nR: ",
      nome, endereco, (NFC ? "com" : "sem"));
    
      try (Scanner scanner = new Scanner(System.in)) {
        resposta = scanner.next().charAt(0); 
        
        while (resposta != 'S' && resposta != 'N') {
          System.out.println("Resposta inválida, tente novamente.");
          resposta = scanner.next().charAt(0); 
        }
    }

    return resposta == 'S';
  }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cliente cliente = novoCliente();

        Compras compras = new Compras();
        if (!compras.validaUltimasCompras()) {
          System.out.println("Operação cancelada. Entre em contato com a agência!");
          scanner.close();
          return;
        }

        Cartao cartao = new Cartao(cliente);
        if (!cartao.confirmarCriacao()) {
          System.out.println("Operação cancelada. Entre em contato com a agência!");
        } else {
          System.out.println("Operação concluída com sucesso!");
        }

        scanner.close();
    }

    public static Cliente novoCliente() {
        // String nome, cpf, token, senha, rua, cidade, estado;
        // int cep;

        // Scanner scanner = new Scanner(System.in);

        // System.out.print("Digite o Nome: ");
        // nome = scanner.nextLine();

        // System.out.print("\nDigite o CPF: ");
        // cpf = scanner.nextLine();

        // System.out.print("\nDigite o Token: ");
        // token = scanner.nextLine();

        // System.out.print("\nDigite a Senha: ");
        // senha = scanner.nextLine();

        // System.out.print("\nDigite a Rua: ");
        // rua = scanner.nextLine();

        // System.out.print("\nDigite a Cidade: ");
        // cidade = scanner.nextLine();

        // System.out.print("\nDigite o Estado: ");
        // estado = scanner.nextLine();

        // System.out.print("\nDigite o CEP: ");
        // cep = scanner.nextInt();
        
        // // 'Limpa' o terminal
        // for (int i = 0; i < 5; i++) {
        //     System.out.println();
        // }

        // scanner.close();

        // return new Cliente(nome, cpf, token, senha, new Endereco(rua, cidade, estado, cep));
        return new Cliente("Leandro", "12345678912", "123", "1234", new Endereco("Rua", "Cidade", "Estado", 12345123));
    }
}
