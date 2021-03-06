/* Options.java
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
public final class Options {

  // static logger
  private static final Logger log = Logger.getLogger(Options.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "Options";
  }

  /** List of options. */
  private final List<Option> options = new ArrayList<>();

  /** Map of options by short option string. */
  private final Map<String, Option> shortMap = new HashMap<>();

  /** Map of options by long option string. */
  private final Map<String, Option> longMap = new HashMap<>();

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
   * @throws ParseException if a duplicate option is passed
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
   * Adds an option.
   *
   * @param shortOpt the short option string
   * @param longOpt the long option string
   * @return the new option
   * @throws ParseException on invalid option string(s) or if a duplicate option is passed
   */
  public Option addOption(final String shortOpt, final String longOpt) throws ParseException {
    return addOption(shortOpt, longOpt, 0, 0);
  }

  /**
   * Adds an option.
   *
   * @param shortOpt the short option string
   * @param longOpt the long option string
   * @param numParameters the number of sub-parameters
   * @return the new option
   * @throws ParseException on invalid option string(s) or if a duplicate option is passed
   */
  public Option addOption(final String shortOpt, final String longOpt, final int numParameters) throws ParseException {
    return addOption(shortOpt, longOpt, numParameters, numParameters);
  }

  /**
   * Adds an option.
   *
   * @param shortOpt the short option string
   * @param longOpt the long option string
   * @param minParameters the minimum number of sub-parameters
   * @param maxParameters the maximum number of sub-parameters
   * @return the new option
   * @throws ParseException on invalid option string(s) or if a duplicate option is passed
   */
  public Option addOption(final String shortOpt, final String longOpt, final int minParameters, final int maxParameters)
      throws ParseException {
    final Option option = new Option(shortOpt, longOpt, minParameters, maxParameters);
    addOption(option);
    return option;
  }

  /**
   * An option builder.
   */
  public static class Builder {

    // the options being built
    private Options options = new Options();

    /**
     * Adds an option.
     *
     * @param option the option
     * @return the builder object, to facilitate chaining
     * @throws ParseException if a duplicate option is passed
     */
    public Builder addOption(final Option option) throws ParseException {
      options.addOption(option);
      return this;
    }

    /**
     * Adds an option.
     *
     * @param shortOpt the short option string
     * @param longOpt the long option string
     * @return the builder object, to facilitate chaining
     * @throws ParseException on invalid option string(s) or if a duplicate option is passed
     */
    public Builder addOption(final String shortOpt, final String longOpt) throws ParseException {
      options.addOption(shortOpt, longOpt);
      return this;
    }

    /**
     * Adds an option.
     *
     * @param shortOpt the short option string
     * @param longOpt the long option string
     * @param numParameters the number of sub-parameters
     * @return the builder object, to facilitate chaining
     * @throws ParseException on invalid option string(s) or if a duplicate option is passed
     */
    public Builder addOption(final String shortOpt, final String longOpt, final int numParameters) throws ParseException {
      options.addOption(shortOpt, longOpt, numParameters);
      return this;
    }

    /**
     * Adds an option.
     *
     * @param shortOpt the short option string
     * @param longOpt the long option string
     * @param minParameters the minimum number of sub-parameters
     * @param maxParameters the maximum number of sub-parameters
     * @return the builder object, to facilitate chaining
     * @throws ParseException on invalid option string(s) or if a duplicate option is passed
     */
    public Builder addOption(final String shortOpt, final String longOpt, final int minParameters, final int maxParameters)
        throws ParseException {
      options.addOption(shortOpt, longOpt, minParameters, maxParameters);
      return this;
    }

    // gets the last option
    private Option getLast() throws ParseException {
      final List<Option> opts = options.options;
      if (opts.isEmpty()) {
        throw new ParseException("Adding a sub-option to empty options");
      }
      return opts.get(opts.size() - 1);
    }

    /**
     * Adds a sub-option.
     *
     * @param subOption the sub-option
     * @return the builder object, to facilitate chaining
     * @throws ParseException if options empty
     */
    public Builder addSubOption(final SubOption subOption) throws ParseException {
      getLast().addSubOption(subOption);
      return this;
    }

    /**
     * Adds a keyword sub-option.
     *
     * @param key the keyword
     * @param subOption the keyword sub-option
     * @return the builder object, to facilitate chaining
     * @throws ParseException on invalid sub-option string(s) or if options empty
     */
    public Builder addKwSubOption(final String key, final SubOption subOption) throws ParseException {
      getLast().addKwSubOption(key, subOption);
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the options object
     */
    public Options build() {
      return options;
    }
  }

  /**
   * Gets a new option builder.
   *
   * @return a new option builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Creates the options object.
   */
  public Options() {
    // no action
  }
}
