package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Disciplina;
import sistemaacademico.modelo.Turma;
import sistemaacademico.modelo.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDisciplinas {
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;

    // Referência para o gerenciador de professores, para pegar objetos Professor
    private GerenciadorProfessor gerenciadorProfessor;

    // Construtor recebe o gerenciador de professores
    public GerenciadorDisciplinas(GerenciadorProfessor gerenciadorProfessor) {
        this.gerenciadorProfessor = gerenciadorProfessor;
        this.disciplinas = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    public void cadastrarDisciplina(Scanner scanner) {
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome da disciplina: ");
        String nome = scanner.nextLine();
        System.out.print("Carga horária: ");
        int carga = scanner.nextInt();
        scanner.nextLine();

        Disciplina d = new Disciplina(codigo, nome, carga);
        disciplinas.add(d);
        System.out.println("Disciplina cadastrada com sucesso!");
    }

    public void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            System.out.println("=== Disciplinas Cadastradas ===");
            for (Disciplina d : disciplinas) {
                System.out.println(d);
            }
        }
    }

    public void criarTurma(Scanner scanner) {
        if (disciplinas.isEmpty()) {
            System.out.println("Cadastre uma disciplina antes de criar turmas.");
            return;
        }

        if (gerenciadorProfessor.getProfessores().isEmpty()) {
            System.out.println("Cadastre um professor antes de criar turmas.");
            return;
        }

        System.out.println("Escolha a disciplina para a turma:");
        for (int i = 0; i < disciplinas.size(); i++) {
            System.out.println((i + 1) + ". " + disciplinas.get(i).getNome());
        }
        int escolha = scanner.nextInt();
        scanner.nextLine();
        if (escolha < 1 || escolha > disciplinas.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Disciplina disciplina = disciplinas.get(escolha - 1);

        // Mostrar lista de professores para escolher
        System.out.println("Escolha o professor para a turma:");
        List<Professor> professores = gerenciadorProfessor.getProfessores();
        for (int i = 0; i < professores.size(); i++) {
            System.out.println((i + 1) + ". " + professores.get(i).getNome() + " (" + professores.get(i).getDepartamento() + ")");
        }
        int escolhaProf = scanner.nextInt();
        scanner.nextLine();
        if (escolhaProf < 1 || escolhaProf > professores.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Professor professor = professores.get(escolhaProf - 1);

        System.out.print("Semestre (ex: 2024.2): ");
        String semestre = scanner.nextLine();
        System.out.print("Forma de avaliação (simples ou ponderada): ");
        String formaAvaliacao = scanner.nextLine();
        System.out.print("É presencial? (true/false): ");
        boolean presencial = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Sala: ");
        String sala = scanner.nextLine();
        System.out.print("Horário: ");
        String horario = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();

        Turma turma = new Turma(disciplina, professor, semestre, formaAvaliacao, presencial, sala, horario, capacidade);
        turmas.add(turma);
        System.out.println("Turma criada com sucesso!");
    }

    public void listarTurmas() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma criada.");
        } else {
            System.out.println("=== Turmas Criadas ===");
            for (Turma t : turmas) {
                System.out.println(t);
            }
        }
    }

    public List<Turma> getTurmas() {
        return turmas;
    }
    public void matricularAlunoEmTurma(Scanner scanner, List<Aluno> alunos) {
    if (turmas.isEmpty()) {
        System.out.println("Nenhuma turma disponível.");
        return;
    }

    System.out.println("Turmas disponíveis:");
    for (int i = 0; i < turmas.size(); i++) {
        System.out.println((i + 1) + ". " + turmas.get(i).getDisciplina().getNome() + " - " + turmas.get(i).getCodigo());
    }

    System.out.print("Escolha a turma (número): ");
    int indiceTurma = scanner.nextInt() - 1;
    scanner.nextLine();

    if (indiceTurma < 0 || indiceTurma >= turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turmaSelecionada = turmas.get(indiceTurma);

    System.out.print("Digite a matrícula do aluno: ");
    String matricula = scanner.nextLine();

    Aluno aluno = null;
    for (Aluno a : alunos) {
        if (a.getMatricula().equals(matricula)) {
            aluno = a;
            break;
        }
    }

    if (aluno == null) {
        System.out.println("Aluno não encontrado.");
        return;
    }

    turmaSelecionada.matricularAluno(aluno);
}
}
