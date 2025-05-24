import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("==== SISTEMA ACADÊMICO FCTE ====");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas/Turmas");
            System.out.println("3. Avaliações e Frequência");
            System.out.println("0. Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // chamar métodos para menu de alunos
                    break;
                case 2:
                    // menu de disciplinas/turmas
                    break;
                case 3:
                    // menu de avaliação/frequência
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}