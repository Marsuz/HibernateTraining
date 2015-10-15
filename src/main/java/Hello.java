import org.hibernate.*;
import java.util.List;

/**
 * Created by Marcin on 2015-10-14.
 */
public class Hello {

    public static void main(String[] args) {
        Message message = new Message("Siema");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long msgId = (Long) session.save(message);
        transaction.commit();
        session.close();

        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        List<Message> messages = newSession.createQuery("from Message m order by m.text asc").list();
        System.out.println( messages.size() + " message(s) found:" );
        for ( Message m : messages ) {
            System.out.println(m.getText());
        }
        newTransaction.commit();
        newSession.close();

        // Third unit of work
        Session thirdSession = HibernateUtil.getSessionFactory().openSession();
        Transaction thirdTransaction = thirdSession.beginTransaction();
        message = (Message) thirdSession.get( Message.class, msgId );
        message.setText( "Greetings Earthling" );
        message.setNextMessage(new Message( "Take me to your leader (please)" ));
        thirdTransaction.commit();
        thirdSession.close();


        // Shutting down the application
        HibernateUtil.shutdown();






    }
}
