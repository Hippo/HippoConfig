/*
 * MIT License
 *
 * Copyright (c) 2020 Hippo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rip.hippo.config.version;

/**
 * @author Hippo
 * @version 1.0.0, 9/8/20
 * @since 1.0.0
 */
public final class SemanticVersion {

  private final int major, minor, patch;

  public SemanticVersion(int major, int minor, int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  public static SemanticVersionBuilder builder() {
    return new SemanticVersionBuilder();
  }

  public int getMajor() {
    return major;
  }

  public int getMinor() {
    return minor;
  }

  public int getPatch() {
    return patch;
  }

  @Override
  public String toString() {
    return String.format("%d.%d.%d", major, minor, patch);
  }

  public static class SemanticVersionBuilder {
    private int major, minor, patch;

    private SemanticVersionBuilder() {
    }

    public SemanticVersionBuilder major(int major) {
      this.major = major;
      return this;
    }

    public SemanticVersionBuilder minor(int minor) {
      this.minor = minor;
      return this;
    }

    public SemanticVersionBuilder patch(int patch) {
      this.patch = patch;
      return this;
    }

    public SemanticVersion build() {
      return new SemanticVersion(major, minor, patch);
    }
  }
}
