package sistemaacademico.modelo;

public abstract class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;

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

    public abstract boolean podeReceberNota(); // polimorfismo
    public abstract int getLimiteDisciplinas(); // para diferenciar especial e normal
}

