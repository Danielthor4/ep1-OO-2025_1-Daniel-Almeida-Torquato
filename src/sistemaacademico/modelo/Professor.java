package sistemaacademico.modelo;

import java.io.Serializable;

public class Professor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String matricula;
    private String departamento;

    public Professor(String nome, String matricula, String departamento) {
        this.nome = nome;
        this.matricula = matricula;
        this.departamento = departamento;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + "), Dept: " + departamento;
    }
}
