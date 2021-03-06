/* SubOption.java
 *
 * Copyright (C) 2019, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.seqparser, a sequential command-line parser.
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
 * The source code is available from <https://github.com/tompecina/seqparser>.
 */

package cz.pecina.seqparser;

import java.util.logging.Logger;

/**
 * Sub-option object.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public final class SubOption {

  // static logger
  private static final Logger log = Logger.getLogger(SubOption.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "SubOption";
  }

  /** Type of the parameter. */
  private final ParameterType type;

  /**
   * Gets the type of the sub-option.
   *
   * @return the type of the sub-option
   */
  public ParameterType getType() {
    return type;
  }

  /**
   * Creates the sub-option object.
   *
   * @param type the type of the sub-option
   */
  public SubOption(final ParameterType type) {
    this.type = type;
  }
}
