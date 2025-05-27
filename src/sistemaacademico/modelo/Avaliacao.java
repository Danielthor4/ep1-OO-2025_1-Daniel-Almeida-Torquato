package sistemaacademico.modelo;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeAluno;
    private String codigoDisciplina;
    private double mediaFinal;
    private double frequencia;

    public Avaliacao(String nomeAluno, String codigoDisciplina, double mediaFinal, double frequencia) {
        this.nomeAluno = nomeAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.mediaFinal = mediaFinal;
        this.frequencia = frequencia;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public double getMediaFinal() {
        return mediaFinal;
    }

    public double getFrequencia() {
        return frequencia;
    }

    @Override
    public String toString() {
        return "Aluno: " + nomeAluno + ", Disciplina: " + codigoDisciplina + ", Média: " + mediaFinal + ", Frequência: " + frequencia + "%";
    }

    public void setMediaFinal(double mediaFinal) {
    this.mediaFinal = mediaFinal;
}

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
}
}
