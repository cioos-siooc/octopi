/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.AuthDao;
import ca.ogsl.octopi.models.auth.Role;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AuthService {

  AuthDao authDao = new AuthDao();

  public List<Role> getUserRoles(String username, String password) throws NoSuchAlgorithmException {
    String hashedPassword = encode(password);
    return this.authDao.getUserRoles(username, hashedPassword);
  }

  /**
   * Encode given string into a SHA-512 string
   *
   * @param toEncode The string to encode
   * @return The encoded string
   */
  private static String encode(String toEncode) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(toEncode.getBytes());

    byte byteData[] = md.digest();

    StringBuffer hexString = new StringBuffer();
    for (byte aByteData : byteData) {
      String hex = Integer.toHexString(0xff & aByteData);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
