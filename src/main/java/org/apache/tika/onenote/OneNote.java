package org.apache.tika.onenote;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneNote {
  List<ExtendedGUID> revisionListOrder = new ArrayList<>();
  Map<ExtendedGUID, Revision> revisionMap = new HashMap<>();
  Map<ExtendedGUID, FileNodePtr> revisionManifestLists = new HashMap<>();
  Map<ExtendedGUID, FileChunkReference> guidToRef = new HashMap<>();
  Map<ExtendedGUID, FileNodePtr> guidToObject = new HashMap<>();

  Multimap<ExtendedGUID, Pair<Long, ExtendedGUID>> revisionRoleMap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
  ExtendedGUID currentRevision = ExtendedGUID.nil();
  List<FileNode> root = new ArrayList<>();
  
  public OneNote() {

  }

  FileChunkReference getAssocGuidToRef(ExtendedGUID guid) {
    FileChunkReference where = guidToRef.get(guid);
    if (where == null) {
      throw new RuntimeException("Failed to find guid " + guid);
    }
    return where;
  }

  void setAssocGuidToRef(ExtendedGUID guid, FileChunkReference ref) {
    guidToRef.put(guid, ref);
  }

  void registerRevisionManifestList(ExtendedGUID guid, FileNodePtr ptr) {
    revisionManifestLists.put(guid, ptr);
    revisionListOrder.add(guid);
  }

  void registerRevisionManifest(FileNode fn) {
    revisionMap.putIfAbsent(fn.gosid, new Revision());
    Revision toModify = revisionMap.get(fn.gosid);
    toModify.gosid = fn.gosid;
    toModify.dependent = fn.subType.revisionManifest.ridDependent;
    currentRevision = fn.gosid;
  }

  public void registerAdditionalRevisionRole(ExtendedGUID gosid, long revisionRole, ExtendedGUID gctxid) {
    revisionRoleMap.put(gosid, Pair.of(revisionRole, gctxid));
  }

  public List<ExtendedGUID> getRevisionListOrder() {
    return revisionListOrder;
  }

  public OneNote setRevisionListOrder(List<ExtendedGUID> revisionListOrder) {
    this.revisionListOrder = revisionListOrder;
    return this;
  }

  public Map<ExtendedGUID, Revision> getRevisionMap() {
    return revisionMap;
  }

  public OneNote setRevisionMap(Map<ExtendedGUID, Revision> revisionMap) {
    this.revisionMap = revisionMap;
    return this;
  }

  public Map<ExtendedGUID, FileNodePtr> getRevisionManifestLists() {
    return revisionManifestLists;
  }

  public OneNote setRevisionManifestLists(Map<ExtendedGUID, FileNodePtr> revisionManifestLists) {
    this.revisionManifestLists = revisionManifestLists;
    return this;
  }

  public Map<ExtendedGUID, FileChunkReference> getGuidToRef() {
    return guidToRef;
  }

  public OneNote setGuidToRef(Map<ExtendedGUID, FileChunkReference> guidToRef) {
    this.guidToRef = guidToRef;
    return this;
  }

  public Map<ExtendedGUID, FileNodePtr> getGuidToObject() {
    return guidToObject;
  }

  public OneNote setGuidToObject(Map<ExtendedGUID, FileNodePtr> guidToObject) {
    this.guidToObject = guidToObject;
    return this;
  }

  public Multimap<ExtendedGUID, Pair<Long, ExtendedGUID>> getRevisionRoleMap() {
    return revisionRoleMap;
  }

  public OneNote setRevisionRoleMap(Multimap<ExtendedGUID, Pair<Long, ExtendedGUID>> revisionRoleMap) {
    this.revisionRoleMap = revisionRoleMap;
    return this;
  }

  public ExtendedGUID getCurrentRevision() {
    return currentRevision;
  }

  public OneNote setCurrentRevision(ExtendedGUID currentRevision) {
    this.currentRevision = currentRevision;
    return this;
  }

  public List<FileNode> getRoot() {
    return root;
  }

  public OneNote setRoot(List<FileNode> root) {
    this.root = root;
    return this;
  }
}
