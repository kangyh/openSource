/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.heepay.rpc.billing.model;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-21")
public class SettleMerchantMessageModel implements org.apache.thrift.TBase<SettleMerchantMessageModel, SettleMerchantMessageModel._Fields>, java.io.Serializable, Cloneable, Comparable<SettleMerchantMessageModel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SettleMerchantMessageModel");

  private static final org.apache.thrift.protocol.TField CLEAR_MERCHANT_MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("clearMerchantMessage", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField SETTLE_MERCHANT_RECORD_FIELD_DESC = new org.apache.thrift.protocol.TField("settleMerchantRecord", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SettleMerchantMessageModelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SettleMerchantMessageModelTupleSchemeFactory());
  }

  public List<ClearDetailMessageModel> clearMerchantMessage; // required
  public SettleMerchantRecordModel settleMerchantRecord; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CLEAR_MERCHANT_MESSAGE((short)1, "clearMerchantMessage"),
    SETTLE_MERCHANT_RECORD((short)2, "settleMerchantRecord");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CLEAR_MERCHANT_MESSAGE
          return CLEAR_MERCHANT_MESSAGE;
        case 2: // SETTLE_MERCHANT_RECORD
          return SETTLE_MERCHANT_RECORD;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLEAR_MERCHANT_MESSAGE, new org.apache.thrift.meta_data.FieldMetaData("clearMerchantMessage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ClearDetailMessageModel.class))));
    tmpMap.put(_Fields.SETTLE_MERCHANT_RECORD, new org.apache.thrift.meta_data.FieldMetaData("settleMerchantRecord", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SettleMerchantRecordModel.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SettleMerchantMessageModel.class, metaDataMap);
  }

  public SettleMerchantMessageModel() {
  }

  public SettleMerchantMessageModel(
    List<ClearDetailMessageModel> clearMerchantMessage,
    SettleMerchantRecordModel settleMerchantRecord)
  {
    this();
    this.clearMerchantMessage = clearMerchantMessage;
    this.settleMerchantRecord = settleMerchantRecord;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SettleMerchantMessageModel(SettleMerchantMessageModel other) {
    if (other.isSetClearMerchantMessage()) {
      List<ClearDetailMessageModel> __this__clearMerchantMessage = new ArrayList<ClearDetailMessageModel>(other.clearMerchantMessage.size());
      for (ClearDetailMessageModel other_element : other.clearMerchantMessage) {
        __this__clearMerchantMessage.add(new ClearDetailMessageModel(other_element));
      }
      this.clearMerchantMessage = __this__clearMerchantMessage;
    }
    if (other.isSetSettleMerchantRecord()) {
      this.settleMerchantRecord = new SettleMerchantRecordModel(other.settleMerchantRecord);
    }
  }

  public SettleMerchantMessageModel deepCopy() {
    return new SettleMerchantMessageModel(this);
  }

  @Override
  public void clear() {
    this.clearMerchantMessage = null;
    this.settleMerchantRecord = null;
  }

  public int getClearMerchantMessageSize() {
    return (this.clearMerchantMessage == null) ? 0 : this.clearMerchantMessage.size();
  }

  public java.util.Iterator<ClearDetailMessageModel> getClearMerchantMessageIterator() {
    return (this.clearMerchantMessage == null) ? null : this.clearMerchantMessage.iterator();
  }

  public void addToClearMerchantMessage(ClearDetailMessageModel elem) {
    if (this.clearMerchantMessage == null) {
      this.clearMerchantMessage = new ArrayList<ClearDetailMessageModel>();
    }
    this.clearMerchantMessage.add(elem);
  }

  public List<ClearDetailMessageModel> getClearMerchantMessage() {
    return this.clearMerchantMessage;
  }

  public SettleMerchantMessageModel setClearMerchantMessage(List<ClearDetailMessageModel> clearMerchantMessage) {
    this.clearMerchantMessage = clearMerchantMessage;
    return this;
  }

  public void unsetClearMerchantMessage() {
    this.clearMerchantMessage = null;
  }

  /** Returns true if field clearMerchantMessage is set (has been assigned a value) and false otherwise */
  public boolean isSetClearMerchantMessage() {
    return this.clearMerchantMessage != null;
  }

  public void setClearMerchantMessageIsSet(boolean value) {
    if (!value) {
      this.clearMerchantMessage = null;
    }
  }

  public SettleMerchantRecordModel getSettleMerchantRecord() {
    return this.settleMerchantRecord;
  }

  public SettleMerchantMessageModel setSettleMerchantRecord(SettleMerchantRecordModel settleMerchantRecord) {
    this.settleMerchantRecord = settleMerchantRecord;
    return this;
  }

  public void unsetSettleMerchantRecord() {
    this.settleMerchantRecord = null;
  }

  /** Returns true if field settleMerchantRecord is set (has been assigned a value) and false otherwise */
  public boolean isSetSettleMerchantRecord() {
    return this.settleMerchantRecord != null;
  }

  public void setSettleMerchantRecordIsSet(boolean value) {
    if (!value) {
      this.settleMerchantRecord = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLEAR_MERCHANT_MESSAGE:
      if (value == null) {
        unsetClearMerchantMessage();
      } else {
        setClearMerchantMessage((List<ClearDetailMessageModel>)value);
      }
      break;

    case SETTLE_MERCHANT_RECORD:
      if (value == null) {
        unsetSettleMerchantRecord();
      } else {
        setSettleMerchantRecord((SettleMerchantRecordModel)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLEAR_MERCHANT_MESSAGE:
      return getClearMerchantMessage();

    case SETTLE_MERCHANT_RECORD:
      return getSettleMerchantRecord();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLEAR_MERCHANT_MESSAGE:
      return isSetClearMerchantMessage();
    case SETTLE_MERCHANT_RECORD:
      return isSetSettleMerchantRecord();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SettleMerchantMessageModel)
      return this.equals((SettleMerchantMessageModel)that);
    return false;
  }

  public boolean equals(SettleMerchantMessageModel that) {
    if (that == null)
      return false;

    boolean this_present_clearMerchantMessage = true && this.isSetClearMerchantMessage();
    boolean that_present_clearMerchantMessage = true && that.isSetClearMerchantMessage();
    if (this_present_clearMerchantMessage || that_present_clearMerchantMessage) {
      if (!(this_present_clearMerchantMessage && that_present_clearMerchantMessage))
        return false;
      if (!this.clearMerchantMessage.equals(that.clearMerchantMessage))
        return false;
    }

    boolean this_present_settleMerchantRecord = true && this.isSetSettleMerchantRecord();
    boolean that_present_settleMerchantRecord = true && that.isSetSettleMerchantRecord();
    if (this_present_settleMerchantRecord || that_present_settleMerchantRecord) {
      if (!(this_present_settleMerchantRecord && that_present_settleMerchantRecord))
        return false;
      if (!this.settleMerchantRecord.equals(that.settleMerchantRecord))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_clearMerchantMessage = true && (isSetClearMerchantMessage());
    list.add(present_clearMerchantMessage);
    if (present_clearMerchantMessage)
      list.add(clearMerchantMessage);

    boolean present_settleMerchantRecord = true && (isSetSettleMerchantRecord());
    list.add(present_settleMerchantRecord);
    if (present_settleMerchantRecord)
      list.add(settleMerchantRecord);

    return list.hashCode();
  }

  @Override
  public int compareTo(SettleMerchantMessageModel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetClearMerchantMessage()).compareTo(other.isSetClearMerchantMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClearMerchantMessage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clearMerchantMessage, other.clearMerchantMessage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSettleMerchantRecord()).compareTo(other.isSetSettleMerchantRecord());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSettleMerchantRecord()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.settleMerchantRecord, other.settleMerchantRecord);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SettleMerchantMessageModel(");
    boolean first = true;

    sb.append("clearMerchantMessage:");
    if (this.clearMerchantMessage == null) {
      sb.append("null");
    } else {
      sb.append(this.clearMerchantMessage);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("settleMerchantRecord:");
    if (this.settleMerchantRecord == null) {
      sb.append("null");
    } else {
      sb.append(this.settleMerchantRecord);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (settleMerchantRecord != null) {
      settleMerchantRecord.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SettleMerchantMessageModelStandardSchemeFactory implements SchemeFactory {
    public SettleMerchantMessageModelStandardScheme getScheme() {
      return new SettleMerchantMessageModelStandardScheme();
    }
  }

  private static class SettleMerchantMessageModelStandardScheme extends StandardScheme<SettleMerchantMessageModel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SettleMerchantMessageModel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLEAR_MERCHANT_MESSAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.clearMerchantMessage = new ArrayList<ClearDetailMessageModel>(_list0.size);
                ClearDetailMessageModel _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ClearDetailMessageModel();
                  _elem1.read(iprot);
                  struct.clearMerchantMessage.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setClearMerchantMessageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SETTLE_MERCHANT_RECORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.settleMerchantRecord = new SettleMerchantRecordModel();
              struct.settleMerchantRecord.read(iprot);
              struct.setSettleMerchantRecordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SettleMerchantMessageModel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.clearMerchantMessage != null) {
        oprot.writeFieldBegin(CLEAR_MERCHANT_MESSAGE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.clearMerchantMessage.size()));
          for (ClearDetailMessageModel _iter3 : struct.clearMerchantMessage)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.settleMerchantRecord != null) {
        oprot.writeFieldBegin(SETTLE_MERCHANT_RECORD_FIELD_DESC);
        struct.settleMerchantRecord.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SettleMerchantMessageModelTupleSchemeFactory implements SchemeFactory {
    public SettleMerchantMessageModelTupleScheme getScheme() {
      return new SettleMerchantMessageModelTupleScheme();
    }
  }

  private static class SettleMerchantMessageModelTupleScheme extends TupleScheme<SettleMerchantMessageModel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SettleMerchantMessageModel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClearMerchantMessage()) {
        optionals.set(0);
      }
      if (struct.isSetSettleMerchantRecord()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetClearMerchantMessage()) {
        {
          oprot.writeI32(struct.clearMerchantMessage.size());
          for (ClearDetailMessageModel _iter4 : struct.clearMerchantMessage)
          {
            _iter4.write(oprot);
          }
        }
      }
      if (struct.isSetSettleMerchantRecord()) {
        struct.settleMerchantRecord.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SettleMerchantMessageModel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.clearMerchantMessage = new ArrayList<ClearDetailMessageModel>(_list5.size);
          ClearDetailMessageModel _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new ClearDetailMessageModel();
            _elem6.read(iprot);
            struct.clearMerchantMessage.add(_elem6);
          }
        }
        struct.setClearMerchantMessageIsSet(true);
      }
      if (incoming.get(1)) {
        struct.settleMerchantRecord = new SettleMerchantRecordModel();
        struct.settleMerchantRecord.read(iprot);
        struct.setSettleMerchantRecordIsSet(true);
      }
    }
  }

}
