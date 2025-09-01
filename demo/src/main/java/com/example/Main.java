package com.example;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioDao usuarioDao = new UsuarioDao();

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 Cadastrar usuário");
            System.out.println("2 Listar usuários");
            System.out.println("3 Deletar usuário");
            System.out.println("4 Contar usuários");
            System.out.println("5 Depositar em usuário");
            System.out.println("6 Sacar em usuário");
            System.out.println("7 Buscar usuário");
            System.out.println("0 Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            if (opcao == 0) {
                System.out.println("Saindo do sistema.");
                break;
            }

            switch (opcao) {
                case 1:
                    System.out.println("----------------------------");
                    System.out.print("Nome do usuário: ");
                    String nome = scanner.nextLine();

                    System.out.print("CPF do usuário: ");
                    String cpf = scanner.nextLine();

                    // Criar usuário com saldo inicial zero
                    Usuario novoUsuario = new Usuario(nome, cpf, 0f);
                    usuarioDao.cadastrarUsuario(novoUsuario);
                    break;

                case 2:
                    List<Usuario> usuarios = usuarioDao.buscarUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        System.out.println("--- Lista de Usuários ---");
                        for (Usuario u : usuarios) {
                            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome() + " | CPF: " + u.getCpf() + " | Saldo: R$ " + u.getSaldo());
                        }
                        System.out.println("-------------------------");
                    }
                    break;

                case 3:
                    System.out.print("ID do usuário a ser deletado: ");
                    int idDeletar = scanner.nextInt();
                    usuarioDao.deletarUsuario(idDeletar);
                    break;

                case 4:
                    int totalUsuarios = usuarioDao.contarUsuarios();
                    System.out.println("Total de usuários cadastrados: " + totalUsuarios);
                    break;

                case 5:
                    System.out.print("ID do usuário para depósito: ");
                    int idDeposito = scanner.nextInt();
                    scanner.nextLine(); // consumir \n

                    System.out.print("Valor a depositar: ");
                    float valorDeposito = scanner.nextFloat();
                    scanner.nextLine(); // consumir \n

                    // Buscar usuário para atualizar saldo
                    List<Usuario> listaUsuarios = usuarioDao.buscarUsuarios();
                    Usuario usuarioParaDeposito = null;
                    for (Usuario u : listaUsuarios) {
                        if (u.getId() == idDeposito) {
                            usuarioParaDeposito = u;
                            break;
                        }
                    }

                    if (usuarioParaDeposito != null) {
                        usuarioParaDeposito.depositar(valorDeposito);
                        // Atualizar saldo no banco
                        usuarioDao.atualizarSaldo(usuarioParaDeposito);
                        System.out.println("Depósito realizado com sucesso.");
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;

                case 6:
                    System.out.println("ID do usuário para saque: ");
                    int IdSaque = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Valor a sacar: ");
                    float valorSaque = scanner.nextFloat();


                    //novaListaUsuarios = não consigo colocar o nome da lista do case 5
                    List<Usuario> novaListaUsuarios = usuarioDao.buscarUsuarios();
                    Usuario usuarioParaSaque = null;
                    for (Usuario u : novaListaUsuarios) {
                        if (u.getId() == IdSaque) {
                            usuarioParaSaque = u;
                            break;
                        }
                    }

                    if (usuarioParaSaque != null) {
                        usuarioParaSaque.sacar(valorSaque);
                        usuarioDao.atualizarSaldo(usuarioParaSaque);
                        System.out.println("Saque realizado com sucesso");
                    }else{
                        System.out.println("Usuário não encontrado");
                    }
                    break;


                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }
}