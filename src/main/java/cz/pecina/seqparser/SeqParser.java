/* SeqParser.java
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SeqParser object.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public class SeqParser {

  // static logger
  private static final Logger log = Logger.getLogger(SeqParser.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "SeqParser";
  }

  // constants
  private static final String SPEC_STR = "\u007f";

  /** Regex for checking option. */
  protected static final Pattern RE_OPT = Pattern.compile("^-[-]?[\\p{Alpha}_].*$");

  /** Regex for parsing sub-parameters. */
  protected static final Pattern RE_KW =
      Pattern.compile("^(?:([\\p{Alpha}_][\\p{Alnum}_]*(?:-[\\p{Alnum}_]+)*)=)?(['\"]?)(.*)\\2$");

  /**
   * Parser for the string of sub-parameters.
   */
  protected static class Splitter implements Iterator<String>, Iterable<String> {

    // constants
    private static final char ESCAPE = '\\';
    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';

    // fields
    private String inp;
    private final StringBuilder out = new StringBuilder();
    private char sep;
    private int idx = 0;
    private boolean singleQuote = false;
    private boolean doubleQuote = false;
    private boolean escape = false;
    private boolean empty = false;

    // for description see Iterable
    @Override
    public Iterator<String> iterator() {
      return this;
    }

    // for description see Iterator
    @Override
    public boolean hasNext() {
      return (inp != null) && ((idx < inp.length()) || !empty);
    }

    // for description see Iterator
    @Override
    public String next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      if (idx == inp.length()) {
        empty = true;
        return "";
      }
      while (idx < inp.length()) {
        empty = true;
        final char ch = inp.charAt(idx++);
        if (ch == ESCAPE) {
          escape = true;
        } else {
          if (escape) {
            escape = false;
          } else if ((ch == sep) && !singleQuote && !doubleQuote) {
            empty = false;
            break;
          } else if (ch == SINGLE_QUOTE) {
            if (singleQuote) {
              singleQuote = false;
            } else if (!doubleQuote) {
              singleQuote = true;
            }
          } else if (ch == DOUBLE_QUOTE) {
            if (doubleQuote) {
              doubleQuote = false;
            } else if (!singleQuote) {
              doubleQuote = true;
            }
          }
        }
        out.append(ch);
      }
      final String res = out.toString();
      out.setLength(0);
      return res;
    }

    /**
     * Creates a new splitter object.
     *
     * @param inp the input string
     * @param sep the separator character
     */
    protected Splitter(final String inp, final char sep) {
      this.inp = inp;
      this.sep = sep;
    }
  }

  /**
   * Parses a string of arguments.
   *
   * @param options the options
   * @param args the string of arguments
   * @param stopOnNonOption if <code>true</code>, stop on the first non-option
   * @return the command line object
   * @throws ParseException on parsing error
   */
  public CommandLine parse(final Options options, final String[] args, final boolean stopOnNonOption)
      throws ParseException {
    final CommandLine cmd = new CommandLine();
    boolean stopParsing = false;
    Option option = null;
    Parameter parameter = null;
    List<SubOption> subOptions = null;
    int subSize = 0;
    int subIdx = 0;
    Map<String, SubOption> kwSubOptions = null;
    for (String arg : args) {
      if (stopParsing) {
        cmd.addRemArg(arg);
      } else if (arg.equals("--")) {
        stopParsing = true;
      } else if (RE_OPT.matcher(arg).matches()) {  // option
        option = arg.startsWith("--") ? options.getOptionLong(arg.substring(2)) : options.getOptionShort(arg.substring(1));
        if (option == null) {
          if (stopOnNonOption) {
            cmd.addRemArg(arg);
            stopParsing = true;
          } else {
            throw new ParseException("Invalid option: " + arg);
          }
        } else {
          parameter = new Parameter(option);
          cmd.addParameter(parameter);
          subOptions = option.getSubOptions();
          subSize = subOptions.size();
          subIdx = 0;
          kwSubOptions = option.getKwSubOptions();
        }
      } else if (parameter == null) {  // misplaced value
        if (stopOnNonOption) {
          cmd.addRemArg(arg);
          stopParsing = true;
        } else {
          throw new ParseException("Invalid option: " + arg);
        }
      } else {  // value
        for (String res : new Splitter(arg, options.getSep())) {
          final Matcher kwMatcher = RE_KW.matcher(res);
          while (kwMatcher.find()) {
            final MatchResult kwRes = kwMatcher.toMatchResult();
            final String key = kwRes.group(1);
            final String val = kwRes.group(3).replace("\\\\", SPEC_STR).replace("\\", "").replace(SPEC_STR, "\\");
            if (key == null) {
              if (subSize == 0) {
                throw new ParseException("No positional parameters allowed for this option");
              } else {
                final SubOption subOption = subOptions.get(subIdx);
                if (!subOption.getType().check(val)) {
                  throw new ParseException(String.format("Invalid positional parameter value: \"%s\"", val));
                }
                parameter.addSubParameter(new SubParameter(val, subOption));
                if (++subIdx == subSize) {  // use the last sub-option for all the rest
                  subIdx--;
                }
              }
            } else {
              final SubOption kwSubOption = kwSubOptions.get(key);
              if (kwSubOption == null) {
                throw new ParseException("Keyword parameter \"" + key + "\" not allowed for this option");
              } else {
                if (!kwSubOption.getType().check(val)) {
                  throw new ParseException(String.format("Invalid keyword parameter value: \"%s\"", val));
                }
                parameter.addKwSubParameter(key, new SubParameter(val, kwSubOption));
              }
            }
          }
        }
        parameter = null;
      }
    }
    for (Parameter par : cmd.getParameters()) {
      final int subPars = par.getSubParameters().size();
      final Option opt = par.getOption();
      if ((subPars < opt.getMinParameters()) || (subPars > opt.getMaxParameters())) {
        throw new ParseException("Invalid number of positional parameters supplied");
      }
    }
    return cmd;
  }

  /**
   * Create a new parser object.
   */
  public SeqParser() {
    // no action
  }
}
