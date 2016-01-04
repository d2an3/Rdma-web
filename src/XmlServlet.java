import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XmlServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello from xml GET method.");
		String userName = request.getParameter("userName");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("Hello! " + userName + " !");
	}
	
	private void writeInputToFile(String input, String outputFileName) throws IOException {
		File file = new File(outputFileName);
		System.out.println(file.getAbsolutePath());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
		out.println(input);
		out.close();
	}
	
	private void fillResponse(HttpServletResponse response, Result result) throws IOException {
		response.setContentType("text/html");
		
		PrintWriter writer = response.getWriter();
		writer.println("Hello! <br>");
		if (result.check_robustness) {
            writer.println("Program is " + ((result.is_sat) ? "not " : "") + "robust");
        } else {
            writer.println("Program " + ((result.is_sat) ? "does not satisfy " : "satisfies ") + "the assertion.");
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code_text = request.getParameter("code_text");
		String outputFileName = "foo.out";
		try {
			writeInputToFile(code_text, outputFileName);
			Result analysisResult = Main.checkFile(outputFileName);			
			fillResponse(response, analysisResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
