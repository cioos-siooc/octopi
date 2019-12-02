/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.util.GenericsUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.transform.DistinctResultTransformer;

public class GenericDao {

  public <T> T getEntityFromCode(String code, Class clazz) {
    T entity;
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    try {
      CriteriaQuery<T> cq = cb.createQuery(clazz);
      Root<T> root = cq.from(clazz);
      cq.where(cb.equal(root.get("code"), code));
      TypedQuery<T> tq = em.createQuery(cq);
      entity = GenericsUtil.getSingleResultOrNull(tq);
      return entity;
    } finally {
      em.close();
    }
  }

  public <T> List<T> getAllEntities(Class<T> clazz) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    List<T> entities;
    try {
      CriteriaQuery<T> cq = cb.createQuery(clazz);
      Root<T> root = cq.from(clazz);
      TypedQuery<T> tq = em.createQuery(cq);
      entities = tq.getResultList();
      entities = DistinctResultTransformer.INSTANCE.transformList(entities);
    } finally {
      em.close();
    }
    return entities;
  }

  public <T> T getEntityFromId(Integer id, Class<T> clazz) {
    T entity;
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    try {
      CriteriaQuery<T> cq = cb.createQuery(clazz);
      Root<T> root = cq.from(clazz);
      cq.where(cb.equal(root.get("id"), id));
      TypedQuery<T> tq = em.createQuery(cq);
      entity = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return entity;
  }

  public <T> T mergeEntity(T entity) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    T databaseEntity;
    try {
      et.begin();
      databaseEntity = em.merge(entity);
      et.commit();
    } finally {
      em.close();
    }
    return databaseEntity;
  }

  public <T> T persistEntity(T entity) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    try {
      et.begin();
      em.persist(entity);
      et.commit();
    } finally {
      em.close();
    }
    return entity;
  }

  public <T> void deleteEntityFromId(Integer id, Class clazz) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    T databaseEntity;
    try {
      et.begin();
      CriteriaQuery<T> cq = cb.createQuery(clazz);
      Root<T> root = cq.from(clazz);
      cq.where(cb.equal(root.get("id"), id));
      TypedQuery<T> tq = em.createQuery(cq);
      databaseEntity = GenericsUtil.getSingleResultOrNull(tq);
      em.remove(databaseEntity);
      et.commit();
    } finally {
      em.close();
    }

  }
}
