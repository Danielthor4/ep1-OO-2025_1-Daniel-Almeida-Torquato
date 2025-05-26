package sistemaacademico.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;
    protected List<Disciplina> disciplinasAprovadas = new ArrayList<>();
    protected List<Turma> turmasMatriculadas = new ArrayList<>();

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    // Getters já existentes
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public List<Disciplina> getDisciplinasAprovadas() {
        return disciplinasAprovadas;
    }

    // Método para adicionar disciplina aprovada
    public void adicionarDisciplinaAprovada(Disciplina disciplina) {
        if (!disciplinasAprovadas.contains(disciplina)) {
            disciplinasAprovadas.add(disciplina);
        }
    }

    public abstract boolean podeReceberNota();
    public abstract int getLimiteDisciplinas();

    public void setNome(String nome) {
    this.nome = nome;
}
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

}
