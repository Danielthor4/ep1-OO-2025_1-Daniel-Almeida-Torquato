package sistemaacademico.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sistemaacademico.modelo.Professor;

public class GerenciadorProfessor {
    private List<Professor> professores = new ArrayList<>();

    // Cadastrar novo professor
    public void cadastrarProfessor(Scanner scanner) {
        System.out.print("Nome do professor: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            return;
        }

        System.out.print("Matrícula do professor: ");
        String matricula = scanner.nextLine().trim();
        if (matricula.isEmpty()) {
            System.out.println("Matrícula não pode ser vazia.");
            return;
        }

        // Verificar duplicidade
        if (buscarProfessorPorMatricula(matricula) != null) {
            System.out.println("Já existe um professor com essa matrícula!");
            return;
        }

        System.out.print("Área do professor: ");
        String area = scanner.nextLine().trim();
        if (area.isEmpty()) {
            System.out.println("Área não pode ser vazia.");
            return;
        }

        Professor professor = new Professor(nome, matricula, area);
        professores.add(professor);

        System.out.println("Professor cadastrado com sucesso!");
    }

    // Listar todos os professores
    public void listarProfessores() {
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }

        System.out.println("Professores cadastrados:");
        for (Professor p : professores) {
            System.out.println("Nome: " + p.getNome() + " | Matrícula: " + p.getMatricula() + " | Área: " + p.getDepartamento());
        }
    }

    // Buscar professor por matrícula
    public Professor buscarProfessorPorMatricula(String matricula) {
        for (Professor p : professores) {
            if (p.getMatricula().equals(matricula)) {
                return p;
            }
        }
        return null;
    }

    // Editar dados de um professor
    public void editarProfessor(Scanner scanner) {
        System.out.print("Digite a matrícula do professor a ser editado: ");
        String matricula = scanner.nextLine().trim();
        Professor professor = buscarProfessorPorMatricula(matricula);

        if (professor == null) {
            System.out.println("Professor não encontrado.");
            return;
        }

        System.out.println("Editando dados do professor: " + professor.getNome());

        System.out.print("Novo nome (pressione ENTER para manter atual): ");
        String novoNome = scanner.nextLine().trim();
        if (!novoNome.isEmpty()) {
            professor.setNome(novoNome);
        }

        System.out.print("Nova área (pressione ENTER para manter atual): ");
        String novaArea = scanner.nextLine().trim();
        if (!novaArea.isEmpty()) {
            professor.setDepartamento(novaArea);
        }

        System.out.println("Dados atualizados com sucesso!");
    }

    // Getter para lista de professores (se precisar usar em outros lugares)
    public List<Professor> getProfessores() {
        return professores;
    }
}
