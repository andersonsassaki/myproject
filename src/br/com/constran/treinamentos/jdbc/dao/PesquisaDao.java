package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Pesquisa;

public class PesquisaDao {

	private Connection conn;
	
	public PesquisaDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public List<Pesquisa> getListaPesquisa(String strSearch, int idUsuario, String grupoUsuario) {
		String strSql;
		/*
		strSql = "SELECT t.idTreinamento, t.descricao, t.data, ";
		strSql = strSql + "0 AS idTreinamentoParticipante, '' AS cpf, '' AS matricula, '' AS nome, ";
		strSql = strSql + "'T' AS tipo ";
		strSql = strSql + "FROM treinamentos t ";
		strSql = strSql + "WHERE t.descricao LIKE '%" + strSearch + "%' ";
		strSql = strSql + "UNION ";
		strSql = strSql + "SELECT t.idTreinamento, t.descricao, t.data, ";
		strSql = strSql + "tp.idTreinamentoParticipante, tp.cpf, tp.matricula, tp.nome, ";
		strSql = strSql + "'C' AS tipo ";
		strSql = strSql + "FROM treinamentoParticipantes tp ";
		strSql = strSql + "INNER JOIN treinamentos t ON (t.idTreinamento = tp.treinamento) ";
		strSql = strSql + "WHERE tp.nome LIKE '%" + strSearch + "%' ";
		*/
		
		if (grupoUsuario.contains("adm")) {
			strSql = "SELECT 'P' AS participanteDoc, t.idTreinamento, t.obra, o.nomeObra, ";
			strSql = strSql + "t.numeroRegistro, t.data, ";
			strSql = strSql + "t.instrutorNome, t.instrutorFuncao, ";
			strSql = strSql + "tp.cpf AS cpfRevisao, ";
			strSql = strSql + "tp.nome AS nomeNumeroDoc, ";
			strSql = strSql + "tp.descFuncao AS descFuncaoDoc, ";
			strSql = strSql + "tp.presencaTreinamento ";
			strSql = strSql + "FROM treinamentos t "; 
			strSql = strSql + "INNER JOIN treinamentoParticipantes tp ON (tp.treinamento = t.idTreinamento) ";
			strSql = strSql + "INNER JOIN obras o ON (o.id = t.obra) ";
			strSql = strSql + "WHERE tp.nome LIKE '%" + strSearch + "%' ";
			strSql = strSql + "UNION ALL ";
			strSql = strSql + "SELECT 'D' AS participanteDoc, t.idTreinamento, t.obra, o.nomeObra, ";
			strSql = strSql + "t.numeroRegistro, t.data, ";
			strSql = strSql + "t.instrutorNome, t.instrutorFuncao, ";
			strSql = strSql + "td.revisaoDocumento AS cpfRevisao, ";
			strSql = strSql + "td.numeroDocumento AS nomeNumeroDoc, ";
			strSql = strSql + "td.descDocumento AS descFuncaoDoc, ";
			strSql = strSql + "'' AS presencaTreinamento ";
			strSql = strSql + "FROM treinamentos t ";
			strSql = strSql + "INNER JOIN treinamentoDocumentos td ON (td.treinamento = t.idTreinamento) ";
			strSql = strSql + "INNER JOIN obras o ON (o.id = t.obra) ";
			strSql = strSql + "WHERE (td.descDocumento LIKE '%" + strSearch + "%' ";
			strSql = strSql + "   OR td.numeroDocumento LIKE '%" + strSearch + "%') ";
			strSql = strSql + "ORDER BY participanteDoc, nomeNumeroDoc ";			
		} else {
			strSql = "SELECT 'P' AS participanteDoc, t.idTreinamento, t.obra, o.nomeObra, ";
			strSql = strSql + "t.numeroRegistro, t.data, ";
			strSql = strSql + "t.instrutorNome, t.instrutorFuncao, ";
			strSql = strSql + "tp.cpf AS cpfRevisao, ";
			strSql = strSql + "tp.nome AS nomeNumeroDoc, ";
			strSql = strSql + "tp.descFuncao AS descFuncaoDoc, ";
			strSql = strSql + "tp.presencaTreinamento ";
			strSql = strSql + "FROM treinamentos t "; 
			strSql = strSql + "INNER JOIN treinamentoParticipantes tp ON (tp.treinamento = t.idTreinamento) ";
			strSql = strSql + "INNER JOIN obras o ON (o.id = t.obra) ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = o.id) ";
			strSql = strSql + "WHERE tp.nome LIKE '%" + strSearch + "%' ";
			strSql = strSql + "  AND ou.usuario = " + idUsuario;
			strSql = strSql + " UNION ALL ";
			strSql = strSql + "SELECT 'D' AS participanteDoc, t.idTreinamento, t.obra, o.nomeObra, ";
			strSql = strSql + "t.numeroRegistro, t.data, ";
			strSql = strSql + "t.instrutorNome, t.instrutorFuncao, ";
			strSql = strSql + "td.revisaoDocumento AS cpfRevisao, ";
			strSql = strSql + "td.numeroDocumento AS nomeNumeroDoc, ";
			strSql = strSql + "td.descDocumento AS descFuncaoDoc, ";
			strSql = strSql + "'' AS presencaTreinamento ";
			strSql = strSql + "FROM treinamentos t ";
			strSql = strSql + "INNER JOIN treinamentoDocumentos td ON (td.treinamento = t.idTreinamento) ";
			strSql = strSql + "INNER JOIN obras o ON (o.id = t.obra) ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = o.id) ";
			strSql = strSql + "WHERE (td.descDocumento LIKE '%" + strSearch + "%' ";
			strSql = strSql + "   OR td.numeroDocumento LIKE '%" + strSearch + "%') ";
			strSql = strSql + "  AND ou.usuario = " + idUsuario;
			strSql = strSql + " ORDER BY participanteDoc, nomeNumeroDoc ";			
		}

		
		 		
		try {
			List<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
								
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Pesquisa pesquisa = new Pesquisa();
				
				pesquisa.setParticipanteDoc(rs.getString("participanteDoc"));
				pesquisa.setIdTreinamento(rs.getInt("idTreinamento"));
				pesquisa.setObra(rs.getInt("obra"));
				pesquisa.setNomeObra(rs.getString("nomeObra"));
				pesquisa.setNumeroRegistro(rs.getString("numeroRegistro"));
								
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data"));	
				pesquisa.setData(data);
								
				pesquisa.setInstrutorNome(rs.getString("instrutorNome"));
				pesquisa.setInstrutorFuncao(rs.getString("instrutorFuncao"));
				pesquisa.setCpfRevisao(rs.getString("cpfRevisao"));
				pesquisa.setNomeNumeroDoc(rs.getString("nomeNumeroDoc"));
				pesquisa.setDescFuncaoDoc(rs.getString("descFuncaoDoc"));
				pesquisa.setPresencaTreinamento(rs.getString("presencaTreinamento"));
				
				if (rs.getString("participanteDoc").equals("P")) {
					TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();	
					pesquisa.setTreinamentoDocumento(treinamentoDocumentoDao.getListaTreinamentosDocs(Integer.toString(rs.getInt("idTreinamento"))));
				} else if (rs.getString("participanteDoc").equals("D")) {
					TreinamentoParticipanteDao treinamentoParticipanteDao = new TreinamentoParticipanteDao();	
					pesquisa.setTreinamentoParticipante(treinamentoParticipanteDao.getListaTreinamentosParticipantes(Integer.toString(rs.getInt("idTreinamento"))));
				}
				
				pesquisas.add(pesquisa);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
				
			return pesquisas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}	
	
}
