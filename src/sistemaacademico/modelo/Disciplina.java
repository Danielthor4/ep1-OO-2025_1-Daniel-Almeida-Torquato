package sistemaacademico.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;
    private int cargaHoraria;
    private List<String> preRequisitos;

    public Disciplina(String codigo, String nome, int cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void adicionarPreRequisito(String codigoDisciplina) {
        preRequisitos.add(codigoDisciplina);
    }

    @Override
    public String toString() {
        return nome + " (" + codigo + "), Carga Horária: " + cargaHoraria;
    }
}