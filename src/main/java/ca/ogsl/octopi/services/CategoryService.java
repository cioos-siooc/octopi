/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.CategoryDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Category;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import java.util.List;
import javax.validation.groups.Default;

public class CategoryService {

  private CategoryDao categoryDao = new CategoryDao();
  private GenericDao genericDao = new GenericDao();

  public List<Category> listCategories() throws AppException {
    return this.genericDao.getAllEntities(Category.class);
  }

  public Category getCategory(Integer id) throws AppException {
    Category category = this.genericDao.getEntityFromId(id, Category.class);
    if (category == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return category;
  }

  public Category postCreateCategory(Category category) throws AppException {
    ValidationUtil.validateBean(category, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(category);
  }

  public void putUpdateCategory(Category category, Category oldCategory) throws AppException {
    ValidationUtil.validateBean(category, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(category, oldCategory);
    this.categoryDao.fullUpdateCategory(category, oldCategory);
  }

  private void checkIfUpdatingToExistingEntity(Category category, Category oldCategory)
      throws AppException {
    if (!category.getId().equals(oldCategory.getId())) {
      Category databaseCategory = this.genericDao.getEntityFromId(category.getId(), Category.class);
      if (databaseCategory != null) {
        throw new AppException(400, 400,
            "Cannot update with id from another existing entity", AppConstants.PORTAL_URL);
      }
    }
  }

  public Category retrieveCategory(Integer categoryId) {
    return this.genericDao.getEntityFromId(categoryId, Category.class);
  }
}
