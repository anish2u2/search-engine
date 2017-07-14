package org.search.engine.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.search.engine.InitSolrFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("webAppInit")
public class WebAppInitialized implements ApplicationContextAware {
	
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		final List<Pojo> pojoList = new ArrayList<Pojo>();
		for (int i = 0; i < 35; i++) {
			Pojo pojo = new Pojo();
			pojo.setId("1234"+i);
			pojo.setText("Hi");
			pojo.setNext_t("next_t");
			pojoList.add(pojo);
		}

		new Thread(new Runnable() {

			public void run() {
				try {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Pushing values to solr.");
					InitSolrFilter.solrServer.addBeans(pojoList);
					InitSolrFilter.solrServer.commit();
					System.out.println("Pushed values to solr.");
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	public class Pojo {
		@Field("id")
		private String id;
		@Field("text")
		private String text;
		@Field("next_t")
		private String next_t;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getNext_t() {
			return next_t;
		}

		public void setNext_t(String next_t) {
			this.next_t = next_t;
		}

	}

}
