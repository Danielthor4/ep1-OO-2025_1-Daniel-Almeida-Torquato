package sistemaacademico.controle;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.AlunoNormal;
import sistemaacademico.modelo.AlunoEspecial;

public class GerenciadorAlunos {
    private List<Aluno> alunos = new ArrayList<>();

    public void cadastrarAluno(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();

        System.out.print("Tipo (1 - Normal | 2 - Especial): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // consumir \n

        // Verificar duplicidade antes de criar aluno
        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) {
                System.out.println("Já existe um aluno com essa matrícula!");
                return;
            }
        }

        Aluno aluno;
        if (tipo == 1) {
            aluno = new AlunoNormal(nome, matricula, curso);
        } else {
            aluno = new AlunoEspecial(nome, matricula, curso);
        }

        alunos.add(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void listarAlunos() {
        for (Aluno a : alunos) {
            System.out.println("Nome: " + a.getNome() + " | Matrícula: " + a.getMatricula());
        }
    }

    public List<Aluno> getAlunos() {
        return alunos;
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
            aluno.setNome(novoNome);  // 'nome' deve ser protected ou criar setter em Aluno
        }

        System.out.print("Novo curso (enter para manter atual): ");
        String novoCurso = scanner.nextLine();
        if (!novoCurso.isBlank()) {
            aluno.setCurso(novoCurso);
        }

        System.out.println("Aluno atualizado com sucesso.");
    }

    private Aluno buscarAlunoPorMatricula(String matricula) {
        for (Aluno a : alunos) { // corrigido de listaAlunos para alunos
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }
        return null;
    }
}
