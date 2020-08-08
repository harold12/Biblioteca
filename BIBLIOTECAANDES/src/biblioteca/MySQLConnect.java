package biblioteca;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 * Es la que hace posible la conexion de las demas clases a la base de datos,
 * la vamos a utilizar varias veces para enviar los sql
 * @see java.sql.SQLException
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 * @see javax.swing.JOptionPane
 * @author Grupo Biblioteca
 */
public class MySQLConnect{
    //apunta hacua el servidor
    Connection conectar=null;
             
/**
 * Suministrar la ubicacion del driver de la base de datos para poder realizar la conexion
 * @return es una cadena con la direccion del driver
 */
public Connection conexion(){   
  try{
    Class.forName("org.gjt.mm.mysql.Driver");//.newInstance();
    conectar=DriverManager.getConnection("jdbc:mysql://localhost/Libros","root","");
    //conectar=DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/personas","luis","");
    
  }catch(SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
                                   }catch(ClassNotFoundException ex) {                                                                }
    System.out.println(conectar);
  return conectar;
    }
}
