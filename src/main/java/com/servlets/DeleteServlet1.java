package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.entities.Note;
import com.helper.FactoryProvider;

public class DeleteServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteServlet1() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Transaction transaction = null;
		Session session = null;

		try {
			int noteId = Integer.parseInt(request.getParameter("note_id").trim());

			session = FactoryProvider.getFactory().openSession();
			transaction = session.beginTransaction();

			Note note = (Note) session.get(Note.class, noteId);
			if (note != null) {
				session.delete(note);
				transaction.commit();
			} else {
				// Note not found, handle accordingly
				System.out.println("Note not found with ID: " + noteId);
			}

			response.sendRedirect("all_notes.jsp");

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback(); // Rollback in case of an error
			}
			e.printStackTrace(); // Log the exception
//			response.sendRedirect("error.jsp"); // Redirect to an error page if needed

		} finally {
			if (session != null) {
				session.close(); // Ensure the session is closed
			}
		}
	}
}