package commandShell;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.*;

public class CommandScanner {

    private CommandTypeInfo[] cmds;
    private CommandTypeInfo cmd;
    private BufferedReader shellReader;
    private String s;

    public CommandScanner(CommandTypeInfo[] cmds, BufferedReader shellReader) throws Exception {
        this.cmds = cmds;
        this.shellReader = shellReader;

    }

    public void readIn() throws IOException {
        s = shellReader.readLine();
    }

    public CommandDescriptor fillInCommandDesc(CommandDescriptor command) throws BadInputException {
        cmd = null;
        for (int index = 0; index < cmds.length; index++) {
            String s2 = s.substring(0, (s.length() > cmds[index].getName().length() ? cmds[index].getName().length() : s.length()));
            if (cmds[index].getName().equals(s2)) {
                command.setCommandType(cmds[index]);
                cmd = cmds[index];
                break;
            }

        }
        if(cmd == null){
            throw new BadInputException("Diesen Befehl gibt es nicht.");
        }

        if (command.getCommandType().getParamTypes() != null) {
            String s3 = s.substring(cmd.getName().length() + 1);
            String[] sa = s3.split(" ");
            Object[] obs = new Object[cmd.getParamTypes().length];

            try{
            for (int index = 0; index < cmd.getParamTypes().length; index++) {
                if (cmd.getParamTypes()[index] == String.class)
                    obs[index] = sa[index];
                else
                    try{
                    obs[index] = Integer.parseInt(sa[index]);
                    }catch(java.lang.NumberFormatException e){
                        throw new BadInputException("Mindestens einer Ihrer Parameter stimmt nicht mit der Konvention überein. Rufen sie 'help' auf, um Informationen über die Befehle zu erhalten.");
                    }
            }
            }catch(ArrayIndexOutOfBoundsException e){
                throw new BadInputException("Sie haben zu viele oder zu wenige Parameter eingetragen.");
            }
            command.setParams(obs);
        }
        return command;
    }
}
