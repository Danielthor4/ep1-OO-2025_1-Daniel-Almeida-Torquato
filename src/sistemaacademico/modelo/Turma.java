package sistemaacademico.modelo;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private Disciplina disciplina;
    private Professor professor;  // Alterado para objeto Professor
    private String semestre;
    private String formaAvaliacao; // "simples" ou "ponderada"
    private boolean presencial;
    private String sala;
    private String horario;
    private int capacidadeMaxima;

    private List<Aluno> alunosMatriculados;
    private List<Double[]> notas; // Array de notas por aluno (P1, P2, P3, L, S)
    private List<Integer> presencas; // porcentagem de presença por aluno (0 a 100)

    public Turma(Disciplina disciplina, Professor professor, String semestre, String formaAvaliacao,
                 boolean presencial, String sala, String horario, int capacidadeMaxima) {
        this.disciplina = disciplina;
        this.professor = professor; // recebe o objeto Professor
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = sala;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = new ArrayList<>();
        this.notas = new ArrayList<>();
        this.presencas = new ArrayList<>();
    }

    public boolean matricularAluno(Aluno aluno) {
        if (alunosMatriculados.size() >= capacidadeMaxima) return false;
        if (aluno instanceof AlunoEspecial && alunosMatriculados.size() >= aluno.getLimiteDisciplinas()) return false;

        alunosMatriculados.add(aluno);
        notas.add(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});
        presencas.add(0);
        return true;
    }

    public double calcularMediaFinal(Double[] notas) {
        if (formaAvaliacao.equals("simples")) {
            return (notas[0] + notas[1] + notas[2] + notas[3] + notas[4]) / 5.0;
        } else {
            return (notas[0] + notas[1] * 2 + notas[2] * 3 + notas[3] + notas[4]) / 8.0;
        }
    }

    public boolean aprovado(int presenca, double media, Aluno aluno) {
        if (!aluno.podeReceberNota()) return presenca >= 75;
        if (presenca < 75) return false;
        return media >= 5.0;
    }

    // Getters e setters (inclusive do professor)

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getSala() {
        return sala;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    @Override
    public String toString() {
        return "Turma de " + disciplina.getNome() + " com " + 
               (professor != null ? professor.getNome() : "Sem professor") + 
               " no semestre " + semestre + " [" + (presencial ? "Presencial" : "Remota") + "]";
    }
}
