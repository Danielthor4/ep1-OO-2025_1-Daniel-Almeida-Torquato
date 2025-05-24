package sistemaacademico.modelo;

public class AlunoEspecial extends Aluno {

    public AlunoEspecial(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
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
