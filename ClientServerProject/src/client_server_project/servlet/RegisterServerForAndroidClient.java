package client_server_project.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/register.do")
public class RegisterServerForAndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServerForAndroidClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--GET--");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--POST--");
		request.setCharacterEncoding("UTF-8");

		/**
		 * data = {
		 * "loginName":"Charlie","interests":["Music","PCGames","Sports"] }
		 */

		// These Parameters should be the same as what you use in Client!!
		// e.g.:Data; LoginName; Interests
		String data = request.getParameter("Data");
		System.out.println("From Client \n"+data);

		/**
		 * *****************************************************************
		 * JSON Parsing:
		 */
		/*
		 * json-lib:
		 */
		JSONObject jsonObjectIn = JSONObject.fromObject(data);

		String loginName = jsonObjectIn.getString("LoginName");
		System.out.println("Register Name is ---> " + loginName
				+ " \nInterests are: ");
		JSONArray interests = jsonObjectIn.getJSONArray("Interests");
		if (interests != null) {
			for (Object obj : interests) {
				System.out.println("* " + obj.toString());
			}
		}
		/**
		 * Business Processing: You can do whatever you like by using these
		 * info.
		 * *****************************************************************
		 */

		/*
		 * Response: to the JSON data:
		 * 
		 * e.g.:
		 * 
		 * { "result":"success", "errorMsg":"" }
		 * 
		 * { "result":"failed", "errorMsg":" Register Failed" }
		 */

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = UTF-8");

		PrintWriter out = null;

		try {
			// Now, out can response to the client and write to the client's
			// WebPage
			out = response.getWriter();

			/**
			 * *****************************************************************
			 * JSON Wrapping again :
			 * for Client By only using a Class,
			 * and set the fields to the object of this class. You will see we
			 * can using JSONObject.fromObject() to wrap every object's fields
			 * and the their values to an JSON data!!
			 */

			ResultJSONBean jsonBean = new ResultJSONBean();
			// OK:200
			jsonBean.setResult("success");
			jsonBean.setErrorMsg("");

//			jsonBean.setResult("failed");
//			jsonBean.setErrorMsg("SORRY, REGISTER FAILED");

			JSONObject jsonObjectOut = JSONObject.fromObject(jsonBean);
			System.out.println(" Back to Client: \n"+jsonObjectOut.toString());
			out.print(jsonObjectOut.toString());

			/**
			 * *****************************************************************
			 */

		} finally {
			if (out != null) {
				out.close();
			}

		}

	}
}
