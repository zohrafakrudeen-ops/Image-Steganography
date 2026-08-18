import database.DatabaseManager;
import ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        try {

            DatabaseManager.getConnection().close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        new LoginFrame();

    }
}