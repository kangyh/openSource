/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.heepay.rpc.risk.model;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-12-08")
public class TransactionModel implements org.apache.thrift.TBase<TransactionModel, TransactionModel._Fields>, java.io.Serializable, Cloneable, Comparable<TransactionModel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TransactionModel");

  private static final org.apache.thrift.protocol.TField MERCHANT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MERCHANT_LOGIN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("merchantLoginName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TRANS_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("transType", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SUCCESS_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("successAmount", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TRANS_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("transNo", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField SUCCESS_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("successTime", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField FEE_WAY_FIELD_DESC = new org.apache.thrift.protocol.TField("feeWay", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField FEE_FIELD_DESC = new org.apache.thrift.protocol.TField("fee", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TransactionModelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TransactionModelTupleSchemeFactory());
  }

  public String merchantId; // required
  public String merchantLoginName; // required
  public String transType; // required
  public String successAmount; // required
  public String transNo; // required
  public String successTime; // required
  public String feeWay; // required
  public String fee; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MERCHANT_ID((short)1, "merchantId"),
    MERCHANT_LOGIN_NAME((short)2, "merchantLoginName"),
    TRANS_TYPE((short)3, "transType"),
    SUCCESS_AMOUNT((short)4, "successAmount"),
    TRANS_NO((short)5, "transNo"),
    SUCCESS_TIME((short)6, "successTime"),
    FEE_WAY((short)7, "feeWay"),
    FEE((short)8, "fee");

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
        case 1: // MERCHANT_ID
          return MERCHANT_ID;
        case 2: // MERCHANT_LOGIN_NAME
          return MERCHANT_LOGIN_NAME;
        case 3: // TRANS_TYPE
          return TRANS_TYPE;
        case 4: // SUCCESS_AMOUNT
          return SUCCESS_AMOUNT;
        case 5: // TRANS_NO
          return TRANS_NO;
        case 6: // SUCCESS_TIME
          return SUCCESS_TIME;
        case 7: // FEE_WAY
          return FEE_WAY;
        case 8: // FEE
          return FEE;
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
    tmpMap.put(_Fields.MERCHANT_ID, new org.apache.thrift.meta_data.FieldMetaData("merchantId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERCHANT_LOGIN_NAME, new org.apache.thrift.meta_data.FieldMetaData("merchantLoginName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRANS_TYPE, new org.apache.thrift.meta_data.FieldMetaData("transType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUCCESS_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("successAmount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRANS_NO, new org.apache.thrift.meta_data.FieldMetaData("transNo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUCCESS_TIME, new org.apache.thrift.meta_data.FieldMetaData("successTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FEE_WAY, new org.apache.thrift.meta_data.FieldMetaData("feeWay", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FEE, new org.apache.thrift.meta_data.FieldMetaData("fee", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TransactionModel.class, metaDataMap);
  }

  public TransactionModel() {
  }

  public TransactionModel(
    String merchantId,
    String merchantLoginName,
    String transType,
    String successAmount,
    String transNo,
    String successTime,
    String feeWay,
    String fee)
  {
    this();
    this.merchantId = merchantId;
    this.merchantLoginName = merchantLoginName;
    this.transType = transType;
    this.successAmount = successAmount;
    this.transNo = transNo;
    this.successTime = successTime;
    this.feeWay = feeWay;
    this.fee = fee;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TransactionModel(TransactionModel other) {
    if (other.isSetMerchantId()) {
      this.merchantId = other.merchantId;
    }
    if (other.isSetMerchantLoginName()) {
      this.merchantLoginName = other.merchantLoginName;
    }
    if (other.isSetTransType()) {
      this.transType = other.transType;
    }
    if (other.isSetSuccessAmount()) {
      this.successAmount = other.successAmount;
    }
    if (other.isSetTransNo()) {
      this.transNo = other.transNo;
    }
    if (other.isSetSuccessTime()) {
      this.successTime = other.successTime;
    }
    if (other.isSetFeeWay()) {
      this.feeWay = other.feeWay;
    }
    if (other.isSetFee()) {
      this.fee = other.fee;
    }
  }

  public TransactionModel deepCopy() {
    return new TransactionModel(this);
  }

  @Override
  public void clear() {
    this.merchantId = null;
    this.merchantLoginName = null;
    this.transType = null;
    this.successAmount = null;
    this.transNo = null;
    this.successTime = null;
    this.feeWay = null;
    this.fee = null;
  }

  public String getMerchantId() {
    return this.merchantId;
  }

  public TransactionModel setMerchantId(String merchantId) {
    this.merchantId = merchantId;
    return this;
  }

  public void unsetMerchantId() {
    this.merchantId = null;
  }

  /** Returns true if field merchantId is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantId() {
    return this.merchantId != null;
  }

  public void setMerchantIdIsSet(boolean value) {
    if (!value) {
      this.merchantId = null;
    }
  }

  public String getMerchantLoginName() {
    return this.merchantLoginName;
  }

  public TransactionModel setMerchantLoginName(String merchantLoginName) {
    this.merchantLoginName = merchantLoginName;
    return this;
  }

  public void unsetMerchantLoginName() {
    this.merchantLoginName = null;
  }

  /** Returns true if field merchantLoginName is set (has been assigned a value) and false otherwise */
  public boolean isSetMerchantLoginName() {
    return this.merchantLoginName != null;
  }

  public void setMerchantLoginNameIsSet(boolean value) {
    if (!value) {
      this.merchantLoginName = null;
    }
  }

  public String getTransType() {
    return this.transType;
  }

  public TransactionModel setTransType(String transType) {
    this.transType = transType;
    return this;
  }

  public void unsetTransType() {
    this.transType = null;
  }

  /** Returns true if field transType is set (has been assigned a value) and false otherwise */
  public boolean isSetTransType() {
    return this.transType != null;
  }

  public void setTransTypeIsSet(boolean value) {
    if (!value) {
      this.transType = null;
    }
  }

  public String getSuccessAmount() {
    return this.successAmount;
  }

  public TransactionModel setSuccessAmount(String successAmount) {
    this.successAmount = successAmount;
    return this;
  }

  public void unsetSuccessAmount() {
    this.successAmount = null;
  }

  /** Returns true if field successAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccessAmount() {
    return this.successAmount != null;
  }

  public void setSuccessAmountIsSet(boolean value) {
    if (!value) {
      this.successAmount = null;
    }
  }

  public String getTransNo() {
    return this.transNo;
  }

  public TransactionModel setTransNo(String transNo) {
    this.transNo = transNo;
    return this;
  }

  public void unsetTransNo() {
    this.transNo = null;
  }

  /** Returns true if field transNo is set (has been assigned a value) and false otherwise */
  public boolean isSetTransNo() {
    return this.transNo != null;
  }

  public void setTransNoIsSet(boolean value) {
    if (!value) {
      this.transNo = null;
    }
  }

  public String getSuccessTime() {
    return this.successTime;
  }

  public TransactionModel setSuccessTime(String successTime) {
    this.successTime = successTime;
    return this;
  }

  public void unsetSuccessTime() {
    this.successTime = null;
  }

  /** Returns true if field successTime is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccessTime() {
    return this.successTime != null;
  }

  public void setSuccessTimeIsSet(boolean value) {
    if (!value) {
      this.successTime = null;
    }
  }

  public String getFeeWay() {
    return this.feeWay;
  }

  public TransactionModel setFeeWay(String feeWay) {
    this.feeWay = feeWay;
    return this;
  }

  public void unsetFeeWay() {
    this.feeWay = null;
  }

  /** Returns true if field feeWay is set (has been assigned a value) and false otherwise */
  public boolean isSetFeeWay() {
    return this.feeWay != null;
  }

  public void setFeeWayIsSet(boolean value) {
    if (!value) {
      this.feeWay = null;
    }
  }

  public String getFee() {
    return this.fee;
  }

  public TransactionModel setFee(String fee) {
    this.fee = fee;
    return this;
  }

  public void unsetFee() {
    this.fee = null;
  }

  /** Returns true if field fee is set (has been assigned a value) and false otherwise */
  public boolean isSetFee() {
    return this.fee != null;
  }

  public void setFeeIsSet(boolean value) {
    if (!value) {
      this.fee = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MERCHANT_ID:
      if (value == null) {
        unsetMerchantId();
      } else {
        setMerchantId((String)value);
      }
      break;

    case MERCHANT_LOGIN_NAME:
      if (value == null) {
        unsetMerchantLoginName();
      } else {
        setMerchantLoginName((String)value);
      }
      break;

    case TRANS_TYPE:
      if (value == null) {
        unsetTransType();
      } else {
        setTransType((String)value);
      }
      break;

    case SUCCESS_AMOUNT:
      if (value == null) {
        unsetSuccessAmount();
      } else {
        setSuccessAmount((String)value);
      }
      break;

    case TRANS_NO:
      if (value == null) {
        unsetTransNo();
      } else {
        setTransNo((String)value);
      }
      break;

    case SUCCESS_TIME:
      if (value == null) {
        unsetSuccessTime();
      } else {
        setSuccessTime((String)value);
      }
      break;

    case FEE_WAY:
      if (value == null) {
        unsetFeeWay();
      } else {
        setFeeWay((String)value);
      }
      break;

    case FEE:
      if (value == null) {
        unsetFee();
      } else {
        setFee((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MERCHANT_ID:
      return getMerchantId();

    case MERCHANT_LOGIN_NAME:
      return getMerchantLoginName();

    case TRANS_TYPE:
      return getTransType();

    case SUCCESS_AMOUNT:
      return getSuccessAmount();

    case TRANS_NO:
      return getTransNo();

    case SUCCESS_TIME:
      return getSuccessTime();

    case FEE_WAY:
      return getFeeWay();

    case FEE:
      return getFee();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MERCHANT_ID:
      return isSetMerchantId();
    case MERCHANT_LOGIN_NAME:
      return isSetMerchantLoginName();
    case TRANS_TYPE:
      return isSetTransType();
    case SUCCESS_AMOUNT:
      return isSetSuccessAmount();
    case TRANS_NO:
      return isSetTransNo();
    case SUCCESS_TIME:
      return isSetSuccessTime();
    case FEE_WAY:
      return isSetFeeWay();
    case FEE:
      return isSetFee();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TransactionModel)
      return this.equals((TransactionModel)that);
    return false;
  }

  public boolean equals(TransactionModel that) {
    if (that == null)
      return false;

    boolean this_present_merchantId = true && this.isSetMerchantId();
    boolean that_present_merchantId = true && that.isSetMerchantId();
    if (this_present_merchantId || that_present_merchantId) {
      if (!(this_present_merchantId && that_present_merchantId))
        return false;
      if (!this.merchantId.equals(that.merchantId))
        return false;
    }

    boolean this_present_merchantLoginName = true && this.isSetMerchantLoginName();
    boolean that_present_merchantLoginName = true && that.isSetMerchantLoginName();
    if (this_present_merchantLoginName || that_present_merchantLoginName) {
      if (!(this_present_merchantLoginName && that_present_merchantLoginName))
        return false;
      if (!this.merchantLoginName.equals(that.merchantLoginName))
        return false;
    }

    boolean this_present_transType = true && this.isSetTransType();
    boolean that_present_transType = true && that.isSetTransType();
    if (this_present_transType || that_present_transType) {
      if (!(this_present_transType && that_present_transType))
        return false;
      if (!this.transType.equals(that.transType))
        return false;
    }

    boolean this_present_successAmount = true && this.isSetSuccessAmount();
    boolean that_present_successAmount = true && that.isSetSuccessAmount();
    if (this_present_successAmount || that_present_successAmount) {
      if (!(this_present_successAmount && that_present_successAmount))
        return false;
      if (!this.successAmount.equals(that.successAmount))
        return false;
    }

    boolean this_present_transNo = true && this.isSetTransNo();
    boolean that_present_transNo = true && that.isSetTransNo();
    if (this_present_transNo || that_present_transNo) {
      if (!(this_present_transNo && that_present_transNo))
        return false;
      if (!this.transNo.equals(that.transNo))
        return false;
    }

    boolean this_present_successTime = true && this.isSetSuccessTime();
    boolean that_present_successTime = true && that.isSetSuccessTime();
    if (this_present_successTime || that_present_successTime) {
      if (!(this_present_successTime && that_present_successTime))
        return false;
      if (!this.successTime.equals(that.successTime))
        return false;
    }

    boolean this_present_feeWay = true && this.isSetFeeWay();
    boolean that_present_feeWay = true && that.isSetFeeWay();
    if (this_present_feeWay || that_present_feeWay) {
      if (!(this_present_feeWay && that_present_feeWay))
        return false;
      if (!this.feeWay.equals(that.feeWay))
        return false;
    }

    boolean this_present_fee = true && this.isSetFee();
    boolean that_present_fee = true && that.isSetFee();
    if (this_present_fee || that_present_fee) {
      if (!(this_present_fee && that_present_fee))
        return false;
      if (!this.fee.equals(that.fee))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_merchantId = true && (isSetMerchantId());
    list.add(present_merchantId);
    if (present_merchantId)
      list.add(merchantId);

    boolean present_merchantLoginName = true && (isSetMerchantLoginName());
    list.add(present_merchantLoginName);
    if (present_merchantLoginName)
      list.add(merchantLoginName);

    boolean present_transType = true && (isSetTransType());
    list.add(present_transType);
    if (present_transType)
      list.add(transType);

    boolean present_successAmount = true && (isSetSuccessAmount());
    list.add(present_successAmount);
    if (present_successAmount)
      list.add(successAmount);

    boolean present_transNo = true && (isSetTransNo());
    list.add(present_transNo);
    if (present_transNo)
      list.add(transNo);

    boolean present_successTime = true && (isSetSuccessTime());
    list.add(present_successTime);
    if (present_successTime)
      list.add(successTime);

    boolean present_feeWay = true && (isSetFeeWay());
    list.add(present_feeWay);
    if (present_feeWay)
      list.add(feeWay);

    boolean present_fee = true && (isSetFee());
    list.add(present_fee);
    if (present_fee)
      list.add(fee);

    return list.hashCode();
  }

  @Override
  public int compareTo(TransactionModel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMerchantId()).compareTo(other.isSetMerchantId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantId, other.merchantId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMerchantLoginName()).compareTo(other.isSetMerchantLoginName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMerchantLoginName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.merchantLoginName, other.merchantLoginName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTransType()).compareTo(other.isSetTransType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTransType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.transType, other.transType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuccessAmount()).compareTo(other.isSetSuccessAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccessAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.successAmount, other.successAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTransNo()).compareTo(other.isSetTransNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTransNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.transNo, other.transNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuccessTime()).compareTo(other.isSetSuccessTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccessTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.successTime, other.successTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFeeWay()).compareTo(other.isSetFeeWay());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFeeWay()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.feeWay, other.feeWay);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFee()).compareTo(other.isSetFee());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFee()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fee, other.fee);
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
    StringBuilder sb = new StringBuilder("TransactionModel(");
    boolean first = true;

    sb.append("merchantId:");
    if (this.merchantId == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("merchantLoginName:");
    if (this.merchantLoginName == null) {
      sb.append("null");
    } else {
      sb.append(this.merchantLoginName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("transType:");
    if (this.transType == null) {
      sb.append("null");
    } else {
      sb.append(this.transType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("successAmount:");
    if (this.successAmount == null) {
      sb.append("null");
    } else {
      sb.append(this.successAmount);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("transNo:");
    if (this.transNo == null) {
      sb.append("null");
    } else {
      sb.append(this.transNo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("successTime:");
    if (this.successTime == null) {
      sb.append("null");
    } else {
      sb.append(this.successTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("feeWay:");
    if (this.feeWay == null) {
      sb.append("null");
    } else {
      sb.append(this.feeWay);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fee:");
    if (this.fee == null) {
      sb.append("null");
    } else {
      sb.append(this.fee);
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

  private static class TransactionModelStandardSchemeFactory implements SchemeFactory {
    public TransactionModelStandardScheme getScheme() {
      return new TransactionModelStandardScheme();
    }
  }

  private static class TransactionModelStandardScheme extends StandardScheme<TransactionModel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TransactionModel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MERCHANT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantId = iprot.readString();
              struct.setMerchantIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MERCHANT_LOGIN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.merchantLoginName = iprot.readString();
              struct.setMerchantLoginNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRANS_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.transType = iprot.readString();
              struct.setTransTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SUCCESS_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.successAmount = iprot.readString();
              struct.setSuccessAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TRANS_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.transNo = iprot.readString();
              struct.setTransNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SUCCESS_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.successTime = iprot.readString();
              struct.setSuccessTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // FEE_WAY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.feeWay = iprot.readString();
              struct.setFeeWayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // FEE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fee = iprot.readString();
              struct.setFeeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TransactionModel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.merchantId != null) {
        oprot.writeFieldBegin(MERCHANT_ID_FIELD_DESC);
        oprot.writeString(struct.merchantId);
        oprot.writeFieldEnd();
      }
      if (struct.merchantLoginName != null) {
        oprot.writeFieldBegin(MERCHANT_LOGIN_NAME_FIELD_DESC);
        oprot.writeString(struct.merchantLoginName);
        oprot.writeFieldEnd();
      }
      if (struct.transType != null) {
        oprot.writeFieldBegin(TRANS_TYPE_FIELD_DESC);
        oprot.writeString(struct.transType);
        oprot.writeFieldEnd();
      }
      if (struct.successAmount != null) {
        oprot.writeFieldBegin(SUCCESS_AMOUNT_FIELD_DESC);
        oprot.writeString(struct.successAmount);
        oprot.writeFieldEnd();
      }
      if (struct.transNo != null) {
        oprot.writeFieldBegin(TRANS_NO_FIELD_DESC);
        oprot.writeString(struct.transNo);
        oprot.writeFieldEnd();
      }
      if (struct.successTime != null) {
        oprot.writeFieldBegin(SUCCESS_TIME_FIELD_DESC);
        oprot.writeString(struct.successTime);
        oprot.writeFieldEnd();
      }
      if (struct.feeWay != null) {
        oprot.writeFieldBegin(FEE_WAY_FIELD_DESC);
        oprot.writeString(struct.feeWay);
        oprot.writeFieldEnd();
      }
      if (struct.fee != null) {
        oprot.writeFieldBegin(FEE_FIELD_DESC);
        oprot.writeString(struct.fee);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TransactionModelTupleSchemeFactory implements SchemeFactory {
    public TransactionModelTupleScheme getScheme() {
      return new TransactionModelTupleScheme();
    }
  }

  private static class TransactionModelTupleScheme extends TupleScheme<TransactionModel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TransactionModel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMerchantId()) {
        optionals.set(0);
      }
      if (struct.isSetMerchantLoginName()) {
        optionals.set(1);
      }
      if (struct.isSetTransType()) {
        optionals.set(2);
      }
      if (struct.isSetSuccessAmount()) {
        optionals.set(3);
      }
      if (struct.isSetTransNo()) {
        optionals.set(4);
      }
      if (struct.isSetSuccessTime()) {
        optionals.set(5);
      }
      if (struct.isSetFeeWay()) {
        optionals.set(6);
      }
      if (struct.isSetFee()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetMerchantId()) {
        oprot.writeString(struct.merchantId);
      }
      if (struct.isSetMerchantLoginName()) {
        oprot.writeString(struct.merchantLoginName);
      }
      if (struct.isSetTransType()) {
        oprot.writeString(struct.transType);
      }
      if (struct.isSetSuccessAmount()) {
        oprot.writeString(struct.successAmount);
      }
      if (struct.isSetTransNo()) {
        oprot.writeString(struct.transNo);
      }
      if (struct.isSetSuccessTime()) {
        oprot.writeString(struct.successTime);
      }
      if (struct.isSetFeeWay()) {
        oprot.writeString(struct.feeWay);
      }
      if (struct.isSetFee()) {
        oprot.writeString(struct.fee);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TransactionModel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.merchantId = iprot.readString();
        struct.setMerchantIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.merchantLoginName = iprot.readString();
        struct.setMerchantLoginNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.transType = iprot.readString();
        struct.setTransTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.successAmount = iprot.readString();
        struct.setSuccessAmountIsSet(true);
      }
      if (incoming.get(4)) {
        struct.transNo = iprot.readString();
        struct.setTransNoIsSet(true);
      }
      if (incoming.get(5)) {
        struct.successTime = iprot.readString();
        struct.setSuccessTimeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.feeWay = iprot.readString();
        struct.setFeeWayIsSet(true);
      }
      if (incoming.get(7)) {
        struct.fee = iprot.readString();
        struct.setFeeIsSet(true);
      }
    }
  }

}

