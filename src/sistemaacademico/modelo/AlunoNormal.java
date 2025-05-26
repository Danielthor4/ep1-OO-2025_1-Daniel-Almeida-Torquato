package sistemaacademico.modelo;

public class AlunoNormal extends Aluno {

    public AlunoNormal(String nome, String matricula, String curso, String semestreAtual) {
        super(nome, matricula, curso, semestreAtual);
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