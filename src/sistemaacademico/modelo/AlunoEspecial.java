package sistemaacademico.modelo;

public class AlunoEspecial extends Aluno {

    public AlunoEspecial(String nome, String matricula, String curso, String semestreAtual) {
        super(nome, matricula, curso, semestreAtual);
    }

    @Override
    public boolean podeReceberNota() {
        return false; // Só frequência
    }

    @Override
    public int getLimiteDisciplinas() {
        return 2; // Pode cursar no máximo 2
    }
}
