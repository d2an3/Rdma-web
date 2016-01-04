

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AjaxServlet" })
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void writeInputToFile(String input, String outputFileName) throws IOException {
		File file = new File(outputFileName);
		System.out.println(file.getAbsolutePath());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
		out.println(input);
		out.close();
	}
	
	private void fillResponse(HttpServletResponse response, Result result) throws IOException {
		response.setContentType("image/png");
		
		response.setIntHeader("robustness", result.check_robustness? 1 : 0);
		response.setIntHeader("sat", result.is_sat? 1 : 0);
		
		if (result.is_sat) {
			FileInputStream in  = new FileInputStream("image.png");
			byte[] buffer = new byte[502400];
			in.read(buffer);
			buffer = Base64.getEncoder().encode(buffer);
			ServletOutputStream out = response.getOutputStream();
			out.write(buffer);
			in.close();
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code_text = request.getParameter("cpp_code");

		String outputFileName = "foo.out";
		try {
			writeInputToFile(code_text, outputFileName);
			Result analysisResult = Main.checkFile(outputFileName);			
			fillResponse(response, analysisResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
