import net.deechael.invokationtcg.server.GeniusInvokationTCGServer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GeniusInvokationTCGServer server = new GeniusInvokationTCGServer();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String cmd = scanner.nextLine();
                if ("stop".equalsIgnoreCase(cmd)) {
                    server.close();
                    System.exit(0);
                }
            }
        }).start();
        server.start();
    }

}
