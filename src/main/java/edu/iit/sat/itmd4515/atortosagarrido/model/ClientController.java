/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author antoniotortosa
 */
@WebServlet(name = "ClientController", urlPatterns = {"/client"})
public class ClientController extends HttpServlet {

    @Resource
    Validator validator;
    
    private static final Logger LOG = Logger.getLogger(ClientController.class.getName());
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClientController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientController at " + request.getContextPath() + "</h1>");
            out.println("</body>"); 
            out.println("</html>");
        }
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Client c = new Client();
        request.setAttribute("client", c);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/clientform.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("firstName").trim();
        String surname = request.getParameter("familyName").trim();
        String dateBrithParam = request.getParameter("birthDate");
        Date dateBirth = null;
        int membershipType = Integer.parseInt(request.getParameter("membership"));
        double height = 0.0;
        double weight = 0.0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(dateBrithParam.isEmpty()){
            LOG.info("Received empty Date");
        }else{
            try {
                dateBirth = format.parse(dateBrithParam);
            } catch (ParseException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (request.getParameter("height").trim().equals("")) {
            LOG.info("Received empty height");
        }else{
             height = Double.parseDouble(request.getParameter("height"));
        }
        if (request.getParameter("weight").trim().equals("")) {
            LOG.info("Received empty weight");
        } else {
            weight = Double.parseDouble(request.getParameter("weight"));
        }
        Client c = new Client(name, surname, dateBirth, membershipType, height, weight);
        LOG.log(Level.INFO, "Received client: {0}", c.toString());
        if(dateBirth!=null){request.setAttribute("dateString", format.format(dateBirth));}
        request.setAttribute("client", c);
        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(c);
        if(constraintViolations.size()>0){
            LOG.info("Constraints violated.");
            constraintViolations.forEach((bad) -> {
                LOG.log(Level.INFO, "{0}: {1}", new Object[]{bad.getPropertyPath(), bad.getMessage()});
            });
            request.setAttribute("mistakes", constraintViolations);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/clientform.jsp");
            rd.forward(request, response);
        }else{
            LOG.info("Client validated. All good.");
            String membershipString = "";
            switch(membershipType){
                case 1:
                    membershipString = "Standard";
                    break;
                case 2:
                    membershipString = "Premium";
                    break;
                case 3:
                    membershipString = "Gold";
                    break;
                default:
                    membershipString = "VIP";
            }
            request.setAttribute("membership", membershipString);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/clientconfirmation.jsp");
            rd.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";  
    }

}
