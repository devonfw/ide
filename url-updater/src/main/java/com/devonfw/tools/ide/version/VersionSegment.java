package com.devonfw.tools.ide.version;

import java.util.Locale;

/**
 * Represents a single segment of a {@link VersionIdentifier}.
 */
public class VersionSegment implements VersionObject<VersionSegment> {

  private static final VersionSegment EMPTY = new VersionSegment("", "", "");

  private final String separator;

  private final String letters;

  private final String lettersLower;

  private final VersionPhase phase;

  private final String digits;

  private final int number;

  private VersionSegment next;

  /**
   * The constructor.
   *
   * @param separator the {@link #getSeparator() separator}.
   * @param letters the {@link #getLetters() letters}.
   * @param digits the {@link #getDigits() digits}.
   */
  VersionSegment(String separator, String letters, String digits) {

    super();
    this.separator = separator;
    this.letters = letters;
    this.lettersLower = this.letters.toLowerCase(Locale.ROOT);
    this.phase = VersionPhase.of(this.lettersLower.replace('_', '-'));
    this.digits = digits;
    if (this.digits.isEmpty()) {
      this.number = -1;
    } else {
      this.number = Integer.parseInt(this.digits);
    }
    if (EMPTY != null) {
      assert (!this.letters.isEmpty() || !this.digits.isEmpty() || !this.separator.isEmpty());
    }
  }

  /**
   * @return the separator {@link String} (e.g. "." or "-") or the empty {@link String} ("") for none.
   */
  public String getSeparator() {

    return this.separator;
  }

  /**
   * @return the letters or the empty {@link String} ("") for none. In canonical {@link VersionIdentifier}s letters
   *         indicate the development phase (e.g. "pre", "rc", "alpha", "beta", "milestone", "test", "dev", "SNAPSHOT",
   *         etc.). However, letters are technically any {@link Character#isLetter(char) letter characters} and may also
   *         be something like a code-name (e.g. "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice
   *         Cream Sandwich", "Jelly Bean" in case of Android internals). Please note that in such case it is impossible
   *         to properly decide which version is greater than another versions. To avoid mistakes, the comparison
   *         supports a strict mode that will let the comparison fail in such case. However, by default (e.g. for
   *         {@link Comparable#compareTo(Object)}) the default {@link String#compareTo(String) string comparison}
   *         (lexicographical) is used to ensure a natural order.
   * @see #getPhase()
   */
  public String getLetters() {

    return this.letters;
  }

  /**
   * @return the {@link VersionPhase} for the {@link #getLetters() letters}. Will be {@link VersionPhase#UNDEFINED} if
   *         unknown and hence never {@code null}.
   * @see #getLetters()
   */
  public VersionPhase getPhase() {

    return this.phase;
  }

  /**
   * @return the digits or the empty {@link String} ("") for none. This is the actual {@link #getNumber() number} part
   *         of this {@link VersionSegment}. So the {@link VersionIdentifier} "1.0.001" will have three segments: The
   *         first one with "1" as digits, the second with "0" as digits, and a third with "001" as digits. You can get
   *         the same value via {@link #getNumber()} but this {@link String} representation will preserve leading zeros.
   */
  public String getDigits() {

    return this.digits;
  }

  /**
   * @return the {@link #getDigits() digits} and integer number. Will be {@code -1} if no {@link #getDigits() digits}
   *         are present.
   */
  public int getNumber() {

    return this.number;
  }

  /**
   * @return the next {@link VersionSegment} or {@code null} if this is the tail of the {@link VersionIdentifier}.
   */
  public VersionSegment getNextOrNull() {

    return this.next;
  }

  /**
   * @return the next {@link VersionSegment} or the {@link #ofEmpty() empty segment} if this is the tail of the
   *         {@link VersionIdentifier}.
   */
  public VersionSegment getNextOrEmpty() {

    if (this.next == null) {
      return EMPTY;
    }
    return this.next;
  }

  /**
   * @return {@code true} if this is the empty {@link VersionSegment}, {@code false} otherwise.
   */
  public boolean isEmpty() {

    return (this == EMPTY);
  }

  /**
   * A valid {@link VersionSegment} has to meet the following requirements:
   * <ul>
   * <li>The {@link #getSeparator() separator} may not be {@link String#length() longer} than a single character (e.g.
   * ".-_1" or "--1" are not considered valid).</li>
   * <li>The {@link #getSeparator() separator} may only contain the characters '.', '-', or '_' (e.g. " 1" or "ö1" are
   * not considered valid).</li>
   * <li>The combination of {@link #getPhase() phase} and {@link #getNumber() number} has to be
   * {@link VersionPhase#isValid(int) valid} (e.g. "pineapple-pen1" or "donut" are not considered valid).</li>
   * </ul>
   *
   */
  @Override
  public boolean isValid() {

    int separatorLen = this.separator.length();
    if (separatorLen > 1) {
      return false;
    } else if (separatorLen == 1) {
      if (!CharCategory.isValidSeparator(this.separator.charAt(0))) {
        return false;
      }
    }
    return this.phase.isValid(this.number);
  }

  @Override
  public VersionComparisonResult compareVersion(VersionSegment other) {

    if (other == null) {
      return VersionComparisonResult.GREATER_UNSAFE;
    }
    if (!this.lettersLower.equals(other.lettersLower)) {
      if ((this.phase == VersionPhase.UNDEFINED) || (other.phase == VersionPhase.UNDEFINED)) {
        if (this.lettersLower.compareTo(other.lettersLower) < 0) {
          return VersionComparisonResult.LESS_UNSAFE;
        } else {
          return VersionComparisonResult.GREATER_UNSAFE;
        }
      }
      if (this.phase != other.phase) {
        if (this.phase.ordinal() < other.phase.ordinal()) {
          return VersionComparisonResult.LESS;
        } else {
          return VersionComparisonResult.GREATER;
        }
      }
    }
    if (this.number < other.number) {
      return VersionComparisonResult.LESS;
    } else if (this.number > other.number) {
      return VersionComparisonResult.GREATER;
    } else if (this.separator.equals(other.separator)) {
      return VersionComparisonResult.EQUAL;
    } else {
      return VersionComparisonResult.EQUAL_UNSAFE;
    }
  }

  @Override
  public String toString() {

    return this.separator + this.letters + this.digits;
  }

  /**
   * @return the {@link #isEmpty() empty} {@link VersionSegment} instance.
   */
  public static VersionSegment ofEmpty() {

    return EMPTY;
  }

  static VersionSegment of(String version) {

    CharReader reader = new CharReader(version);
    VersionSegment start = null;
    VersionSegment current = null;
    while (reader.hasNext()) {
      VersionSegment segment = parseSegment(reader);
      if (current == null) {
        start = segment;
      } else {
        current.next = segment;
      }
      current = segment;
    }
    return start;
  }

  private static VersionSegment parseSegment(CharReader reader) {

    String separator = reader.readSeparator();
    String letters = reader.readLetters();
    String digits = reader.readDigits();
    return new VersionSegment(separator, letters, digits);
  }

}
