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

        Aluno aluno;
        if (tipo == 1) {
            aluno = new AlunoNormal(nome, matricula, curso);
        } else {
            aluno = new AlunoEspecial(nome, matricula, curso);
        }

        // Verificar duplicidade
        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) {
                System.out.println("Já existe um aluno com essa matrícula!");
                return;
            }
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
}