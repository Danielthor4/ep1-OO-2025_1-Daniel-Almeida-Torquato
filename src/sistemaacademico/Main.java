package sistemaacademico;

import java.util.Scanner;
import sistemaacademico.controle.GerenciadorAlunos;
import sistemaacademico.controle.GerenciadorDisciplinas;
import sistemaacademico.controle.GerenciadorAvaliacoes;
import sistemaacademico.controle.GerenciadorProfessor;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GerenciadorAlunos gerenciadorAlunos = new GerenciadorAlunos();
        GerenciadorProfessor gerenciadorProfessor = new GerenciadorProfessor();
        GerenciadorDisciplinas gerenciadorDisciplina = new GerenciadorDisciplinas(gerenciadorProfessor);
        GerenciadorAvaliacoes gerenciadorAvaliacoes = new GerenciadorAvaliacoes();

        int opcao;

        do {
            System.out.println("\n==== SISTEMA ACADÊMICO FCTE ====");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas e Turmas");
            System.out.println("3. Gerenciar Professores");
            System.out.println("4. Avaliações e Frequência");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir o \n deixado pelo nextInt

            switch (opcao) {
                case 1:
                    menuAlunos(scanner, gerenciadorAlunos);
                    break;
                case 2:
                    menuDisciplinas(scanner, gerenciadorDisciplina, gerenciadorAlunos);
                    break;
                case 3:
                    menuProfessores(scanner, gerenciadorProfessor);
                    break;
                case 4:
                    menuAvaliacoes(scanner, gerenciadorAvaliacoes, gerenciadorDisciplina);
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
            System.out.println("3. Editar aluno");
            System.out.println("4. Remover aluno");
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
                case 3:
                    gerenciadorAlunos.editarAluno(scanner);
                    break;
                case 4:
                    gerenciadorAlunos.removerAluno(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcaoAluno != 0);
    }

    private static void menuDisciplinas(Scanner scanner, GerenciadorDisciplinas gerenciadorDisciplina, GerenciadorAlunos gerenciadorAlunos) {
        int opcaoDisc;
        do {
            System.out.println("\n=== MENU DISCIPLINAS/TURMAS ===");
            System.out.println("1. Cadastrar disciplina");
            System.out.println("2. Criar turma");
            System.out.println("3. Listar disciplinas");
            System.out.println("4. Listar turmas");
            System.out.println("5. Matricular aluno em turma");
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
                case 5:
                    gerenciadorDisciplina.matricularAlunoEmTurma(scanner, gerenciadorAlunos.getAlunos());  
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcaoDisc != 0);
    }

    private static void menuProfessores(Scanner scanner, GerenciadorProfessor gerenciadorProfessor) {
    int opcaoProf;
    do {
        System.out.println("\n=== MENU PROFESSORES ===");
        System.out.println("1. Cadastrar professor");
        System.out.println("2. Listar professores");
        System.out.println("3. Editar professor");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        opcaoProf = scanner.nextInt();
        scanner.nextLine();

        switch (opcaoProf) {
            case 1:
                gerenciadorProfessor.cadastrarProfessor(scanner);
                break;
            case 2:
                gerenciadorProfessor.listarProfessores();
                break;
            case 3:
                gerenciadorProfessor.editarProfessor(scanner);
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    } while (opcaoProf != 0);
}


    private static void menuAvaliacoes(Scanner scanner, GerenciadorAvaliacoes gerenciadorAvaliacoes, GerenciadorDisciplinas gerenciadorDisciplinas) {
        int opcaoAvaliacao;
        do {
            System.out.println("\n=== MENU AVALIAÇÕES ===");
            System.out.println("1. Registrar avaliação");
            System.out.println("2. Listar avaliações");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcaoAvaliacao = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoAvaliacao) {
                case 1:
                    gerenciadorAvaliacoes.registrarAvaliacao(scanner, gerenciadorDisciplinas.getTurmas());
                    break;
                case 2:
                    gerenciadorAvaliacoes.listarAvaliacoes();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcaoAvaliacao != 0);
    }
}
