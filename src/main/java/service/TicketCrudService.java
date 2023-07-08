package service;

import database.HibernateUtil;
import entity.Client;
import entity.Planet;
import entity.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TicketCrudService {
    private final SessionFactory sessions = HibernateUtil.getInstance().getSessionFactory();

    public void create (Ticket ticket){
        if (ticket.getClient() == null){
            throw new IllegalArgumentException("Ticket must have a client");
        }
        if (ticket.getFromPlanet() == null || ticket.getToPlanet() == null){
            throw new IllegalArgumentException("The ticket must have a start and end planets");
        }
        Session session = sessions.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ticket.setCreatedAt(String.valueOf(LocalDateTime.now()));
            session.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Ticket getById (long id) {
        Session session = sessions.openSession();
        Ticket ticket = session.get(Ticket.class, id);
        session.close();
        return ticket;
    }

    public void delete(long id){
        Session session = sessions.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Ticket ticket = session.get(Ticket.class, id);
            session.remove(ticket);
            transaction.commit();
        }catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Ticket ticket) {
        if (ticket.getClient() == null){
            throw new IllegalArgumentException("Ticket must have a client");
        }
        if (ticket.getFromPlanet() == null || ticket.getToPlanet() == null){
            throw new IllegalArgumentException("The ticket must have a start and end planets");
        }
        Session session = sessions.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(ticket);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Ticket> getAllTickets() {
        Session session = sessions.openSession();
        List<Ticket> ticketList = session.createQuery("from entity.Ticket", Ticket.class).list();
        session.close();
        return ticketList;
    }
}
