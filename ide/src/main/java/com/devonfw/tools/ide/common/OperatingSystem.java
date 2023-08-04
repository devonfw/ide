package com.devonfw.tools.ide.common;

/**
 * Enum with the supported operating systems.
 */
public enum OperatingSystem {
  /** Microsoft Windows. */
  WINDOWS("windows"),

  /** Apple MacOS (not iOS). */
  MAC("mac"),

  /** Linux (Ubunutu, Debian, SuSe, etc.) */
  LINUX("linux");

  private final String title;

  private OperatingSystem(String title) {

    this.title = title;
  }

  @Override
  public String toString() {

    return this.title;
  }

  /**
   * @param title the {@link #toString() string representation} of the requested {@link OperatingSystem}.
   * @return the according {@link OperatingSystem} or {@code null} if none matches.
   */
  public static OperatingSystem of(String title) {

    for (OperatingSystem os : values()) {
      if (os.toString().equals(title)) {
        return os;
      }
    }
    return null;
  }
}
