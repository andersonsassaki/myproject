package br.com.constran.treinamentos.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.constran.treinamentos.jdbc.dao.ReportDao;
import br.com.constran.treinamentos.util.Constants;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import net.sf.jasperreports.engine.xml.JRXmlLoader;


@Controller
public class ReportController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//@SuppressWarnings("deprecation")
	@RequestMapping(value="/geraRelatorio")
	protected void geraRelatorio(HttpServletRequest request, HttpServletResponse response,
								 @RequestParam("relatorio") String relatorio, @RequestParam("idTreinamento") String treinamento,
								 @RequestParam("ccObra") String ccObra) throws Exception {



			String path = (System.getProperty("os.name").contains("Windows")) ? Constants.URL_REPORTS_PATH_WINDOWS : Constants.URL_REPORTS_PATH_LINUX;
			String path_img = path + Constants.FOLDER_IMAGES;
	        String path_sub = path + Constants.FOLDER_SUBREPORTS;
		
		    InputStream jasperStream = this.getClass().getResourceAsStream(path + relatorio + ".jasper");
		    Map<String,Object> params = new HashMap<>();
		    params.put("idTreinamento", treinamento);
		    params.put("ccObra", ccObra);
		    params.put("SUBREPORT_DIR", path_sub);
	        params.put("IMAGE_DIR", path_img);

		    JasperDesign desenho = JRXmlLoader.load( path + relatorio + ".jrxml" );
		    JasperReport jasperReport = JasperCompileManager.compileReport( desenho );
		    
		    ReportDao reportDao = new ReportDao();
			Connection conn = reportDao.getConexao();
					    
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn/*new JREmptyDataSource()*/);

		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=" + relatorio + ".pdf");

		    final OutputStream outStream = response.getOutputStream();
		    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		    			
    }
	
}
