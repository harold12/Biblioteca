package biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * La clase BASEDATOS usa herenca de la libreria javax.swing.JFrame y sirve para crear la interfaz grafica y sus eventos
 * @see java.sql.Connection
 * @see java.sql.ResultSet
 * @see java.sql.SQLException
 * @see java.sql.Statement
 * @see java.sql.PreparedStatement
 * @see java.text.ParseException
 * @see java.text.SimpleDateFormat
 * @see java.util.Date
 * @see java.util.logging.Level
 * @see java.util.logging.Logger
 * @see javax.swing.JOptionPane
 * @see javax.swing.table.DefaultTableModel
 * @author Grupo Biblioteca
 * @version 1.1.0
 */

public class BASEDATOS extends javax.swing.JFrame{
    /**
     * conn atributo usado para realizar la conexion a la base de datos de MySQL
     * cc objeto de la clase MySQLConnect que trae la direccion de la base de datos y driver
     */    
    private Connection conn;
    //Conexion a base de datos 
    MySQLConnect cc ;
    
    /**
     * Constructor de BASEDATOS que llama al metodo initComponents el cual crea los componentes de la clase
     * y centra la Interfaz Grafica
     */
    public BASEDATOS() {
       initComponents();
       this.setLocationRelativeTo(null); 
     }
    
