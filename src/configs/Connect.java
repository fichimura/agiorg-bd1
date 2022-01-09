/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Connect {
    private static final String user = "root";
    private static final String password = "root";
    private static final String url = "jdbc:mysql://localhost:8080/agiorg1";
    
    
    //estabelece conexao com bd
    public static Connection getConnection(){
        Connection con = null;
        
        try{

            con = DriverManager.getConnection(url, user, password);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao conectar o banco de dados: " + e.getMessage());
        }
        
        
        return con;
    }
}
