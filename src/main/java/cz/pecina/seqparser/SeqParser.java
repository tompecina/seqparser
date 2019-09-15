/* SeqParser.java
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

import java.util.List;
import java.util.Map;
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

  /** Regex for checking option. */
  public static final Pattern RE_OPT = Pattern.compile("^-[-]?[\\p{Alpha}_].*$");

  /** Regex string for splitting sub-parameters. */
  public static final String RES_SPLIT = "(?:^|%s)((['\"]).*?\\2|[^%1$s]*)";

  /** Regex for parsing sub-parameters. */
  public static final Pattern RE_KW =
      Pattern.compile("^(?:([\\p{Alpha}_][\\p{Alnum}_]*(?:-[\\p{Alnum}_]+)*)=)?(['\"]?)(.*)\\2$");

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
    final Pattern reSplit = Pattern.compile(String.format(RES_SPLIT, Pattern.quote(String.valueOf(options.getSep()))));
    final CommandLine cmd = new CommandLine();
    boolean stopParsing = false;
    Option option = null;
    Parameter parameter = null;
    List<SubOption> subOptions = null;
    int subSize = 0;
    int subIndex = 0;
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
          subIndex = 0;
          kwSubOptions = option.getKwSubOptions();
        }
      } else if (parameter == null) {  // value
        if (stopOnNonOption) {
          cmd.addRemArg(arg);
          stopParsing = true;
        } else {
          throw new ParseException("Invalid option: " + arg);
        }
      } else {
        final Matcher splitMatcher = reSplit.matcher(arg);
        while (splitMatcher.find()) {
          final String res = splitMatcher.toMatchResult().group(1);
          final Matcher kwMatcher = RE_KW.matcher(res);
          while (kwMatcher.find()) {
            final MatchResult kwRes = kwMatcher.toMatchResult();
            final String key = kwRes.group(1);
            final String val = kwRes.group(3);
            if (key == null) {
              if (subSize == 0) {
                throw new ParseException("No positional parameters allowed for this option");
              } else {
                parameter.addSubParameter(new SubParameter(val, subOptions.get(subIndex)));
                if (++subIndex == subSize) {  // use the last sub-option for all the rest
                  subIndex--;
                }
              }
            } else {
              final SubOption kwSubOption = kwSubOptions.get(key);
              if (kwSubOption == null) {
                throw new ParseException("Keyword parameter \"" + key + "\" not allowed for this option");
              } else {
                parameter.addKwSubParameter(key, new SubParameter(val, kwSubOption));
              }
            }
          }
        }
        final int subPars = parameter.getSubParameters().size();
        if ((subPars < option.getMinParameters()) || (subPars > option.getMaxParameters())) {
          throw new ParseException("Invalid number of positional parameters supplied");
        }
        parameter = null;
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
