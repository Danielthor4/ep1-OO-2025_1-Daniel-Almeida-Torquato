package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Disciplina;
import sistemaacademico.modelo.Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorMatricula {
    private List<Turma> turmas;
    private List<Aluno> alunos;

    private static final String ARQUIVO_TURMAS = "turmas.dat";
    private static final String ARQUIVO_ALUNOS = "alunos.dat";

    public GerenciadorMatricula() {
        this.turmas = carregarTurmas();
        this.alunos = carregarAlunos();
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    @SuppressWarnings("unchecked")
    public void carregarDados() {
    File arquivo = new File("alunos.dat");
    if (arquivo.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            alunos = (List<Aluno>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados dos alunos: " + e.getMessage());
            alunos = new ArrayList<>();
        }
    } else {
        alunos = new ArrayList<>();
    }
}

    public void matricularAluno(Scanner scanner) {
        if (alunos.isEmpty()) {
            System.out.println("Não há alunos cadastrados.");
            return;
        }
        if (turmas.isEmpty()) {
            System.out.println("Não há turmas criadas.");
            return;
        }

        System.out.println("Selecione o aluno para matricular:");
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println((i + 1) + ". " + alunos.get(i).getNome() + " (" + alunos.get(i).getMatricula() + ")");
        }
        int opcaoAluno = scanner.nextInt();
        scanner.nextLine();
        if (opcaoAluno < 1 || opcaoAluno > alunos.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Aluno aluno = alunos.get(opcaoAluno - 1);

        System.out.println("Selecione a turma para matricular:");
        for (int i = 0; i < turmas.size(); i++) {
            Turma t = turmas.get(i);
            System.out.println((i + 1) + ". " + t.toString());
        }
        int opcaoTurma = scanner.nextInt();
        scanner.nextLine();
        if (opcaoTurma < 1 || opcaoTurma > turmas.size()) {
            System.out.println("Opção inválida.");
            return;
        }
        Turma turma = turmas.get(opcaoTurma - 1);

        if (turma.getAlunosMatriculados().contains(aluno)) {
            System.out.println("Aluno já está matriculado nessa turma.");
            return;
        }

        // Verificar pré-requisitos
        Disciplina disciplina = turma.getDisciplina();
        List<String> preReqs = disciplina.getPreRequisitos();
        for (String codigoPreReq : preReqs) {
            boolean preReqCumprido = aluno.getDisciplinasAprovadas().stream()
                .anyMatch(d -> d.getCodigo().equals(codigoPreReq));
            if (!preReqCumprido) {
                System.out.println("Aluno não cumpriu o pré-requisito: " + codigoPreReq);
                return;
            }
        }

        boolean matriculado = turma.matricularAluno(aluno);
        if (matriculado) {
            aluno.adicionarTurma(turma);
            salvarDados(); // salvar após matrícula
            System.out.println("Aluno matriculado com sucesso!");
        } else {
            System.out.println("Não foi possível matricular o aluno (turma cheia ou limite atingido).");
        }
    }

    public void trancarDisciplina(Scanner scanner) {
    System.out.println("Matrículas cadastradas:");
    for (Aluno a : alunos) {
        System.out.println("- " + a.getMatricula());
    }

    if (alunos.isEmpty()) {
        System.out.println("Não há alunos cadastrados.");
        return;
    }
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas criadas.");
        return;
    }

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

    // Mostrar turmas em que o aluno está matriculado
    System.out.println("Turmas em que o aluno está matriculado:");
    for (Turma t : aluno.getTurmasMatriculadas()) {
        System.out.println("- " + t.getCodigo() + " (" + t.getDisciplina().getNome() + ")");
    }

    System.out.print("Digite o código da turma a ser trancada: ");
    String codigoTurma = scanner.nextLine();

    Turma turmaParaTrancar = null;
    for (Turma t : aluno.getTurmasMatriculadas()) {
        if (t.getCodigo().equalsIgnoreCase(codigoTurma)) {
            turmaParaTrancar = t;
            break;
        }
    }

    if (turmaParaTrancar == null) {
        System.out.println("Aluno não está matriculado nessa turma.");
        return;
    }

    if (turmaParaTrancar.isTrancada(aluno)) {
        System.out.println("Essa disciplina já está trancada para o aluno.");
        return;
    }

    // Atualiza tanto o lado da turma quanto o lado do aluno
    aluno.trancarTurma(turmaParaTrancar);
    turmaParaTrancar.trancar(aluno);

    salvarDados(); // salvar após trancamento
    System.out.println("Disciplina " + turmaParaTrancar.getCodigo() + " trancada com sucesso para o aluno " + aluno.getNome());
}

    public void salvarDados() {
        salvarObjeto(ARQUIVO_TURMAS, turmas);
        salvarObjeto(ARQUIVO_ALUNOS, alunos);
    }

    public void salvarObjeto(String nomeArquivo, Object objeto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(objeto);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public List<Turma> carregarTurmas() {
        Object obj = carregarObjeto(ARQUIVO_TURMAS);
        if (obj instanceof List) {
            return (List<Turma>) obj;
        }
        return new ArrayList<>();
    }
    @SuppressWarnings("unchecked")
    public List<Aluno> carregarAlunos() {
        Object obj = carregarObjeto(ARQUIVO_ALUNOS);
        if (obj instanceof List) {
            return (List<Aluno>) obj;
        }
        return new ArrayList<>();
    }

    public Object carregarObjeto(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
}
