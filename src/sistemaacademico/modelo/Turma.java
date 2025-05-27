package sistemaacademico.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;

    private Disciplina disciplina;
    private Professor professor;
    private String semestre;
    private String formaAvaliacao; // "simples" ou "ponderada"
    private boolean presencial;
    private String sala;
    private String horario;
    private int capacidadeMaxima;
    private String codigo;

    private List<Aluno> alunosMatriculados;
    private List<Double[]> notas; // Array de notas por aluno (P1, P2, P3, L, S)
    private List<Integer> presencas; // porcentagem de presença por aluno (0 a 100)
    private Map<Aluno, Boolean> trancamento; // aluno -> true se trancou

    public Turma(Disciplina disciplina, Professor professor, String semestre, String formaAvaliacao,
                 boolean presencial, String sala, String horario, int capacidadeMaxima) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = sala;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = new ArrayList<>();
        this.notas = new ArrayList<>();
        this.presencas = new ArrayList<>();
        this.trancamento = new HashMap<>();
        this.codigo = disciplina.getCodigo() + "-" + semestre;
    }

    public boolean matricularAluno(Aluno aluno) {
        if (alunosMatriculados.contains(aluno)) return false;

        if (alunosMatriculados.size() >= capacidadeMaxima) return false;

        if (aluno.getTurmasMatriculadas().size() >= aluno.getLimiteDisciplinas()) return false;

        alunosMatriculados.add(aluno);
        aluno.adicionarTurma(this); // <- aqui adiciona ao aluno diretamente
        return true;
}

    public void trancar(Aluno aluno) {
        trancamento.put(aluno, true);
    }

    public boolean isTrancada(Aluno aluno) {
        return trancamento.getOrDefault(aluno, false);
    }

    public double calcularMediaFinal(Double[] notas) {
        if (formaAvaliacao.equals("simples")) {
            return (notas[0] + notas[1] + notas[2] + notas[3] + notas[4]) / 5.0;
        } else {
            return (notas[0] + notas[1] * 2 + notas[2] * 3 + notas[3] + notas[4]) / 8.0;
        }
    }

    public boolean aprovado(int presenca, double media, Aluno aluno) {
        if (!aluno.podeReceberNota()) return presenca >= 75;
        if (presenca < 75) return false;
        return media >= 5.0;
    }

    // Getters e Setters

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public String getSala() {
        return sala;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Turma de " + disciplina.getNome() + " com " +
               (professor != null ? professor.getNome() : "Sem professor") +
               " no semestre " + semestre + " [" + (presencial ? "Presencial" : "Remota") + "]";
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
        this.codigo = disciplina.getCodigo() + "-" + semestre; // atualiza o código também
}

    public void setFormaAvaliacao(String formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
}

    public void setPresencial(boolean presencial) {
        this.presencial = presencial;
}

    public void setSala(String sala) {
        this.sala = sala;
}

    public void setHorario(String horario) {
        this.horario = horario;
}

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
}
    public List<Double[]> getNotas() {
    return notas;
}

    public List<Integer> getPresencas() {
    return presencas;
}
    public boolean editarPresencaAluno(Aluno aluno, int novaPresenca) {
    int index = alunosMatriculados.indexOf(aluno);
    if (index == -1) {
        return false;
    }
    presencas.set(index, novaPresenca);
    return true;
}
}