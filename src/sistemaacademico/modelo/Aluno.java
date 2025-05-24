package sistemaacademico.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;

    // Aqui a lista de disciplinas aprovadas
    protected List<Disciplina> disciplinasAprovadas = new ArrayList<>();

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    // Adiciona uma disciplina à lista de aprovadas
    public void adicionarDisciplinaAprovada(Disciplina disciplina) {
        if (!disciplinasAprovadas.contains(disciplina)) {
            disciplinasAprovadas.add(disciplina);
        }
    }

    // Retorna as disciplinas aprovadas
    public List<Disciplina> getDisciplinasAprovadas() {
        return disciplinasAprovadas;
    }

    public abstract boolean podeReceberNota(); // polimorfismo

    public abstract int getLimiteDisciplinas(); // para diferenciar especial e normal
}