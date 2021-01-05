/*
 * RandomAlphabet is a Java GUI tool to encode and decode text strings using pseudorandomly generated ciphers from string keys.
 *     Copyright (C) 2019  Noah Stanford <noahstandingford@gmail.com>
 *
 *     RandomAlphabet is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     RandomAlphabet is distributed in the hope that it will be fun, interesting, and useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.corruptedark.randomalphabet;

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

public class LetterBucket {
    private final char letter;
    private final List<Integer> values;
    private final SecureRandom rand;

    public LetterBucket(char letter) {
        this.letter = letter;
        this.values = new ArrayList<>();
        rand = new SecureRandom();
    }

    public void addValue(int value) {
        values.add(value);
    }

    public boolean containsValue(int value) {
        boolean contains = false;

        for (int i = 0; !contains && i < values.size(); i++) {
            contains = value == values.get(i);
        }

        return contains;
    }

    public char getLetter() {
        return letter;
    }

    public int randomValue() {
        return values.get(rand.nextInt(values.size()));
    }

}
