package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Turma;
import sistemaacademico.modelo.Avaliacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorAvaliacoes {

    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public void registrarAvaliacao(Scanner scanner, List<Turma> turmas) {
        if (turmas.isEmpty()) {
            System.out.println("Não há turmas criadas.");
            return;
        }

        System.out.println("Selecione a turma para registrar avaliações:");
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println((i + 1) + ". " + turmas.get(i).toString());
        }
        int opcaoTurma = scanner.nextInt();
        scanner.nextLine();

        if (opcaoTurma < 1 || opcaoTurma > turmas.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Turma turma = turmas.get(opcaoTurma - 1);

        List<Aluno> alunosMatriculados = turma.getAlunosMatriculados();
        if (alunosMatriculados.isEmpty()) {
            System.out.println("Nenhum aluno matriculado nessa turma.");
            return;
        }

        System.out.println("Alunos matriculados:");
        for (int i = 0; i < alunosMatriculados.size(); i++) {
            System.out.println((i + 1) + ". " + alunosMatriculados.get(i).getNome());
        }
        System.out.print("Escolha o aluno para registrar avaliação: ");
        int opcaoAluno = scanner.nextInt();
        scanner.nextLine();

        if (opcaoAluno < 1 || opcaoAluno > alunosMatriculados.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        Aluno aluno = alunosMatriculados.get(opcaoAluno - 1);

        // Checa se já existe avaliação para este aluno e disciplina
        boolean jaAvaliado = avaliacoes.stream()
            .anyMatch(a -> a.getNomeAluno().equals(aluno.getNome()) 
                        && a.getCodigoDisciplina().equals(turma.getDisciplina().getCodigo()));

        if (jaAvaliado) {
            System.out.println("Avaliação para este aluno e disciplina já foi registrada.");
            return;
        }

        System.out.print("Digite a média final do aluno: ");
        double mediaFinal = scanner.nextDouble();
        System.out.print("Digite a frequência do aluno (porcentagem): ");
        double frequencia = scanner.nextDouble();
        scanner.nextLine();

        // Regra simples para aprovação
        boolean aprovado = (mediaFinal >= 6.0) && (frequencia >= 75.0);

        if (aprovado) {
            aluno.adicionarDisciplinaAprovada(turma.getDisciplina());
            System.out.println("Aluno aprovado na disciplina " + turma.getDisciplina().getNome() + "!");
        } else {
            System.out.println("Aluno não foi aprovado.");
        }

        // Salva avaliação
        Avaliacao avaliacao = new Avaliacao(aluno.getNome(), turma.getDisciplina().getCodigo(), mediaFinal, frequencia);
        avaliacoes.add(avaliacao);
    }

    public void listarAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação registrada.");
            return;
        }

        System.out.println("\n=== LISTA DE AVALIAÇÕES ===");
        for (Avaliacao a : avaliacoes) {
            System.out.println(a);
        }
    }
}
