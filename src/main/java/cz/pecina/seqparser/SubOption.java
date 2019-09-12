/* SubOption.java
 *
 * Copyright (C) 2015-19, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.pdf, a suite of PDF processing applications.
 *
 * This application is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The source code is available from <https://github.com/tompecina/pdf>.
 */

package cz.pecina.seqparser;

import java.util.logging.Logger;

/**
 * Sub-option object.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
@SuppressWarnings("checkstyle:ConstantName")
public class SubOption {

  // static logger
  private static final Logger log = Logger.getLogger(SubOption.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "SubOption";
  }

  // predefined objects
  public static final SubOption String = new SubOption(String.class);
  public static final SubOption Number = new SubOption(Number.class);

  // fields
  private Class<?> type;

  /**
   * Gets the type of the sub-option.
   *
   * @return the type of the sub-option
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Creates the sub-option object.
   *
   * @param type the type of the sub-option
   */
  public SubOption(final Class<?> type) {
    this.type = type;
  }
}
