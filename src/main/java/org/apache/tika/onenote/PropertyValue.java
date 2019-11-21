package org.apache.tika.onenote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PropertyValue {

  private static final Logger LOG = LoggerFactory.getLogger(PropertyValue.class);

  PropertyID propertyID = new PropertyID();
  // union of one of these things based on the type of the corresponding PropertyID
  long scalar; // holds a boolean value if type = 0x2, retrieved from header
  // either ObjectID or ObjectSpaceID or ContextID (single value in array)
  // either ArrayOfObjectIDs or ArrayOfObjectSpaceIDs or ArrayOfContextID
  List<CompactID> compactIDs = new ArrayList<>();
  PropertySet propertySet = new PropertySet(); // or used to house a single value
  FileChunkReference rawData = new FileChunkReference(); // FourBytesOfLengthFollowedByData

  public void print(OneNoteDocument document, OneNotePtr pointer, int indentLevel) throws IOException {
    boolean isRawText = true; //std::string(get_property_id_name(propertyID.id)).find("TextE")!=-1;

    if (isRawText) {
      LOG.debug("{}<{}", Constants.getIndent(indentLevel + 1),
          Properties.nameOf(propertyID.id));
    }
    if (propertyID.type > 0 && propertyID.type <= 6) {
      if (isRawText) {
        LOG.debug("(%d)", scalar);
      }
    } else if (propertyID.type == 7) {
      OneNotePtr content = new OneNotePtr(pointer);
      content.reposition(rawData);
      if (isRawText) {
        LOG.debug(" [");
        content.dumpHex();
        LOG.debug("]");
      }
    } else if (propertyID.type == 0x9 || propertyID.type == 0x8
        || propertyID.type == 0xb || propertyID.type == 0xc
        || propertyID.type == 0xa || propertyID.type == 0xd) {
		String xtype = "contextID";
      if (propertyID.type == 0x8 || propertyID.type == 0x9) {
        xtype = "OIDs";
      }
      if (propertyID.type == 0xa || propertyID.type == 0xb) {
        xtype = "OSIDS";
      }
      if (isRawText) {
        if (!compactIDs.isEmpty()) {
          LOG.debug("\n");
        }
        for (CompactID compactID : compactIDs) {
          LOG.debug("{}{}[{}]\n", Constants.getIndent(indentLevel + 1), xtype, compactID);
          FileNodePtr where = document.guidToObject.get(compactID.guid);
          if (where != null) {
            where.dereference(document).print(document, pointer,indentLevel + 1);
          }
        }
      }
    } else if (propertyID.type == 0x10 || propertyID.type == 0x11) {
      if (isRawText) {
        LOG.debug("SubProperty\n");
      }
      propertySet.print(document, pointer, indentLevel + 1);
    }
    if (isRawText) {
      LOG.debug(">\n");
    }
  }

  public PropertyID getPropertyID() {
    return propertyID;
  }

  public PropertyValue setPropertyID(PropertyID propertyID) {
    this.propertyID = propertyID;
    return this;
  }

  public long getScalar() {
    return scalar;
  }

  public PropertyValue setScalar(long scalar) {
    this.scalar = scalar;
    return this;
  }

  public List<CompactID> getCompactIDs() {
    return compactIDs;
  }

  public PropertyValue setCompactIDs(List<CompactID> compactIDs) {
    this.compactIDs = compactIDs;
    return this;
  }

  public PropertySet getPropertySet() {
    return propertySet;
  }

  public PropertyValue setPropertySet(PropertySet propertySet) {
    this.propertySet = propertySet;
    return this;
  }

  public FileChunkReference getRawData() {
    return rawData;
  }

  public PropertyValue setRawData(FileChunkReference rawData) {
    this.rawData = rawData;
    return this;
  }
}
