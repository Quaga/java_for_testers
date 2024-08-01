package ru.ctqa.mantis.manager;

import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

public class JamesCliHelper extends HelperBase {
    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) {
        CommandLine command = new CommandLine(
                "C:/Program Files/Zulu/zulu-17/bin/java",
                "-cp", "\"james-server-jpa-app.lib/*\"",
                "org.apache.james.cli.ServerCmd",
                "AddUser", email, password);
        command.setWorkingDirectory(manager.property("james.workingDir"));
        CircularOutputStream out = new CircularOutputStream();
        command.copyOutputTo(out);
        command.execute();
        command.waitFor();
        System.out.println(out);
    }
}
