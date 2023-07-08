package org.example;

import database.HibernateUtil;
import entity.Client;
import entity.Planet;
import entity.Ticket;
import org.flywaydb.core.Flyway;
import service.ClientCrudService;
import service.PlanetCrudService;
import service.TicketCrudService;

import java.util.ResourceBundle;

public class DataBaseTest {
        private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("hibernate");
        private static final String JDBC_URL = "hibernate.connection.url";

        public static void main(String[] args) {
            Flyway.configure()
                    .dataSource(resourceBundle.getString(JDBC_URL), null, null)
                    .load()
                    .migrate();

            ClientCrudService clientCrudService = new ClientCrudService();
            PlanetCrudService planetCrudService = new PlanetCrudService();
            TicketCrudService ticketCrudService = new TicketCrudService();

            Ticket ticket = new Ticket();
            ticket.setClient(clientCrudService.getById(1L));
            ticket.setToPlanet(planetCrudService.getById("MARS"));
            ticket.setFromPlanet(planetCrudService.getById("URAN"));

            ticketCrudService.create(ticket);
            System.out.println(ticketCrudService.getAllTickets());

            HibernateUtil.getInstance().close();
        }

}