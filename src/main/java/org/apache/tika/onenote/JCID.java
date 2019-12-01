package org.apache.tika.onenote;

/**
 * The JCID structure specifies the type of object and the type of data the object contains. A JCID structure can be
 * considered to be an unsigned integer of size four bytes as specified by property set and
 * file data object.
 *
 * <pre>[0,15] - the index</pre>
 * <pre>16 - A</pre>
 * <pre>17 - B</pre>
 * <pre>18 - C</pre>
 * <pre>19 - D</pre>
 * <pre>20 - E</pre>
 * <pre>21 - 31 = reserved</pre>
 *
 * index (2 bytes): An unsigned integer that specifies the type of object.
 *
 * A - IsBinary (1 bit): Specifies whether the object contains encryption data transmitted over the File Synchronization via SOAP over
 * HTTP Protocol, as specified in [MS-FSSHTTP].
 *
 * B - IsPropertySet (1 bit): Specifies whether the object contains a property set.
 *
 * C - IsGraphNode (1 bit): Undefined and MUST be ignored.
 *
 * D - IsFileData (1 bit): Specifies whether the object is a file data object. If the value of IsFileData is "true", then the values of
 * the IsBinary, IsPropertySet, IsGraphNode, and IsReadOnly fields MUST all be false.
 *
 * E - IsReadOnly (1 bit): Specifies whether the object's data MUST NOT be changed when the object is revised.
 *
 * reserved (11 bits): MUST be zero, and MUST be ignored.
 */
public class JCID {
  long index;
  boolean isBinary;
  boolean isPropertySet;
  boolean isGraphNode;
  boolean isFileData;
  boolean isReadOnly;

  public void loadFrom32BitIndex(long fullIndex) {
    index = fullIndex & 0xffff;
    isBinary = ((fullIndex >> 16) & 1) == 1;
    isPropertySet = ((fullIndex >> 17) & 1) == 1;
    isGraphNode = ((fullIndex >> 18) & 1) == 1;
    isFileData = ((fullIndex >> 19) & 1) == 1;
    isReadOnly = ((fullIndex >> 20) & 1) == 1;
    if ((fullIndex >> 21) != 0) {
      throw new RuntimeException("RESERVED_NONZERO");
    }
  }

  @Override
  public String toString() {
    return "JCID{" +
        "index=" + index +
        ", isBinary=" + isBinary +
        ", isPropertySet=" + isPropertySet +
        ", isGraphNode=" + isGraphNode +
        ", isFileData=" + isFileData +
        ", isReadOnly=" + isReadOnly +
        '}';
  }

  public long getIndex() {
    return index;
  }

  public JCID setIndex(long index) {
    this.index = index;
    return this;
  }

  public boolean isBinary() {
    return isBinary;
  }

  public JCID setBinary(boolean binary) {
    isBinary = binary;
    return this;
  }

  public boolean isPropertySet() {
    return isPropertySet;
  }

  public JCID setPropertySet(boolean propertySet) {
    isPropertySet = propertySet;
    return this;
  }

  public boolean isGraphNode() {
    return isGraphNode;
  }

  public JCID setGraphNode(boolean graphNode) {
    isGraphNode = graphNode;
    return this;
  }

  public boolean isFileData() {
    return isFileData;
  }

  public JCID setFileData(boolean fileData) {
    isFileData = fileData;
    return this;
  }

  public boolean isReadOnly() {
    return isReadOnly;
  }

  public JCID setReadOnly(boolean readOnly) {
    isReadOnly = readOnly;
    return this;
  }
}
