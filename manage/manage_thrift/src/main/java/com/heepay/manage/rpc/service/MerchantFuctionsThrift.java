/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.heepay.manage.rpc.service;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-06")
public class MerchantFuctionsThrift implements org.apache.thrift.TBase<MerchantFuctionsThrift, MerchantFuctionsThrift._Fields>, java.io.Serializable, Cloneable, Comparable<MerchantFuctionsThrift> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MerchantFuctionsThrift");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FUNCTION_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("functionCode", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FUNCTION_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("functionName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FUNCTION_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("functionUrl", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField FUNCTION_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("functionStatus", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField PARENT_FUNCTION_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("parentFunctionCode", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MerchantFuctionsThriftStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MerchantFuctionsThriftTupleSchemeFactory());
  }

  public String id; // required
  public String functionCode; // required
  public String functionName; // required
  public String functionUrl; // required
  public String functionStatus; // required
  public String parentFunctionCode; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    FUNCTION_CODE((short)2, "functionCode"),
    FUNCTION_NAME((short)3, "functionName"),
    FUNCTION_URL((short)4, "functionUrl"),
    FUNCTION_STATUS((short)5, "functionStatus"),
    PARENT_FUNCTION_CODE((short)6, "parentFunctionCode");

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
        case 1: // ID
          return ID;
        case 2: // FUNCTION_CODE
          return FUNCTION_CODE;
        case 3: // FUNCTION_NAME
          return FUNCTION_NAME;
        case 4: // FUNCTION_URL
          return FUNCTION_URL;
        case 5: // FUNCTION_STATUS
          return FUNCTION_STATUS;
        case 6: // PARENT_FUNCTION_CODE
          return PARENT_FUNCTION_CODE;
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
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FUNCTION_CODE, new org.apache.thrift.meta_data.FieldMetaData("functionCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FUNCTION_NAME, new org.apache.thrift.meta_data.FieldMetaData("functionName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FUNCTION_URL, new org.apache.thrift.meta_data.FieldMetaData("functionUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FUNCTION_STATUS, new org.apache.thrift.meta_data.FieldMetaData("functionStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARENT_FUNCTION_CODE, new org.apache.thrift.meta_data.FieldMetaData("parentFunctionCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MerchantFuctionsThrift.class, metaDataMap);
  }

  public MerchantFuctionsThrift() {
  }

  public MerchantFuctionsThrift(
    String id,
    String functionCode,
    String functionName,
    String functionUrl,
    String functionStatus,
    String parentFunctionCode)
  {
    this();
    this.id = id;
    this.functionCode = functionCode;
    this.functionName = functionName;
    this.functionUrl = functionUrl;
    this.functionStatus = functionStatus;
    this.parentFunctionCode = parentFunctionCode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MerchantFuctionsThrift(MerchantFuctionsThrift other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetFunctionCode()) {
      this.functionCode = other.functionCode;
    }
    if (other.isSetFunctionName()) {
      this.functionName = other.functionName;
    }
    if (other.isSetFunctionUrl()) {
      this.functionUrl = other.functionUrl;
    }
    if (other.isSetFunctionStatus()) {
      this.functionStatus = other.functionStatus;
    }
    if (other.isSetParentFunctionCode()) {
      this.parentFunctionCode = other.parentFunctionCode;
    }
  }

  public MerchantFuctionsThrift deepCopy() {
    return new MerchantFuctionsThrift(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.functionCode = null;
    this.functionName = null;
    this.functionUrl = null;
    this.functionStatus = null;
    this.parentFunctionCode = null;
  }

  public String getId() {
    return this.id;
  }

  public MerchantFuctionsThrift setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public String getFunctionCode() {
    return this.functionCode;
  }

  public MerchantFuctionsThrift setFunctionCode(String functionCode) {
    this.functionCode = functionCode;
    return this;
  }

  public void unsetFunctionCode() {
    this.functionCode = null;
  }

  /** Returns true if field functionCode is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctionCode() {
    return this.functionCode != null;
  }

  public void setFunctionCodeIsSet(boolean value) {
    if (!value) {
      this.functionCode = null;
    }
  }

  public String getFunctionName() {
    return this.functionName;
  }

  public MerchantFuctionsThrift setFunctionName(String functionName) {
    this.functionName = functionName;
    return this;
  }

  public void unsetFunctionName() {
    this.functionName = null;
  }

  /** Returns true if field functionName is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctionName() {
    return this.functionName != null;
  }

  public void setFunctionNameIsSet(boolean value) {
    if (!value) {
      this.functionName = null;
    }
  }

  public String getFunctionUrl() {
    return this.functionUrl;
  }

  public MerchantFuctionsThrift setFunctionUrl(String functionUrl) {
    this.functionUrl = functionUrl;
    return this;
  }

  public void unsetFunctionUrl() {
    this.functionUrl = null;
  }

  /** Returns true if field functionUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctionUrl() {
    return this.functionUrl != null;
  }

  public void setFunctionUrlIsSet(boolean value) {
    if (!value) {
      this.functionUrl = null;
    }
  }

  public String getFunctionStatus() {
    return this.functionStatus;
  }

  public MerchantFuctionsThrift setFunctionStatus(String functionStatus) {
    this.functionStatus = functionStatus;
    return this;
  }

  public void unsetFunctionStatus() {
    this.functionStatus = null;
  }

  /** Returns true if field functionStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctionStatus() {
    return this.functionStatus != null;
  }

  public void setFunctionStatusIsSet(boolean value) {
    if (!value) {
      this.functionStatus = null;
    }
  }

  public String getParentFunctionCode() {
    return this.parentFunctionCode;
  }

  public MerchantFuctionsThrift setParentFunctionCode(String parentFunctionCode) {
    this.parentFunctionCode = parentFunctionCode;
    return this;
  }

  public void unsetParentFunctionCode() {
    this.parentFunctionCode = null;
  }

  /** Returns true if field parentFunctionCode is set (has been assigned a value) and false otherwise */
  public boolean isSetParentFunctionCode() {
    return this.parentFunctionCode != null;
  }

  public void setParentFunctionCodeIsSet(boolean value) {
    if (!value) {
      this.parentFunctionCode = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case FUNCTION_CODE:
      if (value == null) {
        unsetFunctionCode();
      } else {
        setFunctionCode((String)value);
      }
      break;

    case FUNCTION_NAME:
      if (value == null) {
        unsetFunctionName();
      } else {
        setFunctionName((String)value);
      }
      break;

    case FUNCTION_URL:
      if (value == null) {
        unsetFunctionUrl();
      } else {
        setFunctionUrl((String)value);
      }
      break;

    case FUNCTION_STATUS:
      if (value == null) {
        unsetFunctionStatus();
      } else {
        setFunctionStatus((String)value);
      }
      break;

    case PARENT_FUNCTION_CODE:
      if (value == null) {
        unsetParentFunctionCode();
      } else {
        setParentFunctionCode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case FUNCTION_CODE:
      return getFunctionCode();

    case FUNCTION_NAME:
      return getFunctionName();

    case FUNCTION_URL:
      return getFunctionUrl();

    case FUNCTION_STATUS:
      return getFunctionStatus();

    case PARENT_FUNCTION_CODE:
      return getParentFunctionCode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case FUNCTION_CODE:
      return isSetFunctionCode();
    case FUNCTION_NAME:
      return isSetFunctionName();
    case FUNCTION_URL:
      return isSetFunctionUrl();
    case FUNCTION_STATUS:
      return isSetFunctionStatus();
    case PARENT_FUNCTION_CODE:
      return isSetParentFunctionCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MerchantFuctionsThrift)
      return this.equals((MerchantFuctionsThrift)that);
    return false;
  }

  public boolean equals(MerchantFuctionsThrift that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_functionCode = true && this.isSetFunctionCode();
    boolean that_present_functionCode = true && that.isSetFunctionCode();
    if (this_present_functionCode || that_present_functionCode) {
      if (!(this_present_functionCode && that_present_functionCode))
        return false;
      if (!this.functionCode.equals(that.functionCode))
        return false;
    }

    boolean this_present_functionName = true && this.isSetFunctionName();
    boolean that_present_functionName = true && that.isSetFunctionName();
    if (this_present_functionName || that_present_functionName) {
      if (!(this_present_functionName && that_present_functionName))
        return false;
      if (!this.functionName.equals(that.functionName))
        return false;
    }

    boolean this_present_functionUrl = true && this.isSetFunctionUrl();
    boolean that_present_functionUrl = true && that.isSetFunctionUrl();
    if (this_present_functionUrl || that_present_functionUrl) {
      if (!(this_present_functionUrl && that_present_functionUrl))
        return false;
      if (!this.functionUrl.equals(that.functionUrl))
        return false;
    }

    boolean this_present_functionStatus = true && this.isSetFunctionStatus();
    boolean that_present_functionStatus = true && that.isSetFunctionStatus();
    if (this_present_functionStatus || that_present_functionStatus) {
      if (!(this_present_functionStatus && that_present_functionStatus))
        return false;
      if (!this.functionStatus.equals(that.functionStatus))
        return false;
    }

    boolean this_present_parentFunctionCode = true && this.isSetParentFunctionCode();
    boolean that_present_parentFunctionCode = true && that.isSetParentFunctionCode();
    if (this_present_parentFunctionCode || that_present_parentFunctionCode) {
      if (!(this_present_parentFunctionCode && that_present_parentFunctionCode))
        return false;
      if (!this.parentFunctionCode.equals(that.parentFunctionCode))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_functionCode = true && (isSetFunctionCode());
    list.add(present_functionCode);
    if (present_functionCode)
      list.add(functionCode);

    boolean present_functionName = true && (isSetFunctionName());
    list.add(present_functionName);
    if (present_functionName)
      list.add(functionName);

    boolean present_functionUrl = true && (isSetFunctionUrl());
    list.add(present_functionUrl);
    if (present_functionUrl)
      list.add(functionUrl);

    boolean present_functionStatus = true && (isSetFunctionStatus());
    list.add(present_functionStatus);
    if (present_functionStatus)
      list.add(functionStatus);

    boolean present_parentFunctionCode = true && (isSetParentFunctionCode());
    list.add(present_parentFunctionCode);
    if (present_parentFunctionCode)
      list.add(parentFunctionCode);

    return list.hashCode();
  }

  @Override
  public int compareTo(MerchantFuctionsThrift other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFunctionCode()).compareTo(other.isSetFunctionCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctionCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functionCode, other.functionCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFunctionName()).compareTo(other.isSetFunctionName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctionName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functionName, other.functionName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFunctionUrl()).compareTo(other.isSetFunctionUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctionUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functionUrl, other.functionUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFunctionStatus()).compareTo(other.isSetFunctionStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctionStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functionStatus, other.functionStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParentFunctionCode()).compareTo(other.isSetParentFunctionCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParentFunctionCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parentFunctionCode, other.parentFunctionCode);
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
    StringBuilder sb = new StringBuilder("MerchantFuctionsThrift(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("functionCode:");
    if (this.functionCode == null) {
      sb.append("null");
    } else {
      sb.append(this.functionCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("functionName:");
    if (this.functionName == null) {
      sb.append("null");
    } else {
      sb.append(this.functionName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("functionUrl:");
    if (this.functionUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.functionUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("functionStatus:");
    if (this.functionStatus == null) {
      sb.append("null");
    } else {
      sb.append(this.functionStatus);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("parentFunctionCode:");
    if (this.parentFunctionCode == null) {
      sb.append("null");
    } else {
      sb.append(this.parentFunctionCode);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class MerchantFuctionsThriftStandardSchemeFactory implements SchemeFactory {
    public MerchantFuctionsThriftStandardScheme getScheme() {
      return new MerchantFuctionsThriftStandardScheme();
    }
  }

  private static class MerchantFuctionsThriftStandardScheme extends StandardScheme<MerchantFuctionsThrift> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MerchantFuctionsThrift struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FUNCTION_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.functionCode = iprot.readString();
              struct.setFunctionCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FUNCTION_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.functionName = iprot.readString();
              struct.setFunctionNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FUNCTION_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.functionUrl = iprot.readString();
              struct.setFunctionUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FUNCTION_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.functionStatus = iprot.readString();
              struct.setFunctionStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PARENT_FUNCTION_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.parentFunctionCode = iprot.readString();
              struct.setParentFunctionCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MerchantFuctionsThrift struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.functionCode != null) {
        oprot.writeFieldBegin(FUNCTION_CODE_FIELD_DESC);
        oprot.writeString(struct.functionCode);
        oprot.writeFieldEnd();
      }
      if (struct.functionName != null) {
        oprot.writeFieldBegin(FUNCTION_NAME_FIELD_DESC);
        oprot.writeString(struct.functionName);
        oprot.writeFieldEnd();
      }
      if (struct.functionUrl != null) {
        oprot.writeFieldBegin(FUNCTION_URL_FIELD_DESC);
        oprot.writeString(struct.functionUrl);
        oprot.writeFieldEnd();
      }
      if (struct.functionStatus != null) {
        oprot.writeFieldBegin(FUNCTION_STATUS_FIELD_DESC);
        oprot.writeString(struct.functionStatus);
        oprot.writeFieldEnd();
      }
      if (struct.parentFunctionCode != null) {
        oprot.writeFieldBegin(PARENT_FUNCTION_CODE_FIELD_DESC);
        oprot.writeString(struct.parentFunctionCode);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MerchantFuctionsThriftTupleSchemeFactory implements SchemeFactory {
    public MerchantFuctionsThriftTupleScheme getScheme() {
      return new MerchantFuctionsThriftTupleScheme();
    }
  }

  private static class MerchantFuctionsThriftTupleScheme extends TupleScheme<MerchantFuctionsThrift> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MerchantFuctionsThrift struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetFunctionCode()) {
        optionals.set(1);
      }
      if (struct.isSetFunctionName()) {
        optionals.set(2);
      }
      if (struct.isSetFunctionUrl()) {
        optionals.set(3);
      }
      if (struct.isSetFunctionStatus()) {
        optionals.set(4);
      }
      if (struct.isSetParentFunctionCode()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetFunctionCode()) {
        oprot.writeString(struct.functionCode);
      }
      if (struct.isSetFunctionName()) {
        oprot.writeString(struct.functionName);
      }
      if (struct.isSetFunctionUrl()) {
        oprot.writeString(struct.functionUrl);
      }
      if (struct.isSetFunctionStatus()) {
        oprot.writeString(struct.functionStatus);
      }
      if (struct.isSetParentFunctionCode()) {
        oprot.writeString(struct.parentFunctionCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MerchantFuctionsThrift struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.functionCode = iprot.readString();
        struct.setFunctionCodeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.functionName = iprot.readString();
        struct.setFunctionNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.functionUrl = iprot.readString();
        struct.setFunctionUrlIsSet(true);
      }
      if (incoming.get(4)) {
        struct.functionStatus = iprot.readString();
        struct.setFunctionStatusIsSet(true);
      }
      if (incoming.get(5)) {
        struct.parentFunctionCode = iprot.readString();
        struct.setParentFunctionCodeIsSet(true);
      }
    }
  }

}
