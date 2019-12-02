/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AuthEntityManagerFactory implements ServletContextListener {

  private static EntityManagerFactory emf;

  public static EntityManager createEntityManager() {
    if (emf == null) {
      throw new IllegalStateException("Context is not initialized yet.");
    }

    return emf.createEntityManager();
  }

  public static CriteriaBuilder getCriteriaBuilder() {
    if (emf == null) {
      throw new IllegalStateException("Context is not initialized yet.");
    }

    return emf.getCriteriaBuilder();
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    emf = Persistence.createEntityManagerFactory("auth");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    emf.close();
  }
}

