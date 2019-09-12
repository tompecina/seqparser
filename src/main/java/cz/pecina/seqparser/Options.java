/* Options.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Options object.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public class Options {

  // static logger
  private static final Logger log = Logger.getLogger(Options.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "Options";
  }

  // fields
  private List<Option> options = new ArrayList<>();
  private Map<String, Option> shortMap = new HashMap<>();
  private Map<String, Option> longMap = new HashMap<>();
  private char sep = ',';

  /**
   * Gets the option using the short option string.
   *
   * @param shortOpt the short option string or <code>null</code> if not found
   * @return the option
   */
  public Option getOptionShort(final String shortOpt) {
    return shortMap.get(shortOpt);
  }

  /**
   * Gets the option using the long option string.
   *
   * @param longOpt the long option string or <code>null</code> if not found
   * @return the option
   */
  public Option getOptionLong(final String longOpt) {
    return longMap.get(longOpt);
  }

  /**
   * Adds an option.
   *
   * @param option the option
   * @return the options object, to facilitate chaining
   * @throws ParseException if duplicate option is passed
   */
  public Options addOption(final Option option) throws ParseException {
    final String shortOpt = option.getShortOpt();
    final String longOpt = option.getLongOpt();
    if (((shortOpt != null) && shortMap.containsKey(shortOpt)) || ((longOpt != null) && longMap.containsKey(longOpt))) {
      throw new ParseException("Duplicate option");
    }
    options.add(option);
    if (shortOpt != null) {
      shortMap.put(shortOpt, option);
    }
    if (longOpt != null) {
      longMap.put(longOpt, option);
    }
    return this;
  }

  /**
   * Gets the separator string. It is used to divide sub-parameter values.
   *
   * @return the separator
   */
  public char getSep() {
    return sep;
  }

  /**
   * Sets the separator string. It is used to divide sub-parameter values.
   *
   * @param sep the separator
   * @return the options object, to facilitate chaining
   */
  public Options setSep(final char sep) {
    this.sep = sep;
    return this;
  }

  /**
   * Creates the options object.
   */
  public Options() {
    // no action
  }
}
