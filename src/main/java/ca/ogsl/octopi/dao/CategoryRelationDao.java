/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.CategoryRelation;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CategoryRelationDao {

  public void fullUpdateTopic(CategoryRelation categoryRelation,
      CategoryRelation oldCategoryRelation) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    try {
      et.begin();
      CategoryRelation persistedCategoryRelation = em
          .find(CategoryRelation.class, oldCategoryRelation.getId());
      em.remove(persistedCategoryRelation);
      em.flush();
      em.merge(categoryRelation);
      et.commit();
    } finally {
      em.close();
    }
  }
}
