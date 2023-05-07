import java.io.UnsupportedEncodingException;

import com.ghgande.j2mod.modbus.ModbusException;

public class ModbusExample {

    public static void main(String[] args) {

        ModbusTCPRequest modbusTCPRequest = new ModbusTCPRequest("XXX.XXX.XXX.XXX", 502);

        try {

            //Read Holding Register 0x03
            System.out.println(modbusTCPRequest.readHoldingRegister(0x000, 7, MessageType.STRING));
            System.out.println(modbusTCPRequest.readHoldingRegister(0x0AA, 5, MessageType.STRING));
            System.out.println(modbusTCPRequest.readHoldingRegister(0x090, 1, MessageType.DOUBLE));
            //Read Input Register 0x04
            System.out.println(modbusTCPRequest.readInputRegister(0x007, 1, MessageType.INT));

            readAllHoldingRegister();
        } catch (Exception e) {
            System.out.println(e.getMessage());  
        }
    }

    public static void readAllHoldingRegister() throws UnsupportedEncodingException, ModbusException {
       
        ModbusTCPRequest modbusTCPRequest = new ModbusTCPRequest("XXX.XXX.XXX.XXX", 502);
            
        //Aufgrund des Timeouts in drei Anfragen aufgeteilt
        System.out.println(modbusTCPRequest.readMultipleRegister(0x000, 0x007D));
        System.out.println("----------------------------------------");
        System.out.println(modbusTCPRequest.readMultipleRegister(0x07E, 0x00FB));
        System.out.println("----------------------------------------");
        System.out.println(modbusTCPRequest.readMultipleRegister(0x0FC, 0x011C));
        System.out.println("----------------------------------------");
      
      }
}
