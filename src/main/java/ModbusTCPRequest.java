import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.util.ModbusUtil;

enum MessageType {
    INT, DOUBLE, STRING
} 


public class ModbusTCPRequest {

    
    MessageType messageType;

    private final Logger logger = Logger.getLogger(ModbusTCPRequest.class.getName());
    private TCPMasterConnection connection;

    private ModbusRequest req = null;
    private byte[] message = null;


    public ModbusTCPRequest(String adress, int port) {

        connection = null;

        connect(adress, port);
    }

    public void disConnect() {
        if (connection != null) {
            connection.close();
            connection = null;
        }

    }

    public void connect(String adress, int port) {

        disConnect();

        try {
            connection = new TCPMasterConnection(InetAddress.getByName(adress));
            connection.setPort(port);
            connection.setTimeout(15000); // Set the connection timeout to 5 seconds
            connection.connect();
            
            logger.log(Level.INFO, "Modbus RTU Over TCP Connection Success");
            logger.log(Level.INFO, "Connection Adresse: " + connection.getAddress() + ":" + connection.getPort());
            logger.log(Level.INFO, "ModbusTransport: " + connection.getModbusTransport().toString());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Modbus RTU Over TCP Connection Error: " + e.getMessage());
        }

    }
    
    public String readMultipleRegister(int startingAddress, int numberOfRegisters) throws ModbusException, UnsupportedEncodingException {
        this.messageType=messageType;

        numberOfRegisters=numberOfRegisters-startingAddress;
        req = new ReadMultipleRegistersRequest(startingAddress, numberOfRegisters);
            
       req.setUnitID(1);
       req.setHeadless();

       // Create a new transaction
       ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
       transaction.setRetries(1); 
       transaction.setRequest(req);
       transaction.execute();
       ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) transaction.getResponse();
       res.setHeadless();
      
       
       byte[] message = Arrays.copyOfRange(res.getMessage(), 1, res.getMessage().length);

       //In res.getHexMessage() befindet sich die UnitID gefolgt vom FunctionsCode und dem ByteCount danach kommt die eigentliche Message

        return res.getHexMessage();
       
    }

    public Object readHoldingRegister(int startingAddress, int numberOfRegisters, MessageType messageType) throws ModbusException, UnsupportedEncodingException {

        this.messageType=messageType;
         req = new ReadMultipleRegistersRequest(startingAddress, numberOfRegisters);
                    
        req.setUnitID(1);
        req.setHeadless();

        // Create a new transaction
        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
        transaction.setRetries(1); 
        transaction.setRequest(req);
        transaction.execute();
        
        ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) transaction.getResponse();
        res.setHeadless();
       
        
        //In res.getMassge: befindet sich der ByteCount mit der Länge 1 Byte, der Rest sind die Daten
        //hier werden die Daten in ein Array kopiert
        byte[] message = Arrays.copyOfRange(res.getMessage(), 1, res.getMessage().length);

        //In res.getHexMessage() befindet sich die UnitID gefolgt vom FunctionsCode und dem ByteCount danach kommt die eigentliche Message

    
        System.out.println("----------------------");
        System.out.println("TransactionID: "+res.getTransactionID());
        System.out.println("ProtocolID: "+res.getProtocolID());
        System.out.println("FunctionCode: "+res.getFunctionCode());
        System.out.println("UnitID: "+res.getUnitID());
        System.out.println("ByteCount: "+res.getByteCount());
        System.out.println("----------------------");

        

        logger.log(Level.INFO, "Modbus Complete Message " + res.getHexMessage());
        logger.log(Level.INFO, "Modbus: ByteCount ("+res.getByteCount()+") Message: " + ModbusUtil.toHex(message));

        switch (messageType) {
            case INT:
                return getIntValue(message);
            case DOUBLE:
                return getDoubleValue(message);
            case STRING:
                return getStringValue(message);
            default:
                throw new IllegalArgumentException("Unsupported data type: " + messageType);
        }
    }

    /*
        public Object sendRequest(int startingAddress, int numberOfRegisters, String dataType) throws ModbusIOException, ModbusSlaveException, ModbusException {
            // con.connect();
            req = new ReadMultipleRegistersRequest(startingAddress, numberOfRegisters);
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            trans.execute();
            res = (ReadMultipleRegistersResponse) trans.getResponse();
            con.close();

            switch (dataType) {
                case "int":
                    return getIntValue(res);
                case "string":
                    return getStringValue(res);
                default:
                    throw new IllegalArgumentException("Unsupported data type: " + dataType);
            }
        }
    */
    private int getIntValue(byte[] message) {

        return ModbusUtil.registerToUnsignedShort(message);
        //return message[message.length-1] << 8 | (message[message.length ] & 0xff);
    }

    private double getDoubleValue(byte[] message) {
        
        return ModbusUtil.registerToUnsignedShort(message);
        //return message[message.length-1] << 8 | (message[message.length ] & 0xff);
    }

    private String getStringValue(byte[] message) throws UnsupportedEncodingException {

        return new String(message, StandardCharsets.UTF_8);
    }

    public Object readInputRegister(int startingAddress, int numberOfRegisters, MessageType messageType) throws ModbusException, UnsupportedEncodingException {
        
        this.messageType=messageType;
        req = new ReadInputRegistersRequest(startingAddress, numberOfRegisters);
        req.setUnitID(1);
        req.setHeadless();

        // Create a new transaction
        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
        transaction.setRetries(1); 
        transaction.setRequest(req);
        transaction.execute();
        
        ReadInputRegistersResponse res = (ReadInputRegistersResponse) transaction.getResponse();
        res.setHeadless();
       
        //In res.getMassge: befindet sich der ByteCount mit der Länge 1 Byte, der Rest sind die Daten
        //hier werden die Daten in ein Array kopiert
        byte[] message = Arrays.copyOfRange(res.getMessage(), 1, res.getMessage().length);

        //In res.getHexMessage() befindet sich die UnitID gefolgt vom FunctionsCode und dem ByteCount danach kommt die eigentliche Message

    
        System.out.println("----------------------");
        System.out.println("TransactionID: "+res.getTransactionID());
        System.out.println("ProtocolID: "+res.getProtocolID());
        System.out.println("FunctionCode: "+res.getFunctionCode());
        System.out.println("UnitID: "+res.getUnitID());
        System.out.println("ByteCount: "+res.getByteCount());
        System.out.println("----------------------");

        logger.log(Level.INFO, "Modbus Complete Message " + res.getHexMessage());
        logger.log(Level.INFO, "Modbus: ByteCount ("+res.getByteCount()+") Message: " + ModbusUtil.toHex(message));

        switch (messageType) {
            case INT:
                return getIntValue(message);
            case DOUBLE:
                return getDoubleValue(message);
            case STRING:
                return getStringValue(message);
            default:
                throw new IllegalArgumentException("Unsupported data type: " + messageType);
        }
    }
}
