package sistemaacademico.controle;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.AlunoNormal;
import sistemaacademico.modelo.Turma;
import sistemaacademico.modelo.AlunoEspecial;

public class GerenciadorAlunos {
    private List<Aluno> alunos = new ArrayList<>();
    private static final String ARQUIVO_ALUNOS = "alunos.dat";

    public GerenciadorAlunos() {
        carregarDados();
    }

    public void cadastrarAluno(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();
        System.out.print("Semestre atual: ");
        String semestreAtual = scanner.nextLine();

        System.out.print("Tipo (1 - Normal | 2 - Especial): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // consumir \n

        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) {
                System.out.println("Já existe um aluno com essa matrícula!");
                return;
            }
        }

        Aluno aluno;
        if (tipo == 1) {
            aluno = new AlunoNormal(nome, matricula, curso, semestreAtual);
        } else {
            aluno = new AlunoEspecial(nome, matricula, curso, semestreAtual);
        }

        alunos.add(aluno);
        salvarDados();
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void listarAlunos() {
        for (Aluno a : alunos) {
            String tipoAluno = (a instanceof AlunoEspecial) ? "Especial" : "Normal";
            System.out.println("Nome: " + a.getNome() + " | Matrícula: " + a.getMatricula() + " | Tipo: " + tipoAluno);
            System.out.println("  -> Semestre Atual: " + a.getSemestreAtual());

            List<Turma> turmas = a.getTurmasMatriculadas();
            if (turmas.isEmpty()) {
                System.out.println("  -> Nenhuma turma matriculada.");
            } else {
                System.out.println("  -> Turmas:");
                for (Turma t : turmas) {
                    boolean trancada = t.isTrancada(a);
                    System.out.println("     - " + t.getDisciplina().getNome() +
                                       " | Código: " + t.getCodigo() +
                                       " | Semestre: " + t.getSemestre() +
                                       (trancada ? " [TRANCADA]" : ""));
                }
            }
            System.out.println();
        }
    }

    public void editarAluno(Scanner scanner) {
        System.out.print("Digite a matrícula do aluno a ser editado: ");
        String matricula = scanner.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Aluno encontrado: " + aluno.getNome() + ", Curso: " + aluno.getCurso());

        System.out.print("Novo nome (enter para manter atual): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            aluno.setNome(novoNome);
        }

        System.out.print("Novo curso (enter para manter atual): ");
        String novoCurso = scanner.nextLine();
        if (!novoCurso.isBlank()) {
            aluno.setCurso(novoCurso);
        }

        salvarDados();
        System.out.println("Aluno atualizado com sucesso.");
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }
        return null;
    }

    public void removerAluno(Scanner scanner) {
        System.out.print("Digite a matrícula do aluno a ser removido: ");
        String matricula = scanner.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        alunos.remove(aluno);
        salvarDados();
        System.out.println("Aluno removido com sucesso!");
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void trancarSemestre(Scanner scanner) {
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = scanner.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.print("Digite o semestre a ser trancado (ex: 2025.2): ");
        String semestre = scanner.nextLine();

        boolean algumaTrancada = false;
        for (Turma turma : aluno.getTurmasMatriculadas()) {
            if (turma.getSemestre().equals(semestre) && !turma.isTrancada(aluno)) {
                turma.trancar(aluno);
                algumaTrancada = true;
                System.out.println("Turma " + turma.getCodigo() + " trancada.");
            }
        }

        if (algumaTrancada) {
            salvarDados();
        } else {
            System.out.println("Nenhuma turma encontrada no semestre " + semestre + " para trancar.");
        }
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_ALUNOS))) {
            oos.writeObject(alunos);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados dos alunos: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void carregarDados() {
    File arquivo = new File(ARQUIVO_ALUNOS);
    if (arquivo.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                alunos = (List<Aluno>) obj;
            } else {
                System.out.println("Arquivo de alunos está em formato inválido.");
                alunos = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados dos alunos: " + e.getMessage());
        }
    } else {
        alunos = new ArrayList<>();
    }
}
}
