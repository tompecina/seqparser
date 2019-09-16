/* TestSeqParser.java
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestSeqParser extends TestCase {

  public void testToString() {
    assertEquals("SeqParser", new SeqParser().toString());
  }

  public void testSplitter() {

    SeqParser.Splitter p = new SeqParser.Splitter(null, ',');
    assertFalse(p.hasNext());
    try {
      p.next();
      fail();
    } catch (NoSuchElementException expected) { }

    p = new SeqParser.Splitter("a", ',');
    assertTrue(p.hasNext());
    try {
      assertEquals("a", p.next());
    } catch (NoSuchElementException e) {
      fail();
    }
    assertFalse(p.hasNext());
    try {
      p.next();
      fail();
    } catch (NoSuchElementException expected) { }

    p = new SeqParser.Splitter("", ',');
    assertTrue(p.hasNext());
    try {
      assertEquals("", p.next());
    } catch (NoSuchElementException e) {
      fail();
    }
    assertFalse(p.hasNext());
    try {
      p.next();
      fail();
    } catch (NoSuchElementException expected) { }

    p = new SeqParser.Splitter(",", ',');
    assertTrue(p.hasNext());
    try {
      assertEquals("", p.next());
    } catch (NoSuchElementException e) {
      fail();
    }
    try {
      assertEquals("", p.next());
    } catch (NoSuchElementException e) {
      fail();
    }
    assertFalse(p.hasNext());
    try {
      p.next();
      fail();
    } catch (NoSuchElementException expected) { }

    String[][] pat = {
      {"a", "a"},
      {"\"a\"", "\"a\""},
      {"a,b", "a", "b"},
      {"\"a\",b", "\"a\"", "b"},
      {"\"a,b\"", "\"a,b\""},
      {"\"a,b\",c", "\"a,b\"", "c"},
      {"a,\"b, c\"", "a", "\"b, c\""},
      {"\"a, b\",\"c, d\"", "\"a, b\"", "\"c, d\""},
      {"\"a", "\"a"},
      {"a\"", "a\""},
      {"'a'", "'a'"},
      {"'a',b", "'a'", "b"},
      {"'a,b'", "'a,b'"},
      {"'a,b',c", "'a,b'", "c"},
      {"a,'b, c'", "a", "'b, c'"},
      {"'a, b','c, d'", "'a, b'", "'c, d'"},
      {"'a", "'a"},
      {"a'", "a'"},
      {"\"a, b\",'c, d'", "\"a, b\"", "'c, d'"},
      {"\"a, b','c, d\"", "\"a, b','c, d\""},
      {"'a, b\",\"c, d'", "'a, b\",\"c, d'"},
      {"\"a, b\",'c, d'", "\"a, b\"", "'c, d'"},
      {"'a, b',\"c, d\"", "'a, b'", "\"c, d\""},
      {"\"a'", "\"a'"},
      {"'a\"", "'a\""},
      {"", ""},
      {",", "", ""},
      {",a", "", "a"},
      {"a,", "a", ""},
      {"\"a\"\"b\"", "\"a\"\"b\""},
      {"'a''b'", "'a''b'"},
      {"a\\\"b", "a\\\"b"},
      {"\"a\\\"b\"", "\"a\\\"b\""},
      {"a\\'b", "a\\'b"},
      {"'a\\'b'", "'a\\'b'"},
      {"'a\\\"b'", "'a\\\"b'"},
      {"\"a\\\'b\"", "\"a\\'b\""},
      {"\\\\a", "\\\\a"},
      {"a\\,b", "a\\,b"},
      {"\"a\\,b\"", "\"a\\,b\""},
      {"a,,b", "a", "", "b"},
      {"a\"b\"", "a\"b\""},
      {"k=\"v\"", "k=\"v\""}
    };
    for (String[] s : pat) {
      int i = 0;
      for (String t : new SeqParser.Splitter(s[0], ',')) {
        assertEquals(s[0], s[++i], t);
      }
    }
  }

  public void testReKw() {
    String[][] pat = {
      {"", null, "", ""},
      {"a=", "a", "", ""},
      {"a-b=", "a-b", "", ""},
      {"a=b", "a", "", "b"},
      {"a=ř", "a", "", "ř"},
      {"a=b=c", "a", "", "b=c"},
      {"a=b=c=d", "a", "", "b=c=d"},
      {"_abc=def", "_abc", "", "def"},
      {"\"\"", null, "\"", ""},
      {"a=\"b\"", "a", "\"", "b"},
      {"a=\"b", "a", "", "\"b"},
      {"a=b\"", "a", "", "b\""},
      {"a=b\"c\"", "a", "", "b\"c\""},
      {"a= \"b\"", "a", "", " \"b\""},
      {"''", null, "'", ""},
      {"a='b'", "a", "'", "b"},
      {"a='b", "a", "", "'b"},
      {"a=b'", "a", "", "b'"},
      {"a=b'c'", "a", "", "b'c'"},
      {"a= 'b'", "a", "", " 'b'"},
      {" =a", null, "", " =a"},
      {"a=\"b'", "a", "", "\"b'"}
    };
    for (String[] s : pat) {
      Matcher m = SeqParser.RE_KW.matcher(s[0]);
      assertTrue(s[0], m.find());
      assertEquals(s[0], 3, m.toMatchResult().groupCount());
      for (int i = 1; i <= 3; i++) {
        assertEquals(String.format("%s, #%d", s[0], i), s[i], m.toMatchResult().group(i));
      }
    }
  }

  private static SubOption getSubOption(final String str) {
    switch (str) {
      case "String": {
        return ParameterType.String;
      }
      case "Integer": {
        return ParameterType.Integer;
      }
    }
    return null;
  }

  public void testParse() {
    try {
      for (Object oTc : new JSONObject(new String(Files.readAllBytes(Paths.get(getClass().getResource("TestParse1.json")
          .toURI())))).getJSONArray("testCases")) {
        JSONObject tc = (JSONObject) oTc;
        String id = tc.getString("id");
        String m = "Case \"" + id + "\"";
        JSONObject request = tc.getJSONObject("request");
        JSONObject jOptions = request.getJSONObject("options");
        Options options = new Options();
        if (!jOptions.isNull("sep")) {
          options.setSep(jOptions.getString("sep").charAt(0));
        }
        for (Object oOption : jOptions.getJSONArray("options")) {
          JSONObject jOption = (JSONObject) oOption;
          Option option = new Option(jOption.optString("shortOpt", null), jOption.optString("longOpt", null),
              jOption.getInt("minParameters"), jOption.getInt("maxParameters"));
          options.addOption(option);
          for (Object oSubOption : jOption.getJSONArray("subOptions")) {
            String jSubOption = (String) oSubOption;
            SubOption subOption = getSubOption(jSubOption);
            option.addSubOption(subOption);
          }
          JSONObject jKwSubOptions = jOption.getJSONObject("kwSubOptions");
          for (String key : jKwSubOptions.keySet()) {
            String jSubOption = jKwSubOptions.getString(key);
            SubOption subOption = getSubOption(jSubOption);
            option.addKwSubOption(key, subOption);
          }
        }
        JSONArray jArgs = request.getJSONArray("args");
        List<String> lArgs = new ArrayList<>();
        for (Object oArg : jArgs) {
          lArgs.add((String) oArg);
        }
        String[] args = lArgs.stream().toArray(String[]::new);
        boolean stopOnNonOption = request.getBoolean("stopOnNonOption");

        JSONObject result = tc.getJSONObject("result");
        boolean exception = result.getBoolean("exception");
        CommandLine line = null;
        SeqParser parser = new SeqParser();
        try {
          line = parser.parse(options, args, stopOnNonOption);
          if (exception) {
            fail(m + ": exception expected, but not thrown");
          }
        } catch (ParseException e) {
          if (!exception) {
            fail(m + ": exception: " + e.getMessage());
          }
          continue;
        }
        List<Parameter> parameters = line.getParameters();
        JSONArray jParameters = result.getJSONArray("parameters");
        assertEquals(m, jParameters.length(), parameters.size());
        for (int numPar = 0; numPar < parameters.size(); numPar++) {
          Parameter parameter = parameters.get(numPar);
          JSONObject jParameter = jParameters.getJSONObject(numPar);
          assertEquals(m, jParameter.getString("optionName"), parameter.getOption().getName());
          List<SubParameter> subParameters = parameter.getSubParameters();
          JSONArray jSubParameters = jParameter.getJSONArray("subParameters");
          assertEquals(m, jSubParameters.length(), subParameters.size());
          for (int numSubPar = 0; numSubPar < subParameters.size(); numSubPar++) {
            SubParameter subParameter = subParameters.get(numSubPar);
            JSONObject jSubParameter = jSubParameters.getJSONObject(numSubPar);
            assertEquals(m, jSubParameter.getString("value"), subParameter.getAsString());
            assertSame(m, getSubOption(jSubParameter.getString("subOption")), subParameter.getSubOption());
          }
          Map<String, SubParameter> kwSubParameters = parameter.getKwSubParameters();
          JSONObject jKwSubParameters = jParameter.getJSONObject("kwSubParameters");
          assertEquals(m, jKwSubParameters.length(), kwSubParameters.size());
          for (String key : jKwSubParameters.keySet()) {
            assertEquals(m, jKwSubParameters.getJSONObject(key).getString("value"), kwSubParameters.get(key).getAsString());
            assertSame(m, getSubOption(jKwSubParameters.getJSONObject(key).getString("subOption")),
                kwSubParameters.get(key).getSubOption());
          }
        }
        JSONArray jRemArgs = result.getJSONArray("remArgs");
        List<String> remArgs = line.getRemArgs();
        assertEquals(m, jRemArgs.length(), remArgs.size());
        for (int i = 0; i < remArgs.size(); i++) {
          assertEquals(m, jRemArgs.getString(i), remArgs.get(i));
        }
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
