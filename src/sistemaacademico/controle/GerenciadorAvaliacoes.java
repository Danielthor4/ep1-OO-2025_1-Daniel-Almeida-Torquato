package sistemaacademico.controle;

import sistemaacademico.modelo.Aluno;
import sistemaacademico.modelo.Turma;
import sistemaacademico.modelo.Avaliacao;
import sistemaacademico.modelo.Disciplina;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorAvaliacoes {

    private List<Avaliacao> avaliacoes;

    private static final String ARQUIVO = "avaliacoes.dat";

    public GerenciadorAvaliacoes() {
        carregarAvaliacoes();
    }

    public void registrarAvaliacao(Scanner scanner, List<Turma> turmas) {
        if (turmas.isEmpty()) {
            System.out.println("Não há turmas criadas.");
            return;
        }

        System.out.println("Selecione a turma para registrar avaliações:");
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println((i + 1) + ". " + turmas.get(i));
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

        boolean aprovado = (mediaFinal >= 5.0) && (frequencia >= 75.0);

        if (aprovado) {
            aluno.adicionarDisciplinaAprovada(turma.getDisciplina());
            System.out.println("Aluno aprovado na disciplina " + turma.getDisciplina().getNome() + "!");
        } else {
            System.out.println("Aluno não foi aprovado.");
        }

        Avaliacao avaliacao = new Avaliacao(aluno.getNome(), turma.getDisciplina().getCodigo(), mediaFinal, frequencia);
        avaliacoes.add(avaliacao);
        salvarAvaliacoes();
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

    public void salvarAvaliacoes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(avaliacoes);
        } catch (IOException e) {
            System.out.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarAvaliacoes() {
        File arquivo = new File(ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                avaliacoes = (List<Avaliacao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Erro ao carregar avaliações: " + e.getMessage());
                avaliacoes = new ArrayList<>();
            }
        } else {
            avaliacoes = new ArrayList<>();
        }
    }
    public void lancarNotasEPresenca(Scanner scanner, List<Turma> turmas) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas disponíveis.");
        return;
    }

    System.out.println("\n=== SELECIONE A TURMA ===");
    for (int i = 0; i < turmas.size(); i++) {
        System.out.println((i + 1) + ". " + turmas.get(i));
    }
    int opcaoTurma = scanner.nextInt();
    scanner.nextLine();

    if (opcaoTurma < 1 || opcaoTurma > turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turma = turmas.get(opcaoTurma - 1);
    List<Aluno> alunos = turma.getAlunosMatriculados();

    if (alunos.isEmpty()) {
        System.out.println("Nenhum aluno matriculado nesta turma.");
        return;
    }

    System.out.println("\n=== SELECIONE O ALUNO ===");
    for (int i = 0; i < alunos.size(); i++) {
        System.out.println((i + 1) + ". " + alunos.get(i).getNome());
    }
    int opcaoAluno = scanner.nextInt();
    scanner.nextLine();

    if (opcaoAluno < 1 || opcaoAluno > alunos.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Aluno aluno = alunos.get(opcaoAluno - 1);

    System.out.println("Lançando notas para " + aluno.getNome() + ":");
    Double[] notas = new Double[5];
    String[] nomesNotas = { "P1", "P2", "P3", "Listas (L)", "Seminário (S)" };
    for (int i = 0; i < 5; i++) {
        System.out.print(nomesNotas[i] + ": ");
        notas[i] = scanner.nextDouble();
    }

    System.out.print("Informe a presença (%): ");
    int presenca = scanner.nextInt();
    scanner.nextLine();

    int indexAluno = turma.getAlunosMatriculados().indexOf(aluno);
    if (indexAluno == -1) {
        System.out.println("Aluno não encontrado na turma.");
        return;
    }

    turma.getNotas().set(indexAluno, notas);
    turma.getPresencas().set(indexAluno, presenca);

    double mediaFinal = turma.calcularMediaFinal(notas);
    boolean aprovado = turma.aprovado(presenca, mediaFinal, aluno);

    System.out.printf("Média final: %.2f - Presença: %d%% - %s\n", mediaFinal, presenca,
                      aprovado ? "APROVADO" : "REPROVADO");

    // Registrar avaliação no histórico (caso ainda não esteja registrada)
    boolean jaAvaliado = avaliacoes.stream()
        .anyMatch(a -> a.getNomeAluno().equals(aluno.getNome())
                    && a.getCodigoDisciplina().equals(turma.getDisciplina().getCodigo()));

    if (!jaAvaliado) {
        Avaliacao avaliacao = new Avaliacao(aluno.getNome(), turma.getDisciplina().getCodigo(), mediaFinal, presenca);
        avaliacoes.add(avaliacao);
        if (aprovado) {
            aluno.adicionarDisciplinaAprovada(turma.getDisciplina());
        }
        salvarAvaliacoes();
    }
}
    public void editarAvaliacao(Scanner scanner, List<Turma> turmas) {
    if (avaliacoes.isEmpty()) {
        System.out.println("Nenhuma avaliação registrada.");
        return;
    }

    System.out.println("\n=== AVALIAÇÕES REGISTRADAS ===");
    for (int i = 0; i < avaliacoes.size(); i++) {
        Avaliacao a = avaliacoes.get(i);
        System.out.printf("%d. Aluno: %s | Disciplina: %s | Média: %.2f | Frequência: %.0f%%\n",
                i + 1, a.getNomeAluno(), a.getCodigoDisciplina(), a.getMediaFinal(), a.getFrequencia());
    }

    System.out.print("Escolha o número da avaliação a ser editada: ");
    int opcao = scanner.nextInt();
    scanner.nextLine();

    if (opcao < 1 || opcao > avaliacoes.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Avaliacao avaliacao = avaliacoes.get(opcao - 1);

    System.out.print("Nova média final: ");
    double novaMedia = scanner.nextDouble();
    System.out.print("Nova frequência (%): ");
    double novaFrequencia = scanner.nextDouble();
    scanner.nextLine();

    avaliacao.setMediaFinal(novaMedia);
    avaliacao.setFrequencia(novaFrequencia);

    // Atualizar aprovação
    String codDisciplina = avaliacao.getCodigoDisciplina();
    String nomeAluno = avaliacao.getNomeAluno();
    Aluno aluno = null;
    Disciplina disciplina = null;

    // Procurar aluno e disciplina nas turmas
    for (Turma turma : turmas) {
        if (turma.getDisciplina().getCodigo().equals(codDisciplina)) {
            for (Aluno a : turma.getAlunosMatriculados()) {
                if (a.getNome().equals(nomeAluno)) {
                    aluno = a;
                    disciplina = turma.getDisciplina();

                    boolean aprovado = turma.aprovado((int) novaFrequencia, novaMedia, aluno);

                    if (aprovado) {
                        aluno.adicionarDisciplinaAprovada(disciplina);
                        System.out.println("Aluno aprovado após edição.");
                    } else {
                        aluno.removerDisciplinaAprovada(disciplina);
                        System.out.println("Aluno não aprovado após edição.");
                    }
                    break;
                }
            }
        }
        if (aluno != null) break;
    }

    salvarAvaliacoes();
    System.out.println("Avaliação atualizada com sucesso.");
}
    public void editarPresenca(Scanner scanner, List<Turma> turmas) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas disponíveis.");
        return;
    }

    System.out.println("Selecione a turma para editar presença:");
    for (int i = 0; i < turmas.size(); i++) {
        System.out.println((i + 1) + ". " + turmas.get(i));
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
        System.out.println("Nenhum aluno matriculado nesta turma.");
        return;
    }

    System.out.println("Alunos matriculados:");
    for (int i = 0; i < alunosMatriculados.size(); i++) {
        System.out.println((i + 1) + ". " + alunosMatriculados.get(i).getNome());
    }
    System.out.print("Escolha o aluno para editar presença: ");
    int opcaoAluno = scanner.nextInt();
    scanner.nextLine();

    if (opcaoAluno < 1 || opcaoAluno > alunosMatriculados.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Aluno aluno = alunosMatriculados.get(opcaoAluno - 1);

    System.out.print("Digite a nova presença do aluno (0 a 100): ");
    int novaPresenca = scanner.nextInt();
    scanner.nextLine();

    if (novaPresenca < 0 || novaPresenca > 100) {
        System.out.println("Valor de presença inválido. Deve ser entre 0 e 100.");
        return;
    }

    // Atualiza a presença no objeto Turma
    boolean sucesso = turma.editarPresencaAluno(aluno, novaPresenca);

    if (sucesso) {
        System.out.println("Presença atualizada com sucesso!");
    } else {
        System.out.println("Erro ao atualizar presença.");
    }
}
    public void mostrarSituacaoAluno(List<Turma> turmas, Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas cadastradas.");
        return;
    }

    System.out.println("Selecione a turma:");
    for (int i = 0; i < turmas.size(); i++) {
        System.out.println((i + 1) + ". " + turmas.get(i));
    }
    int opcaoTurma = scanner.nextInt();
    scanner.nextLine();

    if (opcaoTurma < 1 || opcaoTurma > turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turma = turmas.get(opcaoTurma - 1);
    List<Aluno> alunos = turma.getAlunosMatriculados();

    if (alunos.isEmpty()) {
        System.out.println("Não há alunos matriculados nessa turma.");
        return;
    }

    System.out.println("Selecione o aluno:");
    for (int i = 0; i < alunos.size(); i++) {
        System.out.println((i + 1) + ". " + alunos.get(i).getNome());
    }
    int opcaoAluno = scanner.nextInt();
    scanner.nextLine();

    if (opcaoAluno < 1 || opcaoAluno > alunos.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Aluno aluno = alunos.get(opcaoAluno - 1);

    int idx = turma.getAlunosMatriculados().indexOf(aluno);
    Double[] notas = turma.getNotas().get(idx);
    int presenca = turma.getPresencas().get(idx);
    double mediaFinal = turma.calcularMediaFinal(notas);
    boolean aprovado = turma.aprovado(presenca, mediaFinal, aluno);

    System.out.println("Aluno: " + aluno.getNome());
    System.out.println("Média Final: " + String.format("%.2f", mediaFinal));
    System.out.println("Presença: " + presenca + "%");
    System.out.println("Situação: " + (aprovado ? "Aprovado" : "Reprovado"));
}
    public void relatorioPorTurma(List<Turma> turmas, Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas cadastradas.");
        return;
    }

    System.out.println("Selecione a turma:");
    for (int i = 0; i < turmas.size(); i++) {
        System.out.println((i + 1) + ". " + turmas.get(i));
    }

    int opcao = scanner.nextInt();
    scanner.nextLine();

    if (opcao < 1 || opcao > turmas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Turma turma = turmas.get(opcao - 1);
    List<Aluno> alunos = turma.getAlunosMatriculados();

    System.out.println("\n=== RELATÓRIO POR TURMA ===");
    System.out.println("Turma: " + turma.getCodigo());
    for (Aluno aluno : alunos) {
        int idx = alunos.indexOf(aluno);
        Double[] notas = turma.getNotas().get(idx);
        int presenca = turma.getPresencas().get(idx);
        double media = turma.calcularMediaFinal(notas);
        boolean aprovado = turma.aprovado(presenca, media, aluno);

        System.out.println("Aluno: " + aluno.getNome() + " | Matrícula: " + aluno.getMatricula()
                + " | Média: " + media + " | Presença: " + presenca + "%"
                + " | Situação: " + (aprovado ? "Aprovado" : "Reprovado"));
    }
}
    public void relatorioPorDisciplina(List<Turma> turmas, Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas cadastradas.");
        return;
    }

    System.out.print("Digite o código da disciplina: ");
    String codigo = scanner.nextLine();

    boolean encontrou = false;
    for (Turma turma : turmas) {
        if (turma.getDisciplina().getCodigo().equalsIgnoreCase(codigo)) {
            encontrou = true;
            System.out.println("\nTurma: " + turma.getCodigo());
            List<Aluno> alunos = turma.getAlunosMatriculados();
            for (Aluno aluno : alunos) {
                int idx = alunos.indexOf(aluno);
                Double[] notas = turma.getNotas().get(idx);
                int presenca = turma.getPresencas().get(idx);
                double media = turma.calcularMediaFinal(notas);
                boolean aprovado = turma.aprovado(presenca, media, aluno);

                System.out.println("Aluno: " + aluno.getNome() + " | Matrícula: " + aluno.getMatricula()
                        + " | Média: " + media + " | Presença: " + presenca + "%"
                        + " | Situação: " + (aprovado ? "Aprovado" : "Reprovado"));
            }
        }
    }

    if (!encontrou) {
        System.out.println("Nenhuma turma encontrada para esta disciplina.");
    }
}
    public void relatorioPorProfessor(List<Turma> turmas, Scanner scanner) {
    if (turmas.isEmpty()) {
        System.out.println("Não há turmas cadastradas.");
        return;
    }

    System.out.print("Digite o nome do professor: ");
    String nomeProf = scanner.nextLine();
    boolean encontrou = false;

    for (Turma turma : turmas) {
        if (turma.getProfessor() != null && turma.getProfessor().getNome().equalsIgnoreCase(nomeProf)) {
            encontrou = true;
            System.out.println("\nTurma: " + turma.getCodigo() + " | Disciplina: " + turma.getDisciplina().getNome());
            List<Aluno> alunos = turma.getAlunosMatriculados();
            for (Aluno aluno : alunos) {
                int idx = alunos.indexOf(aluno);
                Double[] notas = turma.getNotas().get(idx);
                int presenca = turma.getPresencas().get(idx);
                double media = turma.calcularMediaFinal(notas);
                boolean aprovado = turma.aprovado(presenca, media, aluno);

                System.out.println("Aluno: " + aluno.getNome() + " | Matrícula: " + aluno.getMatricula()
                        + " | Média: " + media + " | Presença: " + presenca + "%"
                        + " | Situação: " + (aprovado ? "Aprovado" : "Reprovado"));
            }
        }
    }

    if (!encontrou) {
        System.out.println("Nenhuma turma encontrada para este professor.");
    }
}
    public List<Avaliacao> getAvaliacoes() {
    return avaliacoes;
}
    public void exibirBoletimIndividual(Scanner scanner, List<Turma> turmas, List<Avaliacao> avaliacoes) {
    System.out.print("Digite o nome do aluno: ");
    String nomeAluno = scanner.nextLine();

    boolean encontrou = false;

    for (Turma turma : turmas) {
        for (Aluno aluno : turma.getAlunosMatriculados()) {
            if (aluno.getNome().equalsIgnoreCase(nomeAluno)) {
                encontrou = true;
                Avaliacao avaliacao = avaliacoes.stream()
                    .filter(a -> a.getNomeAluno().equalsIgnoreCase(nomeAluno) &&
                                 a.getCodigoDisciplina().equals(turma.getDisciplina().getCodigo()))
                    .findFirst()
                    .orElse(null);

                System.out.println("\n--- Disciplina: " + turma.getDisciplina().getNome() + " ---");
                System.out.println("Matrícula: " + aluno.getMatricula());

                if (avaliacao != null) {
                    System.out.printf("Nota: %.2f\n", avaliacao.getMediaFinal());
                    System.out.printf("Frequência: %.2f%%\n", avaliacao.getFrequencia());
                    String situacao = (avaliacao.getMediaFinal() >= 5.0 && avaliacao.getFrequencia() >= 75.0) ? "Aprovado" : "Reprovado";
                    System.out.println("Situação: " + situacao);
                } else {
                    System.out.println("Avaliação ainda não registrada para essa disciplina.");
                }
            }
        }
    }

    if (!encontrou) {
        System.out.println("Aluno não encontrado ou não matriculado em nenhuma turma.");
    }
}
}
