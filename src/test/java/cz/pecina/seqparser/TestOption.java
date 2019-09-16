/* TestOption.java
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

import junit.framework.TestCase;

public class TestOption extends TestCase {

  public void testToString() {
    try {
      assertEquals("Option \"a\"", new Option("a", null).toString());
    } catch (ParseException e) {
      fail();
    }
  }

  public void testOption1() {
    String[][] succ = {
        {"a", null}, {"A", null}, {"_", null}, {"aa", null}, {"a0", null}, {"a_", null}, {null, "a"}, {null, "A"},
        {null, "_"}, {null, "aa"}, {null, "a0"}, {null, "a_"}, {null, "a-b"}, {null, "a-b0-d"}, {null, "a-0-d"},
        {null, "a-bc-d0f"}, {null, "ab-ce-e0g"}};
    String[][] fail = {
        {null, null}, {"", null}, {"0abc", null}, {"-abc", null}, {"a-bc", null}, {"0abc", null}, {"-abc", null},
        {"á", null}, {null, ""}, {null, "0abc"}, {null, "-abc"}, {null, "0abc"}, {null, "-abc"}, {null, "abc-"},
        {null, "a--bc"}, {null, "ab--c"}, {null, "á"}};
    for (String[] t : succ) {
      try {
        new Option(t[0], t[1]);
      } catch (ParseException e) {
        fail(String.format("\"%s\"/\"%s\"", t[0], t[1]));
      }
    }
    for (String[] t : fail) {
      try {
        new Option(t[0], t[1]);
        fail(String.format("\"%s\"/\"%s\"", t[0], t[1]));
      } catch (ParseException expected) { }
    }
  }

  public void testOption2() {
    int[][] succ = {{0, 0}, {1, 1}, {0, 1}, {0, 10}, {3, 4}};
    int[][] fail = {{-1, 0}, {-20, -1}, {3, 2}};
    for (int[] t : succ) {
      try {
        new Option("a", "a", t[0], t[1]);
      } catch (ParseException e) {
        fail(String.format("\"%d\"/\"%d\"", t[0], t[1]));
      }
    }
    for (int[] t : fail) {
      try {
        new Option("a", "a", t[0], t[1]);
        fail(String.format("\"%d\"/\"%d\"", t[0], t[1]));
      } catch (ParseException expected) { }
    }
  }

  public void testOption3() {
    try {
      assertEquals(2, new Option("a", null, 2).getMinParameters());
      assertEquals(2, new Option("a", null, 2).getMaxParameters());
      assertEquals(0, new Option("a", null).getMinParameters());
      assertEquals(0, new Option("a", null).getMaxParameters());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetShortOpt() {
    try {
      assertEquals("a", new Option("a", null).getShortOpt());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetLongOpt() {
    try {
      assertEquals("a", new Option(null, "a").getLongOpt());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetName() {
    try {
      assertEquals("a", new Option("a", null).getName());
      assertEquals("a", new Option(null, "a").getName());
      assertEquals("b", new Option("a", "b").getName());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testSubOptions() {
    try {
      Option o = new Option("a", null);
      assertSame(o, o.addSubOption(ParameterType.String));
      assertSame(o, o.addSubOption(ParameterType.Integer));
      assertEquals(2, o.getSubOptions().size());
      assertSame(ParameterType.String, o.getSubOptions().get(0));
      assertSame(ParameterType.Integer, o.getSubOptions().get(1));
      assertSame(ParameterType.String, o.getSubOption(0));
      assertSame(ParameterType.Integer, o.getSubOption(1));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testKwSubOptions() {
    try {
      Option o = new Option("a", null);
      assertSame(o, o.addKwSubOption("b", ParameterType.String));
      assertSame(o, o.addKwSubOption("c", ParameterType.Integer));
      assertEquals(2, o.getKwSubOptions().size());
      assertSame(ParameterType.String, o.getKwSubOptions().get("b"));
      assertSame(ParameterType.Integer, o.getKwSubOptions().get("c"));
      assertNull(o.getKwSubOptions().get("d"));
      assertSame(ParameterType.String, o.getKwSubOption("b"));
      assertSame(ParameterType.Integer, o.getKwSubOption("c"));
      assertNull(o.getKwSubOption("d"));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testAddKwSubOption() {
    String[] succ = {"a", "A", "_", "aa", "a0", "a_", "a-b", "a-b0-d", "a-0-d", "a-bc-d0f", "ab-ce-e0g"};
    String[] fail = {null, "", "0abc", "-abc", "0abc", "-abc", "abc-", "a--bc", "ab--c", "á"};
    for (String t : succ) {
      try {
        new Option("a", null).addKwSubOption(t, ParameterType.String);
      } catch (ParseException e) {
        fail(t);
      }
    }
    for (String t : fail) {
      try {
        new Option("a", null).addKwSubOption(t, ParameterType.String);
        fail(t);
      } catch (ParseException expected) { }
    }
  }

  public void testGetMinParameters() {
    try {
      assertEquals(2, new Option("a", null, 2, 3).getMinParameters());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetMaxParameters() {
    try {
      assertEquals(3, new Option("a", null, 2, 3).getMaxParameters());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
