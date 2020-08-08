package biblioteca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BASEDATOS extends javax.swing.JFrame {
        
        
    private Connection conn;
     
    MySQLConnect cc = new MySQLConnect();
        
    public BASEDATOS() {
      initComponents();
      this.setLocationRelativeTo(null); 
     }
    

    private static boolean isDate(String cadena){        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy/MM/dd");
        Date anio = null;
       try {    
            anio = formatoDelTexto.parse(cadena);
            return true;
           } catch (ParseException nfe){
            return false;
           }
        }  
    
       private static boolean isNumeric(String cadena){        
        
       try {    
           int numero = Integer.parseInt(cadena);
            return true;
           } catch (NumberFormatException nfe){
            return false;
           }
        }  
    
     //esta variable global es con fines de capturar el item seleccionado que viene de un evento, jComboBox1ItemStateChanged().
     String general="";
    Libro  libros ;
    //metodo para insertar valores en la tabla 
    public void Insertar(){
        //atributo que conecta a la base de datos
        cc = new MySQLConnect();        
        //
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
 /*   void Buscar (String valor){
         cc=new MySQLConnect();
        Connection cn=cc.conexion();
        
        String sql = "";
        
        sql="SELECT * FROM libro WHERE id_libro LIKE '%'"+Buscador.getText()+"'%'";
        try{
        
        Statement st=cn.createStatement();
        
        ResultSet rs=st.executeQuery(sql);
        
        }catch(SQLException es){
            
            System.out.println(es.getMessage());
        
        }
    }*/
        
    void mostrardatos(String valor){
         cc=new MySQLConnect();
        Connection cn=cc.conexion();
        DefaultTableModel modelo=new DefaultTableModel();
       
        modelo.addColumn("Id");
        modelo.addColumn("Titulo");
        modelo.addColumn("Editorial");
        modelo.addColumn("Year");
        modelo.addColumn("tipo");
        
        jTable.setModel(modelo);
        String sql="";
        if (valor.equals(""))
        {
            sql="SELECT * FROM libro";
             
        String [] datos=new String [5];
        try{
            
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);        
            modelo.addRow(datos);
            }
            jTable.setModel(modelo);
        }catch(SQLException ex){
            Logger.getLogger(Libro.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        else{
           String ssql="SELECT * FROM libro WHERE (id_libro LIKE '"+valor+"%' OR titulo LIKE '%"+valor+"%')";
           
            String [] datos=new String [5];
           
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
            modelo.addRow(datos);
            info.setText(datos[i]); 
            i++;          
           }
            jTable.setModel(modelo);
            
             }catch(SQLException ed){
               System.out.println(ed.getMessage());
            }
             
         }
        
    }
     
      
    
 public void Modificar (){         
       cc = new MySQLConnect();
       conn=cc.conexion();
       
    if((isDate(jTYear.getText()))==false||(isNumeric(jTextId.getText()))==false||(jTitulo.getText().equals(""))||(jEditorial.getText().equals(""))||(general=="")){       
            JOptionPane.showMessageDialog(rootPane, "formato equivocado");          
        }else{
    try{
    int id_libro= Integer.parseInt(jTextId.getText());
    
    PreparedStatement pst=conn.prepareStatement("UPDATE libro SET titulo='"+ jTitulo.getText()+"',editorial='"+jEditorial.getText()+"',tipo='"+general+"' WHERE id_libro='"+id_libro+"' ");
         
        pst.executeUpdate();
        mostrardatos("");
        
        }catch(Exception e){
        System.out.print(e.getMessage());
         }
        } 
      }
 

   public void Eliminar(){
 
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



 

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTitulo = new javax.swing.JTextField();
        jEditorial = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jId = new javax.swing.JLabel();
        jTextId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        bCrear = new javax.swing.JButton();
        jTYear = new javax.swing.JTextField();
        Buscador = new javax.swing.JTextField();
        Buscar = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        info = new javax.swing.JTextArea();
        jModificar = new javax.swing.JButton();
        jEliminar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Titulo:");

        jLabel2.setText("Editorial:");

        jLabel3.setText("Year:");

        jLabel4.setText("Tipo:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(Genero.values()));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jId.setText("ID:");

        jTextId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdActionPerformed(evt);
            }
        });

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

        bCrear.setText("Crear");
        bCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCrearActionPerformed(evt);
            }
        });

        jTYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTYearActionPerformed(evt);
            }
        });

        Buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscadorActionPerformed(evt);
            }
        });
        Buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BuscadorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscadorKeyTyped(evt);
            }
        });

        Buscar.setText("Buscar");

        info.setColumns(20);
        info.setRows(5);
        jScrollPane4.setViewportView(info);

        jModificar.setText("Modificar");
        jModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jModificarActionPerformed(evt);
            }
        });

        jEliminar.setText("Eliminar");
        jEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Buscador, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jId)
                        .addGap(43, 43, 43)
                        .addComponent(jTextId, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bCrear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jModificar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jEditorial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jTitulo)
                            .addComponent(jTYear, javax.swing.GroupLayout.Alignment.LEADING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(bCrear)
                                    .addComponent(jModificar))
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jEliminar)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void YearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearActionPerformed

    private void jTextIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
       general = jComboBox1.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void bCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCrearActionPerformed
        // TODO add your handling code here:
         Insertar();       
    }//GEN-LAST:event_bCrearActionPerformed

    private void jTYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTYearActionPerformed

    private void BuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscadorActionPerformed

    private void BuscadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscadorKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_BuscadorKeyTyped
    
        
    //listado.add(new Libros (libros.getTitulo,));

    private void BuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscadorKeyReleased
        /* TODO add your handling code here:    */
          info.setText("");
    
                 mostrardatos(Buscador.getText());
                   
    }//GEN-LAST:event_BuscadorKeyReleased


    private void jModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jModificarActionPerformed
        // TODO add your handling code here:
           Modificar();
     
    }//GEN-LAST:event_jModificarActionPerformed

    private void jEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEliminarActionPerformed
        // TODO add your handling code here:
        Eliminar();
    }//GEN-LAST:event_jEliminarActionPerformed
    
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
    private javax.swing.JLabel Buscar;
    private javax.swing.JButton bCrear;
    private javax.swing.JTextArea info;
    private javax.swing.JComboBox<Genero> jComboBox1;
    private javax.swing.JTextField jEditorial;
    private javax.swing.JButton jEliminar;
    private javax.swing.JLabel jId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jModificar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTYear;
    private javax.swing.JTable jTable;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextId;
    private javax.swing.JTextField jTitulo;
    // End of variables declaration//GEN-END:variables



   
}
