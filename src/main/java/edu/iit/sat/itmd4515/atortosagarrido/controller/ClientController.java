 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.controller;

import edu.iit.sat.itmd4515.atortosagarrido.model.Client;
import edu.iit.sat.itmd4515.atortosagarrido.model.Membership;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
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
    
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    //@Resource(lookup = "jdbc/itmd4515")
    //DataSource ds;
    
    @PersistenceContext(name = "itmd4515PU")
    EntityManager em;
    
    @Resource
    UserTransaction tx;
            
            
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
        List<Membership> membershipAll = em.createNamedQuery("Membership.getAll",Membership.class)
                .getResultList();
        request.setAttribute("memtypes", membershipAll.toArray());
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
        Client c = receiveClient(request, response);
        LOG.log(Level.INFO, "Received client: {0}", c);
        if(c.getBirthDate()!=null){request.setAttribute("dateString", format.format(c.getBirthDate()));}
        request.setAttribute("client", c);
        List<Membership> memberships = em.createNamedQuery("Membership.getAll",Membership.class).getResultList();
        request.setAttribute("memtypes", memberships);
        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(c);
        if(!constraintViolations.isEmpty()){
            LOG.info("Constraints violated.");
            for(ConstraintViolation<Client> bad : constraintViolations){
                LOG.log(Level.INFO, "{0}: {1}", new Object[]{bad.getPropertyPath(), bad.getMessage()});
            }
            request.setAttribute("mistakes", constraintViolations);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/clientform.jsp");
            rd.forward(request, response);
        }else{
            LOG.info("Client validated. All good.");
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/clientconfirmation.jsp");
            rd.forward(request, response);
            try {
                tx.begin();
                em.persist(c);
                tx.commit();
            } catch (Exception ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("/atortosagarrido/error.jsp");
            }
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

    private Client receiveClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("firstName").trim();
        String surname = request.getParameter("familyName").trim();
        String dateBrithParam = request.getParameter("birthDate");
        Date dateBirth = null;
        Membership membership = em.createNamedQuery("Membership.findByName",Membership.class)
                .setParameter("name", request.getParameter("membership"))
                .getSingleResult();
        double height = 0.0;
        double weight = 0.0;
        if(dateBrithParam.isEmpty()){
            LOG.info("Received empty Date");
        }else{
            try {
                dateBirth = format.parse(dateBrithParam);
            } catch (ParseException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("/atortosagarrido/error.jsp");
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
        Client n_c = new Client(name, surname, dateBirth, height, weight);
        n_c.setMembership(membership);
        return n_c;
    }

}