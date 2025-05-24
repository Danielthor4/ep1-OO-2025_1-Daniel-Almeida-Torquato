package sistemaacademico;

import java.util.Scanner;
import sistemaacademico.controle.GerenciadorAlunos;
import sistemaacademico.controle.GerenciadorDisciplinas;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GerenciadorAlunos gerenciadorAlunos = new GerenciadorAlunos();
        GerenciadorDisciplinas gerenciadorDisciplina = new GerenciadorDisciplinas();

        int opcao;

        do {
            System.out.println("\n==== SISTEMA ACADÊMICO FCTE ====");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas/Turmas");
            System.out.println("3. Avaliações e Frequência");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir o \n

            switch (opcao) {
                case 1:
                    menuAlunos(scanner, gerenciadorAlunos);
                    break;
                case 2:
                    menuDisciplinas(scanner, gerenciadorDisciplina);
                    break;
                case 3:
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

    private static void menuAlunos(Scanner scanner, GerenciadorAlunos gerenciadorAlunos) {
        int opcaoAluno;
        do {
            System.out.println("\n=== MENU ALUNOS ===");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcaoAluno = scanner.nextInt();
            scanner.nextLine();

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
    }

    private static void menuDisciplinas(Scanner scanner, GerenciadorDisciplinas gerenciadorDisciplina) {
        int opcaoDisc;
        do {
            System.out.println("\n=== MENU DISCIPLINAS/TURMAS ===");
            System.out.println("1. Cadastrar disciplina");
            System.out.println("2. Criar turma");
            System.out.println("3. Listar disciplinas");
            System.out.println("4. Listar turmas");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcaoDisc = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoDisc) {
                case 1:
                    gerenciadorDisciplina.cadastrarDisciplina(scanner);
                    break;
                case 2:
                    gerenciadorDisciplina.criarTurma(scanner);
                    break;
                case 3:
                    gerenciadorDisciplina.listarDisciplinas();
                    break;
                case 4:
                    gerenciadorDisciplina.listarTurmas();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcaoDisc != 0);
    }
}
