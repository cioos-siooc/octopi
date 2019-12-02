/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.Category;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CategoryDao {

  public void fullUpdateCategory(Category category, Category oldCategory) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    try {
      et.begin();
      Category persistedCategory = em.find(Category.class, oldCategory.getId());
      em.remove(persistedCategory);
      em.flush();
      em.merge(category);
      et.commit();
    } finally {
      em.close();
    }
  }
}
