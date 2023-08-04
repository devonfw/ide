package com.devonfw.tools.ide.version;

import java.util.Objects;

/**
 * Data-type to represent a {@link VersionIdentifier} in a structured way and allowing
 * {@link #compareVersion(VersionIdentifier) comparison} of {@link VersionIdentifier}s.
 */
public final class VersionIdentifier implements VersionObject<VersionIdentifier> {

  private final VersionSegment start;

  private final VersionPhase developmentPhase;

  private final boolean valid;

  private VersionIdentifier(VersionSegment start) {

    super();
    Objects.requireNonNull(start);
    this.start = start;
    boolean isValid = this.start.getSeparator().isEmpty() && this.start.getLetters().isEmpty();
    boolean hasPositiveNumber = false;
    VersionPhase phase = VersionPhase.NONE;
    VersionSegment segment = this.start;
    while (segment != null) {
      if (!segment.isValid()) {
        isValid = false;
      } else if (segment.getNumber() > 0) {
        hasPositiveNumber = true;
      }
      VersionPhase segmentPhase = segment.getPhase();
      if (segmentPhase != VersionPhase.NONE) {
        if (segmentPhase.isDevelopmentPhase()) {
          if (phase == VersionPhase.NONE) {
            phase = segmentPhase;
          } else {
            phase = VersionPhase.UNDEFINED;
            isValid = false;
          }
        }
      }
      segment = segment.getNextOrNull();
    }
    this.developmentPhase = phase;
    this.valid = isValid && hasPositiveNumber;
  }

  /**
   * @return the first {@link VersionSegment} of this {@link VersionIdentifier}. To get other segments use
   *         {@link VersionSegment#getNextOrEmpty()} or {@link VersionSegment#getNextOrNull()}.
   */
  public VersionSegment getStart() {

    return this.start;
  }

  /**
   * A valid {@link VersionIdentifier} has to meet the following requirements:
   * <ul>
   * <li>All {@link VersionSegment segments} themselves are {@link VersionSegment#isValid() valid}.</li>
   * <li>The {@link #getStart() start} {@link VersionSegment segment} shall have an {@link String#isEmpty() empty}
   * {@link VersionSegment#getSeparator() separator} (e.g. ".1.0" or "-1-2" are not considered valid).</li>
   * <li>The {@link #getStart() start} {@link VersionSegment segment} shall have an {@link String#isEmpty() empty}
   * {@link VersionSegment#getLetters() letter-sequence} (e.g. "RC1" or "beta" are not considered valid).</li>
   * <li>Have at least one {@link VersionSegment segment} with a positive {@link VersionSegment#getNumber() number}
   * (e.g. "0.0.0" or "0.alpha" are not considered valid).</li>
   * <li>Have at max one {@link VersionSegment segment} with a {@link VersionSegment#getPhase() phase} that is a real
   * {@link VersionPhase#isDevelopmentPhase() development phase} (e.g. "1.alpha1.beta2" or "1.0.rc1-milestone2" are not
   * considered valid).</li>
   * </ul>
   */
  @Override
  public boolean isValid() {

    return this.valid;
  }

  /**
   * @return the {@link VersionPhase#isDevelopmentPhase() development phase} of this {@link VersionIdentifier}. Will be
   *         {@link VersionPhase#NONE} if no development phase is specified in any {@link VersionSegment} and will be
   *         {@link VersionPhase#UNDEFINED} if more than one {@link VersionPhase#isDevelopmentPhase() development phase}
   *         is specified (e.g. "1.0-alpha1.rc2").
   */
  public VersionPhase getDevelopmentPhase() {

    return this.developmentPhase;
  }

  @Override
  public VersionComparisonResult compareVersion(VersionIdentifier other) {

    if (other == null) {
      return VersionComparisonResult.GREATER_UNSAFE;
    }
    VersionSegment thisSegment = this.start;
    VersionSegment otherSegment = other.start;
    VersionComparisonResult result = null;
    boolean unsafe = false;
    boolean todo = true;
    do {
      result = thisSegment.compareVersion(otherSegment);
      if (result.isEqual()) {
        if (thisSegment.isEmpty() && otherSegment.isEmpty()) {
          todo = false;
        } else if (result.isUnsafe()) {
          unsafe = true;
        }
      } else {
        todo = false;
      }
      thisSegment = thisSegment.getNextOrEmpty();
      otherSegment = otherSegment.getNextOrEmpty();
    } while (todo);
    if (unsafe) {
      return result.withUnsafe();
    }
    return result;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    VersionSegment segment = this.start;
    while (segment != null) {
      sb.append(segment.toString());
      segment = segment.getNextOrNull();
    }
    return sb.toString();
  }

  /**
   * @param version the {@link #toString() string representation} of the {@link VersionIdentifier} to parse.
   * @return the parsed {@link VersionIdentifier}.
   */
  public static VersionIdentifier of(String version) {

    return new VersionIdentifier(VersionSegment.of(version));
  }

}
