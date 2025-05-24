package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Disciplina;
import sistemaacademico.modelo.Turma;

import java.util.List;
import java.util.Scanner;

public class GerenciadorMatricula {
    private List<Turma> turmas;
    private List<Aluno> alunos;

    public GerenciadorMatricula(List<Turma> turmas, List<Aluno> alunos) {
        this.turmas = turmas;
        this.alunos = alunos;
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

        // Verificar se já está matriculado
        if (turma.getAlunosMatriculados().contains(aluno)) {
            System.out.println("Aluno já está matriculado nessa turma.");
            return;
        }

        // Verificar pré-requisitos da disciplina
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

        // Tenta matricular
        boolean matriculado = turma.matricularAluno(aluno);
        if (matriculado) {
            System.out.println("Aluno matriculado com sucesso!");
        } else {
            System.out.println("Não foi possível matricular o aluno (turma cheia ou limite atingido).");
        }
    }
}
