package org.apache.tika.onenote;

import java.util.Objects;

/**
 * A file chunk reference specifies a reference to data in the file.
 *
 * Each file chunk reference contains an <pre>stp</pre> field and a <pre>cb</pre> field.
 *
 * The <pre>stp</pre> field is a stream pointer that specifies the offset, in bytes, from the beginning of the file where the referenced
 * data is located.
 *
 * The <pre>cb</pre> field specifies the size, in bytes, of the referenced data. The sizes, in bytes, of the
 * stp and cb fields are specified by the structures in this section.
 *
 * There are some Special values:
 *
 * fcrNil - Specifies a file chunk reference where all bits of the stp field are set to 1, and all bits of the cb field are set to zero.
 *
 * fcrZero - Specifies a file chunk reference where all bits of the stp and cb fields are set to zero.
 *
 */
public class FileChunkReference {

  long stp;
  long cb;

  public FileChunkReference() {

  }

  public FileChunkReference(long stp, long cb) {
    this.stp = stp;
    this.cb = cb;
  }

  public static FileChunkReference nil() {
    return new FileChunkReference(-1L, 0L);
  }

  @Override
  public String toString() {
    return "FileChunkReference{" +
      "stp=" + stp +
      ", cb=" + cb +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileChunkReference that = (FileChunkReference) o;
    return stp == that.stp &&
      cb == that.cb;
  }

  @Override
  public int hashCode() {
    return Objects.hash(stp, cb);
  }

  public long getStp() {
    return stp;
  }

  public FileChunkReference setStp(long stp) {
    this.stp = stp;
    return this;
  }

  public long getCb() {
    return cb;
  }

  public FileChunkReference setCb(long cb) {
    this.cb = cb;
    return this;
  }
}
