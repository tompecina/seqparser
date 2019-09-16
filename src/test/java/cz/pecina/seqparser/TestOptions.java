/* TestOptions.java
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
 * The source code is available from <https://github.com/tompecina/pdf>.
 */

package cz.pecina.seqparser;

import junit.framework.TestCase;

public class TestOptions extends TestCase {

  public void testToString() {
    assertEquals("Options", new Options().toString());
  }

  public void testSep() {
    try {
      assertEquals(';', new Options().setSep(';').getSep());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
    char[] succ = {',', ':', 'a', 'á', 'ř', '-', ';', '!', ',', 'Ω'};
    char[] fail = {' ', '\t', '\n', '\r', '\u000b', '\u000c'};
    for (char t : succ) {
      try {
        new Options().setSep(t);
      } catch (ParseException e) {
        fail(String.format("char: \"%c\"", t));
      }
    }
    for (char t : fail) {
      try {
        new Options().setSep(t);
        fail(String.format("char: \"%c\"", t));
      } catch (ParseException expected) { }
    }
  }

  public void testGetOption() {
    Options o = new Options();
    try {
      Option s = new Option("a", null);
      Option l = new Option(null, "b");
      o.addOption(s).addOption(l);
      assertSame(s, o.getOptionShort("a"));
      assertSame(l, o.getOptionLong("b"));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testAddOption() {
    Options o = new Options();
    try {
      Option s = new Option("a", null);
      Option l = new Option(null, "b");
      o.addOption(s).addOption(l);
      try {
        o.addOption(new Option("a", "c"));
        fail("short");
      } catch (ParseException expected) { }
      try {
        o.addOption(new Option("c", "b"));
        fail("long");
      } catch (ParseException expected) { }
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
