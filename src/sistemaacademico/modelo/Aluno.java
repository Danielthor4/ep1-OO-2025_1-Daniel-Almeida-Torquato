package sistemaacademico.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;
    protected List<Disciplina> disciplinasAprovadas = new ArrayList<>(); // Adicione este atributo

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
}