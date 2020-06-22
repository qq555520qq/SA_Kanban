package kanban.domain;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

public class KanbanStart extends HttpServlet implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ApplicationContext.getInstance();
        System.out.println("Kanban Start!");

    }
}