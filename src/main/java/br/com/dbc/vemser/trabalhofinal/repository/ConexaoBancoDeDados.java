package br.com.dbc.vemser.trabalhofinal.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

//    @Value("${db.oracle.server}")
    private String server;
//    @Value("${db.oracle.port}")
    private String port;
//    @Value("${db.oracle.database}")
    private String databse;
//    @Value("${db.oracle.user}")
    private String user;
//    @Value("${db.oracle.password}")
    private String password;
//    @Value("${db.oracle.scheme}")
    private String scheme;


    public Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + databse;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, user, password);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + scheme);

        return con;
    }
}
