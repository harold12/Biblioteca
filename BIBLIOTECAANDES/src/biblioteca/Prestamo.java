package biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * La clase se usa para modificar la llave foranea de la tabla libro
 * @see java.sql.Connection
 * @see java.sql.PreparedStatement
 * @author Grupo Biblioteca
 */
public class Prestamo {
   
   Connection conn;
    //Conexion con la base de datos libro
    MySQLConnect cc ;
   
    private int id_usuario;
   
    /**
     * Recibe el id del usuario
     * @param id_usuario sera asignado al atributo id usuario de la clase
     */
    public Prestamo(int id_usuario ){
          this.id_usuario = id_usuario;
           
    }
      
    public Prestamo( ){
          
           
    }
    boolean bandera = false; 
/**
 * Sirve para enviar el parametro recibido a la base de datos
 * @param id_libro Sera recibido y enviado a la base de datos
 */
public void Alquilar(int id_libro){
    //Conexion de base de datos
    
    cc = new MySQLConnect();
       conn=cc.conexion();
     String sql;
     
       try{
       sql = "SELECT ausuario FROM libro";
       
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sql);
      
   
        while(rs.next()){
  
             if(rs.getString("ausuario")==null||rs.getInt("ausuario")==id_libro){         
                  bandera= true;

        }else{ 
             JOptionPane.showMessageDialog(null,"libro ya esta prestado");
             }
        }
           
        if(bandera==true){
            PreparedStatement pst=conn.prepareStatement("UPDATE libro SET ausuario='"+getIdusuario()+"' WHERE id_libro='"+id_libro+"'");
            pst.executeUpdate();
               
           
      
      }}catch(SQLException e){  
               System.out.println(e.getMessage());
            }
       }

    /**
     * @return the id_usuario
     */
    public int getIdusuario() {
        return id_usuario;
    }

    /**
     * @return the id_libro
     */

}
