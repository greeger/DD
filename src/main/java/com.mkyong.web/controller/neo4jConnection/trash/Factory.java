package trash;

import node.*;
import relationship.*;

import org.neo4j.ogm.config.Configuration;
//import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
//import org.neo4j.ogm.transaction.Transaction;

public class Factory {

    /**
     * Session factory for connecting to Neo4j database
     */
    private static SessionFactory sessionFactory;

    //  Configuration info for connecting to the Neo4J database
    static private final String SERVER_URI = "bolt://localhost:7687";
    static private final String SERVER_USERNAME = "neo4j";
    static private final String SERVER_PASSWORD = "11";


    /**
     * Constructor
     */
    public Factory() {
    }

	public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
				//  Define session factory for connecting to Neo4j database
				Configuration configuration = new Configuration.Builder().uri(SERVER_URI).credentials(SERVER_USERNAME, SERVER_PASSWORD).build();
				sessionFactory = new SessionFactory(configuration, "node", "relationship");

            } catch (Exception e) {}
        }
        return sessionFactory;
    }
}