/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.util.Vector;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class Serial {

    private CommPort commPort = null;
    private SerialPort serialPort = null;
    private SerialReader reader;
    private SerialWriter writer;
    
    public InputStream in;
    public OutputStream out;
    
    /**
     * Serial Connection
     */
    public Serial()
    {
        super();
        in = new PipedInputStream();
        out = new PipedOutputStream();
        
    }
    
    public void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            throw new IOException("Error: Port " + portName + " is currently in use");
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);
         
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                //reader = new SerialReader(in);
                //new Thread(reader).start();
                //writer = new SerialWriter(out);
                //new Thread(writer).start();

            }
            else
            {
                throw new IOException("Error: " + portName + "is not a Serial Port.");
            }
        } 
        
    }
    
    
    public void close() throws Exception{
        
        if(serialPort != null){
            
            //reader.terminate();
            //reader.terminate();
            
            serialPort.getInputStream().close();
            serialPort.getOutputStream().close();
            serialPort.close();
            serialPort = null;
            
            commPort.getInputStream().close();
            commPort.getOutputStream().close();
            commPort.close();
            commPort = null;
            
            in.close();
            out.close();
        }
        else{
            throw new Exception("Can't close. Port is not open.");
        }
    }
    
    
    
    public static Vector<String> listPorts() throws Exception
    {
        Vector<String> PortNames = new Vector();
        
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            
            if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL){
                PortNames.add(portIdentifier.getName());
            }
        }
        
        if(PortNames.isEmpty()){
            throw new IOException("Error: No Serial Ports found.");
        }
        
        return PortNames;
    }
    
    private static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
    
    
    public void Send(String message){
        try{
         out.write(message.getBytes());
        }
        catch (IOException e){
            
        }
    }
    
    
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        private volatile boolean running = true;

        /**
         * SerialReader Thread
         * @param in 
         */
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void terminate() 
        {
            running = false;
        }
        
        public void run ()
        {
            
            while(running){
                
            }
            /*
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    //TODO
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }    
            */
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        private volatile boolean running = true;
        
        /**
         * SerialWriter Thread
         * @param out 
         */
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void terminate() 
        {
            running = false;
        }
        
        public void run ()
        {
            
            while(running){
                
            }
            /*
            try
            {          
                //TODO
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }      
            */
        }
    }
    

}

