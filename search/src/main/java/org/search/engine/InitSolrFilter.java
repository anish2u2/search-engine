package org.search.engine;

import java.io.File;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.servlet.SolrDispatchFilter;
import org.springframework.stereotype.Component;

@Component("SolrRequestFilter")
public class InitSolrFilter extends SolrDispatchFilter {

	public static SolrClient solrServer;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Initializing Solr Server.");
		File workingDirectory = (File) arg0.getServletContext().getAttribute(ServletContext.TEMPDIR);
		System.out.println("Working directory path:" + workingDirectory.getAbsolutePath());
		System.out.println("Context path:" + arg0.getServletContext().getContextPath());
		String absolutePath = workingDirectory.getAbsolutePath().split(arg0.getServletContext().getContextPath())[0];
		System.out.println("Solr home path :" + absolutePath);
		System.setProperty("solr.solr.home", "D:/x_solr/solr/");
		System.setProperty("solr.data.dir", "D:/x_solr/solr/search-core/data/");
		arg0.getServletContext().setAttribute("solr.solr.home", "D:/x_solr/solr/");
		// CoreContainer.Initializer conreContainerInitializer = new
		// CoreContainer.Initializer();
		// CoreContainer coreContainer;
		try {

			// //coreContainer = conreContainerInitializer.initialize();
			// for (String coreName : coreContainer.getCoreNames()) {
			// System.out.println("COre:" + coreName);
			// }

			System.out.println("Initializing Solr Filter...");
			super.init(arg0);
			System.out.println("Solr Home directory:" + SolrResourceLoader.locateSolrHome());

			CoreContainer coreContainer = CoreContainer.createAndLoad(new File("D:/x_solr/solr/").toPath());
			System.out.println("COre success fully created..");
			/*
			 * SolrCore solrCore = coreContainer.create("search-core",
			 * ImmutableMap.of(CoreDescriptor.CORE_DATADIR, new
			 * File("D:/x_solr/solr/").getAbsolutePath()));
			 */
			solrServer = new EmbeddedSolrServer(coreContainer, "search-core");
			System.out.println("getting cores:" + coreContainer.getAllCoreNames());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Initialization of  Solr Filter done...");
	}

	public SolrClient getSolrClient() {
		return solrServer;
	}

}
