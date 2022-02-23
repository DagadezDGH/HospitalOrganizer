package dad.hospitalorganizer.informes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dad.hospitalorganizer.controller.EntradaVerController;
import dad.hospitalorganizer.controller.SalidaVerController;
import dad.hospitalorganizer.models.Entrada;
import dad.hospitalorganizer.models.EntradaArticulo;
import dad.hospitalorganizer.models.SalidaArticulo;
import dad.hospitalorganizer.models.tablaMostrar;
import javafx.beans.property.ListProperty;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GenerarPDF {

	public static void generarPdfEntrada(List<EntradaArticulo> lista) throws JRException, IOException {
		// compila el informe
		JasperReport report = JasperCompileManager.compileReport(GenerarPDF.class.getResourceAsStream("/reports/entrada.jrxml"));

		// mapa de parámetros para el informe
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("anyo", 2014); // no lo uso, pero se lo paso

		// generamos el informe (combinamos el informe compilado con los datos)
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,
				new JRBeanCollectionDataSource(lista));

		// exporta el informe a un fichero PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, "Entrada.pdf");

		// Abre el archivo PDF generado con el programa predeterminado del sistema
		Desktop.getDesktop().open(new File("Entrada.pdf"));
	}
	
	public static void generarPdfSalida(List<tablaMostrar> lista) throws JRException, IOException {
		// compila el informe
		JasperReport report = JasperCompileManager.compileReport(GenerarPDF.class.getResourceAsStream("/reports/salida.jrxml"));

		// mapa de parámetros para el informe
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("anyo", 2014); // no lo uso, pero se lo paso

		// generamos el informe (combinamos el informe compilado con los datos)
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,
				new JRBeanCollectionDataSource(lista));

		// exporta el informe a un fichero PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, "Salida.pdf");

		// Abre el archivo PDF generado con el programa predeterminado del sistema
		Desktop.getDesktop().open(new File("Salida.pdf"));
	}

}