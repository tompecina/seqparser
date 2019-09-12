/* Option.java
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
import java.util.regex.Pattern;

/**
 * Option object.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public class Option {

  // static logger
  private static final Logger log = Logger.getLogger(Option.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "Option";
  }

  // constants
  private static final Pattern RE_SHORT = Pattern.compile("^[\\p{Alpha}_][\\p{Alnum}_]*$");
  private static final Pattern RE_LONG = Pattern.compile("^[\\p{Alpha}_][-\\p{Alnum}_]*$");

  // fields
  private String shortOpt;
  private String longOpt;
  private int minParameters;
  private int maxParameters;
  private List<SubOption> subOptions = new ArrayList<>();
  private Map<String, SubOption> kwSubOptions = new HashMap<>();

  /**
   * Gets the short option string.
   *
   * @return the short option string
   */
  public String getShortOpt() {
    return shortOpt;
  }

  /**
   * Gets the long option string.
   *
   * @return the long option string
   */
  public String getLongOpt() {
    return longOpt;
  }

  /**
   * Gets the name of the option, i.e. the long option string, or the short one if empty.
   *
   * @return the name of the option
   */
  public String getName() {
    return (longOpt != null) ? longOpt : shortOpt;
  }

  /**
   * Gets the minimum number of sub-parameters.
   *
   * @return the minimum number of sub-parameters
   */
  public int getMinParameters() {
    return minParameters;
  }

  /**
   * Gets the maximum number of sub-parameters.
   *
   * @return the maximum number of sub-parameters
   */
  public int getMaxParameters() {
    return maxParameters;
  }

  /**
   * Gets the list of sub-options.
   *
   * @return the list of sub-options
   */
  public List<SubOption> getSubOptions() {
    return subOptions;
  }

  /**
   * Gets the map of keyword sub-options.
   *
   * @return the map of keyword sub-options
   */
  public Map<String, SubOption> getKwSubOptions() {
    return kwSubOptions;
  }

  /**
   * Adds a sub-option.
   *
   * @param subOption the sub-option
   * @return the option object, to facilitate chaining
   */
  public Option addSubOption(final SubOption subOption) {
    subOptions.add(subOption);
    return this;
  }

  /**
   * Adds a keyword sub-option.
   *
   * @param key the keyword
   * @param subOption the keyword sub-option
   * @return the option object, to facilitate chaining
   */
  public Option addKwSubOption(final String key, final SubOption subOption) {
    kwSubOptions.put(key, subOption);
    return this;
  }

  /**
   * Creates the option object.
   *
   * @param shortOpt the short option string
   * @param longOpt the long option string
   * @param minParameters the minimum number of sub-parameters
   * @param maxParameters the maximum number of sub-parameters
   * @throws ParseException on invalid option string(s)
   */
  public Option(final String shortOpt, final String longOpt, final int minParameters, final int maxParameters)
      throws ParseException {
    if ((shortOpt == null) && (longOpt == null)) {
      throw new ParseException("Unrecognizable option, either short or long string must be supplied");
    }
    if ((shortOpt != null) && !RE_SHORT.matcher(shortOpt).matches()) {
      throw new ParseException("Invalid short option string");
    }
    if ((longOpt != null) && !RE_LONG.matcher(longOpt).matches()) {
      throw new ParseException("Invalid long option string");
    }
    if ((minParameters < 0) || (maxParameters < 0) || (minParameters > maxParameters)) {
      throw new ParseException("Invalid number of positional parameters");
    }
    this.shortOpt = shortOpt;
    this.longOpt = longOpt;
    this.minParameters = minParameters;
    this.maxParameters = maxParameters;
  }
}
