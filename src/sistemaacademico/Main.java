package sistemaacademico;

import java.util.Scanner;
import sistemaacademico.controle.GerenciadorAlunos;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GerenciadorAlunos gerenciadorAlunos = new GerenciadorAlunos();

        int opcao;

        do {
            System.out.println("==== SISTEMA ACADÊMICO FCTE ====");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas/Turmas");
            System.out.println("3. Avaliações e Frequência");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir o \n deixado pelo nextInt

            switch (opcao) {
                case 1:
                    int opcaoAluno;
                    do {
                        System.out.println("\n=== MENU ALUNOS ===");
                        System.out.println("1. Cadastrar aluno");
                        System.out.println("2. Listar alunos");
                        System.out.println("0. Voltar");
                        System.out.print("Escolha: ");
                        opcaoAluno = scanner.nextInt();
                        scanner.nextLine(); // consumir \n

                        switch (opcaoAluno) {
                            case 1:
                                gerenciadorAlunos.cadastrarAluno(scanner);
                                break;
                            case 2:
                                gerenciadorAlunos.listarAlunos();
                                break;
                            case 0:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida.");
                        }
                    } while (opcaoAluno != 0);
                    break;

                case 2:
                    // menu de disciplinas/turmas
                    System.out.println("Funcionalidade em desenvolvimento.");
                    break;
                case 3:
                    // menu de avaliação/frequência
                    System.out.println("Funcionalidade em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
