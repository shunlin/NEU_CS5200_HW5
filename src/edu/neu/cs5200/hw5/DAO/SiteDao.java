package edu.neu.cs5200.hw5.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.neu.cs5200.hw5.models.Site;

import java.util.ArrayList;
import java.util.List;

@Path("/site")
public class SiteDao {
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("DBHW5");
	private EntityManager em = null;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("id") int siteId) {
		Site site = null;
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site = em.find(Site.class, siteId);
		
		em.getTransaction().commit();
		em.close();
		
		return site;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> findAllSites() {
		List<Site> sites = new ArrayList<Site>();
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("select site from Site site");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return sites;
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("id") int siteId, Site site) {
		List<Site> sites = new ArrayList<Site>();
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site.setId(siteId);
		em.merge(site);
		
		Query query = em.createQuery("select site from Site site");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return sites;
	}

	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> removeSite(@PathParam("id") int siteId) {
		List<Site> sites = new ArrayList<Site>();
		Site site = null;
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		site = em.find(Site.class, siteId);
		em.remove(site);
		
		Query query = em.createQuery("select site from Site site");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return sites;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> createSite(Site site) {
		List<Site> sites = new ArrayList<Site>();
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(site);
		
		Query query = em.createQuery("select site from Site site");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return sites;
	}
	
	public SiteDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void main (String[] args) {
		SiteDao sd = new SiteDao();
		
		//Site s = new Site(2,"Site 2",21.32,43.54);
		//sd.updateSite(2, s);
		
		List<Site> sites = sd.findAllSites();
		
		for (Site site : sites) {
			System.out.println(site.getName());
		}
		
		
		/*
		List<Tower> towers = sd.findAllTower();
		
		for (Tower tower : towers) {
			System.out.println(tower.getName());
			List<Equipment> equipments = tower.getEquipments();
			System.out.println(tower.getSite().getName());
			for (Equipment equipment : equipments) {
				System.out.println(equipment.getName());
			}
		}
		*/
		
		
    }
}
