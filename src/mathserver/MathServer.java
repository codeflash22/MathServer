package mathserver;

import java.net.*;
import java.io.*;
/**
 *
 * @author Faisal
 */
public class MathServer {
    
    protected MathService mathService;
    protected Socket socket;

    public void setMathService(MathService mathService) {
        this.mathService = mathService;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public void execute() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Read the message from client and parse into execution
        String msg = reader.readLine();
        double result = parseExecution(msg);
        
        // Write the result back to the client
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("" + result);
        writer.newLine();
        writer.flush();
        
        // close the stream
        reader.close();
        writer.close();
    }
    
    protected double parseExecution(String msg) throws IllegalArgumentException {
        double result = Double.MAX_VALUE;
        String[] elements = msg.split(":");
        if(elements.length != 3)
            throw new IllegalArgumentException("Parsing Error!!");
        
        double valOne = 0;
        double valTwo = 0;
        try{
            valOne = Double.parseDouble(elements[1]);
            valTwo = Double.parseDouble(elements[2]);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Invalid Argument!!");
        }
        
        switch(elements[0].charAt(0)){
            case '+':
                result = mathService.add(valOne, valTwo);
            break;
            
            case '-':
                result = mathService.div(valOne, valTwo);
            break;
            
            case '*':
                result = mathService.mul(valOne, valTwo);
            break;
            
            case '/':
                result = mathService.div(valOne, valTwo);
            break;
            
            default:
                throw new IllegalArgumentException("Invalid Math Operation!!");
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Math Server is running");
        ServerSocket serverSocket = new ServerSocket(2245);
        Socket socket = serverSocket.accept();
        
        // Run a math server that talks to the client
        MathServer mathServer = new MathServer();
        mathServer.setMathService(new PlainMathService());
        mathServer.setSocket(socket);
        mathServer.execute();
        System.out.println("Math Server is closed!!");
    }
    
}
