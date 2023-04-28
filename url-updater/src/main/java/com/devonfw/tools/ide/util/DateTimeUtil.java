package com.devonfw.tools.ide.util;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for operations on data and time (java.time).
 */
public final class DateTimeUtil {

  // construction forbidden
  private DateTimeUtil() {

  }

  /**
   * @param start the first {@link Instant}.
   * @param end the second {@link Instant}.
   * @return {@code true} if the first {@link Instant} is after the second.
   */
  public static boolean isAfter(Instant start, Instant end) {

    Integer delta = compareDuration(start, end, Duration.ZERO);
    if (delta == null) {
      return false;
    }
    return delta.intValue() < 0;
  }

  /**
   * @param start the first {@link Instant}.
   * @param end the second {@link Instant}.
   * @return {@code true} if the first {@link Instant} is before the second.
   */
  public static boolean isBefore(Instant start, Instant end) {

    Integer delta = compareDuration(start, end, Duration.ZERO);
    if (delta == null) {
      return false;
    }
    return delta.intValue() > 0;
  }

  /**
   * @param start the start {@link Instant}.
   * @param end the end {@link Instant}.
   * @param duration the {@link Duration} to compare to.
   * @return {@code 0} if the {@link Duration} from {@code start} to {@code end} is equal to the given {@link Duration},
   *         negative value if less, positive value is greater and {@code null} if one of the given values was
   *         {@code null}.
   */
  public static Integer compareDuration(Instant start, Instant end, Duration duration) {

    if ((start == null) || (end == null) || (duration == null)) {
      return null;
    }
    Duration delta = Duration.between(start, end);
    return Integer.valueOf(delta.compareTo(duration));
  }

}
