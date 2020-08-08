/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 * Es la clase con la cual vamos a crear la interfaz grafica del login y vamos a usarla para crear la tabla usuario
 * en la base de dato de sql
 * @see java.sql.Connection
 * @see java.sql.PreparedStatement
 * @see java.sql.ResultSet
 * @see java.sql.SQLException
 * @see java.sql.Statement
 * @see java.util.logging.Level
 * @see java.util.logging.Logger
 * @see javax.swing.JOptionPane
 * @author Grupo Biblioteca
 */
public class Entrada extends javax.swing.JFrame {
        
        private boolean bandera = false;
        private Connection conn;
    //Conexion a la base de datos de usuario  
    MySQLConnect cc = new MySQLConnect();
    
    /**
     * Constructor de Entrada que llama al metodo initComponents el cual crea los componentes de la clase
     */
    public Entrada() {
        initComponents();
       this.setLocationRelativeTo(null); 
    }  
        
    /**
     * Metodo para hacer el control de tipo de dato que se esta utilizando, en este caso un entero
     * @param cadena parametro que recibe un String y trasnforma un String en un entero
     * @exception NumberFormatException si se introducen caracteres no numericos, o no se quitan los espacios en blanco al principio y al final del String
     * @return <ul>
     * <li>true: Si la cadena se puede convertir a tipo entero</li>
     * <li>false: Sino se puede convertir a entero y me salta la exceptio NumberFormatException</li>
     * </ul>
     */
       private static boolean isNumeric(String cadena){        
        
        try {    
        int numero = Integer.parseInt(cadena);
        return true;
        }catch(NumberFormatException nfe){
        return false;
         }
        }  
    
     
    /**
     * metodo para insertar valores en la tabla 
     */
public void Insertar(){
        //atributo que conecta a la base de datos
        cc = new MySQLConnect();        
        
        conn=cc.conexion();        
        if((isNumeric(Nombre.getText()))==true||isNumeric(Contraseña.getText())==true||Nombre.equals("")||Contraseña.equals("")){       
        JOptionPane.showMessageDialog(rootPane, "formato equivocado");        
        }else{
        
        try{
        PreparedStatement pst=conn.prepareStatement("INSERT INTO usuario(nombre_usuario, contraseña)VALUES(?,?)");
         pst.setString(1,Nombre.getText());
         pst.setString(2,Contraseña.getText());
  
        int a=pst.executeUpdate();
        if(a>0){
          JOptionPane.showMessageDialog(null,"Registro exitoso");
        }
        else{
         JOptionPane.showMessageDialog(null,"Error al agregar");
           }     
        }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
        }           
      }  
    }
    

 
   /**
    * Metodo que nos ayuda a comprobar si en la base de datos existe el usuario
    * @return <ul>
     * <li>true: Si el usuario existe en la base de datos usuario</li>
     * <li>false: Si el usuario no existe en la base de datos de usuario</li>
     * </ul>
    */
   public boolean Verificar(){
       
        cc=new MySQLConnect();
        conn=cc.conexion();
       
        if (Nombre.getText().equals("")||Contraseña.getText().equals("")){
                    
        JOptionPane.showMessageDialog(null, "faltan datos");
        
        }else{
            
        String sql="";
        sql="SELECT * FROM libro";
        Prestamo pre;
            try{
            //para poder leer la varibale sql 
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                
           if(Nombre.getText().equals(rs.getString(1))){
               //convertimos todos los id a enteros para poder enviarlos al objeto Prestamo mediante
              // el constructor;
               
              int en = Integer.parseInt(rs.getString(1));
              
              pre = new Prestamo(en);
                
              }               
           }
            bandera = true;
          }catch(SQLException ex){
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE,null,ex);
           }
        }
        return bandera;
   }
        
   public void Veri(){
        cc=new MySQLConnect();
        conn=cc.conexion();

        String sql="SELECT * FROM libro";
        
        try{
            //para poder leer la varibale sql 
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                
           if(Nombre.getText().equals(rs.getString(1))){
               //convertimos todos los id a enteros para poder enviarlos al objeto Prestamo mediante
              // el constructor;
               
              int en = Integer.parseInt(rs.getString(1));
              
              Prestamo pre = new Prestamo(en);
                
               }
            }
             }catch(SQLException e){
                    System.out.println(e.getMessage());
                    }
           }
   
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombre = new javax.swing.JLabel();
        Nombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Crear = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Contraseña = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nombre.setText("NOMBRE:");
        getContentPane().add(nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 60, 20));

        Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreActionPerformed(evt);
            }
        });
        getContentPane().add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 160, -1));

        jLabel1.setText("CONTRASEÑA:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 80, 20));

        Crear.setText("CREAR");
        Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearActionPerformed(evt);
            }
        });
        getContentPane().add(Crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 80, -1));

        jButton2.setText("ENTRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));
        getContentPane().add(Contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 160, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/WhatsApp Image 2019-03-11 at 11.47.07 PM.jpeg"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    private void CrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearActionPerformed
        // TODO add your handling code here:
        Insertar();
        Veri();     
    }//GEN-LAST:event_CrearActionPerformed
    /**
     * Es el evento que nos cierra la ventana en caso de que se logee un usuario
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(Verificar()==true){
         BASEDATOS base = new BASEDATOS();
         base.setVisible(true); 
         this.setVisible(false);   
             
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * El main es el metodo principal de todo el programa
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Entrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Contraseña;
    private javax.swing.JButton Crear;
    private javax.swing.JTextField Nombre;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel nombre;
    // End of variables declaration//GEN-END:variables
}
