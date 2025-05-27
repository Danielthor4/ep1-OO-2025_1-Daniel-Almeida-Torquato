package sistemaacademico.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String nome;
    protected String matricula;
    protected String curso;
    protected String semestreAtual;
    protected List<Disciplina> disciplinasAprovadas = new ArrayList<>();
    protected List<Turma> turmasMatriculadas = new ArrayList<>();
    protected List<Turma> turmasTrancadas = new ArrayList<>();

    public Aluno(String nome, String matricula, String curso, String semestreAtual) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.semestreAtual = semestreAtual;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getSemestreAtual() { return semestreAtual; }
    public void setSemestreAtual(String semestreAtual) { this.semestreAtual = semestreAtual; }

    public List<Disciplina> getDisciplinasAprovadas() {
        return disciplinasAprovadas;
    }

    public void adicionarDisciplinaAprovada(Disciplina disciplina) {
        if (!disciplinasAprovadas.contains(disciplina)) {
            disciplinasAprovadas.add(disciplina);
        }
    }

    public abstract boolean podeReceberNota();
    public abstract int getLimiteDisciplinas();

    public List<Turma> getTurmasMatriculadas() {
        return turmasMatriculadas;
    }

    public void adicionarTurma(Turma turma) {
        if (!turmasMatriculadas.contains(turma)) {
            turmasMatriculadas.add(turma);
        } else {
            System.out.println("Aluno já está matriculado nesta turma.");
        }
    }

    public List<Turma> getTurmasTrancadas() {
        return turmasTrancadas;
    }

    public void trancarTurma(Turma turma) {
        if (turmasMatriculadas.contains(turma) && !turmasTrancadas.contains(turma)) {
            turmasMatriculadas.remove(turma);  // Remover da lista de matriculadas
            turmasTrancadas.add(turma);        // Adicionar à lista de trancadas
            System.out.println("Turma trancada com sucesso.");
    }   else {
            System.out.println("Turma não encontrada ou já trancada.");
    }
}
    public void removerDisciplinaAprovada(Disciplina disciplina) {
        disciplinasAprovadas.remove(disciplina);
}
}