    /**
     * Metodo para hacer control de tipo de dato que se esta utilizando en este caso una fecha
     * @param cadena parametro que recibe un String y trasnforma un String en una fecha
     * @exception NumberFormatException si se introducen caracteres no numericos, o no se quitan los espacios en blanco al principio y al final del String
     * @return <ul>
     * <li>true: Si la cadena se puede convertir a tipo fecha</li>
     * <li>false: Sila cadena no se puede convertir a tipo fecha</li>
     * </ul>
     */
    public static boolean isDate(String cadena){        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd");
        Date anio = null;
       try {
           anio = formatoDelTexto.parse(cadena);
           return true;
        } catch (ParseException nfe){
              System.out.println(nfe.getMessage());
           return false;
          }
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
    public static boolean isNumeric(String cadena){        
        try {    
            int numero = Integer.parseInt(cadena);
            return true;
        }catch(NumberFormatException nfe){ 
            return false;
        }
    }  
    
     //esta variable global es con fines de capturar el item seleccionado que viene de un evento, jComboBox1ItemStateChanged().
     String general="";
     Libro  libros ;
    /**
     * metodo para insertar valores en la tabla
     */
    public void Insertar(){
        //atributo que conecta a la base de datos
        cc = new MySQLConnect();        
        
        conn=cc.conexion();        
        if((isDate(jTYear.getText()))==false||isNumeric(jTextId.getText())==false||jTitulo.getText().equals("")||jEditorial.getText().equals("")){       
        JOptionPane.showMessageDialog(rootPane, "formato equivocado");        
        }else{
        
        try{
        
        int id = Integer.parseInt(jTextId.getText());
        libros = new Libro( id , jTitulo.getText(), jEditorial.getText(),jTYear.getText(), general );
               
        PreparedStatement pst=conn.prepareStatement("INSERT INTO libro(id_libro, titulo, editorial, anio, tipo)VALUES(?,?,?,?,?)");
         pst.setInt(1,libros.getId());
         pst.setString(2,libros.getTitulo());
         pst.setString(3,libros.getEditorial());
         pst.setString(4,libros.getYear());
         pst.setString(5,libros.getTipo());
           
        int a=pst.executeUpdate();
        if(a>0){
         JOptionPane.showMessageDialog(null,"Registro exitoso");
         mostrardatos("");
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
     * Metodo para mostrar los datos de la tabla
     * @param valor es un mensaje que hace que el metodo mostrardatos tenga diferentes comportamientos
     */
    public void mostrardatos(String valor){
        //Conecta la base de datos en el metodo
        cc=new MySQLConnect();
        Connection cn=cc.conexion();
        //Crea el modelo de la tabla
        DefaultTableModel modelo=new DefaultTableModel();
        // Añade el nobre de las columnas a la tabla
        modelo.addColumn("Id");
        modelo.addColumn("Titulo");
        modelo.addColumn("Editorial");
        modelo.addColumn("Year");
        modelo.addColumn("tipo");
        modelo.addColumn("id_usuario");
        
        jTable.setModel(modelo);
        String sql="";
        if (valor.equals(""))
        {
            sql="SELECT * FROM libro";
             
        String [] datos=new String [6];
        try{
            //para poder leer la varibale sql 
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            datos[5]=rs.getString(6);
            modelo.addRow(datos);
         }
           jTable.setModel(modelo);
         }catch(SQLException ex){
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE,null,ex);
           }
         
         }
         else{
            String ssql="SELECT * FROM libro WHERE  (id_libro LIKE '"+valor+"%' or titulo LIKE'"+valor+"%')";
           
            String [] datos=new String [6];
           
             try{
             Statement st=cn.createStatement();
             ResultSet rs=st.executeQuery(ssql);
             
             while(rs.next()){

             int i = 0;   
             datos[0]=rs.getString(1);
             datos[1]=rs.getString(2);
             datos[2]=rs.getString(3);
             datos[3]=rs.getString(4);
             datos[4]=rs.getString(5);
             datos[5]=rs.getString(6);
             modelo.addRow(datos);
             
            if(rs.getString(1).equals(valor)){
            info.setText("ID = '"+rs.getString(1)+"' TITULO = '"+rs.getString(2)+"' EDITORIAL = '"+rs.getString(3)+"' AÑO = '"+rs.getString(4)+"' TIPO = '"+rs.getString(5)+"' ID_USUARIO = '"+rs.getString(6));
            }
            
             }
            jTable.setModel(modelo);
             
            }catch(SQLException ed){
               System.out.println(ed.getMessage());
            } 
          }
        }
     
    
      
    /**
     * El metodo modificar tiene la funcionalidad de hacer que el programa modifique los datos de algun documento ya existente
     */
 public void Modificar (){  
        //conexion con la base de datos
       cc = new MySQLConnect();
       conn=cc.conexion();
       
       if((isDate(jTYear.getText()))==false||(isNumeric(jTextId.getText()))==false||(jTitulo.getText().equals(""))||(jEditorial.getText().equals(""))||(general=="")){       
         JOptionPane.showMessageDialog(rootPane, "formato equivocado");          
         }else{
     try{
        int id_libro= Integer.parseInt(jTextId.getText());
        PreparedStatement pst=conn.prepareStatement("UPDATE libro SET titulo='"+jTitulo.getText()+"',editorial='"+jEditorial.getText()+"',tipo='"+general+"' WHERE id_libro='"+id_libro+"' ");
        pst.executeUpdate();
        mostrardatos("");
        }catch(Exception e){
        System.out.print(e.getMessage());
         }
        } 
      }
 
 

 /**
     * El metodo Elimnar tiene la funcionalidad de hacer que el programa elimine el documento que se desea eliminar
     */
   public void Eliminar(){
         //conexion con la base de datos
        cc=new MySQLConnect();
        conn=cc.conexion();
    
        String cod = jTextId.getText();
      try{
        PreparedStatement pst=conn.prepareStatement("DELETE FROM libro WHERE id_libro='"+cod+"'");
        pst.executeUpdate();
        mostrardatos("");// TODO add your handling code here:
         } catch (Exception e){
        }  
      }
   
   /**
    * El metodo Prestar sirve para enviar el id del documento al metodo del objeto de la clase Prestamo Alquilar
    */
public void Prestar(){
    //conexion con la base de datos
      cc=new MySQLConnect();
        conn=cc.conexion();
       
        Prestamo pre = new Prestamo();
        
       if(isNumeric(Buscador.getText())==true){
           
        int numero = Integer.parseInt(Buscador.getText());
         
        pre.Alquilar(numero);            
        }   
       }
            
           
        
    
      
        

 

    /**
     *Este método se llama desde el constructor para inicializar el formulario.
     *ADVERTENCIA: NO modifique este código. El contenido de este método siempre es regenerado por el Editor de formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextId = new javax.swing.JTextField();
        jTitulo = new javax.swing.JTextField();
        jEditorial = new javax.swing.JTextField();
        jTYear = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Buscador = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        info = new javax.swing.JTextArea();
        Prestamo = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 255, 102));
        jLabel1.setText("ID:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, 20));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 510, 230));

        jLabel2.setForeground(new java.awt.Color(0, 255, 102));
        jLabel2.setText("TITULO:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 60, 20));

        jLabel3.setForeground(new java.awt.Color(0, 255, 102));
        jLabel3.setText("EDITORIAL:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 60, 20));
        getContentPane().add(jTextId, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 190, -1));
        getContentPane().add(jTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 190, -1));
        getContentPane().add(jEditorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 190, -1));
        getContentPane().add(jTYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 190, -1));

        jButton1.setText("CREAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 70, 20));

        jButton2.setText("MODIFICAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 90, 20));

        jButton3.setText("ELIMINAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 80, 20));

        jLabel4.setForeground(new java.awt.Color(0, 255, 102));
        jLabel4.setText("AÑOS:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 60, 20));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(Genero.values()));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 90, -1));

        jLabel6.setForeground(new java.awt.Color(0, 255, 102));
        jLabel6.setText("TIPO:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 60, 20));

        jLabel7.setForeground(new java.awt.Color(0, 204, 51));
        jLabel7.setText("BUSCAR:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 60, 20));

        Buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BuscadorKeyReleased(evt);
            }
        });
        getContentPane().add(Buscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 180, -1));

        info.setColumns(20);
        info.setRows(5);
        jScrollPane4.setViewportView(info);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 510, 50));

        Prestamo.setText("PRESTAMO");
        Prestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrestamoActionPerformed(evt);
            }
        });
        getContentPane().add(Prestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eye-closeup-820x330.jpg"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
         general = jComboBox1.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Insertar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscadorKeyReleased
        // TODO add your handling code here:
          info.setText("");
    
                 mostrardatos(Buscador.getText());
    }//GEN-LAST:event_BuscadorKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Modificar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Eliminar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void PrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrestamoActionPerformed
        // TODO add your handling code here:
       Prestar();
       
    }//GEN-LAST:event_PrestamoActionPerformed
    
        
    
    /**
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
            java.util.logging.Logger.getLogger(BASEDATOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASEDATOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASEDATOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASEDATOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BASEDATOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Buscador;
    private javax.swing.JButton Prestamo;
    private javax.swing.JTextArea info;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<Genero> jComboBox1;
    private javax.swing.JTextField jEditorial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTYear;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField jTextId;
    private javax.swing.JTextField jTitulo;
    // End of variables declaration//GEN-END:variables



   
}
