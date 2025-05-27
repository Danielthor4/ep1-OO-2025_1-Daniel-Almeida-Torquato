package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Disciplina;
import sistemaacademico.modelo.Turma;
import sistemaacademico.modelo.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDisciplinas {
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;

    private static final String ARQ_DISCIPLINAS = "disciplinas.dat";
    private static final String ARQ_TURMAS = "turmas.dat";

    private GerenciadorProfessor gerenciadorProfessor;

    public GerenciadorDisciplinas(GerenciadorProfessor gerenciadorProfessor) {
        this.gerenciadorProfessor = gerenciadorProfessor;
        carregarDados();
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
        salvarDisciplinas();
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

        System.out.print("Semestre (ex: 2025.1): ");
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
        salvarTurmas();
        System.out.println("Turma criada com sucesso!");
    }

    public void listarTurmas() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma criada.");
        } else {
            System.out.println("=== Turmas Criadas ===");
            for (Turma t : turmas) {
                System.out.println(t);

                List<Aluno> alunos = t.getAlunosMatriculados();
                if (alunos.isEmpty()) {
                    System.out.println("   -> Nenhum aluno matriculado.");
                } else {
                    System.out.println("   -> Alunos matriculados:");
                    for (Aluno a : alunos) {
                        boolean trancada = t.isTrancada(a);
                        System.out.println("      - " + a.getNome() + " (" + a.getMatricula() + ")" +
                                (trancada ? " [TRANCADO]" : ""));
                    }
                }
                System.out.println();
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

        for (Turma t : aluno.getTurmasMatriculadas()) {
            if (t.getHorario().equals(turmaSelecionada.getHorario())) {
                System.out.println("Erro: Choque de horário com a turma " + t.getDisciplina().getNome());
                return;
            }
        }

        boolean sucesso = turmaSelecionada.matricularAluno(aluno);

        if (sucesso) {
            aluno.adicionarTurma(turmaSelecionada);
            salvarTurmas(); // Importante: salva o novo estado da turma com aluno
            System.out.println("Aluno matriculado com sucesso!");
        } else {
            System.out.println("Não foi possível matricular o aluno (limite ou capacidade atingida).");
        }
    }
    @SuppressWarnings("unchecked")
    public void carregarDados() {
        // Disciplinas
        File arq1 = new File(ARQ_DISCIPLINAS);
        if (arq1.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq1))) {
                disciplinas = (List<Disciplina>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
                disciplinas = new ArrayList<>();
            }
        } else {
            disciplinas = new ArrayList<>();
        }

        // Turmas
        File arq2 = new File(ARQ_TURMAS);
        if (arq2.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq2))) {
                turmas = (List<Turma>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Erro ao carregar turmas: " + e.getMessage());
                turmas = new ArrayList<>();
            }
        } else {
            turmas = new ArrayList<>();
        }
    }

    public void salvarDisciplinas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_DISCIPLINAS))) {
            oos.writeObject(disciplinas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }

    public void salvarTurmas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ_TURMAS))) {
            oos.writeObject(turmas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }
    public void editarDisciplina(Scanner scanner) {
    if (disciplinas.isEmpty()) {
        System.out.println("Nenhuma disciplina cadastrada.");
        return;
    }

    System.out.print("Digite o código da disciplina a ser editada: ");
    String codigo = scanner.nextLine();

    Disciplina disciplina = null;
    for (Disciplina d : disciplinas) {
        if (d.getCodigo().equalsIgnoreCase(codigo)) {
            disciplina = d;
            break;
        }
    }

    if (disciplina == null) {
        System.out.println("Disciplina não encontrada.");
        return;
    }

    System.out.println("Disciplina encontrada: " + disciplina.getNome() + " (Carga: " + disciplina.getCargaHoraria() + "h)");

    System.out.print("Novo nome (pressione Enter para manter): ");
    String novoNome = scanner.nextLine();
    if (!novoNome.isBlank()) {
        disciplina.setNome(novoNome);
    }

    System.out.print("Nova carga horária (ou Enter para manter): ");
    String novaCarga = scanner.nextLine();
    if (!novaCarga.isBlank()) {
        try {
            int novaCargaInt = Integer.parseInt(novaCarga);
            disciplina.setCargaHoraria(novaCargaInt);
        } catch (NumberFormatException e) {
            System.out.println("Carga horária inválida. Mantida a original.");
        }
    }

    salvarDisciplinas();
    System.out.println("Disciplina atualizada com sucesso.");
}
    public void editarTurma(Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Nenhuma turma cadastrada.");
        return;
    }

    System.out.println("=== Turmas Disponíveis ===");
    for (int i = 0; i < turmas.size(); i++) {
        Turma t = turmas.get(i);
        System.out.println((i + 1) + ". " + t.getDisciplina().getNome() + " - " + t.getCodigo());
    }

    System.out.print("Escolha a turma que deseja editar (número): ");
    int escolha = scanner.nextInt();
    scanner.nextLine();

    if (escolha < 1 || escolha > turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turma = turmas.get(escolha - 1);

    System.out.println("Editando turma da disciplina: " + turma.getDisciplina().getNome());

    System.out.print("Novo semestre (atual: " + turma.getSemestre() + "): ");
    String semestre = scanner.nextLine();
    if (!semestre.isBlank()) {
        turma.setSemestre(semestre);
    }

    System.out.print("Nova forma de avaliação (atual: " + turma.getFormaAvaliacao() + "): ");
    String forma = scanner.nextLine();
    if (!forma.isBlank()) {
        turma.setFormaAvaliacao(forma);
    }

    System.out.print("É presencial? (atual: " + turma.isPresencial() + ") [true/false ou Enter para manter]: ");
    String presencialStr = scanner.nextLine();
    if (!presencialStr.isBlank()) {
        turma.setPresencial(Boolean.parseBoolean(presencialStr));
    }

    System.out.print("Nova sala (atual: " + turma.getSala() + "): ");
    String sala = scanner.nextLine();
    if (!sala.isBlank()) {
        turma.setSala(sala);
    }

    System.out.print("Novo horário (atual: " + turma.getHorario() + "): ");
    String horario = scanner.nextLine();
    if (!horario.isBlank()) {
        turma.setHorario(horario);
    }

    System.out.print("Nova capacidade (atual: " + turma.getCapacidadeMaxima() + "): ");
    String capacidadeStr = scanner.nextLine();
    if (!capacidadeStr.isBlank()) {
        try {
            int novaCapacidade = Integer.parseInt(capacidadeStr);
            turma.setCapacidadeMaxima(novaCapacidade);
        } catch (NumberFormatException e) {
            System.out.println("Capacidade inválida. Mantida a original.");
        }
    }

    salvarTurmas();
    System.out.println("Turma atualizada com sucesso.");
}
    public void removerTurma(Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Nenhuma turma cadastrada.");
        return;
    }

    System.out.println("=== Turmas Cadastradas ===");
    for (int i = 0; i < turmas.size(); i++) {
        Turma t = turmas.get(i);
        System.out.println((i + 1) + ". " + t.getDisciplina().getNome() + " - " + t.getCodigo());
    }

    System.out.print("Digite o número da turma que deseja remover: ");
    int escolha = scanner.nextInt();
    scanner.nextLine();

    if (escolha < 1 || escolha > turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turmaRemovida = turmas.remove(escolha - 1);
    salvarTurmas();
    System.out.println("Turma '" + turmaRemovida.getCodigo() + "' removida com sucesso.");
}
    public void removerDisciplina(Scanner scanner) {
    if (disciplinas.isEmpty()) {
        System.out.println("Nenhuma disciplina cadastrada.");
        return;
    }

    System.out.println("=== Disciplinas Cadastradas ===");
    for (int i = 0; i < disciplinas.size(); i++) {
        Disciplina d = disciplinas.get(i);
        System.out.println((i + 1) + ". " + d.getNome() + " (" + d.getCodigo() + ")");
    }

    System.out.print("Digite o número da disciplina que deseja remover: ");
    int escolha = scanner.nextInt();
    scanner.nextLine();

    if (escolha < 1 || escolha > disciplinas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Disciplina disciplinaSelecionada = disciplinas.get(escolha - 1);

    // Verifica se há turmas associadas
    for (Turma t : turmas) {
        if (t.getDisciplina().equals(disciplinaSelecionada)) {
            System.out.println("Não é possível remover: há turmas associadas a esta disciplina.");
            return;
        }
    }

    disciplinas.remove(disciplinaSelecionada);
    salvarDisciplinas();
    System.out.println("Disciplina '" + disciplinaSelecionada.getNome() + "' removida com sucesso.");
}
}
