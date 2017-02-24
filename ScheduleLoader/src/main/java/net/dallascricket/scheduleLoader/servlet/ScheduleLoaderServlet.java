package net.dallascricket.scheduleLoader.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.dallascricket.scheduleLoader.controller.ScheduleLoaderController;

@WebServlet("/ScheduleLoaderServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)   	// 100 MB
public class ScheduleLoaderServlet extends HttpServlet {


	private static final long serialVersionUID = -1451484932420205910L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        //Get all the parts from request and write it to the file on server
		StringBuilder log = new StringBuilder();
		Part part = req.getPart("fileName");
        InputStream inputStream = part.getInputStream();
        ScheduleLoaderController controller = new ScheduleLoaderController(inputStream);
        try{
        controller.loadTapeBall();
        controller.loadLeatherBall();
        log.append("schedule uploaded succesfully!!!");
        }catch(Exception e){
        	log.append("schedule upload failed "+e.getMessage());
        }
        req.setAttribute("message", log);
        getServletContext().getRequestDispatcher("/response.jsp").forward(
                req, resp);
        
  
	}
	
	



}
