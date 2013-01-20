package edu.cmu.cs.webapp.todolist7.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.todolist7.databean.ItemBean;
import edu.cmu.cs.webapp.todolist7.formbean.ItemForm;
import edu.cmu.cs.webapp.todolist7.model.ItemDAO;
import edu.cmu.cs.webapp.todolist7.model.Model;

public class AddAction extends Action {
	private FormBeanFactory<ItemForm>  itemFormFactory  = FormBeanFactory.getInstance(ItemForm.class);
	
	private ItemDAO itemDAO;

	public AddAction(Model model) {
		itemDAO = model.getItemDAO();
	}

	public String getName() { return "add.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
       		// Fetch the items now, so that in case there is no form or there are errors
       		// We can just dispatch to the JSP to show the item list (and any errors)
       		request.setAttribute("items", itemDAO.getItems());
       		
	        ItemForm form = itemFormFactory.create(request);
        	request.setAttribute("form", form);

	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) {
	        	return "todolist.jsp";
	        }
	        
	        ItemBean item = new ItemBean();
	        item.setItem(form.getItem());

        	if (form.getAction().equals("Add to Top")) {
        		itemDAO.addToTop(item);
        	} else if (form.getAction().equals("Add to Bottom")) {
        		itemDAO.addToBottom(item);
        	} else {
        		errors.add("Invalid action: " + form.getAction());
        	}

       		// Fetch the items again, since we modified the list
       		request.setAttribute("items", itemDAO.getItems());
       		
       		return "todolist.jsp";

        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
