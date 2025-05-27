package sistemaacademico;

import java.util.Scanner;
import sistemaacademico.controle.GerenciadorAlunos;
import sistemaacademico.controle.GerenciadorDisciplinas;
import sistemaacademico.controle.GerenciadorMatricula;
import sistemaacademico.controle.GerenciadorAvaliacoes;
import sistemaacademico.controle.GerenciadorProfessor;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instancia os gerenciadores
        GerenciadorAlunos gerenciadorAlunos = new GerenciadorAlunos();
        GerenciadorProfessor gerenciadorProfessor = new GerenciadorProfessor();
        GerenciadorDisciplinas gerenciadorDisciplina = new GerenciadorDisciplinas(gerenciadorProfessor);
        GerenciadorAvaliacoes gerenciadorAvaliacoes = new GerenciadorAvaliacoes();
        GerenciadorMatricula gerenciadorMatricula = new GerenciadorMatricula();

        // Carregar dados salvos (assumindo que esses métodos existem)
        gerenciadorAlunos.carregarDados();
        gerenciadorProfessor.carregarProfessores();
        gerenciadorDisciplina.carregarDados();
        gerenciadorAvaliacoes.carregarAvaliacoes();
        gerenciadorMatricula.carregarAlunos();

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
                    menuAlunos(scanner, gerenciadorAlunos, gerenciadorDisciplina, gerenciadorMatricula);
                    break;
                case 2:
                    menuDisciplinas(scanner, gerenciadorDisciplina, gerenciadorAlunos, gerenciadorMatricula);
                    break;
                case 3:
                    menuProfessores(scanner, gerenciadorProfessor);
                    break;
                case 4:
                    menuAvaliacoes(scanner, gerenciadorAvaliacoes, gerenciadorDisciplina);
                    break;
                case 0:
                    System.out.println("Encerrando e salvando dados...");
                    // Salvar dados antes de sair
                    gerenciadorAlunos.salvarDados();
                    gerenciadorProfessor.salvarDados();
                    gerenciadorDisciplina.salvarDisciplinas();
                    gerenciadorAvaliacoes.salvarAvaliacoes();
                    gerenciadorMatricula.salvarDados();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // Os menus seguem iguais, não alterei nada neles para manter foco na persistência
    private static void menuAlunos(Scanner scanner, GerenciadorAlunos gerenciadorAlunos, GerenciadorDisciplinas gerenciadorDisciplina, GerenciadorMatricula gerenciadorMatricula) {
        int opcaoAluno;
        do {
            System.out.println("\n=== MENU ALUNOS ===");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos");
            System.out.println("3. Editar aluno");
            System.out.println("4. Remover aluno");
            System.out.println("5. Matricular aluno em turma");
            System.out.println("6. Trancar disciplina");
            System.out.println("7. Trancar semestre");
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
                case 5:
                    gerenciadorDisciplina.matricularAlunoEmTurma(scanner, gerenciadorAlunos.getAlunos());  
                    break;
                case 6:
                    gerenciadorMatricula.trancarDisciplina(scanner);
                    break;
                case 7:
                    gerenciadorAlunos.trancarSemestre(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcaoAluno != 0);
    }

    private static void menuDisciplinas(Scanner scanner, GerenciadorDisciplinas gerenciadorDisciplina, GerenciadorAlunos gerenciadorAlunos, GerenciadorMatricula gerenciadorMatricula) {
        int opcaoDisc;
        do {
            System.out.println("\n=== MENU DISCIPLINAS/TURMAS ===");
            System.out.println("1. Cadastrar disciplina");
            System.out.println("2. Criar turma");
            System.out.println("3. Listar disciplinas");
            System.out.println("4. Listar turmas");
            System.out.println("5. Editar disciplina");
            System.out.println("6. Editar turma");
            System.out.println("7. Remover disciplina");
            System.out.println("8. Remover turma");
            System.out.println("9. Matricular aluno em turma");
            System.out.println("10. Trancar turma");
            System.out.println("11. Trancar semestre");
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
                    gerenciadorDisciplina.editarDisciplina(scanner);
                    break;
                case 6:
                    gerenciadorDisciplina.editarTurma(scanner);
                    break;
                case 7:
                    gerenciadorDisciplina.removerDisciplina(scanner);
                    break;
                case 8:
                    gerenciadorDisciplina.removerTurma(scanner);
                    break;
                case 9:
                    gerenciadorDisciplina.matricularAlunoEmTurma(scanner, gerenciadorAlunos.getAlunos());  
                    break;
                case 10:
                    gerenciadorMatricula.trancarDisciplina(scanner);
                    break;
                case 11:
                    gerenciadorAlunos.trancarSemestre(scanner);
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
            System.out.println("4. Remover professor");
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
                case 4:
                    gerenciadorProfessor.removerProfessor(scanner);
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
            System.out.println("\n=== MENU AVALIAÇÕES/FREQUÊNCIA ===");
            System.out.println("1. Registrar avaliação");
            System.out.println("2. Listar avaliações");
            System.out.println("3. Editar avaliação e presença");
            System.out.println("4. Lançar Notas e Presença");
            System.out.println("5. Calcular e listar média final");
            System.out.println("6. Exibir relatório de notas e presença por turma");
            System.out.println("7. Exibir relatório de notas e presença por disciplina");
            System.out.println("8. Exibir relatório de notas e presença por professor");
            System.out.println("9. Exibir boletim individual de aluno com ou sem dados das turmas");
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
                case 3:
                    gerenciadorAvaliacoes.editarAvaliacao(scanner, gerenciadorDisciplinas.getTurmas());
                    break;
                case 4:
                    gerenciadorAvaliacoes.lancarNotasEPresenca(scanner, gerenciadorDisciplinas.getTurmas());
                    break;
                case 5:
                    gerenciadorAvaliacoes.mostrarSituacaoAluno(gerenciadorDisciplinas.getTurmas(), scanner);
                    break;
                case 6:
                    gerenciadorAvaliacoes.relatorioPorTurma(gerenciadorDisciplinas.getTurmas(), scanner);
                    break;
                case 7:
                    gerenciadorAvaliacoes.relatorioPorDisciplina(gerenciadorDisciplinas.getTurmas(), scanner);
                    break;
                case 8:
                    gerenciadorAvaliacoes.relatorioPorProfessor(gerenciadorDisciplinas.getTurmas(), scanner);
                    break;
                case 9:
                    gerenciadorAvaliacoes.exibirBoletimIndividual(scanner, gerenciadorDisciplinas.getTurmas(), gerenciadorAvaliacoes.getAvaliacoes());
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
