package com.upreader.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import com.upreader.AsyncResource;
import com.upreader.UpreaderApplication;

/**
 * handles initialization of persistence engine
 * 
 * could be used as an L2 cache because it survives across requests, while the EntityManager is created for each Context
 * 
 * @author Flavius
 *
 */
public class EntityStore implements AsyncResource {
	private final UpreaderApplication application;
	private DataSource dataSource;
	private EntityManagerFactory emf;
	
	public EntityStore(UpreaderApplication application) {
		this.application = application;
	}
		
	@Override
	public void begin() {
		this.dataSource = getApplication().getServlet().getDataSource();
		emf = Persistence.createEntityManagerFactory("default");
	}
	
	public UpreaderApplication getApplication() {
		return application;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}	
	
	public EntityManager createEm() {
		return emf.createEntityManager();
	}
	
	public void closeEm(EntityManager em) {
		em.close();
	}

	@Override
	public void end() {
		if(this.emf != null)
			this.emf.close();
	}
}
