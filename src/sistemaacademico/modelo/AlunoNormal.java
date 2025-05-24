package sistemaacademico.modelo;

public class AlunoNormal extends Aluno {

    public AlunoNormal(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public boolean podeReceberNota() {
        return true;
    }

    @Override
    public int getLimiteDisciplinas() {
        return Integer.MAX_VALUE; // Sem limite prático
    }
}