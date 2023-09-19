import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    PersonManagerRepository personManagerRepository;
    Scanner scan;

    public MenuManager() {
        personManagerRepository = new PersonManagerRepository();
        scan = new Scanner(System.in);
    }

    public void handle() {
        while (true) {
            try {
                int option = showMenu();
                int OPTION_TO_EXIT = 6;
                if (option == OPTION_TO_EXIT) break;

                actions(option);
            } catch (RuntimeException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }

    private int showMenu() {
        String input = JOptionPane.showInputDialog("""
                [ 1 ] Inserir um usuário
                [ 2 ] Pegar usuário
                [ 3 ] Pegar todos os usuários
                [ 4 ] Remover o usuário
                [ 5 ] Atualizar o usuiário
                [ 6 ] Sair""");

        return Integer.parseInt(input);
    }

    private void actions(int option) {
        switch (option) {
            case 1 -> {
                addPerson();
            }
            case 2 -> {
                getPerson();
            }
            case 3 -> {
                getAllPersons();
            }
            case 4 -> {
                removePerson();
            }
            case 5 -> {
                updatePerson();
            }
        }
    }

    private void addPerson() {
        String name = JOptionPane.showInputDialog("Digite o nome do usuário: ");
        String age = JOptionPane.showInputDialog("Digite a idade do usuário: ");
        this.personManagerRepository.insertPerson(name, Integer.parseInt(age));
    }

    private void getPerson() {
        String name = JOptionPane.showInputDialog("Digite o nome do usuário: ");
        Document person = this.personManagerRepository.getByName(name);

        if (person == null)
            throw new RuntimeException("No person with this name was found");

        StringBuilder message = new StringBuilder();
        message.append("id: " + person.get("_id"));
        message.append("\n");
        message.append("name: " + person.get("name"));
        message.append("\n");
        message.append("age: " + person.get("age"));

        JOptionPane.showMessageDialog(null, message.toString(), person.get("name").toString(),JOptionPane.INFORMATION_MESSAGE);
    }

    private void getAllPersons() {
        FindIterable<Document> persons = this.personManagerRepository.getAllPersons();
        StringBuilder message = new StringBuilder();
        persons.forEach((el) -> {
            String fieldId = "_id";
            message.append(fieldId + ": ");
            message.append(el.get(fieldId).toString());
            message.append("\n");

            String fieldName = "name";
            message.append(fieldName + ": ");
            message.append(el.get("name").toString());
            message.append("\n");
            message.append("\n");

        });

        JOptionPane.showMessageDialog(null, message.toString(), "Pessoas",JOptionPane.INFORMATION_MESSAGE);
    }

    private void removePerson() {
        String name = JOptionPane.showInputDialog("Digite o nome do usuário: ");
        this.personManagerRepository.removeByName(name);
    }

    private void updatePerson() {
        String name = JOptionPane.showInputDialog("Digite o nome do usuário para atualizar: ");

        String age = JOptionPane.showInputDialog("Digite a idade do usuário: ");

        this.personManagerRepository.updatePerson(name, Integer.parseInt(age));
    }
}
